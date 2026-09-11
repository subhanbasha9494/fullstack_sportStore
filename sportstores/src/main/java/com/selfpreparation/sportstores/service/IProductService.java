package com.selfpreparation.sportstores.service;

import com.selfpreparation.sportstores.dto.ProductDto;

import java.util.List;



public interface IProductService {
    List<ProductDto> getProducts();
}
