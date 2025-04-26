package in.grocerymart.grocerymart_backend.io;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItem {

    private String groceryId;
    private int quantity;
    private double price;
    private String category;
    private String imageUrl;
    private String description;
    private String name;
}

