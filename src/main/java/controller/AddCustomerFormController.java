package controller;

import DB.DbConnection;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddCustomerFormController implements Initializable {

    public TableColumn colTitle;
    public TableColumn colSalary;
    public TableColumn colDob;
    public TableColumn colCity;
    public TableColumn colProvince;
    public TableColumn colPCode;
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtSalary;
    public JFXTextField txtCity;
    public JFXTextField txtPCode;
    public JFXTextField txtProvince;
    @FXML
    private TableColumn colAddress;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colName;
    @FXML
    private JFXComboBox<String> combName;
    @FXML
    private DatePicker dateDob;
    @FXML
    private TableView<Customer> tableCustomer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        ObservableList<String> titleList = FXCollections.observableArrayList();
        titleList.add("MR.");
        titleList.add("MISS");
        titleList.add("MRS");
        combName.setItems(titleList);

        tableCustomer.getSelectionModel().selectedItemProperty().addListener((observableValue, oldVal, newVal) -> {
            if (newVal != null) {
                addValueToText(newVal);
            }
        });

        loadTable();
    }

    private void addValueToText(Customer newVal) {
        txtId.setText(newVal.getId());
        txtName.setText(newVal.getName());
        txtAddress.setText(newVal.getAddress());
        txtSalary.setText("" + newVal.getSalary());
        combName.setValue(newVal.getTitle());
        dateDob.setValue(newVal.getDob());
        txtCity.setText(newVal.getCity());
        txtPCode.setText(newVal.getPostalCode());
        txtProvince.setText(newVal.getProvince());
    }


    @FXML
    void btnAddOnAction(ActionEvent event) {

        Customer customer = new Customer(
                txtId.getText(),
                combName.getValue(),
                txtName.getText(),
                dateDob.getValue(),
                Double.parseDouble(txtSalary.getText()),
                txtAddress.getText(),
                txtCity.getText(),
                txtProvince.getText(),
                txtPCode.getText()
        );

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

            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Added").show();
                loadTable();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        clear();
    }

    public void clear() {
        txtId.setText("");
        txtName.setText("");
        txtSalary.setText("");
        txtAddress.setText("");

    }

    private void loadTable() {
        ObservableList<Customer> customerObList = FXCollections.observableArrayList();
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
        tableCustomer.setItems(customerObList);


    }

    @FXML
    void btnReloadOnaction(ActionEvent event) {
        loadTable();
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            String SQL = "DELETE FROM CUSTOMER  WHERE CustID='" + txtId.getText() + "' ";
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Delete successfully").show();
                loadTable();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {


    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        Customer customer = new Customer(
                txtId.getText(),
                combName.getValue(),
                txtName.getText(),
                dateDob.getValue(),
                Double.parseDouble(txtSalary.getText()),
                txtAddress.getText(),
                txtCity.getText(),
                txtProvince.getText(),
                txtPCode.getText()
        );

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

            boolean isUpdate = preparedStatement.executeUpdate() > 0;
            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Updated!").show();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
loadTable();
    }


}
