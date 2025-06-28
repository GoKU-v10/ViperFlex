package com.wizard.ViperFlex.service;

import com.wizard.ViperFlex.Entity.User;
import com.wizard.ViperFlex.Entity.UserPrincipal;
import com.wizard.ViperFlex.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> user = repo.findByEmail(email);

        if(user==null){
            throw new UsernameNotFoundException("User 404");
        }
        return new UserPrincipal(user);
    }
}
