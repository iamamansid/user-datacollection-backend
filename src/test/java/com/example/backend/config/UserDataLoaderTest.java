package com.example.backend.config;

import com.example.backend.entity.User;
import com.example.backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class UserDataLoaderTest {

    @Autowired
    private UserDataLoader userDataLoader;

    @Autowired
    private ApplicationConfig config;

    @MockBean
    private UserService userService;

    @Captor
    private ArgumentCaptor<List<User>> usersCaptor;

    @Test
    void loadData_WithAutoloadEnabled_ShouldLoadUsers() {
        // Set autoload to true for this test
        config.getDatabase().setAutoloadData(true);
        
        // Act
        userDataLoader.loadData();
        
        // Verify
        verify(userService, times(1)).saveAll(usersCaptor.capture());
        
        List<User> capturedUsers = usersCaptor.getValue();
        assertNotNull(capturedUsers);
        assertFalse(capturedUsers.isEmpty());
        
        // Verify it respects max records setting
        int maxRecords = config.getDatabase().getMaxRecords();
        assertTrue(capturedUsers.size() <= maxRecords, 
                "Number of loaded users should be <= max records config value");
    }
    
    @Test
    void loadData_WithAutoloadDisabled_ShouldNotLoadUsers() {
        // Set autoload to false for this test
        config.getDatabase().setAutoloadData(false);
        
        // Act
        userDataLoader.loadData();
        
        // Verify
        verify(userService, never()).saveAll(any());
    }
}
