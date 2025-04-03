package controller.item;

import DB.DbConnection;
import dto.Customer;
import dto.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemController implements ItemService{
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
}
