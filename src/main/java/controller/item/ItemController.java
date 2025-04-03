package controller.item;

import DB.DbConnection;
import dto.Customer;
import dto.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemController implements ItemService{
    @Override
    public boolean addItem(Item item) {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Item VALUES(?,?,?,?,?)");
            preparedStatement.setObject(1,item.getItemCode());
            preparedStatement.setObject(2,item.getDescription());
            preparedStatement.setObject(3,item.getPackSize());
            preparedStatement.setObject(4,item.getUnitPrice());
            preparedStatement.setObject(5,item.getQty());
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateItem(Item item) {

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE  Item SET Description=? , PackSize=? , UnitPrice=? , QtyOnHand=? WHERE ItemCode=?");

            preparedStatement.setObject(1,item.getDescription());
            preparedStatement.setObject(2,item.getPackSize());
            preparedStatement.setObject(3,item.getUnitPrice());
            preparedStatement.setObject(4,item.getQty());
            preparedStatement.setObject(5,item.getItemCode());
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Item searchItem(String id) {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Item WHERE ItemCode='" + id + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

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
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Item WHERE ItemCode='" + id + "' ");
             return  preparedStatement.executeUpdate()>0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ObservableList<Item> getAllItem() {
        ObservableList<Item> itemObList = FXCollections.observableArrayList();
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            System.out.println(connection);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM  Item");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Item item = new Item(
                        resultSet.getString("ItemCode"),
                        resultSet.getString("Description"),
                        resultSet.getString("PackSize"),
                        resultSet.getDouble("UnitPrice"),
                        resultSet.getInt("QtyOnHand")
                );
                itemObList.add(item);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return itemObList;
    }
}
