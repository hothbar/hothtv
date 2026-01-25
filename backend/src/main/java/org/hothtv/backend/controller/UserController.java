package org.hothtv.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hothtv.backend.dto.CreateUserRequestDto;
import org.hothtv.backend.model.UserModel;
import org.hothtv.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // POST /api/users
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserModel create(@RequestBody CreateUserRequestDto req) {
        return userService.createUser(req);
    }

    // GET /api/users/{id}
    @GetMapping("/{id}")
    public UserModel get(@PathVariable Long id) {
        return userService.getUser(id);
    }

    // DELETE /api/users/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
