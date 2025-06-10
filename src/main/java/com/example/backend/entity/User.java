package com.example.backend.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Users")
public class User {
    public Long getId() {
        return id;
    }

    @Id
    @NotNull(message = "ID cannot be null")
    private Long id;
    
    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMaidenName(String maidenName) {
        this.maidenName = maidenName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    private String maidenName;
    
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;
    
    private String ssn;
}
