package com.pureproduce.ecommerce_oil.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pureproduce.ecommerce_oil.exception.UserException;
import com.pureproduce.ecommerce_oil.model.User;
import com.pureproduce.ecommerce_oil.service.UserService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> getuserProfileHandler(@RequestHeader("Authorization") String jwt) throws UserException{
        System.out.println("/api/users/profile");
        User user = userService.findUserProfileByJwt(jwt);

        return new ResponseEntity<User>(user, HttpStatus.ACCEPTED);
        
    }

    

}
