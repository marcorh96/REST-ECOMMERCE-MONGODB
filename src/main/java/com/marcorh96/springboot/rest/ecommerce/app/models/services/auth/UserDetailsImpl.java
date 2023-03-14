package com.marcorh96.springboot.rest.ecommerce.app.models.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.marcorh96.springboot.rest.ecommerce.app.models.dao.UserRepository;

@Service
public class UserDetailsImpl implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      return userRepository.findByEmail(username).orElseThrow(() -> {
        throw new UsernameNotFoundException("User does´nt exists");
      });
    }
    
}
