package com.marcorh96.springboot.rest.ecommerce.app.models.services;

import com.marcorh96.springboot.rest.ecommerce.app.models.document.Category;

public interface ICategoryService {
    public Category findById(String id);

    public Category save(Category category);

    public void delete(String id);
}
