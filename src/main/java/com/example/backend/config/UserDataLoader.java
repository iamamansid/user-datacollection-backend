package com.example.backend.config;

import com.example.backend.entity.User;
import com.example.backend.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserDataLoader {
    @Autowired
    private UserService service;
    
    @Autowired
    private ApplicationConfig config;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    private RestTemplate restTemplate;
    
    @Autowired
    public void setRestTemplateBuilder(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
            .setConnectTimeout(Duration.ofMillis(config.getApi().getConnectionTimeout()))
            .setReadTimeout(Duration.ofMillis(config.getApi().getReadTimeout()))
            .build();
    }
    
    @PostConstruct
    public void loadData() {
        // Skip loading if auto-loading is disabled
        if (!config.getDatabase().isAutoloadData()) {
            log.info("Auto-loading of data is disabled. Skipping initial data load.");
            return;
        }
        
        log.info("Starting initial data load from external API");
        String url = config.getApi().getUsersUrl();
        log.debug("Fetching data from URL: {}", url);
        
        try {
            JsonNode root = restTemplate.getForObject(url, JsonNode.class);
            List<User> users = new ArrayList<>();            if (root != null && root.has("users")) {
                log.info("Successfully retrieved user data from API");
                
                JsonNode usersNode = root.get("users");
                int maxRecords = config.getDatabase().getMaxRecords();
                int recordsToProcess = Math.min(usersNode.size(), maxRecords);
                
                log.info("Processing up to {} users from external API", recordsToProcess);
                
                for (int i = 0; i < recordsToProcess; i++) {
                    JsonNode node = usersNode.get(i);
                    try {
                        User user = new User();
                        user.setId(node.get("id").asLong());
                        user.setFirstName(node.get("firstName").asText());
                        user.setLastName(node.get("lastName").asText());
                        user.setMaidenName(node.has("maidenName") ? node.get("maidenName").asText() : null);
                        user.setEmail(node.get("email").asText());
                        user.setSsn(node.has("ssn") ? node.get("ssn").asText() : null);
                        users.add(user);
                    } catch (Exception e) {
                        log.error("Error processing user from API: {}", node, e);
                        // Continue processing other users
                    }
                }
                log.info("Processed {} users from external API", users.size());
                service.saveAll(users);
                log.info("Successfully loaded all users into database");
            } else {
                log.error("Failed to retrieve user data from API or invalid response format");
            }
        } catch (Exception e) {
            log.error("Error occurred while loading initial data", e);
        }
    }
}

