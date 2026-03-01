package com.codewithmosh.store.Service;

import com.codewithmosh.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
         var user = userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found"));
         return new User(
                 user.getEmail(),
                 user.getPassword(),
                 Collections.emptyList()
         );
    }
}
