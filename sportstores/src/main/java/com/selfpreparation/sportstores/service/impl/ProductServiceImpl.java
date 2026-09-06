package com.selfpreparation.sportstores.service.impl;

import com.selfpreparation.sportstores.dto.ProductDto;
import com.selfpreparation.sportstores.entity.Product;
import com.selfpreparation.sportstores.repository.ProductRepository;
import com.selfpreparation.sportstores.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;

//    @Override
//    public List<Product> getProducts(){
//        return productRepository.findAll();
//    }

    @Override
    public List<ProductDto> getProducts(){
        return productRepository.findAll()
                .stream().map(this::transformToDto).collect(Collectors.toList());
    }

    public ProductDto transformToDto(Product product){
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);
        productDto.setProductId(product.getId());
        return productDto;
    }
}
