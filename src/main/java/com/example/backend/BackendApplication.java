package com.example.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(BackendApplication.class);

	public static void main(String[] args) {
		logger.info("Starting Backend Application");
		try {
			SpringApplication.run(BackendApplication.class, args);
			logger.info("Backend Application started successfully");
		} catch (Exception e) {
			logger.error("Error starting Backend Application", e);
			throw e;
		}
	}

}
