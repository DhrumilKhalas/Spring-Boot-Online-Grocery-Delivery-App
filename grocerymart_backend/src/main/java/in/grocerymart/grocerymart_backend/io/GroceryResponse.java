package in.grocerymart.grocerymart_backend.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroceryResponse {
    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private double price;
    private String category;
}

