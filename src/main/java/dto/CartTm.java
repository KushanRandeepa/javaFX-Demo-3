package dto;

import lombok.*;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString


public class CartTm {

    private String itemCode;
    private String description;
    private Integer qty;
    private Double unitPrice;
    private Double total;

}
