package com.example.skycash_demo.model;

import javax.persistence.*;


@Entity
@Table (name = "user_role")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "role")
    private String role;

    @Column (name = "description")
    private String description;

    public UserRole(String role, String description) {
        this.role = role;
        this.description = description;
    }

    public UserRole() {

    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public String toString() {
        return "UserRole [id=" + id
                + ", role=" + role
                + ", description=" + description + "]";
    }
}
