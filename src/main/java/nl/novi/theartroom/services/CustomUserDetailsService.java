package nl.novi.theartroom.services;


import nl.novi.theartroom.dtos.userdtos.UserDto;
import nl.novi.theartroom.models.Authority;
import nl.novi.theartroom.models.User;
import nl.novi.theartroom.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //TODO Exception hier aanpassen

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findById(username);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found with username: " + username);
        }
        User foundUser = user.get();

        String password = foundUser.getPassword();

        Set<Authority> authorities = foundUser.getAuthorities();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority: authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }

        return new org.springframework.security.core.userdetails.User(username, password, grantedAuthorities);
    }

}