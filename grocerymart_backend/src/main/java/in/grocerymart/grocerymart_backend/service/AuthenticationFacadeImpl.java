package in.grocerymart.grocerymart_backend.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;

@Component
public class AuthenticationFacadeImpl implements AutthenticationFacade{
    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}

