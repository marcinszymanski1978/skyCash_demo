package com.example.skycash_demo.service;

import com.example.skycash_demo.model.User;
import com.example.skycash_demo.model.UserRole;
import com.example.skycash_demo.repositories.UserRepository;
import com.example.skycash_demo.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ModelService {

    private static final String DEFAULT_ROLE = "ROLE_USER";
    private static final String DEFAULT_DESCRIPTION = "additional role";
    private UserRepository userRepository;
    private UserRoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ModelService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(UserRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void addUserWithDefaultRole(User user) {
        UserRole defaultRole = roleRepository.findByRole(DEFAULT_ROLE);
        user.getRoles().add(defaultRole);
        String passwordHash = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordHash);
        userRepository.save(user);
    }

    public void addRole(UserRole userRole) {
        roleRepository.save(userRole);
    }

    public List<User> getAllUsers() {
        List<User> userList = userRepository.findAll();
        if (userList.size() > 0) {
            return userList;
        } else {
            return new ArrayList<User>();
        }
    }

    public List<UserRole> getAllRoles() {
        List<UserRole> userRoleList = roleRepository.findAll();
        if (userRoleList.size() > 0) {
            return userRoleList;
        } else {
            return new ArrayList<UserRole>();
        }
    }

    public User getUserById(Long id) throws ResourceNotFoundException {
            Optional<User> user = userRepository.findById(id);
            if(user.isPresent()) {
                return user.get();
            } else {
                throw new ResourceNotFoundException("User not found for this id : " + id);
            }
        }

    public UserRole getRoleById(Long id) throws ResourceNotFoundException {
        Optional<UserRole> userRole = roleRepository.findById(id);
        if(userRole.isPresent()) {
            return userRole.get();
        } else {
            throw new ResourceNotFoundException("Role not found for this id : " + id);
        }
    }

    public User updateUserREST (User userDetails, Long id) throws ResourceNotFoundException {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()){
                User updatedUser = new User();
                updatedUser.setFirstName(userDetails.getFirstName());
                updatedUser.setLastName(userDetails.getLastName());
                updatedUser.setEmail(userDetails.getEmail());
                updatedUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));
                updatedUser.setRoles(userDetails.getRoles());
                //UserRole defaultRole = roleRepository.findByRole(DEFAULT_ROLE);
                //user.getRoles().add(defaultRole);
                userRepository.save(updatedUser);
                return updatedUser;
            } else {
                throw new ResourceNotFoundException("User not found for this id :"+ id);
            }
    }

    public User updateUserMVC (User userDetails, Long id) throws ResourceNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User updatedUser = new User();
            updatedUser.setFirstName(userDetails.getFirstName());
            updatedUser.setLastName(userDetails.getLastName());
            updatedUser.setEmail(userDetails.getEmail());
            updatedUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));
//            updatedUser.setRoles(userDetails.getRoles());
            UserRole defaultRole = roleRepository.findByRole(DEFAULT_ROLE);
            updatedUser.getRoles().add(defaultRole);
            userRepository.save(updatedUser);
            return updatedUser;
        } else {
            throw new ResourceNotFoundException("User not found for this id :" + id);
        }
    }

    public UserRole updateRole (UserRole roleDetails, Long id) throws ResourceNotFoundException {
        Optional<UserRole> userRole = roleRepository.findById(id);
        if (userRole.isPresent()){
            UserRole role = new UserRole();
            role.setRole(roleDetails.getRole());
            role.setDescription(roleDetails.getDescription());
            roleRepository.save(role);
            return role;
        } else {
            throw new ResourceNotFoundException("Role not found for this id :"+ id);
        }
    }

    public void deleteUserById(Long id) throws ResourceNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("User not found for this id : " + id);
        }
    }

    public void deleteRoleById(Long id) throws ResourceNotFoundException {
        Optional<UserRole> userRole = roleRepository.findById(id);
        if(userRole.isPresent()) {
            roleRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Role not found for this id : " + id);
        }
    }
}



