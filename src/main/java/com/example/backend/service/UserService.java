package com.example.backend.service;

import com.example.backend.entity.User;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    public List<User> getAllUsers();

    public List<User> searchUsers(String keyword);

    public Optional<User> getUserById(Long id);

    public Optional<User> getUserByEmail(String email);

    public void saveAll(List<User> users);
}
