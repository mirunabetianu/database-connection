package bll;

import dao.ProductDAO;
import model.Product;

import java.util.NoSuchElementException;

public class ProductBLL {
    public Product findClientById(int id) {
        Product pr = new ProductDAO().findById(id);
        if (pr == null) {
            throw new NoSuchElementException("The product with id =" + id + " was not found!");
        }
        return pr;
    }

    public int insert(Product product) {
        try {
            int i = new ProductDAO().insert(product);
            return 1;
        }catch(Exception e)
        {
            return 0;
        }
    }

    public int delete(int id)
    {
        try{
            int i = new ProductDAO().delete(id);
            return 1;
        }catch(Exception e)
        {
            throw new NoSuchElementException("The product with id =" + id+ " was not found!");
        }
    }

    public int viewAll()
    {
        try{
            int i = new ProductDAO().viewAll();
            return 1;
        }catch(Exception e)
        {
            throw new NoSuchElementException("Cannot display data!");
        }
    }

    public void update(String field, Object obj, int id)
    {
        try{
            int i = new ProductDAO().update(field,obj,id);
        }catch(Exception e)
        {
            throw new NoSuchElementException("Cannot find data to update");
        }
    }
}
