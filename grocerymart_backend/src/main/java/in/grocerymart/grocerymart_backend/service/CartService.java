package in.grocerymart.grocerymart_backend.service;

import in.grocerymart.grocerymart_backend.io.CartRequest;
import in.grocerymart.grocerymart_backend.io.CartResponse;

public interface CartService {

    CartResponse addToCart(CartRequest request);

    CartResponse getCart();

    void clearCart();

    CartResponse removeFromCart(CartRequest cartRequest);
}

