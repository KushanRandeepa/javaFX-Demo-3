package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
@AllArgsConstructor
public class Item {
    private String itemCode;
    private String description;
    private String packSize;
    private double unitPrice;
    private int qty;

}
