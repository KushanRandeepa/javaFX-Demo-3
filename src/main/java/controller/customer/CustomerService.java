package controller.customer;

import dto.Customer;
import javafx.collections.ObservableList;

import java.util.List;

public interface CustomerService {
    boolean addCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
    Customer searchCustomer(String id);
    boolean deleteCustomer(String id);
    ObservableList<Customer>getAllCustomer();

    List<String> getAllCustomerIds();
}
