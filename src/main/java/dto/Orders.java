package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Orders {

    private String orderID;
    private LocalDate orderDate;
    private String customerID;
    private List<OrderDetail> orderDetails;
}
