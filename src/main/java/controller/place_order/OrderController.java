package controller.place_order;

import DB.DbConnection;
import controller.item.ItemController;
import dto.OrderDetail;
import dto.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderController {

    public boolean placeOrder(Orders orders) throws SQLException {
        String SQL="INSERT INTO orders VALUES(?,?,?)";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setObject(1,orders.getOrderID());
        preparedStatement.setObject(2,orders.getOrderDate());
        preparedStatement.setObject(3,orders.getCustomerID());
        boolean isOrderAdd = preparedStatement.executeUpdate() > 0;
        if (isOrderAdd){
            boolean isOrderDetailAdd = new OrderDetailController().addOrderDetails(orders.getOrderDetails());
            if (isOrderDetailAdd){
                boolean isUpdateStock = ItemController.getInstance().updatestock(orders.getOrderDetails());
                if (isUpdateStock){
                    return  true;
                }
            }
        }
            return false;
    }
}
