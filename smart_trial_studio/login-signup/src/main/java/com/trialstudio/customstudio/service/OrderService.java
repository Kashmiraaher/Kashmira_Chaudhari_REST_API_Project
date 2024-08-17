package com.trialstudio.customstudio.service;

import com.trialstudio.customstudio.model.Order;
import com.trialstudio.customstudio.model.User;
import com.trialstudio.customstudio.repository.OrderRepository;
import com.trialstudio.customstudio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    public void placeOrder(String userId, Order order) {
        // Set the current date and time
        order.setOrderDate(LocalDateTime.now());
        // Save order to the Order collection
        orderRepository.save(order);

        // Fetch the user
        Optional<User> userOptional = userRepository.findById(userId);
        if (((Optional<?>) userOptional).isPresent()) {
            User user = userOptional.get();

            // Add order to the user's orders list
            List<Order> userOrders = user.getOrders();
            userOrders.add(order);

            // Update the user in the User collection
            userRepository.save(user);
        } else {
            // Handle the case where the user is not found
            throw new RuntimeException("User not found");
        }
    }

    public List<Order> getOrdersByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user!=null) {
            return user.getOrders();
        } else {
            throw new RuntimeException("User not found");
        }
    }
    public void saveOrder(Order order) {
        // Save the order to the database
        orderRepository.save(order);
    }
}



//@Service
//public class OrderService {
//
//    @Autowired
//    private OrderRepository orderRepository;
//
//    public Order placeOrder(Order order) {
//        // You can add additional business logic here, e.g., validating order details, calculating totals, etc.
//        return orderRepository.save(order);
//    }
//}
