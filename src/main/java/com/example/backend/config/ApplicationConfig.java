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

    private Api api = new Api();
    private Database database = new Database();
    
    @Getter
    @Setter
    public static class Api {

        private String usersUrl = "https://dummyjson.com/users";
        

        private int connectionTimeout = 5000;
        

        private int readTimeout = 10000;
    }
    
    @Getter
    @Setter
    public static class Database {

        private boolean autoloadData = false;
        

        private int maxRecords = 100;
    }
}
