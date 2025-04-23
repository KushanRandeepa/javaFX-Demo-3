package controller.item;

import DB.DbConnection;
import dto.Customer;
import dto.Item;
import dto.OrderDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemController implements ItemService{

    private static ItemController instance;

    private ItemController(){}

    public static ItemController getInstance(){
        return instance==null?instance = new ItemController():instance;
    }

    @Override
    public boolean addItem(Item item) {
        String SQL="INSERT INTO Item VALUES(?,?,?,?,?)";
        try {
            Object execute = CrudUtil.execute(SQL,
                    item.getItemCode(),
                    item.getDescription(),
                    item.getPackSize(),
                    item.getUnitPrice(),
                    item.getQty()
            );
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateItem(Item item) {
        String SQL="UPDATE  Item SET Description=? , PackSize=? , UnitPrice=? , QtyOnHand=? WHERE ItemCode=?";

        try {
            Object execute = CrudUtil.execute(SQL,
                    item.getDescription(),
                    item.getPackSize(),
                    item.getUnitPrice(),
                    item.getQty(),
                    item.getItemCode()
            );
            if (execute!=null){
                return true;
            }else {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Item searchItem(String id) {
        String SQL="SELECT * FROM Item WHERE ItemCode='" + id + "'";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL);

            while (resultSet.next()){
              return new Item(
                         resultSet.getString(1),
                         resultSet.getString(2),
                         resultSet.getString(3),
                         resultSet.getDouble(4),
                         resultSet.getInt(5)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public boolean deleteItem(String id) {
        String SQL= "DELETE FROM Item WHERE ItemCode='" + id + "' ";
        try {
            Object execute = CrudUtil.execute(SQL);
            return true;
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ObservableList<Item> getAllItem() {
        ObservableList<Item> itemObList = FXCollections.observableArrayList();
       String SQL="SELECT * FROM Item";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL);
            while(resultSet.next()){
               itemObList.add(new Item(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4),
                        resultSet.getInt(5)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return itemObList;
    }

    @Override
    public List<String> itemIdslist() {
        ArrayList<String> itemIdsList = new ArrayList<>();
        ObservableList<Item> allItem = getAllItem();
        allItem.forEach(id->{
            itemIdsList.add(id.getItemCode());
        });
        return itemIdsList;
    }

    @Override
    public boolean updatestock(List<OrderDetail> orderDetails) {
        for(OrderDetail orderDetail:orderDetails){
            boolean isUpdateStock = updatestock(orderDetail);
            if (!isUpdateStock){
                return false;
            }
        }
        return true;
    }
    public boolean updatestock(OrderDetail orderDetail){
        String SQL="UPDATE item SET QtyOnHand=QtyOnHand-? WHERE Itemcode=?";
        try {
            return CrudUtil.execute(SQL,orderDetail.getQty(),orderDetail.getItemCode());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
