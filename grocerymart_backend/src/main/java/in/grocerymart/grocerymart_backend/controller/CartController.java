package in.grocerymart.grocerymart_backend.controller;

import in.grocerymart.grocerymart_backend.io.CartRequest;
import in.grocerymart.grocerymart_backend.io.CartResponse;
import in.grocerymart.grocerymart_backend.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/cart")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;
    @PostMapping
    public CartResponse addToCart(@RequestBody CartRequest request) {
        String groceryId = request.getGroceryId();
        if (groceryId == null || groceryId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Grocery Id not found");
        }
        return cartService.addToCart(request);
    }

    @GetMapping
    public CartResponse getCart() {
        return cartService.getCart();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCart() {
        cartService.clearCart();
    }

    @PostMapping("/remove")
    public CartResponse removeFromCart(@RequestBody CartRequest request) {
        String groceryId = request.getGroceryId();
        if (groceryId == null || groceryId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Grocery Id not found");
        }
        return cartService.removeFromCart(request);
    }

}

