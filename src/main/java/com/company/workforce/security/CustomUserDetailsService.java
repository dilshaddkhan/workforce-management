package com.company.workforce.security;

import com.company.workforce.repository.UserRepository;
import com.company.workforce.user.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(
            String username
    ) {

        System.out.println(
                "Loading user: " + username
        );

        User user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(
                                () -> new UsernameNotFoundException(
                                        "User not found"
                                )
                        );
        System.out.println(
                "Authority = ROLE_" + user.getRole().name()
        );
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(
                        "ROLE_" + user.getRole().name()
                )
                .build();
    }
}