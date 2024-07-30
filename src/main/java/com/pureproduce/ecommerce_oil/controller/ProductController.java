package com.pureproduce.ecommerce_oil.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pureproduce.ecommerce_oil.exception.ProductException;
import com.pureproduce.ecommerce_oil.model.Product;
import com.pureproduce.ecommerce_oil.service.ProductService;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity <Page<Product>> findProductByCategoryHandler(
        @RequestParam(required = false) String category,
            @RequestParam(required = false) List<String> color,
            @RequestParam(required = false) List<String> size,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) Integer minDiscount,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String stock,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize
    ){
        Page<Product> res = productService.getAllProduct(category, color, null, minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize);
        System.out.println("complete products"+category+color+minPrice+maxPrice+minDiscount+sort+stock+pageNumber+pageSize);
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @GetMapping("/products/id/{productId}")
    public ResponseEntity<Product> findProductByIdHandler(@PathVariable("productId") Long ProductId)throws ProductException{
        Product product = productService.findProductById(ProductId);
        return new ResponseEntity<Product>(product, HttpStatus.ACCEPTED);
    }

}
