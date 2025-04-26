package in.grocerymart.grocerymart_backend.io;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthenticationResponse {
    private String email;
    private String token;
}

