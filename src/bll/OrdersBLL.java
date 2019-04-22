package bll;

import dao.OrdersDAO;
import model.Orders;

import java.util.NoSuchElementException;

public class OrdersBLL {

    public int insert(Orders orders) {
        try {
            int i = new OrdersDAO().insert(orders);
            return 1;
        }catch(Exception e)
        {
            return 0;
        }
    }

    public int viewAll()
    {
        try{
            int i = new OrdersDAO().viewAll();
            return 1;
        }catch(Exception e)
        {
            throw new NoSuchElementException("Cannot display data!");
        }
    }
}
