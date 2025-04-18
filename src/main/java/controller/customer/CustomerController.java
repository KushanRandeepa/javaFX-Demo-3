package controller.customer;

import DB.DbConnection;
import dto.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerController implements CustomerService {

    private  static CustomerController instance;

    private CustomerController(){}

    public static  CustomerController getInstance(){
        return instance==null?instance=new CustomerController():instance;
    }

    @Override
    public boolean addCustomer(Customer customer) {
        String SQL = "INSERT INTO Customer VALUES(?,?,?,?,?,?,?,?,?)";
        try {
            Object execute = CrudUtil.execute(SQL,
                    customer.getId(),
                    customer.getTitle(),
                    customer.getName(),
                    customer.getDob(),
                    customer.getSalary(),
                    customer.getAddress(),
                    customer.getCity(),
                    customer.getProvince(),
                    customer.getPostalCode()
            );
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean updateCustomer(Customer customer) {
        String SQL = "UPDATE  Customer SET CustTitle=? , CustName=? , DOB=? , salary=? , CustAddress=? ,  City=? , Province=? , PostalCode=? WHERE CustID=?";
        try {
            boolean execute = CrudUtil.execute(SQL,
                    customer.getTitle(),
                    customer.getName(),
                    customer.getDob(),
                    customer.getSalary(),
                    customer.getAddress(),
                    customer.getCity(),
                    customer.getProvince(),
                    customer.getPostalCode(),
                    customer.getId()
            );
            return execute;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteCustomer(String id) {
        String SQL = "DELETE FROM CUSTOMER  WHERE CustID='" + id + "' ";

        try {
            Object execute = CrudUtil.execute(SQL);
                return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Customer searchCustomer(String id) {
        String SQL="SELECT * FROM Customer WHERE CustID='" + id + "'";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL);

            while (resultSet.next()){
                return new Customer(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4).toLocalDate(),
                        resultSet.getDouble(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ObservableList<Customer> getAllCustomer() {
        ObservableList<Customer> customerObList = FXCollections.observableArrayList();
        String SQL="SELECT * FROM Customer";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL);

            while (resultSet.next()) {
                customerObList.add(new Customer(
                        resultSet.getString("CustID"),
                        resultSet.getString("CustTitle"),
                        resultSet.getString("CustName"),
                        resultSet.getDate("DOB").toLocalDate(),
                        resultSet.getDouble("salary"),
                        resultSet.getString("CustAddress"),
                        resultSet.getString("City"),
                        resultSet.getString("Province"),
                        resultSet.getString("PostalCode")
                        )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerObList;
    }
@Override
    public List<String> getAllCustomerIds(){
        ArrayList<String> custIdsList = new ArrayList<>();

        ObservableList<Customer> allcustomer=getAllCustomer();
        allcustomer.forEach(obj->{
            custIdsList.add(obj.getId());
    });
    return custIdsList;
    }

}
