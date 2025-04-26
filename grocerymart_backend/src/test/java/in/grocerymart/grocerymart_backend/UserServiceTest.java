package in.grocerymart.grocerymart_backend;

import in.grocerymart.grocerymart_backend.entity.UserEntity;
import in.grocerymart.grocerymart_backend.io.UserRequest;
import in.grocerymart.grocerymart_backend.io.UserResponse;
import in.grocerymart.grocerymart_backend.repository.UserRepository;
import in.grocerymart.grocerymart_backend.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private UserRequest userRequest;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userRequest = new UserRequest("John Doe", "john@example.com", "password123");
        userEntity = new UserEntity("1", "John Doe", "john@example.com", "encodedPassword");
    }

    @Test
    void testRegisterUser_Success() {
        when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userRequest.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserResponse response = userService.registerUser(userRequest);

        assertNotNull(response);
        assertEquals("John Doe", response.getName());
        assertEquals("john@example.com", response.getEmail());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testRegisterUser_AlreadyExists() {
        when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(Optional.of(userEntity));

        assertThrows(RuntimeException.class, () -> userService.registerUser(userRequest));
        verify(userRepository, never()).save(any(UserEntity.class));
    }
}
