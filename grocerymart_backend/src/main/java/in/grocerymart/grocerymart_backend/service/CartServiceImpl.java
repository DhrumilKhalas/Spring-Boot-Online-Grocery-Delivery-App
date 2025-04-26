package in.grocerymart.grocerymart_backend.service;

import in.grocerymart.grocerymart_backend.entity.CartEntity;
import in.grocerymart.grocerymart_backend.io.CartRequest;
import in.grocerymart.grocerymart_backend.io.CartResponse;
import in.grocerymart.grocerymart_backend.repository.CartRespository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService{

    private final CartRespository cartRespository;
    private final UserService userService;
    @Override
    public CartResponse addToCart(CartRequest request) {
        String loggedInUserId = userService.findByUserId();
        Optional<CartEntity> cartOptional = cartRespository.findByUserId(loggedInUserId);
        CartEntity cart = cartOptional.orElseGet(() -> new CartEntity(loggedInUserId, new HashMap<>()));
        Map<String, Integer> cartItems = cart.getItems();
        cartItems.put(request.getGroceryId(), cartItems.getOrDefault(request.getGroceryId(), 0) + 1);
        cart.setItems(cartItems);
        cart = cartRespository.save(cart);
        return convertToResponse(cart);
    }

    @Override
    public CartResponse getCart() {
        String loggedInUserId = userService.findByUserId();
        CartEntity entity = cartRespository.findByUserId(loggedInUserId)
                .orElse(new CartEntity(null, loggedInUserId, new HashMap<>()));
        return convertToResponse(entity);
    }

    @Override
    public void clearCart() {
        String loggedInUserId = userService.findByUserId();
        cartRespository.deleteByUserId(loggedInUserId);
    }

    @Override
    public CartResponse removeFromCart(CartRequest cartRequest) {
        String loggedInUserId = userService.findByUserId();
        CartEntity entity = cartRespository.findByUserId(loggedInUserId)
                .orElseThrow(() -> new RuntimeException("Cart is not found"));
        Map<String, Integer> cartItems = entity.getItems();
        if (cartItems.containsKey(cartRequest.getGroceryId())) {
            int currentQty = cartItems.get(cartRequest.getGroceryId());
            if (currentQty > 0) {
                cartItems.put(cartRequest.getGroceryId(), currentQty - 1);
            } else {
                cartItems.remove(cartRequest.getGroceryId());
            }
            entity = cartRespository.save(entity);
        }
        return convertToResponse(entity);
    }

    private CartResponse convertToResponse(CartEntity cartEntity) {
        return CartResponse.builder()
                .id(cartEntity.getId())
                .userId(cartEntity.getUserId())
                .items(cartEntity.getItems())
                .build();
    }
}

