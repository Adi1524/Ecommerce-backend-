package com.pureproduce.ecommerce_oil.controller;

import org.springframework.web.bind.annotation.RestController;

import com.pureproduce.ecommerce_oil.config.JwtProvider;
import com.pureproduce.ecommerce_oil.exception.UserException;
import com.pureproduce.ecommerce_oil.model.Cart;
import com.pureproduce.ecommerce_oil.model.User;
import com.pureproduce.ecommerce_oil.repository.UserRepository;
import com.pureproduce.ecommerce_oil.request.LoginRequest;
import com.pureproduce.ecommerce_oil.response.AuthResponse;
import com.pureproduce.ecommerce_oil.service.CartService;
import com.pureproduce.ecommerce_oil.service.CustomerUserServiceImplementation;


import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping; 


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final CustomerUserServiceImplementation customerUserService;
    private final CartService cartService;



    public AuthController(UserRepository userRepository, 
                          CustomerUserServiceImplementation customerUserService,
                          PasswordEncoder passwordEncoder,
                          JwtProvider jwtProvider,
                          CartService cartService) {
        this.userRepository = userRepository;
        this.customerUserService = customerUserService;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.cartService = cartService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {
        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();

        User isEmailExist = userRepository.findByEmail(email);
        if (isEmailExist != null) {
            throw new UserException("Email is Already Used With Another Account");
        }

        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFirstName(firstName);
        createdUser.setLastName(lastName);

        User savedUser = userRepository.save(createdUser);
      
        @SuppressWarnings("unused")
        Cart cart = cartService.createCart(savedUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse= new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("signup success");

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest) {
        try {
            String email = loginRequest.getEmail();
            String password = loginRequest.getPassword();

            Authentication authentication = authenticate(email, password);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtProvider.generateToken(authentication);

            AuthResponse authResponse= new AuthResponse();
            authResponse.setJwt(token);
            authResponse.setMessage("signin success");

            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AuthResponse(null, "Invalid credentials"), HttpStatus.UNAUTHORIZED);
        }
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customerUserService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid Password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }
}
