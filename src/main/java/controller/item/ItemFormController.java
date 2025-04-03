package controller.item;

import DB.DbConnection;
import com.jfoenix.controls.JFXTextField;
import dto.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {

    ItemService itemService = new ItemController();


    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colPackSize;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colunitprice;

    @FXML
    private TableView<Item> tableItem;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtItemCode;

    @FXML
    private JFXTextField txtPackSize;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtUnitPrice;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colunitprice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        tableItem.getSelectionModel().selectedItemProperty().addListener((observableValue, oldVal, newVal) -> {
            if (newVal != null) {
                addValueToText(newVal);
            }
        });

        loadTable();
    }

    private void addValueToText(Item newVal) {
        txtItemCode.setText(newVal.getItemCode());
        txtDescription.setText(newVal.getDescription());
        txtPackSize.setText(newVal.getPackSize());
        txtUnitPrice.setText("" + newVal.getUnitPrice());
        txtQty.setText("" + newVal.getQty());

    }

    public void loadTable() {
        tableItem.setItems(itemService.getAllItem());
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        boolean isAdd = itemService.addItem(new Item(
                        txtItemCode.getText(),
                        txtDescription.getText(),
                        txtPackSize.getText(),
                        Double.parseDouble(txtUnitPrice.getText()),
                        Integer.parseInt(txtQty.getText())
                )
        );
        if (isAdd){
            new Alert(Alert.AlertType.CONFIRMATION,"Item Added!").show();
            loadTable();
        }else{
            new Alert(Alert.AlertType.ERROR).show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        boolean isDelete = itemService.deleteItem(txtItemCode.getText());
        if(isDelete){
            new Alert(Alert.AlertType.CONFIRMATION,"Item Delete!").show();
            loadTable();
        }else {
            new Alert(Alert.AlertType.ERROR).show();
        }

    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadTable();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        Item item = itemService.searchItem(txtItemCode.getText());
        txtItemCode.setText(item.getItemCode());
        txtDescription.setText(item.getDescription());
        txtPackSize.setText(item.getPackSize());
        txtUnitPrice.setText(""+item.getUnitPrice());
        txtQty.setText(""+item.getQty());
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        boolean isUpdate = itemService.updateItem(new Item(
                        txtItemCode.getText(),
                        txtDescription.getText(),
                        txtPackSize.getText(),
                        Double.parseDouble(txtUnitPrice.getText()),
                        Integer.parseInt(txtQty.getText())
                )
        );
        if (isUpdate) {
            new Alert(Alert.AlertType.CONFIRMATION, "Item Updated!").show();
            loadTable();
        } else {
            new Alert(Alert.AlertType.ERROR).show();
        }

    }


}
