package com.pureproduce.ecommerce_oil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pureproduce.ecommerce_oil.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long > {

    public User findByEmail(String email);
     

}
