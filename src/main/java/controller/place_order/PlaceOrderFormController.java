package controller.place_order;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.customer.CustomerController;
import controller.item.ItemController;
import dto.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;


import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {

    @FXML
    public Label lblUniPrice;

    @FXML
    public TextField txtOrderID;
    ObservableList<CartTm> cartTable = FXCollections.observableArrayList();
    @FXML
    private TableColumn<?, ?> colDescription;
    @FXML
    private TableColumn<?, ?> colItemCode;
    @FXML
    private TableColumn<?, ?> colQty;
    @FXML
    private TableColumn<?, ?> colTotal;
    @FXML
    private TableColumn<?, ?> colUnitPrice;
    @FXML
    private JFXComboBox<String> comboxCustId;
    @FXML
    private JFXComboBox<String> comboxItemcode;
    @FXML
    private Label lblNetTotal;
    @FXML
    private Label lblOrderdate;
    @FXML
    private Label lblOrdertime;
    @FXML
    private TableView<CartTm> tblOrder;
    @FXML
    private JFXTextField txtCustAddress;
    @FXML
    private JFXTextField txtQty;
    @FXML
    private JFXTextField txtStock;
    @FXML
    private JFXTextField txtCustName;
    @FXML
    private JFXTextField txtDescription;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateAndTime();
        loadCustomerIds();
        loadItemIds();

        comboxCustId.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            if (t1 != null) {
                searchCustDetails(t1);
            }
        });

        comboxItemcode.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            if (t1 != null) {
                searchItemDetails(t1);
            }

        });
    }

    private void searchItemDetails(String t1) {
        Item item = ItemController.getInstance().searchItem(t1);
        txtDescription.setText(item.getDescription());
        txtStock.setText(String.valueOf(item.getQty()));
        lblUniPrice.setText(String.valueOf(item.getUnitPrice()));

    }

    private void searchCustDetails(String t1) {
        Customer customer = CustomerController.getInstance().searchCustomer(t1);
        txtCustName.setText(customer.getName());
        txtCustAddress.setText(customer.getAddress());

    }

    private void loadItemIds() {
        List<String> itemIdsList = ItemController.getInstance().itemIdslist();
        ObservableList<String> itemIds = FXCollections.observableArrayList();
        itemIdsList.forEach(obj -> itemIds.add(obj));

        comboxItemcode.setItems(itemIds);
    }

    private void loadCustomerIds() {
        List<String> custIdsList = CustomerController.getInstance().getAllCustomerIds();
        ObservableList<String> objects = FXCollections.observableArrayList();
        custIdsList.forEach(id -> objects.add(id));
        comboxCustId.setItems(objects);
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        lblOrderdate.setText(simpleDateFormat.format(date));

        //=---------------------------------------------

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime now = LocalTime.now();
            lblOrdertime.setText(now.getHour() + ":" + now.getMinute() + ":" + now.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();


    }

    @FXML
    void btnOnActionAddToCart(ActionEvent event) {

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        String itemCode = comboxItemcode.getValue();
        String description = txtDescription.getText();
        Integer qty = Integer.valueOf(txtQty.getText());
        Double unitPrice = Double.valueOf(lblUniPrice.getText());
        Double total = qty * unitPrice;
        Integer itemStock = Integer.valueOf(txtStock.getText());

        if (itemStock < qty) {
            new Alert(Alert.AlertType.WARNING, "Invalied QTY").show();
        } else {
            cartTable.add(new CartTm(itemCode, description, qty, unitPrice, total));
            tblOrder.setItems(cartTable);
            calculateTotal();
        }
    }

    private void calculateTotal() {

        Double netTotal = 0.0;
        for (CartTm cartTm : cartTable) {
            netTotal += cartTm.getTotal();
        }
        lblNetTotal.setText(String.valueOf(netTotal));


    }

    @FXML
    void btnOnActionPlaceOrder(ActionEvent event) {

        String orderID = txtOrderID.getText();
        LocalDate date = LocalDate.parse(lblOrderdate.getText());
        String custID = comboxCustId.getValue();
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();

        cartTable.forEach(obj ->
                orderDetails.add(new OrderDetail(
                        txtOrderID.getText(),
                        obj.getItemCode(),
                        obj.getQty(),
                        0.0))
        );
        Orders orders = new Orders(orderID, date, custID, orderDetails);
        System.out.println(orders);
        try {
            boolean isPlaceOrder = new OrderController().placeOrder(orders);
            if(isPlaceOrder){
                new Alert(Alert.AlertType.CONFIRMATION,"Order is Placed Successfully!").show();
            }else {
                new Alert(Alert.AlertType.ERROR).show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
