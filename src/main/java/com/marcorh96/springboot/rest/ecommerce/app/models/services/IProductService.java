package com.marcorh96.springboot.rest.ecommerce.app.models.services;
import java.util.List;

import com.marcorh96.springboot.rest.ecommerce.app.models.document.Product;

public interface IProductService {
    public List<Product> findAll();

    public Product findById(String id);

    public Product save(Product product);

    public void delete(String id);

    public List<Product> saveAll(List<Product> products);

}
