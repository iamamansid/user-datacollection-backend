package com.example.backend.controller;

import com.example.backend.Controller.UserController;
import com.example.backend.entity.User;
import com.example.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User testUser1;
    private User testUser2;
    private List<User> userList;

    @BeforeEach
    void setUp() {
        
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
        
        when(userService.getAllUsers()).thenReturn(userList);

        
        ResponseEntity<List<User>> response = userController.getAllUsers();        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void getAllUsers_WhenExceptionThrown_ShouldReturnInternalServerError() {
        
        when(userService.getAllUsers()).thenThrow(new RuntimeException("Database connection failed"));

        
        ResponseEntity<List<User>> response = userController.getAllUsers();

        
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void getUserById_ExistingId_ShouldReturnUser() {
        
        when(userService.getUserById(1L)).thenReturn(Optional.of(testUser1));

        
        ResponseEntity<User> response = userController.getUserById(1L);

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void getUserById_NonExistingId_ShouldReturnNotFound() {
        
        when(userService.getUserById(99L)).thenReturn(Optional.empty());

        
        ResponseEntity<User> response = userController.getUserById(99L);

        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).getUserById(99L);
    }

    @Test
    void getUserByEmail_ExistingEmail_ShouldReturnUser() {
        
        String email = "john.doe@example.com";
        when(userService.getUserByEmail(email)).thenReturn(Optional.of(testUser1));

        
        ResponseEntity<User> response = userController.getUserByEmail(email);

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(email, response.getBody().getEmail());
        verify(userService, times(1)).getUserByEmail(email);
    }

    @Test
    void searchUsers_ShouldReturnMatchingUsers() {
        
        String keyword = "John";
        when(userService.searchUsers(keyword)).thenReturn(Arrays.asList(testUser1));

        
        ResponseEntity<List<User>> response = userController.searchUsers(keyword);

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("John", response.getBody().get(0).getFirstName());
        verify(userService, times(1)).searchUsers(keyword);
    }
}