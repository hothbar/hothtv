package org.hothtv.backend.users.service;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.common.error.NotFoundException;
import org.hothtv.backend.users.dto.CreateUserRequest;
import org.hothtv.backend.users.model.User;
import org.hothtv.backend.users.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User createUser(CreateUserRequest req) {
        User user = new User();
        user.setFirstName(req.firstName());
        user.setLastName(req.lastName());
        user.setEmail(req.email());

        // TEMP: plain text for learning — replace with BCrypt later
        user.setPasswordHash(req.password());

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found: " + id));
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User not found: " + id);
        }
        userRepository.deleteById(id);
    }
}
