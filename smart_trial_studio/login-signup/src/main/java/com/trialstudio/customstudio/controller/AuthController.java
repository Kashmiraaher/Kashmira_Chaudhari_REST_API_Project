package com.trialstudio.customstudio.controller;

import com.trialstudio.customstudio.model.Order;
import com.trialstudio.customstudio.model.Product;
import com.trialstudio.customstudio.model.User;
import com.trialstudio.customstudio.service.OrderService;
import com.trialstudio.customstudio.service.ProductService;
import com.trialstudio.customstudio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.trialstudio.customstudio.service.CartService;


import java.util.List;
import java.util.Optional;
import java.util.Map;


@Controller
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> authenticateUser(@RequestBody User loginUser) {
        String username = loginUser.getUsername();
        String password = loginUser.getPassword();

        User user = userService.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.ok(Map.of("message", "User authenticated successfully"));
        } else {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid username or password"));
        }
    }


    @PostMapping("/addproduct")
    @ResponseBody
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
         productService.saveProduct(product);
         return ResponseEntity.ok("Product added successfully");
    }

    @GetMapping("/getallproducts")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/getproductbyid")
    public ResponseEntity<Product> getProductByID(@RequestParam String id){
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/deleteproductbyid")
    public ResponseEntity<?> deleteProduct(@RequestParam String id){
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        List<Product> products = productService.searchProducts(keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String showSignupPage() {
        return "signup";
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable String id, @RequestBody Product productDetails) {
        Optional<Product> updatedProduct = productService.updateProduct(id, productDetails);
        if (updatedProduct.isPresent()) {
            return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateProductPartial(@PathVariable String id, @RequestBody Product productDetails) {
        Optional<Product> updatedProduct = productService.partialUpdateProduct(id, productDetails);
        if (updatedProduct.isPresent()) {
            return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/cart")  // To add the product into the cart
    @ResponseBody
    public ResponseEntity<String> addToCart(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String productId = request.get("productId");

        // Implement logic to add product to cart for the user
        // Example:
        Optional<User> user = userService.addProductToCart(username, productId);
        if (user.isPresent()) {
            return ResponseEntity.ok("Product added to cart");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
        }
    }

    @PostMapping("/kiosk")  // To add the product into the KIOSK
    @ResponseBody
    public ResponseEntity<String> addToKiosk(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String productId = request.get("productId");

        // Implement logic to add product to cart for the user
        // Example:
        Optional<User> user = userService.addProductToKiosk(username, productId);
        if (user.isPresent()) {
            return ResponseEntity.ok("Product added to Kiosk");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
        }
    }
//    @PostMapping("/cart/remove")
//    @ResponseBody
//    public ResponseEntity<String> removeFromCart(@RequestBody Map<String, String> request) {
//        String username = request.get("username");
//        String productId = request.get("productId");
//
//        Optional<User> user = userService.removeProductFromCart(username, productId);
//        if (user.isPresent()) {
//            return ResponseEntity.ok("Product removed from cart");
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
//        }
//    }

    @DeleteMapping("/cart/remove/{productId}")
    @ResponseBody
    public ResponseEntity<String> removeFromCart(@PathVariable String productId, @RequestParam String username) {
        boolean isRemoved = userService.removeProductFromCart(username, productId);
        if (isRemoved) {
            return ResponseEntity.ok("Product removed from cart");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product or user not found");
        }
    }

    @GetMapping("/cart/{username}")
    public ResponseEntity<List<Product>> getCartItems(@PathVariable String username) {
        //String username = getUsernameFromSecurityContext();
        Optional<List<Product>> cartItems = userService.getCartItems(username);
        if (cartItems.isPresent()) {
            return ResponseEntity.ok(cartItems.get());
        } else {
            return ResponseEntity.status(400).body(null);
        }
    }

    private String getUsernameFromSecurityContext() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    //Cart count api for browse.html page
    @GetMapping("/cart/count/{username}")
    public ResponseEntity<Integer> getCartItemCount(@PathVariable String username) {
        int itemCount = userService.getCartItemCount(username);
        return ResponseEntity.ok(itemCount);
    }

//    Kiosk API's


    @GetMapping("/kiosk/count")
    public ResponseEntity<Integer> getKioskItemCount() {
        // Assuming you have a method in CartService that gets the count of items in the kiosk cart
        int itemCount = cartService.getCartItemCount();
        return ResponseEntity.ok(itemCount);
    }

    @GetMapping("/searchByName")
    @ResponseBody
    public ResponseEntity<List<Product>> searchProductsByName(@RequestParam String keyword) {
        List<Product> products = productService.searchProductsByName(keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @PostMapping("/kioskcart")
    @ResponseBody
    public ResponseEntity<String> addKioskToCart(@RequestBody Map<String, String> request) {
        cartService.addToCart(request.get("productId"));
        return ResponseEntity.ok("Product added to cart");
    }

    @DeleteMapping("/kioskcart/remove/{productId}")
    @ResponseBody
    public ResponseEntity<String> removeFromCart(@PathVariable String productId) {
        cartService.removeFromCart(productId);
        return ResponseEntity.ok("Product removed from cart");
    }

    @GetMapping("/getkioskcart")
    public ResponseEntity<List<Product>> getKioskCartItems() {
        List<Product> cartItems = cartService.getCartItems();
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }

    @GetMapping("/kioskcart/count")
    public ResponseEntity<Integer> getCartItemCount() {
        int itemCount = cartService.getCartItemCount();
        return ResponseEntity.ok(itemCount);
    }

    @GetMapping("/resetkioskcart")
    public ResponseEntity<String> clearKisokCart(){
        cartService.clearCart();
        return ResponseEntity.ok("Cart has been reset");
    }

//    Order API's



//    @PostMapping("/placeOrder")
//    public ResponseEntity<Order> placeOrder(@RequestBody Order order) {
//        Order savedOrder = orderService.placeOrder(order);
//        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
//    }
//
//    @PostMapping("/placeOrder")
//    public String placeOrder(@RequestParam String userId, @ModelAttribute Order order) {
//        orderService.placeOrder(userId, order);
//        return "redirect:/orderConfirmation";
//    }

    @PostMapping("/placeOrder/{username}")
    public  ResponseEntity<String> placeOrder(@PathVariable String username, @RequestBody Order order) {
        userService.placeOrder(username, order);
        //orderService.placeOrder(username, order);
        return ResponseEntity.ok("Order Placed");
    }

    @GetMapping("orders/{username}")
    public ResponseEntity<List<Order>> getOrders(@PathVariable String username) {
        List<Order> orders = orderService.getOrdersByUsername(username);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/placekioskorder")
    public ResponseEntity<String> placeOrder(@RequestBody Order order) {
        orderService.saveOrder(order); // Assuming saveOrder method is implemented in the OrderService
        return ResponseEntity.ok("Order Placed Successfully");
    }

//   browser clear cart: confirmation page ---> browser page

    @PostMapping("/clearcart")
    public ResponseEntity<Void> clearCart(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        User user = userService.findByUsername(username);
        if (user != null) {
            user.getCart().clear();
            userService.save(user); // Save the user to update the cart in the database
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


//    kiosk clear cart: kioskconfirmation page ---> kioskbrowser page
    @PostMapping("/resetkioskcart")
    public ResponseEntity<Void> resetKioskCart() {
        cartService.clearCart(); // Assuming clearCart is a method in cartService that clears the kiosk cart
        return ResponseEntity.ok().build();
    }



}

