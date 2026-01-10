package org.hothtv.backend.people.dto;

public record AddCastRequest(
        String characterName,
        Integer billingOrder
) {}
