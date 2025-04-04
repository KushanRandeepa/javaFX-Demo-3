package controller.order;

import com.jfoenix.controls.JFXTextField;
import dto.Orders;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderFormController implements Initializable {
    OrderService orderService=new OrderController();
    @FXML
    private TableColumn<?, ?> colCustID;

    @FXML
    private TableColumn<?, ?> colOrderDate;

    @FXML
    private TableColumn<?, ?> colOrderID;

    @FXML
    private DatePicker dateOrder;

    @FXML
    private TableView<Orders> tableOrder;



    @FXML
    private JFXTextField txtCustID;

    @FXML
    private JFXTextField txtOrderID;

    @FXML
    void btnAddOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnReloadOnaction(ActionEvent event) {
        loadTable();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCustID.setCellValueFactory(new PropertyValueFactory<>("custID"));
        colOrderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));

        tableOrder.getSelectionModel().selectedItemProperty().addListener((observableValue, oldVal, newVal) ->{
            if(newVal!=null){
            addValueToText(newVal);
            }
        } );
        loadTable();
    }

    private void addValueToText(Orders newVal) {
        txtCustID.setText(newVal.getCustID());
        txtOrderID.setText(newVal.getOrderID());
        dateOrder.setValue(newVal.getOrderDate());
    }

    public void loadTable(){
        tableOrder.setItems(orderService.getAllOrders());
    }
}
