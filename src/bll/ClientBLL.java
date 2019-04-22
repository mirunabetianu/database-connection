package bll;

import bll.validators.ClientAgeValidator;
import bll.validators.EmailValidator;
import bll.validators.Validator;
import dao.*;
import model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ClientBLL {
    private List<Validator<Client>> validators;

    public ClientBLL() {
        validators = new ArrayList<Validator<Client>>();
        validators.add(new EmailValidator());
        validators.add(new ClientAgeValidator());
    }

    public Client findClientById(int id) {
        Client cl = new ClientDAO().findById(id);
        if (cl == null) {
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return cl;
    }

    public int insert(Client client) {

        try {
            for (Validator<Client> v : validators) {
                v.validate(client);
            }
            return new ClientDAO().insert(client);
        }catch(Exception e)
        {
            return 0;
        }
    }

    public int delete(int id)
    {
        try{
            int i = new ClientDAO().delete(id);
            return 1;
        }catch(Exception e)
        {
            throw new NoSuchElementException("The client with id =" + id+ " was not found!");
        }
    }

    public int viewAll()
    {
        try{
            int i = new ClientDAO().viewAll();
            return 1;
        }catch(Exception e)
        {
            throw new NoSuchElementException("Cannot display data!");
        }
    }

    public void update(String field, Object obj, int id)
    {
        try{
            int i = new ClientDAO().update(field,obj,id);
        }catch(Exception e)
        {
            throw new NoSuchElementException("Cannot find data to update");
        }
    }

}
