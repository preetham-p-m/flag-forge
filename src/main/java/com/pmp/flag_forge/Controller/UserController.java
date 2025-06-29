package com.pmp.flag_forge.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.UUID;

import com.pmp.flag_forge.Service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.pmp.flag_forge.Model.User;
import com.pmp.flag_forge.Model.UserDto;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody @Valid UserDto userDto) {
        var user = userService.create(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable UUID id) {
        userService.deleteById(id);
        return ResponseEntity.ok("User deleted successfully for id " + id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable UUID id) {
        var user = userService.getById(id);
        return ResponseEntity.ok(user);
    }
}
