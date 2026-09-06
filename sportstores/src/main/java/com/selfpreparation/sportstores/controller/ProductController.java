package com.selfpreparation.sportstores.controller;

import com.selfpreparation.sportstores.dto.ProductDto;
import com.selfpreparation.sportstores.entity.Product;
import com.selfpreparation.sportstores.repository.ProductRepository;
import com.selfpreparation.sportstores.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/products")
//@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    private final IProductService iProductService;

//    @GetMapping
//    public List<Product> getProducts(){
//
//        List<Product> productList = iProductService.getProducts();
//
//        return productList;
//    }
    @GetMapping
    public List<ProductDto> getProducts(){
        List<ProductDto> productList = iProductService.getProducts();
        return productList;
    }
}
