package com.trialstudio.customstudio.repository;



import com.trialstudio.customstudio.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends  MongoRepository<Product, String>{
    List<Product> findByNameContainingIgnoreCaseOrBrandContainingIgnoreCaseOrCategoryContainingIgnoreCase(String name, String brand, String category);
    List<Product> findByNameContainingIgnoreCase(String name);
}




