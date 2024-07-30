package com.pureproduce.ecommerce_oil.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pureproduce.ecommerce_oil.response.ApiResponse;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<ApiResponse> homeController(){
        ApiResponse res = new ApiResponse("Welcome to e-commerce System", true);
        return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
    }

}
