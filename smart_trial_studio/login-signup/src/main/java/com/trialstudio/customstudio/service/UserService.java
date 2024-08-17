package com.trialstudio.customstudio.service;

import com.trialstudio.customstudio.model.Order;
import com.trialstudio.customstudio.model.Product;
import com.trialstudio.customstudio.model.User;
import com.trialstudio.customstudio.repository.OrderRepository;
import com.trialstudio.customstudio.repository.ProductRepository;
import com.trialstudio.customstudio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> addProductToCart(String username, String productId) {
        User user = userRepository.findByUsername(username);
        Product product = productRepository.findById(productId).orElse(null);

        if (user != null && product != null) {
            user.getCart().add(product);
            userRepository.save(user);
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public Optional<User> addProductToKiosk(String username, String productId) {
        User user = userRepository.findByUsername(username);
        Product product = productRepository.findById(productId).orElse(null);

        if (user != null && product != null) {
            user.getKiosk().add(product);
            userRepository.save(user);
            return Optional.of(user);
        }
        return Optional.empty();
    }

//    public Optional<User> removeProductFromCart(String username, String productId) {
//        User user = userRepository.findByUsername(username);
//        Product product = productRepository.findById(productId).orElse(null);
//
//        if (user != null && product != null && user.getCart().contains(product)) {
//            user.getCart().remove(product);
//            userRepository.save(user);
//            return Optional.of(user);
//        }
//        return Optional.empty();
//    }

    public boolean removeProductFromCart(String username, String productId) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return false;
        }

        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return false;
        }

        boolean productRemoved = user.getCart().removeIf(p -> p.getId().equals(productId));
        if (productRemoved) {
            userRepository.save(user);
            return true;
        }

        return false;
    }



    public Optional<List<Product>> getCartItems(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return Optional.of(user.getCart());
        }
        return Optional.empty();
    }

    public int getCartItemCount(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user.getCart().size();
        }
        return 0; // or throw exception if user not found
    }

    public int getKioskItemCount(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user.getKiosk().size();
        }
        return 0; // or throw exception if user not found
    }

//    Order


    public Optional<User> placeOrder(String username, Order order) {
        // Find the user by username
        User user = userRepository.findByUsername(username);

        if (user != null) {
            order.setOrderDate(LocalDateTime.now());
            // Add the order to the user's orders list
            user.getOrders().add(order);

            // Save the order in the Order collection
            orderRepository.save(order);

            // Save the user with the updated orders list
            userRepository.save(user);

            return Optional.of(user);
        }
        return Optional.empty();
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
