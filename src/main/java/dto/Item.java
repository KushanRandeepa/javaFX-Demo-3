package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
public class Item {
    private String itemCode;
    private String description;
    private String packSize;
    private Double unitPrice;

    public Item(String itemCode, String description, String packSize, Double unitPrice, Integer qty) {
        this.itemCode = itemCode;
        this.description = description;
        this.packSize = packSize;
        this.unitPrice = unitPrice;
        this.qty = qty;
    }

    private Integer qty;


}
