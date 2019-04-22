package presentation;

import dao.AbstractDAO;
import dao.ClientDAO;
import dao.ProductDAO;
import model.Address;
import model.Client;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

public class View extends JFrame {
    //MENU
    private JButton productButton = new JButton("Edit products  ");
    private JButton clientButton = new JButton("Edit clients     ");
    private JButton orderButton = new JButton("Place order");
    private JButton addressButton = new JButton("Edit addresses");
    JFrame mainFrame = new JFrame();
    private JButton backButton1 = new JButton("Back");
    private JButton backButton2 = new JButton("Back");
    private JButton backButton3 = new JButton("Back");
    private JButton backButton4 = new JButton("Back");
    private JButton backButton5 = new JButton("Back");

    //CLIENT
    JFrame clientFrame = new JFrame();
    private JButton insertClient = new JButton("Insert");
    private JButton deleteClient = new JButton("Delete");
    private JButton editClient = new JButton("Edit");
    JTable tableClient = new JTable();

    //PRODUCT
    JFrame productFrame = new JFrame();
    private JButton insertProduct = new JButton("Insert");
    private JButton deleteProduct = new JButton("Delete");
    private JButton editProduct = new JButton("Edit");
    JTable tableProduct = new JTable(10,5);

    //ADDRESS
    JFrame addressFrame = new JFrame();
    private JButton insertAddress = new JButton("Insert");
    private JButton deleteAddress = new JButton("Delete");
    private JButton editAddress = new JButton("Edit");
    JTable tableAddress = new JTable(10,5);

    private JTextField[] fields_clients;
    private JTextField[] fields_products;
    private JTextField[] fields_addreses;

    //ORDER
    JFrame orderFrame = new JFrame();
    private JComboBox<Integer> comboBoxClient;
    private JComboBox<Integer> comboBoxProduct;
    private JButton placeOrder = new JButton("Finish order");
    private JTextField orderText = new JTextField(5);
    private JButton seeOrder = new JButton("See orders");
    JTable tableOrder = new JTable(10,5);
    JFrame allOrders = new JFrame();
    JTable client = new JTable();
    JTable product = new JTable();
    public View()
    {
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        newClientFrame();
        newAddressFrame();
        newProductFrame();
        newOrderFrame();
        allOrders();
        panel1.setLayout(new BoxLayout(panel1,BoxLayout.Y_AXIS));
        panel1.add(clientButton);
        panel1.add(productButton);
        panel2.add(orderButton);
        panel2.setSize(30,30);
        panel1.add(addressButton);
        panel3.add(panel1);
        panel3.add(panel2);
        mainFrame.add(panel3);
        mainFrame.setTitle("Main Frame");
        mainFrame.pack();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        mainFrame.setLocationRelativeTo(null);

    }

    private void newClientFrame()
    {
        clientFrame.setTitle("Client Frame");
        genericFrame(tableClient, insertClient, deleteClient, editClient, backButton3,clientFrame,Client.class);
    }

    private void newProductFrame()
    {
        productFrame.setTitle("Product Frame");
        genericFrame(tableProduct, insertProduct, deleteProduct, editProduct,backButton2, productFrame,Product.class);
    }

    private void newAddressFrame()
    {
        addressFrame.setTitle("Address Frame");
        genericFrame(tableAddress,insertAddress,deleteAddress,editAddress,backButton1,addressFrame, Address.class);
    }

    void genericTableFiller(AbstractDAO abstractDAO, JTable table, Class c)
    {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnCount(c.getDeclaredFields().length);
        abstractDAO.viewAll();
        int i = 0;
        while(abstractDAO.tableData[i]!=null){tableModel.addRow(abstractDAO.tableData[i]);i++;}
        tableModel.setColumnIdentifiers(abstractDAO.columns);
        table.setModel(tableModel);
    }

    private void genericFrame(JTable tableProduct, JButton insertProduct, JButton deleteProduct, JButton editProduct,JButton backButton, JFrame productFrame, Class c) {
        JPanel panel1 = new JPanel();
        tableProduct.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(tableProduct, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(400,150));
        panel1.add(scrollPane);
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2,BoxLayout.X_AXIS));
        panel2.add(insertProduct);
        panel2.add(deleteProduct);
        panel2.add(editProduct);
        panel2.add(backButton);
        JPanel panel3 = new JPanel();
        panel3.setLayout(new BoxLayout(panel3,BoxLayout.Y_AXIS));
        panel3.add(panel1);
        panel3.add(addColumnInfo(c));
        panel3.add(panel2);
        productFrame.add(panel3);
        productFrame.pack();
        productFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        productFrame.setVisible(false);
        productFrame.setLocationRelativeTo(null);
    }

    private JPanel addColumnInfo(Class c)
    {
        JPanel final_panel = new JPanel();
        if(c == Client.class)fields_clients = new JTextField[c.getDeclaredFields().length];
        else if(c == Product.class)fields_products = new JTextField[c.getDeclaredFields().length];
            else if(c == Address.class)fields_addreses= new JTextField[c.getDeclaredFields().length];

        final_panel.setLayout(new BoxLayout(final_panel,BoxLayout.Y_AXIS));
        int i = 0;
        for(Field field : c.getDeclaredFields())
        {
            JPanel panel = new JPanel();
            panel.add(new JLabel(field.getName()));
            if(c == Client.class) {
                fields_clients[i] = new JTextField(7);
                panel.add(fields_clients[i]);
            }else if(c == Product.class)
            {
                fields_products[i] = new JTextField(7);
                panel.add(fields_products[i]);
            }else if(c == Address.class)
            {
                fields_addreses[i] = new JTextField(7);
                panel.add(fields_addreses[i]);
            }
            i++;
            final_panel.add(panel);
        }

        return final_panel;
    }

    String[] getFields(Class c)
    {
        String[] text = new String[c.getDeclaredFields().length];
        for(int i = 0; i < c.getDeclaredFields().length; i++)
        {
            if(c == Client.class)text[i] = fields_clients[i].getText();
            else if(c == Product.class) text[i] = fields_products[i].getText();
            else if(c == Address.class) text[i] = fields_addreses[i].getText();
        }
        return text;
    }

    private void newOrderFrame() {

        genericTableFiller(new ClientDAO(),client,Client.class);
        genericTableFiller(new ProductDAO(),product,Product.class);
        JPanel panel1 = new JPanel();
        JScrollPane scrollPane1 = new JScrollPane(client, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane1.setPreferredSize(new Dimension(300, 150));
        panel1.add(scrollPane1);

        JScrollPane scrollPane2 = new JScrollPane(product, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane2.setPreferredSize(new Dimension(300, 150));
        panel1.add(scrollPane2);

        Integer[] idClientListaux = new Integer[100];
        Integer[] idProductListaux = new Integer[100];
        ClientDAO c = new ClientDAO();
        c.viewAll();
        int i = 0;

        while(c.tableData[i+1] != null)
        {
            idClientListaux[i] = Integer.parseInt(c.tableData[i][0]);
            i++;
        }
        ProductDAO p = new ProductDAO();
        p.viewAll();

        Integer[] idClientList = new Integer[i];
        System.arraycopy(idClientListaux,0,idClientList,0,i);

        i = 0;

        while(p.tableData[i+1] != null)
        {
            idProductListaux[i] = Integer.parseInt(p.tableData[i][0]);
            i++;
        }
        Integer[] idProductList = new Integer[i];
        System.arraycopy(idProductListaux,0,idProductList,0,i);

        comboBoxClient = new JComboBox<>(idClientList);
        comboBoxProduct = new JComboBox<>(idProductList);

        JPanel panel2 = new JPanel();
        panel2.add(new JLabel("Choose client:"));
        panel2.add(comboBoxClient);
        panel2.add(new JLabel("Choose product"));
        panel2.add(comboBoxProduct);
        panel2.add(new JLabel("Enter quantity"));
        panel2.add(orderText);
        JPanel panel4 = new JPanel();
        JPanel panel3 = new JPanel();
        panel3.setLayout(new BoxLayout(panel3,BoxLayout.Y_AXIS));
        panel4.add(placeOrder);
        panel4.add(backButton4);
        panel4.add(seeOrder);
        panel3.add(panel1);
        panel3.add(panel2);
        panel3.add(panel4);

        orderFrame.setTitle("Orders frame");
        orderFrame.add(panel3);
        orderFrame.pack();
        orderFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        orderFrame.setVisible(false);
        orderFrame.setLocationRelativeTo(null);
    }

    private void allOrders()
    {
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1,BoxLayout.Y_AXIS));
        JScrollPane scrollPane1 = new JScrollPane(tableOrder, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane1.setPreferredSize(new Dimension(300, 150));
        panel1.add(scrollPane1);
        panel1.add(backButton5);

        allOrders.add(panel1);
        allOrders.pack();
        allOrders.setLocationRelativeTo(null);
        allOrders.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        allOrders.setVisible(false);
    }

    int getIdClient()
    {
        return comboBoxClient.getItemAt(comboBoxClient.getSelectedIndex());
    }

    int getIdProduct()
    {
        return comboBoxProduct.getItemAt(comboBoxProduct.getSelectedIndex());
    }

    int getQuantity()
    {
        try {
            return Integer.parseInt(orderText.getText());
        }catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null,"Please introduce the quantity!","Error",JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }

    //mainFrame
    void addClientListener(ActionListener a){clientButton.addActionListener(a);}
    void addProductListener(ActionListener a){productButton.addActionListener(a);}
    void addAddressListener(ActionListener a){addressButton.addActionListener(a);}
    void addBackListener(ActionListener a){backButton1.addActionListener(a);backButton2.addActionListener(a);backButton3.addActionListener(a);backButton4.addActionListener(a);backButton5.addActionListener(a);}
    void addOrderListener(ActionListener a){orderButton.addActionListener(a);}

    //clientFrame
    void addClientInsertListener(ActionListener a){insertClient.addActionListener(a);}
    void addClientDeleteListener(ActionListener a){deleteClient.addActionListener(a);}
    void addClientEditListener(ActionListener a){editClient.addActionListener(a);}

    //productFrame
    void addProductInsertListener(ActionListener a){insertProduct.addActionListener(a);}
    void addProductDeleteListener(ActionListener a){deleteProduct.addActionListener(a);}
    void addProductEditListener(ActionListener a){editProduct.addActionListener(a);}

    //addressFrame
    void addAddressInsertListener(ActionListener a){insertAddress.addActionListener(a);}
    void addAddressDeleteListener(ActionListener a){deleteAddress.addActionListener(a);}
    void addAddressEditListener(ActionListener a){editAddress.addActionListener(a);}

    //orderFrame
    void addFinishOrderListener(ActionListener a){placeOrder.addActionListener(a);}
    void addSeeOrdersListener(ActionListener a){seeOrder.addActionListener(a);}
}
