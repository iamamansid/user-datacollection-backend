package com.example.backend.service;

import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser1;
    private User testUser2;
    private List<User> userList;

    @BeforeEach
    void setUp() {
        // Create test users
        testUser1 = new User();
        testUser1.setId(1L);
        testUser1.setFirstName("John");
        testUser1.setLastName("Doe");
        testUser1.setEmail("john.doe@example.com");
        testUser1.setSsn("123-45-6789");

        testUser2 = new User();
        testUser2.setId(2L);
        testUser2.setFirstName("Jane");
        testUser2.setLastName("Smith");
        testUser2.setEmail("jane.smith@example.com");
        testUser2.setSsn("987-65-4321");

        userList = Arrays.asList(testUser1, testUser2);
    }

    @Test
    void getAllUsers_ShouldReturnAllUsers() {
        // Arrange
        when(userRepository.findAll()).thenReturn(userList);

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_ExistingId_ShouldReturnUser() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser1));

        // Act
        Optional<User> result = userService.getUserById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("John", result.get().getFirstName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUserById_NonExistingId_ShouldReturnEmpty() {
        // Arrange
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userService.getUserById(99L);

        // Assert
        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findById(99L);
    }

    @Test
    void getUserByEmail_ExistingEmail_ShouldReturnUser() {
        // Arrange
        String email = "john.doe@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(testUser1));

        // Act
        Optional<User> result = userService.getUserByEmail(email);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void searchUsers_ShouldReturnMatchingUsers() {
        // Arrange
        String keyword = "John";
        when(userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrSsnContainingIgnoreCase(
                keyword, keyword, keyword)).thenReturn(Arrays.asList(testUser1));

        // Act
        List<User> result = userService.searchUsers(keyword);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
        verify(userRepository, times(1))
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrSsnContainingIgnoreCase(
                        keyword, keyword, keyword);
    }    @Test
    void saveAll_ShouldSaveAllUsers() {
        // Arrange
        // JpaRepository.saveAll returns the saved entities, but we don't need to mock a return value here
        when(userRepository.saveAll(userList)).thenReturn(userList);

        // Act - should not throw exception
        assertDoesNotThrow(() -> userService.saveAll(userList));

        // Assert
        verify(userRepository, times(1)).saveAll(userList);
    }
}