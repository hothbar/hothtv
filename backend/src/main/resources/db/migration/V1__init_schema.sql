-- =========================
-- V1__init_schema.sql
-- =========================
-- DEV CONVENIENCE:
-- Flyway runs migrations ONCE. These DROP statements are useful for local resets
-- when you manually run flyway clean/migrate (or recreate your DB).
-- In production, you usually do NOT drop tables in migrations.

-- Drop triggers / function first (safe if they don't exist)
DROP TRIGGER IF EXISTS trg_single_watchable_kind ON single_watchable;
DROP TRIGGER IF EXISTS trg_episode_watchable_kind ON episode_watchable;
DROP FUNCTION IF EXISTS enforce_watchable_subtype();

-- Drop tables in dependency order
DROP TABLE IF EXISTS watch_history CASCADE;

DROP TABLE IF EXISTS subscription CASCADE;
DROP TABLE IF EXISTS subscription_plan CASCADE;

DROP TABLE IF EXISTS episode_watchable CASCADE;
DROP TABLE IF EXISTS single_watchable CASCADE;
DROP TABLE IF EXISTS watchable CASCADE;

DROP TABLE IF EXISTS title_director CASCADE;
DROP TABLE IF EXISTS title_cast CASCADE;
DROP TABLE IF EXISTS title_category CASCADE;

DROP TABLE IF EXISTS episode CASCADE;
DROP TABLE IF EXISTS season CASCADE;

DROP TABLE IF EXISTS single_title CASCADE;
DROP TABLE IF EXISTS title CASCADE;

DROP TABLE IF EXISTS person CASCADE;
DROP TABLE IF EXISTS category CASCADE;

DROP TABLE IF EXISTS users CASCADE;

-- =========================
-- Core tables
-- =========================

CREATE TABLE users (
                       user_id        BIGSERIAL PRIMARY KEY,
                       first_name     VARCHAR(100) NOT NULL,
                       last_name      VARCHAR(100) NOT NULL,
                       email          VARCHAR(255) NOT NULL UNIQUE,
                       password_hash  VARCHAR(255) NOT NULL,
                       joined_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                       CONSTRAINT chk_users_email_format
                           CHECK (email ~* '^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$')
);

CREATE TABLE title (
                       title_id      BIGSERIAL PRIMARY KEY,
                       type          VARCHAR(20) NOT NULL, -- SINGLE or SERIES
                       name          VARCHAR(255) NOT NULL,
                       release_date  DATE,
                       created_at    TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                       description   TEXT,
                       CONSTRAINT chk_title_type CHECK (type IN ('SINGLE', 'SERIES'))
);

-- SingleTitle is a subtype of Title
CREATE TABLE single_title (
                              title_id          BIGINT PRIMARY KEY REFERENCES title(title_id) ON DELETE CASCADE,
                              duration_minutes  INT NOT NULL,
                              CONSTRAINT chk_single_title_duration CHECK (duration_minutes > 0)
);

CREATE OR REPLACE FUNCTION enforce_single_title_type()
RETURNS TRIGGER AS $$
BEGIN
  IF (SELECT type FROM title WHERE title_id = NEW.title_id) <> 'SINGLE' THEN
    RAISE EXCEPTION 'Title % must have type SINGLE to be in single_title', NEW.title_id;
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_single_title_type
    BEFORE INSERT OR UPDATE ON single_title
    FOR EACH ROW EXECUTE FUNCTION enforce_single_title_type();


CREATE TABLE season (
                        season_id      BIGSERIAL PRIMARY KEY,
                        title_id       BIGINT NOT NULL REFERENCES title(title_id) ON DELETE CASCADE,
                        season_number  INT NOT NULL,
                        CONSTRAINT chk_season_number CHECK (season_number > 0),
                        CONSTRAINT uq_season_title_number UNIQUE (title_id, season_number)
);

CREATE TABLE episode (
                         episode_id        BIGSERIAL PRIMARY KEY,
                         season_id         BIGINT NOT NULL REFERENCES season(season_id) ON DELETE CASCADE,
                         episode_number    INT NOT NULL,
                         name              VARCHAR(255) NOT NULL,
                         duration_minutes  INT NOT NULL,
                         CONSTRAINT chk_episode_number CHECK (episode_number > 0),
                         CONSTRAINT chk_episode_duration CHECK (duration_minutes > 0),
                         CONSTRAINT uq_episode_season_number UNIQUE (season_id, episode_number)
);

CREATE TABLE category (
                          category_id   BIGSERIAL PRIMARY KEY,
                          name          VARCHAR(100) NOT NULL,
                          slug          VARCHAR(120) NOT NULL UNIQUE,
                          description   TEXT,
                          is_active     BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE person (
                        person_id      BIGSERIAL PRIMARY KEY,
                        first_name     VARCHAR(100) NOT NULL,
                        last_name      VARCHAR(100) NOT NULL,
                        date_of_birth  DATE,
                        date_of_death  DATE,
                        CONSTRAINT chk_person_dates
                            CHECK (date_of_death IS NULL OR date_of_birth IS NULL OR date_of_death >= date_of_birth)
);

-- =========================
-- Junction tables
-- =========================

CREATE TABLE title_category (
                                title_id     BIGINT NOT NULL REFERENCES title(title_id) ON DELETE CASCADE,
                                category_id  BIGINT NOT NULL REFERENCES category(category_id) ON DELETE CASCADE,
                                PRIMARY KEY (title_id, category_id)
);

CREATE TABLE title_cast (
                            title_id        BIGINT NOT NULL REFERENCES title(title_id) ON DELETE CASCADE,
                            person_id       BIGINT NOT NULL REFERENCES person(person_id) ON DELETE CASCADE,
                            character_name  VARCHAR(255),
                            billing_order   INT,
                            PRIMARY KEY (title_id, person_id),
                            CONSTRAINT chk_cast_billing_order CHECK (billing_order IS NULL OR billing_order > 0)
);

CREATE TABLE title_director (
                                title_id   BIGINT NOT NULL REFERENCES title(title_id) ON DELETE CASCADE,
                                person_id  BIGINT NOT NULL REFERENCES person(person_id) ON DELETE CASCADE,
                                PRIMARY KEY (title_id, person_id)
);

-- =========================
-- Watchable (unifies "single playable item" vs episode)
-- =========================

CREATE TABLE watchable (
                           watchable_id  BIGSERIAL PRIMARY KEY,
                           kind          VARCHAR(20) NOT NULL,
                           CONSTRAINT chk_watchable_kind CHECK (kind IN ('SINGLE', 'EPISODE'))
);

CREATE TABLE single_watchable (
                                  watchable_id      BIGINT PRIMARY KEY REFERENCES watchable(watchable_id) ON DELETE CASCADE,
                                  single_title_id   BIGINT NOT NULL REFERENCES single_title(title_id) ON DELETE RESTRICT
);

ALTER TABLE single_watchable
    ADD CONSTRAINT uq_single_watchable_single_title UNIQUE (single_title_id);

CREATE TABLE episode_watchable (
                                   watchable_id   BIGINT PRIMARY KEY REFERENCES watchable(watchable_id) ON DELETE CASCADE,
                                   episode_id     BIGINT NOT NULL REFERENCES episode(episode_id) ON DELETE RESTRICT
);

ALTER TABLE episode_watchable
    ADD CONSTRAINT uq_episode_watchable_episode UNIQUE (episode_id);

-- Enforce correct pairing between watchable.kind and subtype rows
CREATE OR REPLACE FUNCTION enforce_watchable_subtype()
RETURNS TRIGGER AS $$
BEGIN
  IF (TG_TABLE_NAME = 'single_watchable') THEN
    IF (SELECT kind FROM watchable WHERE watchable_id = NEW.watchable_id) <> 'SINGLE' THEN
      RAISE EXCEPTION 'watchable.kind must be SINGLE for single_watchable';
    END IF;
  ELSIF (TG_TABLE_NAME = 'episode_watchable') THEN
    IF (SELECT kind FROM watchable WHERE watchable_id = NEW.watchable_id) <> 'EPISODE' THEN
      RAISE EXCEPTION 'watchable.kind must be EPISODE for episode_watchable';
    END IF;
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_single_watchable_kind
    BEFORE INSERT OR UPDATE ON single_watchable
    FOR EACH ROW EXECUTE FUNCTION enforce_watchable_subtype();

CREATE TRIGGER trg_episode_watchable_kind
    BEFORE INSERT OR UPDATE ON episode_watchable
    FOR EACH ROW EXECUTE FUNCTION enforce_watchable_subtype();

-- =========================
-- Subscription
-- =========================

CREATE TABLE subscription_plan (
                                   plan_id        BIGSERIAL PRIMARY KEY,
                                   name           VARCHAR(100) NOT NULL UNIQUE,
                                   price          NUMERIC(10,2) NOT NULL,
                                   duration_days  INT NOT NULL,
                                   CONSTRAINT chk_plan_price CHECK (price >= 0),
                                   CONSTRAINT chk_plan_duration CHECK (duration_days > 0)
);

CREATE TABLE subscription (
                              subscription_id  BIGSERIAL PRIMARY KEY,
                              user_id          BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
                              plan_id          BIGINT NOT NULL REFERENCES subscription_plan(plan_id) ON DELETE RESTRICT,
                              start_at         TIMESTAMPTZ NOT NULL,
                              end_at           TIMESTAMPTZ,
                              status           VARCHAR(20) NOT NULL,
                              CONSTRAINT chk_subscription_status CHECK (status IN ('ACTIVE', 'CANCELED', 'EXPIRED')),
                              CONSTRAINT chk_subscription_dates CHECK (end_at IS NULL OR end_at >= start_at)
);

-- One ACTIVE subscription per user
CREATE UNIQUE INDEX uq_one_active_subscription_per_user
    ON subscription(user_id)
    WHERE status = 'ACTIVE';

-- =========================
-- WatchHistory
-- =========================

CREATE TABLE watch_history (
                               watch_id          BIGSERIAL PRIMARY KEY,
                               user_id           BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
                               watchable_id      BIGINT NOT NULL REFERENCES watchable(watchable_id) ON DELETE CASCADE,
                               watched_at        TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                               progress_seconds  INT NOT NULL DEFAULT 0,
                               completed         BOOLEAN NOT NULL DEFAULT FALSE,
                               CONSTRAINT chk_watch_progress CHECK (progress_seconds >= 0),
                               CONSTRAINT uq_watch_history_user_watchable UNIQUE (user_id, watchable_id)
);

-- Helpful indexes
CREATE INDEX idx_watch_history_user_updated ON watch_history(user_id, watched_at DESC);
CREATE INDEX idx_title_name ON title(name);
CREATE INDEX idx_category_slug ON category(slug);
