package com.trialstudio.customstudio.service;

import com.trialstudio.customstudio.model.Product;
import com.trialstudio.customstudio.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    public List<Product> searchProducts(String keyword) {
        return productRepository.findByNameContainingIgnoreCaseOrBrandContainingIgnoreCaseOrCategoryContainingIgnoreCase(keyword, keyword, keyword);
    }

    public Optional<Product> updateProduct(String id, Product productDetails) {
        return productRepository.findById(id).map(product -> {
            product.setName(productDetails.getName());
            product.setSku(productDetails.getSku());
            product.setBrand(productDetails.getBrand());
            product.setCategory(productDetails.getCategory());
            product.setImgUrl(productDetails.getImgUrl());
            product.setColor(productDetails.getColor());
            product.setSize(productDetails.getSize());
            product.setDescription(productDetails.getDescription());
            product.setAvailability(productDetails.getAvailability());
            return productRepository.save(product);
        });
    }

    public Optional<Product> partialUpdateProduct(String id, Product productDetails) {
        return productRepository.findById(id).map(product -> {
            if (productDetails.getName() != null) {
                product.setName(productDetails.getName());
            }
            if (productDetails.getSku() != null) {
                product.setSku(productDetails.getSku());
            }
            if (productDetails.getBrand() != null) {
                product.setBrand(productDetails.getBrand());
            }
            // Add other fields similarly

            // Save the updated product
            return productRepository.save(product);
        });
    }

    public List<Product> searchProductsByName(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }


//    public boolean removeProductFromCart(String productId, String username) {
//        Optional<Cart> cartOptional = cartRepository.findByUsername(username);
//        if (cartOptional.isPresent()) {
//            Cart cart = cartOptional.get();
//            List<Product> products = cart.getProducts();
//            Optional<Product> productToRemove = products.stream()
//                    .filter(product -> product.getId().equals(productId))
//                    .findFirst();
//            if (productToRemove.isPresent()) {
//                products.remove(productToRemove.get());
//                cart.setProducts(products);
//                cartRepository.save(cart);
//                return true;
//            }
//        }
//        return false;
//    }

}
