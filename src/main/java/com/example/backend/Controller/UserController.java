package com.example.backend.Controller;

import com.example.backend.entity.User;
import com.example.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("REST request - Get all users");
        try {
            List<User> response = service.getAllUsers();
            if (response!=null) {
                log.debug("Returning {} users", response.size());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                log.error("Failed to retrieve users");
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            log.error("Exception while retrieving all users", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String q) {
        log.info("REST request - Search users with keyword: {}", q);
        try {
            List<User> response = service.searchUsers(q);
            if (response!=null){
                log.debug("Found {} users matching search criteria", response.size());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                log.error("Failed to search users with keyword: {}", q);
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            log.error("Exception while searching users with keyword: {}", q, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        log.info("REST request - Get user by ID: {}", id);
        try {
            User res = service.getUserById(id).orElseThrow();
            if(res!=null){
                log.debug("Found user with ID: {}", id);
                return new ResponseEntity<>(res, HttpStatus.OK);
            } else {
                log.error("User found but null returned for ID: {}", id);
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }        } catch (Exception e) {
            log.error("Failed to find user with ID: {}", id, e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/email")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        log.info("REST request - Get user by email: {}", email);
        try {
            User res = service.getUserByEmail(email).orElseThrow();
            if(res!=null){
                log.debug("Found user with email: {}", email);
                return new ResponseEntity<>(res, HttpStatus.OK);
            } else {
                log.error("User found but null returned for email: {}", email);
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }        } catch (Exception e) {
            log.error("Failed to find user with email: {}", email, e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
