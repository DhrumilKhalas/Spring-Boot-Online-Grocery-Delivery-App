package in.grocerymart.grocerymart_backend.service;

import in.grocerymart.grocerymart_backend.entity.GroceryEntity;
import in.grocerymart.grocerymart_backend.io.GroceryRequest;
import in.grocerymart.grocerymart_backend.io.GroceryResponse;
import in.grocerymart.grocerymart_backend.repository.GroceryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GroceryServiceImpl implements GroceryService {

    @Autowired
    private S3Client s3Client;
    @Autowired
    private GroceryRepository groceryRepository;

    @Value("${aws.s3.bucketname}")
    private String bucketName;

    @Override
    public String uploadFile(MultipartFile file) {
        String filenameExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
        String key = UUID.randomUUID().toString()+"."+filenameExtension;
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .acl("public-read")
                    .contentType(file.getContentType())
                    .build();
            PutObjectResponse response = s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

            if (response.sdkHttpResponse().isSuccessful()) {
                return "https://"+bucketName+".s3.amazonaws.com/"+key;
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File upload failed");
            }
        }catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occured while uploading the file");
        }
    }

    @Override
    public GroceryResponse addGrocery(GroceryRequest request, MultipartFile file) {
        GroceryEntity newGroceryEntity = convertToEntity(request);
        String imageUrl = uploadFile(file);
        newGroceryEntity.setImageUrl(imageUrl);
        newGroceryEntity = groceryRepository.save(newGroceryEntity);
        return convertToResponse(newGroceryEntity);
    }

    private GroceryEntity convertToEntity(GroceryRequest request) {
        return GroceryEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .price(request.getPrice())
                .build();

    }

    private GroceryResponse convertToResponse(GroceryEntity entity) {
        return GroceryResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .category(entity.getCategory())
                .price(entity.getPrice())
                .imageUrl(entity.getImageUrl())
                .build();
    }

    @Override
    public List<GroceryResponse> readGroceries() {
        List<GroceryEntity> databaseEntries = groceryRepository.findAll();
        return databaseEntries.stream().map(object -> convertToResponse(object)).collect(Collectors.toList());
    }

    @Override
    public GroceryResponse readGrocery(String id) {
        GroceryEntity existingGrocery = groceryRepository.findById(id).orElseThrow(() -> new RuntimeException("Grocery not found for the id:"+id));
        return convertToResponse(existingGrocery);
    }

    @Override
    public boolean deleteFile(String filename) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(filename)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
        return true;
    }

    @Override
    public void deleteGrocery(String id) {
        GroceryResponse response = readGrocery(id);
        String imageUrl = response.getImageUrl();
        String filename = imageUrl.substring(imageUrl.lastIndexOf("/")+1);
        boolean isFileDelete = deleteFile(filename);
        if (isFileDelete) {
            groceryRepository.deleteById(response.getId());
        }
    }

}


