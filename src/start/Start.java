package start;

import bll.ClientBLL;
import model.Client;
import presentation.Controller;
import presentation.View;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Start {
    protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());

    public static void main(String[] args) throws SQLException {

        Client student = new Client(2, "dorel", 1, "dorel", 11);

        ClientBLL clientBLL = new ClientBLL();
 /*       int id = clientBLL.insert(student);
        if (id > 0) {
            clientBLL.findClientById(id);
        }*/
         //Generate error
        try {
          //  int id = clientBLL.update("name","dorela",10);
            View v = new View();
            Controller c = new Controller(v);
        } catch (Exception ex) {
            LOGGER.log(Level.INFO, ex.getMessage());
        }
    }
}
