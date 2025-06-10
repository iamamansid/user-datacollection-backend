package com.example.backend.service.impl;

import com.example.backend.service.UserService;

import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    public List<User> getAllUsers() {
        log.info("Fetching all users from database");
        try {
            List<User> users = repository.findAll();
            log.debug("Retrieved {} users from database", users.size());
            return users;
        } catch (Exception e) {
            log.error("Error retrieving all users from database", e);
            throw e;
        }
    }

    public List<User> searchUsers(String keyword) {
        log.info("Searching users with keyword: {}", keyword);
        try {
            List<User> users = repository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrSsnContainingIgnoreCase(keyword, keyword, keyword);
            log.debug("Found {} users matching keyword: {}", users.size(), keyword);
            return users;
        } catch (Exception e) {
            log.error("Error searching users with keyword: {}", keyword, e);
            throw e;
        }
    }

    public Optional<User> getUserById(Long id) {
        log.info("Fetching user by ID: {}", id);
        try {
            Optional<User> user = repository.findById(id);
            if (user.isPresent()) {
                log.debug("User found with ID: {}", id);
            } else {
                log.debug("No user found with ID: {}", id);
            }
            return user;
        } catch (Exception e) {
            log.error("Error fetching user with ID: {}", id, e);
            throw e;
        }
    }

    public Optional<User> getUserByEmail(String email) {
        log.info("Fetching user by email: {}", email);
        try {
            Optional<User> user = repository.findByEmail(email);
            if (user.isPresent()) {
                log.debug("User found with email: {}", email);
            } else {
                log.debug("No user found with email: {}", email);
            }
            return user;
        } catch (Exception e) {
            log.error("Error fetching user with email: {}", email, e);
            throw e;
        }
    }

    public void saveAll(List<User> users) {
        log.info("Saving {} users to database", users.size());
        try {
            repository.saveAll(users);
            log.debug("Successfully saved {} users to database", users.size());
        } catch (Exception e) {
            log.error("Error saving users to database", e);
            throw e;
        }
    }
}

