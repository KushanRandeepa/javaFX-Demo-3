package controller.item;

import dto.Customer;
import dto.Item;
import javafx.collections.ObservableList;

public interface ItemService {

    boolean addItem(Item item);
    boolean updateItem(Item item);
    Customer searchItem(String id);
    boolean deleteItem(String id);
    ObservableList<Item> getAllItem();


}
