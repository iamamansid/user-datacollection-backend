package com.example.backend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class ApplicationConfig {
    
    /**
     * External API configuration
     */
    private Api api = new Api();
    
    /**
     * Database configuration
     */
    private Database database = new Database();
    
    @Getter
    @Setter
    public static class Api {
        /**
         * Base URL for users API
         */
        private String usersUrl = "https://dummyjson.com/users";
        
        /**
         * Connection timeout in milliseconds
         */
        private int connectionTimeout = 5000;
        
        /**
         * Read timeout in milliseconds
         */
        private int readTimeout = 10000;
    }
    
    @Getter
    @Setter
    public static class Database {
        /**
         * Enable automatic loading of data on startup
         */
        private boolean autoloadData = false;
        
        /**
         * Maximum number of records to load
         */
        private int maxRecords = 100;
    }
}
