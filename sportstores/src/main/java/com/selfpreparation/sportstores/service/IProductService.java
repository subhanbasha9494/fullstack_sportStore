package com.selfpreparation.sportstores.service;

import com.selfpreparation.sportstores.dto.ProductDto;
import com.selfpreparation.sportstores.entity.Product;

import java.util.List;

public interface IProductService {
    //List<Product> getProducts();
    List<ProductDto> getProducts();
}
