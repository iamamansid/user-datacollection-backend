package com.example.backend.mapper;

import com.example.backend.dto.UserDTO;
import com.example.backend.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserMapper {
    public UserDTO toDTO(User user) {
        try {
            if (user == null) {
                log.warn("Attempt to convert null User to DTO");
                return null;
            }
            
            log.debug("Converting User entity to UserDTO: {}", user.getId());
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setEmail(user.getEmail());
            userDTO.setSsn(user.getSsn());
            return userDTO;
        } catch (Exception e) {
            log.error("Error converting User to DTO", e);
            throw e;
        }
    }
      public User toEntity(UserDTO userDTO) {
        try {
            if (userDTO == null) {
                log.warn("Attempt to convert null UserDTO to entity");
                return null;
            }
            
            log.debug("Converting UserDTO to User entity: {}", userDTO.getId());
            User user = new User();
            user.setId(userDTO.getId());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setSsn(userDTO.getSsn());
            return user;
        } catch (Exception e) {
            log.error("Error converting UserDTO to entity", e);
            throw e;
        }
    }
}