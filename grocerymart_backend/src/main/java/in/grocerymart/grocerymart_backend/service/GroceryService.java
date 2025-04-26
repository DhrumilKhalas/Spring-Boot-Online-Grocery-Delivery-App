package in.grocerymart.grocerymart_backend.service;

import in.grocerymart.grocerymart_backend.io.GroceryRequest;
import in.grocerymart.grocerymart_backend.io.GroceryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GroceryService {
    String uploadFile(MultipartFile file);

    GroceryResponse addGrocery(GroceryRequest request, MultipartFile file);

    List<GroceryResponse> readGroceries();

    GroceryResponse readGrocery(String id);

    boolean deleteFile(String filename);

    void deleteGrocery(String id);

}




