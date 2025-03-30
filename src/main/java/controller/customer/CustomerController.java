package controller.customer;

import DB.DbConnection;
import dto.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerController implements CustomerService {

    @Override
    public boolean addCustomer(Customer customer) {
        boolean isAdded;
        try {
            String SQL = "INSERT INTO Customer VALUES(?,?,?,?,?,?,?,?,?)";
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setObject(1, customer.getId());
            preparedStatement.setObject(2, customer.getTitle());
            preparedStatement.setObject(3, customer.getName());
            preparedStatement.setObject(4, customer.getDob());
            preparedStatement.setObject(5, customer.getSalary());
            preparedStatement.setObject(6, customer.getAddress());
            preparedStatement.setObject(7, customer.getCity());
            preparedStatement.setObject(8, customer.getProvince());
            preparedStatement.setObject(9, customer.getPostalCode());
            isAdded = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (isAdded) {
            return true;
        }
        return false;
    }


    @Override
    public boolean updateCustomer(Customer customer) {
        boolean isUpdated;

        String SQL = "UPDATE  Customer SET CustTitle=? , CustName=? , DOB=? , salary=? , CustAddress=? ,  City=? , Province=? , PostalCode=? WHERE CustID=?";
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setObject(1, customer.getTitle());
            preparedStatement.setObject(2, customer.getName());
            preparedStatement.setObject(3, customer.getDob());
            preparedStatement.setObject(4, customer.getSalary());
            preparedStatement.setObject(5, customer.getAddress());
            preparedStatement.setObject(6, customer.getCity());
            preparedStatement.setObject(7, customer.getProvince());
            preparedStatement.setObject(8, customer.getPostalCode());
            preparedStatement.setObject(9, customer.getId());
            isUpdated = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (isUpdated) {
            return true;
        }
        return false;
    }


    @Override
    public boolean deleteCustomer(String id) {
        boolean isDeleted;
        try {
            String SQL = "DELETE FROM CUSTOMER  WHERE CustID='" + id + "' ";
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            isDeleted = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (isDeleted) {
            return true;
        }
        return false;
    }


    @Override
    public Customer searchCustomer(String id) {
        return null;
    }

    ObservableList<Customer> customerObList = FXCollections.observableArrayList();

    @Override
    public ObservableList<Customer> getAllCustomer() {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            System.out.println(connection);
            PreparedStatement preparedStatement = connection.prepareStatement("select * from customer");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Customer customer = new Customer(
                        resultSet.getString("CustID"),
                        resultSet.getString("CustTitle"),
                        resultSet.getString("CustName"),
                        resultSet.getDate("DOB").toLocalDate(),
                        resultSet.getDouble("salary"),
                        resultSet.getString("CustAddress"),
                        resultSet.getString("City"),
                        resultSet.getString("Province"),
                        resultSet.getString("PostalCode")
                );
                customerObList.add(customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerObList;
    }
}
