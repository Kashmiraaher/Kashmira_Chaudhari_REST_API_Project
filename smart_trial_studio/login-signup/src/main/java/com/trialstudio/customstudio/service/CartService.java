package com.trialstudio.customstudio.service;

        import com.trialstudio.customstudio.model.Product;
        import com.trialstudio.customstudio.repository.ProductRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import java.util.ArrayList;
        import java.util.List;

@Service
public class CartService {

    @Autowired
    private ProductRepository productRepository;
    private List<Product> cartItems = new ArrayList<>();

    public void addToCart(String productId) {
      Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            cartItems.add(product);
        }
    }

    public void removeFromCart(String productId) {
        cartItems.removeIf(product -> product.getId().equals(productId));
    }

    public List<Product> getCartItems() {
        return new ArrayList<>(cartItems);
    }

    public int getCartItemCount() {
        return cartItems.size();
    }

    public void clearCart() {
        cartItems.clear();
    }
}
