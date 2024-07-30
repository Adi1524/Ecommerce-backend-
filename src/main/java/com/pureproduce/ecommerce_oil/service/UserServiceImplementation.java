package com.pureproduce.ecommerce_oil.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pureproduce.ecommerce_oil.config.JwtProvider;
import com.pureproduce.ecommerce_oil.exception.UserException;
import com.pureproduce.ecommerce_oil.model.User;
import com.pureproduce.ecommerce_oil.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServiceImplementation implements UserService {

    private UserRepository userRepository;
    private JwtProvider jwtProvider;


    @Override
    public User findUserById(Long userId) throws UserException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
            
        }
        throw new UserException("user not found with id"+userId+" !!");
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        System.out.println("user service");
        String email = jwtProvider.getEmailFromToken(jwt);
        System.out.println("user email"+email);

        User user = userRepository.findByEmail(email);
        
        if(user== null){
            throw new UserException("user not found with the jwt");
        }
        
        System.out.println("user email"+user.getId());


        return user;
    }

}
