package com.marcorh96.springboot.rest.ecommerce.app.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.marcorh96.springboot.rest.ecommerce.app.models.dao.UserRepository;
import com.marcorh96.springboot.rest.ecommerce.app.config.jwt.JwtProvider;
import com.marcorh96.springboot.rest.ecommerce.app.models.auth.UserLoginDTO;
import com.marcorh96.springboot.rest.ecommerce.app.models.auth.UserResponseDTO;
import com.marcorh96.springboot.rest.ecommerce.app.models.dao.OrderRepository;
import com.marcorh96.springboot.rest.ecommerce.app.models.document.Role;
import com.marcorh96.springboot.rest.ecommerce.app.models.document.User;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User save(User user) {
        user.setRole(Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void delete(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> saveAll(List<User> users) {
        return userRepository.saveAll(users);
    }

    @Override
    public void deleteOrdersByUserId(String userId) {
        orderRepository.deleteOrdersByUserId(userId);
    }

    @Override
    public UserResponseDTO login(UserLoginDTO user) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(),
                        user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);

        String token = JwtProvider.generarTokenJWT(user.getEmail());

        User userByEmail = userRepository.findByEmail(user.getEmail()).orElse(null);

        UserResponseDTO userResponse = new UserResponseDTO();
        userResponse.setId(userByEmail.getId());
        userResponse.setEmail(userByEmail.getEmail());
        userResponse.setPassword(userByEmail.getPassword());
        userResponse.setRole(userByEmail.getRole());
        userResponse.setToken(token);

        return userResponse;

    }

}
