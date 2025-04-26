package in.grocerymart.grocerymart_backend.repository;

import in.grocerymart.grocerymart_backend.entity.GroceryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroceryRepository extends MongoRepository<GroceryEntity, String> {
}

