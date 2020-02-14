package com.example.skycash_demo.model;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.validator.constraints.NotEmpty;


@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name="user_id", referencedColumnName="id")},
            inverseJoinColumns = {@JoinColumn(name="role_id", referencedColumnName="id")})
    private Set<UserRole> roles = new LinkedHashSet<>();

    public Long getId(){ return id;}
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Set<UserRole> getRoles() {
        return roles;
    }
    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }
    @Override
    public String toString() {
        return "User [id=" + id
                + ", firstName=" + firstName
                + ", lastName=" + lastName
                + ", email=" + email
                + ", password=" + password
                + ", roles=" + roles + "]";
    }


    public void setRoles() {
    }
}