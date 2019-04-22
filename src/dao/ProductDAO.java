package dao;

import connection.ConnectionFactory;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDAO extends AbstractDAO<Product> {

    public int getQuantity(int id)
    {
        String queryQuantity = "SELECT quantity FROM Product WHERE id = ?";
        return specificQuert(id, queryQuantity);
    }

    public int getPrice(int id)
    {
        String queryQuantity = "SELECT price FROM Product WHERE id = ?";
        return specificQuert(id, queryQuantity);
    }

    public String getName(int id)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT name FROM Product WHERE id = ?";
        try
        {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            resultSet = statement.executeQuery();

            String string="";
            while(resultSet.next())
            {
                string = resultSet.getString(1);
            }
            return string;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int specificQuert(int id, String query) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int price = 0;
        try
        {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            resultSet = statement.executeQuery();

            String string="";
            while(resultSet.next())
            {
                string = resultSet.getString(1);
            }
            price = Integer.parseInt(string);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return price;
    }


}
