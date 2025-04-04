package controller.order;

import DB.DbConnection;
import dto.Orders;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderController implements OrderService{

    @Override
    public ObservableList<Orders> getAllOrders() {
        ObservableList<Orders> orderOBList= FXCollections.observableArrayList();
        String SQL="SELECT * FROM Orders";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL);
            while(resultSet.next()){
               orderOBList.add(new Orders(
                       resultSet.getString(1),
                       resultSet.getDate(2).toLocalDate(),
                       resultSet.getString(3)
               )
               );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderOBList;
    }
}
