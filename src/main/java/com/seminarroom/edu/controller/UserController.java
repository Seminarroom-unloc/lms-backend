package com.seminarroom.edu.controller;

import com.seminarroom.edu.config.JwtService;
import com.seminarroom.edu.model.User;
import com.seminarroom.edu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String tokenHeader) {
        try {
            String token = tokenHeader.replace("Bearer ", "");
            String username = jwtService.extractUsername(token);
            Optional<User> userOpt = userRepository.findByUsername(username);
            return userOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(@RequestHeader("Authorization") String tokenHeader,
                                           @RequestBody User updatedUser) {
        try {
            String token = tokenHeader.replace("Bearer ", "");
            String username = jwtService.extractUsername(token);
            Optional<User> userOpt = userRepository.findByUsername(username);

            if (userOpt.isEmpty()) return ResponseEntity.notFound().build();

            User user = userOpt.get();
            user.setFullName(updatedUser.getFullName());
            user.setEmail(updatedUser.getEmail());
            user.setUsername(updatedUser.getUsername());
            user.setPhone(updatedUser.getPhone());
            user.setBio(updatedUser.getBio());
            user.setLocation(updatedUser.getLocation());
            user.setOccupation(updatedUser.getOccupation());
            user.setInterests(updatedUser.getInterests());

            userRepository.save(user);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Update failed due to token error");
        }
    }
}
