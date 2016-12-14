import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by abmiro on 11/12/16.
 */
public class MakeBill extends JInternalFrame {


    public MakeBill(Dimension screenSize) {
        super("Make Bill", true, true, true, true);
        setLayout(null);
        /**
         * Customer Details Panel
         */
        JPanel customerDetailsPanel = new JPanel();
        customerDetailsPanel.setBounds(0, 0, ((int) screenSize.getWidth())-10, 75);
        //JLabel lblCurrentTime = getCurrentTime();
        JLabel lblCustomerName = new JLabel("Customer Name :");
        JTextField txtCustomerName = new JTextField(15);

        JLabel lblCustomerAddress = new JLabel("Address :");
        JTextField txtCustomerAddress = new JTextField(15);

        JLabel lblCustomerMobile = new JLabel("Mobile No :");
        JTextField txtCustomerMobile = new JTextField(15);

        //customerDetailsPanel.add(lblCurrentTime);
        customerDetailsPanel.add(lblCustomerName);
        customerDetailsPanel.add(txtCustomerName);
        customerDetailsPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        customerDetailsPanel.add(lblCustomerAddress);
        customerDetailsPanel.add(txtCustomerAddress);
        customerDetailsPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        customerDetailsPanel.add(lblCustomerMobile);
        customerDetailsPanel.add(txtCustomerMobile);
        customerDetailsPanel.setBorder(BorderFactory.createTitledBorder("Customer Details"));
        /******************************end Customer Details************************************************************/

        /**
         * The table for Bill
         */
        JPanel billPanel = new JPanel(new GridLayout(0, 1));
        DefaultTableModel billTableModel = new DefaultTableModel(0, 0);
        String[] columnNames = {"Trade Name", "Pkg. Size", "Technical Name", "Comapny Name",
                "Batch No.", "Exp. Date", "VAT %", "Qty", "Rate", "Ass. Value", "VAT Amt", "Total Amount"};

        JTable table = new JTable();

        billTableModel.setColumnIdentifiers(columnNames);
        table.setModel(billTableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        //scrollPane.setBounds(100, 100, (billPanel.getWidth())-75, 290);

        billPanel.add(scrollPane);
        billPanel.setBorder(BorderFactory.createTitledBorder("Bill"));
        billPanel.setBounds(0, 75, ((int) screenSize.getWidth())-10, 200);
        /*************************** end ****************************************************/

        /**
         * Product Selection
         */

        JPanel addProductPanel = new JPanel(null);

        addProductPanel.setBounds(0, 275, (((int) screenSize.getWidth()) / 2)-10, ((int) screenSize.getHeight()) - 305);
        addProductPanel.setBorder(BorderFactory.createTitledBorder("Select Product"));

        JLabel lblProductName = new JLabel("Product Name :");
        lblProductName.setBounds(60, 30, 100, 30);
        JTextField txtProductName = new JTextField(15);
        txtProductName.setBounds(180, 30, 200, 30);
        JLabel lblSerialNumber = new JLabel("Serial Number :");
        lblSerialNumber.setBounds(60, 80, 100, 30);
        JTextField txtSerialNumber = new JTextField(15);
        txtSerialNumber.setBounds(180, 80, 200, 30);
        JLabel lblQuantity = new JLabel("Quantity / Size :");
        lblQuantity.setBounds(60, 130, 100, 30);
        JTextField txtQuantity = new JTextField(15);
        txtQuantity.setBounds(180, 130, 200, 30);

        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(100, 190, 100, 30);
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String itemSerialNumber = txtSerialNumber.getText();
                    String tradeName = txtProductName.getText();
                    int quantity = Integer.parseInt(txtQuantity.getText());
                    Class.forName("com.mysql.jdbc.Driver");
                    //System.out.println("Creating connection...");
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock_management","root","root");

                    PreparedStatement queryData = connection.prepareStatement("select * from stock_management");
                    ResultSet records = queryData.executeQuery();
                    String data[] = new String[12];


                    while(records.next()){
                        if(itemSerialNumber.equals(records.getString(2)) ) {
                            data[0] = records.getString(1);
                            data[1] = records.getString(3);
                            data[2] = records.getString(4);
                            data[3] = records.getString(5);
                            data[4] = records.getString(6);
                            data[5] = records.getString(7);
                            data[6] = String.valueOf(records.getFloat(8));
                            data[7] = String.valueOf(quantity);
                            data[8] = String.valueOf(records.getFloat(10));
                            data[9] = String.valueOf(records.getFloat(11));
                            data[10] = String.valueOf(records.getFloat(12));
                            data[11] = String.valueOf(records.getFloat(13));

                            billTableModel.addRow(data);

                        }
                    }
                }catch (Exception exception){exception.printStackTrace();}
            }
        });

        addProductPanel.add(lblProductName);
        addProductPanel.add(txtProductName);
        addProductPanel.add(lblSerialNumber);
        addProductPanel.add(txtSerialNumber);
        addProductPanel.add(lblQuantity);
        addProductPanel.add(txtQuantity);
        addProductPanel.add(btnAdd);
        /****************************** end ******************************************************/

        /**
         * Final Bill Options
         */
        JPanel finalBillPanel = new JPanel();
        finalBillPanel.setBounds((((int) screenSize.getWidth()) / 2) - 15, 275, (((int) screenSize.getWidth()) / 2), ((int) screenSize.getHeight()) - 305);
        finalBillPanel.setBorder(BorderFactory.createTitledBorder("Print Final Bill"));

        JButton btnPrint = new JButton("Print Bill");
        btnPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrinterJob job = PrinterJob.getPrinterJob();
                job.setPrintable(new HomePage(""));
                boolean ok = job.printDialog();
                if (ok) {
                    try {
                        job.print();
                    } catch (PrinterException ex) {
              /* The job did not successfully complete */
                    }
                }
            }
        });

        finalBillPanel.add(btnPrint);
        /********************************** end *********************************************************/

        add(customerDetailsPanel);
        add(billPanel);
        add(addProductPanel);
        add(finalBillPanel);
    }
}
