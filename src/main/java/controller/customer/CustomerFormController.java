package controller.customer;

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
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

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


     CustomerService customerService=new CustomerController();

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
        boolean added=customerService.addCustomer(customer);
        if (added){
            new Alert(Alert.AlertType.CONFIRMATION,"Customer Added!");
            clear();
            loadTable();
        }else{
            new Alert(Alert.AlertType.ERROR);

        }
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

        boolean delete=customerService.deleteCustomer(txtId.getText());
        if(delete){
            new Alert(Alert.AlertType.CONFIRMATION,"Customer Delete!");

        }else{
            new Alert(Alert.AlertType.ERROR);

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
        boolean update=customerService.updateCustomer(customer);
        if(update){
            new Alert(Alert.AlertType.CONFIRMATION,"Customer Updated!");
            loadTable();
        }else {
            new Alert(Alert.AlertType.ERROR);
        }
    }


}
