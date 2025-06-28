package com.wizard.ViperFlex.service;

import com.wizard.ViperFlex.Entity.User;
import com.wizard.ViperFlex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public User saveUser(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        System.out.println(user.getPassword());
        return repo.save(user);
    }

    public Optional<User> findByEmail(String email){
        return repo.findByEmail(email);
    }

    public boolean existsByEmail(String email){
        return repo.existsByEmail(email);
    }
}
