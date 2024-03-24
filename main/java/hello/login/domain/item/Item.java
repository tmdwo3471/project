package hello.login.domain.item;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Data
public class Item {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;
    private UploadFile attachFile;
    private List<UploadFile> imageFiles;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity, List<UploadFile> imageFiles) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.imageFiles = imageFiles;
    }
}