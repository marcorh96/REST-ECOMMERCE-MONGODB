package com.marcorh96.springboot.rest.ecommerce.app.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.marcorh96.springboot.rest.ecommerce.app.models.dao.ProductRepository;

import com.marcorh96.springboot.rest.ecommerce.app.models.document.Product;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> saveAll(List<Product> products){
        return productRepository.saveAll(products);
    }

}
