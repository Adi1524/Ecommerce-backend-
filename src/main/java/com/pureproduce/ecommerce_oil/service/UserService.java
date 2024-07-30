package com.pureproduce.ecommerce_oil.service;

import com.pureproduce.ecommerce_oil.exception.UserException;
import com.pureproduce.ecommerce_oil.model.User;

public interface UserService {
    public User findUserById(Long userId ) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;
    




}
