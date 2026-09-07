package com.microserviceProject.Product_service.controller;

import com.microserviceProject.Product_service.dto.ProductRequest;
import com.microserviceProject.Product_service.dto.ProductResponse;
import com.microserviceProject.Product_service.model.Product;
import com.microserviceProject.Product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    public final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest product) {

        productService.createProduct(product);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){

        return productService.getAllProducts();


    }
}
