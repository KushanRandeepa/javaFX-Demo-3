package controller.place_order;

import dto.OrderDetail;
import util.CrudUtil;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailController {

    public boolean addOrderDetails(List<OrderDetail> orderDetails) {
        for (OrderDetail orderDetail:orderDetails){
            boolean isOrderDetailAdd = addOrderDetails(orderDetail);
            if(!isOrderDetailAdd){
                return false;
            }
        }
        return true;
    }

    public boolean addOrderDetails(OrderDetail orderDetail){
        String SQL="INSERT INTO orderdetail VALUES(?,?,?,?)";
        try {
             return CrudUtil.execute(SQL,
                    orderDetail.getOrderID(),
                    orderDetail.getItemCode(),
                    orderDetail.getQty(),
                    orderDetail.getDiscount()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
