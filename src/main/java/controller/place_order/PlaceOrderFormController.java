package controller.place_order;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.customer.CustomerController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Duration;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {

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
    private JFXComboBox<?> comboxItemcode;

    @FXML
    private Label lbOrderId;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderdate;

    @FXML
    private Label lblOrdertime;

    @FXML
    private TableView<?> tblOrder;

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
    }

    private void loadCustomerIds() {
        List<String > custIdsList=  CustomerController.getInstance().getAllCustomerIds();
        ObservableList<String> objects = FXCollections.observableArrayList();
        custIdsList.forEach(id->{
            objects.add(id);
        });
        comboxCustId.setItems(objects);
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        lblOrderdate.setText(simpleDateFormat.format(date));

        //=---------------------------------------------

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO , e->{
            LocalTime now = LocalTime.now();
            lblOrdertime.setText(now.getHour()+":"+ now.getMinute()+":"+now.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();


    }

    @FXML
    void btnOnActionAddToCart(ActionEvent event) {

    }

    @FXML
    void btnOnActionPlaceOrder(ActionEvent event) {

    }


}
