package presentation;

import bll.AddressBLL;
import bll.ClientBLL;
import bll.OrdersBLL;
import bll.ProductBLL;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.sun.codemodel.internal.JOp;
import com.sun.tools.corba.se.idl.constExpr.Or;
import dao.*;
import model.Address;
import model.Client;
import model.Orders;
import model.Product;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.pdf.PdfWriter;

public class Controller {
    private View view;

    public Controller(View view)
    {
        this.view = view;
        view.addClientListener(new addClientListener());
        view.addBackListener(new addBackListener());
        view.addProductListener(new addProductListener());
        view.addAddressListener(new addAddressListener());
        view.addClientInsertListener(new addInsertClientListener());
        view.addClientDeleteListener(new addDeleteClientListener());
        view.addClientEditListener(new addEditClientListener());
        view.addProductInsertListener(new addInsertProductListener());
        view.addProductDeleteListener(new addDeleteProductListener());
        view.addProductEditListener(new addEditProductListener());
        view.addAddressInsertListener(new addInsertAddressListener());
        view.addAddressDeleteListener(new addDeleteAddressListener());
        view.addAddressEditListener(new addEditAddressListener());
        view.addOrderListener(new addOrderListener());
        view.addFinishOrderListener(new addPlaceOrderListener());
        view.addSeeOrdersListener(new addSeeOrderListener());
    }


    public class addClientListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            view.mainFrame.setVisible(false);
            view.clientFrame.setVisible(true);
            view.genericTableFiller(new ClientDAO(),view.tableClient,Client.class);
        }
    }

    public class addBackListener implements ActionListener{

        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
             view.mainFrame.setVisible(true);
             view.clientFrame.setVisible(false);
             view.addressFrame.setVisible(false);
             view.productFrame.setVisible(false);
             view.orderFrame.setVisible(false);
             view.allOrders.setVisible(false);
        }
    }
    public class addProductListener implements  ActionListener{

        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
                view.mainFrame.dispose();
                view.productFrame.setVisible(true);
                view.genericTableFiller(new ProductDAO(),view.tableProduct,Product.class);
        }
    }
    public class addAddressListener implements ActionListener{

        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
                view.mainFrame.dispose();
                view.addressFrame.setVisible(true);
                view.genericTableFiller(new AddressDAO(),view.tableAddress, Address.class);

        }
    }

    public class addInsertClientListener implements ActionListener
    {

        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
                String[] data = view.getFields(Client.class);
                ClientBLL clientBLL = new ClientBLL();
                try{
                    int id = clientBLL.insert(new Client(Integer.parseInt(data[0]),data[1],Integer.parseInt(data[2]),data[3],Integer.parseInt(data[4])));
                    view.genericTableFiller(new ClientDAO(),view.tableClient,Client.class);
                    view.tableClient.repaint();
                    view.tableClient.revalidate();
                }catch(Exception ex) {
                    JOptionPane.showMessageDialog(null, "Something is wrong. Please introduce valid data!","Error",JOptionPane.ERROR_MESSAGE);
                }
        }
    }

    public class addDeleteClientListener implements ActionListener
    {

        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] data = view.getFields(Client.class);
            ClientBLL clientBLL = new ClientBLL();
            try{
                int id = clientBLL.delete(Integer.parseInt(data[0]));
                view.genericTableFiller(new ClientDAO(),view.tableClient,Client.class);
                view.tableClient.repaint();
                view.tableClient.revalidate();
            }catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "Something is wrong. Please introduce valid data!","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public class addEditClientListener implements ActionListener
    {

        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] data = view.getFields(Client.class);
            ClientBLL clientBLL = new ClientBLL();
            try{
                if(!data[1].equals(""))clientBLL.update("name",data[1],Integer.parseInt(data[0]));
                if(!data[2].equals(""))clientBLL.update("idAddress",Integer.parseInt(data[2]),Integer.parseInt(data[0]));
                if(!data[3].equals(""))clientBLL.update("email",data[3],Integer.parseInt(data[0]));
                if(!data[4].equals(""))clientBLL.update("age",Integer.parseInt(data[4]),Integer.parseInt(data[0]));
                view.genericTableFiller(new ClientDAO(),view.tableClient,Client.class);
                view.tableClient.repaint();
                view.tableClient.revalidate();
            }catch(Exception ex) {
                view.genericTableFiller(new ClientDAO(),view.tableClient,Client.class);
               // JOptionPane.showMessageDialog(null,"Something is wrong. Please introduce valid data!","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public class addInsertProductListener implements ActionListener
    {

        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] data = view.getFields(Product.class);
            ProductBLL productBLL = new ProductBLL();
            try{
                int id = productBLL.insert(new Product(Integer.parseInt(data[0]),data[1],Integer.parseInt(data[2]),Integer.parseInt(data[3])));
                view.genericTableFiller(new ProductDAO(),view.tableProduct, Product.class);
                view.tableProduct.repaint();
                view.tableProduct.revalidate();
            }catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "Something is wrong. Please introduce valid data!","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public class addDeleteProductListener implements ActionListener
    {

        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] data = view.getFields(Product.class);
            ProductBLL productBLL = new ProductBLL();
            try{
                int id = productBLL.delete(Integer.parseInt(data[0]));
                view.genericTableFiller(new ProductDAO(),view.tableProduct,Product.class);
                view.tableProduct.repaint();
                view.tableProduct.revalidate();
            }catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "Something is wrong. Please introduce valid data!","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public class addEditProductListener implements ActionListener
    {

        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] data = view.getFields(Product.class);
            ProductBLL productBLL = new ProductBLL();
            try{
                if(!data[1].equals(""))productBLL.update("name",data[1],Integer.parseInt(data[0]));
                if(!data[2].equals(""))productBLL.update("price",Integer.parseInt(data[2]),Integer.parseInt(data[0]));
                if(!data[3].equals(""))productBLL.update("quantity",Integer.parseInt(data[3]),Integer.parseInt(data[0]));
                view.genericTableFiller(new ProductDAO(),view.tableProduct,Product.class);
                view.tableProduct.repaint();
                view.tableProduct.revalidate();
            }catch(Exception ex) {
                view.genericTableFiller(new ProductDAO(),view.tableProduct,Product.class);
              //  JOptionPane.showMessageDialog(null,"Something is wrong. Please introduce valid data!","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public class addInsertAddressListener implements ActionListener
    {

        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] data = view.getFields(Address.class);
            AddressBLL addressBLL = new AddressBLL();
            try{
                int id = addressBLL.insert(new Address(Integer.parseInt(data[0]),data[1],data[2],data[3],data[4]));
                view.genericTableFiller(new AddressDAO(),view.tableAddress,Address.class);
                view.tableAddress.repaint();
                view.tableAddress.revalidate();
            }catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "Something is wrong. Please introduce valid data!","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public class addDeleteAddressListener implements ActionListener
    {

        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] data = view.getFields(Address.class);
            AddressBLL addressBLL = new AddressBLL();
            try{
                int id = addressBLL.delete(Integer.parseInt(data[0]));
                view.genericTableFiller(new AddressDAO(),view.tableAddress,Address.class);
                view.tableAddress.repaint();
                view.tableAddress.revalidate();
            }catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "Something is wrong. Please introduce valid data!","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public class addEditAddressListener implements ActionListener
    {

        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] data = view.getFields(Address.class);
            AddressBLL addressBLL = new AddressBLL();
            try{
                if(!data[1].equals(""))addressBLL.update("street",data[1],Integer.parseInt(data[0]));
                if(!data[2].equals(""))addressBLL.update("city",data[2],Integer.parseInt(data[0]));
                if(!data[3].equals(""))addressBLL.update("country",data[3],Integer.parseInt(data[0]));
                if(!data[4].equals(""))addressBLL.update("zip_code",data[4],Integer.parseInt(data[0]));
                view.genericTableFiller(new AddressDAO(),view.tableAddress,Client.class);
                view.tableAddress.repaint();
                view.tableAddress.revalidate();
            }catch(Exception ex) {
                view.genericTableFiller(new AddressDAO(),view.tableAddress,Address.class);
                //JOptionPane.showMessageDialog(null,"Something is wrong. Please introduce valid data!","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public class addOrderListener implements ActionListener
    {

        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
                view.mainFrame.setVisible(false);
                view.orderFrame.setVisible(true);
                view.genericTableFiller(new ProductDAO(),view.product,Product.class);
                view.genericTableFiller(new ClientDAO(),view.client,Client.class);
        }
    }

    public class addPlaceOrderListener implements ActionListener
    {

        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            int idProduct = view.getIdProduct();
            int idClient = view.getIdClient();

            int quantity = view.getQuantity();

            int productQuantity = new ProductDAO().getQuantity(idProduct);
            int productPrice = new ProductDAO().getPrice(idProduct);
            String productName = new ProductDAO().getName(idProduct);

            OrdersBLL ordersBLL = new OrdersBLL();
            ProductBLL productBLL = new ProductBLL();
            int id;

            try{
                if(productQuantity >= quantity) {
                    id = ordersBLL.insert(new Orders(0, idClient, idProduct, productPrice * productQuantity));
                    productBLL.update("quantity", productQuantity - quantity, idProduct);
                    view.genericTableFiller(new ProductDAO(),view.tableProduct,Product.class);
                    view.genericTableFiller(new ClientDAO(),view.tableClient,Client.class);

                    Random random = new Random();
                    Document document = new Document();
                    int bill = 1000 + random.nextInt(1000);
                    PdfPTable table = new PdfPTable(new float[] { 2, 1, 2 });
                    table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell("Name");
                    table.addCell("Quantity");
                    table.addCell("Price");
                    table.setHeaderRows(1);
                    PdfPCell[] cells = table.getRow(0).getCells();
                    for (int j=0;j<cells.length;j++){
                        cells[j].setBackgroundColor(BaseColor.GRAY);
                    }
                    table.addCell(productName);
                    table.addCell(String.valueOf(quantity));
                    table.addCell(String.valueOf(productPrice));

                    PdfWriter.getInstance(document, new FileOutputStream("bill.pdf"));
                    document.open();
                    document.add(table);
                    document.add(new Paragraph("Total price: "+quantity*productPrice));
                    document.close();
                    System.out.println("Done");

                }
                else JOptionPane.showMessageDialog(null,"Under stock");
            }catch(Exception ex) {
                JOptionPane.showMessageDialog(null,"Something is wrong. Please introduce valid data!\n" + ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public class addSeeOrderListener implements ActionListener
    {

        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
                view.allOrders.setTitle("All orders");
                view.orderFrame.setVisible(false);
                view.allOrders.setVisible(true);
                view.genericTableFiller(new OrdersDAO(),view.tableOrder, Orders.class);
        }
    }

}
