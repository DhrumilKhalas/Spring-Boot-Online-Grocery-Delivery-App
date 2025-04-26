package in.grocerymart.grocerymart_backend.controller;

import in.grocerymart.grocerymart_backend.io.UserRequest;
import in.grocerymart.grocerymart_backend.io.UserResponse;
import in.grocerymart.grocerymart_backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@RequestBody UserRequest request) {
        return userService.registerUser(request);
    }
}

