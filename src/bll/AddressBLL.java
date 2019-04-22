package bll;

import dao.AddressDAO;
import model.Address;

import java.util.NoSuchElementException;

public class AddressBLL {

    public Address findAddressById(int id) {
        Address ad = new AddressDAO().findById(id);
        if (ad == null) {
            throw new NoSuchElementException("The address with id =" + id + " was not found!");
        }
        return ad;
    }

    public int insert(Address address) {
        try {
            int i = new AddressDAO().insert(address);
            return 1;
        }catch(Exception e)
        {
            return 0;
        }
    }

    public int delete(int id)
    {
        try{
            int i = new AddressDAO().delete(id);
            return 1;
        }catch(Exception e)
        {
            throw new NoSuchElementException("The address with id =" + id+ " was not found!");
        }
    }

    public int viewAll()
    {
        try{
            int i = new AddressDAO().viewAll();
            return 1;
        }catch(Exception e)
        {
            throw new NoSuchElementException("Cannot display data!");
        }
    }

    public void update(String field, Object obj, int id)
    {
        try{
            int i = new AddressDAO().update(field,obj,id);
        }catch(Exception e)
        {
            throw new NoSuchElementException("Cannot find data to update");
        }
    }
}
