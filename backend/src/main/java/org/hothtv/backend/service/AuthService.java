package org.hothtv.backend.service;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.dto.LoginRequestDto;
import org.hothtv.backend.dto.LoginResponseDto;
import org.hothtv.backend.repository.UserRepository;
import org.hothtv.backend.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponseDto login(LoginRequestDto req) {
        return userRepository.findByEmail(req.email())
                .filter(user -> passwordEncoder.matches(req.password(), user.getPasswordHash()))
                .map(user -> new LoginResponseDto(jwtService.generate(user.getEmail())))
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
    }
}
