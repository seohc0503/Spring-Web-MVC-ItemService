package hello.itemservice.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// 상품 도메인 개발
// 1.상품 객체

@Data
public class Item {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
