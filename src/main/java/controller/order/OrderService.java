package controller.order;

import dto.Orders;
import javafx.collections.ObservableList;

public interface OrderService {
    ObservableList<Orders> getAllOrders();

}
