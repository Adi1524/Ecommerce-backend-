package com.pureproduce.ecommerce_oil.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pureproduce.ecommerce_oil.exception.ProductException;
import com.pureproduce.ecommerce_oil.model.Product;
import com.pureproduce.ecommerce_oil.request.CreateProductRequest;
import com.pureproduce.ecommerce_oil.response.ApiResponse;
import com.pureproduce.ecommerce_oil.service.ProductService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/admin/products")
@AllArgsConstructor
public class AdminProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/")
    public ResponseEntity<Product> createProductHandler(@RequestBody CreateProductRequest req) throws ProductException{

        Product createProduct = productService.createProduct(req);

        return new ResponseEntity<Product>(createProduct, HttpStatus.ACCEPTED);

    }

    @GetMapping("/recent")
    public ResponseEntity<List<Product>> recentlyAddedProducts(){
        List<Product> products= productService.recentlyAddedProducts();

        return new ResponseEntity<List<Product>>(products,HttpStatus.OK);

    }

    @PutMapping("/{productId}/update")
    public ResponseEntity<Product> updateProductHandler(@RequestBody Product req,@PathVariable("productId") Long productId ) throws ProductException{
        Product updateProduct= productService.updateProduct(productId, req);

        return new ResponseEntity<Product>(updateProduct, HttpStatus.OK);
    }

    @PostMapping("/creates")
    public ResponseEntity<ApiResponse> createMultipleProduct(@RequestBody CreateProductRequest[] reqs) throws ProductException{
        for(CreateProductRequest product:reqs){
            productService.createProduct(product);
        }
        ApiResponse res= new ApiResponse("Products created succefully", true);
        return new ResponseEntity<ApiResponse>(res,HttpStatus.ACCEPTED);

    }
    



}
