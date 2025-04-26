package in.grocerymart.grocerymart_backend.service;

import in.grocerymart.grocerymart_backend.io.UserRequest;
import in.grocerymart.grocerymart_backend.io.UserResponse;

public interface UserService {

    UserResponse registerUser(UserRequest request);

    String findByUserId();
}

