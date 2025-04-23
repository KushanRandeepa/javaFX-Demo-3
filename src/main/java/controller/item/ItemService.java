package controller.item;

import dto.Customer;
import dto.Item;
import dto.OrderDetail;
import javafx.collections.ObservableList;

import java.util.List;

public interface ItemService {

    boolean addItem(Item item);
    boolean updateItem(Item item);
    Item searchItem(String id);
    boolean deleteItem(String id);
    ObservableList<Item> getAllItem();
    List<String> itemIdslist();

    boolean updatestock(List<OrderDetail> orderDetails);

}
