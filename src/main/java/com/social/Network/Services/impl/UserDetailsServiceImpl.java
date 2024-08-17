package com.social.Network.Services.impl;


import org.apache.catalina.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.Network.Entities.Role;
import com.social.Network.Repositories.UserRepository;

import java.security.Principal;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = (User) userRepository.findUserByEmail(email);
        if (user == null)
            throw new UsernameNotFoundException("Invalid username or password.");

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    @SuppressWarnings("unchecked")
	private static Collection<? extends GrantedAuthority> mapRolesToAuthorities(Iterator<org.apache.catalina.Role> iterator) {
        return ((Collection<? extends GrantedAuthority>) iterator).stream()
                    .map(role -> new SimpleGrantedAuthority(((Principal) role).getName()))
                    .collect(Collectors.toList());
    }

}
