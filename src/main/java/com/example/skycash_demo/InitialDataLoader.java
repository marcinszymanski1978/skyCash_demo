package com.example.skycash_demo;

import com.example.skycash_demo.model.User;
import com.example.skycash_demo.model.UserRole;
import com.example.skycash_demo.repositories.UserRepository;
import com.example.skycash_demo.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        if (alreadySetup)
            return;

        UserRole userRole = new UserRole("ROLE_USER","restricted access");
        roleRepository.save(userRole);
        UserRole adminRole = new UserRole("ROLE_ADMIN","full access");
        roleRepository.save(adminRole);

        User admin = new User();
        admin.setFirstName("Marcin");
        admin.setLastName("Szymanski");
        admin.setPassword(passwordEncoder.encode("password"));
        admin.setEmail("marcin@gmail.com");
        admin.getRoles().add(adminRole);
        userRepository.save(admin);

        alreadySetup = true;

    }

}
