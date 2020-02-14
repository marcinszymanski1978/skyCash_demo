package com.example.skycash_demo.controllers;

import java.util.List;
import java.util.Set;

import com.example.skycash_demo.model.User;
import com.example.skycash_demo.model.UserRole;
import com.example.skycash_demo.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ControllerREST {

    private ModelService userService;

    @Autowired
    public void setUserService(ModelService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> list = userService.getAllUsers();
        return new ResponseEntity<List<User>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(path = "/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserRole>> getAllRoles() {
        List<UserRole> list = userService.getAllRoles();
        return new ResponseEntity<List<UserRole>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(path = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User entity = userService.getUserById(id);
        return new ResponseEntity<User>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(path = "/roles/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserRole> getRoleById(@PathVariable("id") Long id) {
        UserRole entity = userService.getRoleById(id);
        return new ResponseEntity<UserRole>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(path = "/users/{id}/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<UserRole>> getUserRoles(@PathVariable("id") Long id) {
        Set<UserRole> userRoleSet = userService.getUserById(id).getRoles();
        return new ResponseEntity<Set<UserRole>>(userRoleSet, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(path = "/{uid}/roles/{rid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserRole> getRoleByIdFromUser(@PathVariable(value = "uid") Long uid, @PathVariable (value = "rid") Long rid) {
        User user = userService.getUserById(uid);
        UserRole entity = userService.getRoleById(rid);
        return new ResponseEntity<UserRole>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping (path = "/userAdd")
    public ResponseEntity<User> addUser(@ModelAttribute @Valid User user)
    {
       userService.addUserWithDefaultRole(user);
       return new ResponseEntity<User>(user, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/roleAdd")
    public ResponseEntity<UserRole> addRole(UserRole userRole) {
        userService.addRole(userRole);
        return new ResponseEntity<UserRole>(userRole, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping("/users/{uid}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "uid") Long uid, @Valid @RequestBody User userDetails){
       return new ResponseEntity<User>(userService.updateUserREST(userDetails, uid), new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping("/roles/{rid}")
    public ResponseEntity<UserRole> updateRole(@PathVariable(value = "rid") Long rid, @Valid @RequestBody UserRole roleDetails){
        return new ResponseEntity<UserRole>(userService.updateRole(roleDetails, rid), new HttpHeaders(), HttpStatus.OK);
    }


    @DeleteMapping("/users/{id}")
    public HttpStatus deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return HttpStatus.OK;
    }

    @DeleteMapping("/roles/{id}")
    public HttpStatus deleteRoleById(@PathVariable("id") Long id) {
        userService.deleteRoleById(id);
        return HttpStatus.OK;
    }
}
