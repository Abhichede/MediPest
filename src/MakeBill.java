import jdk.nashorn.internal.scripts.JO;

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
        String[] columnNames = {"Trade Name", "Technical Name", "Comapny Name", "Pkg. Size",
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

        JPanel addProductPanel = new JPanel(new GridLayout(5, 2, 20, 10));

        addProductPanel.setBounds(0, 275, (((int) screenSize.getWidth()) / 2)-10, ((int) screenSize.getHeight()) - 305);
        addProductPanel.setBorder(BorderFactory.createTitledBorder("Select Product"));

        JLabel lblProductName = new JLabel("TRADE NAME :");
        JTextField txtProductName = new JTextField(15);
        JLabel lblTechnicalName = new JLabel("TECHNICAL NAME :");
        JTextField txtTechnicalName = new JTextField(15);
        JLabel lblVat = new JLabel("VAT %");
        JTextField txtVat = new JTextField(10);
        JLabel lblQuantity = new JLabel("Quantity / Size :");
        JTextField txtQuantity = new JTextField(15);

        JLabel lblTotalQuantity = new JLabel("TOTAL QUANTITY : ");
        JTextField txtTotalQuantity = new JTextField(6);
        txtTotalQuantity.setEditable(false);
        txtTotalQuantity.setText("0");
        JLabel lblTotalAssValue = new JLabel("TOTAL ASS. VALUE : ");
        JTextField txtTotalAssValue = new JTextField(6);
        txtTotalAssValue.setEditable(false);
        txtTotalAssValue.setText("0");
        JLabel lblTotalVAT = new JLabel("TOTAL VAT : ");
        JTextField txtTotalVAT = new JTextField(6);
        txtTotalVAT.setEditable(false);
        txtTotalVAT.setText("0");
        JLabel lblTotalAmount = new JLabel("TOTAL AMOUNT : ");
        JTextField txtTotalAmount = new JTextField(6);
        txtTotalAmount.setEditable(false);
        txtTotalAmount.setText("0");

        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(100, 190, 100, 30);
        btnAdd.addActionListener(new ActionListener()  {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String technicalName = txtTechnicalName.getText();
                    String tradeName = txtProductName.getText();
                    Float vatInPer = Float.parseFloat(txtVat.getText());
                    int quantity = Integer.parseInt(txtQuantity.getText());
                    int flag = 0;
                    Class.forName("com.mysql.jdbc.Driver");
                    //System.out.println("Creating connection...");
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock_management","root","root");

                    PreparedStatement queryData = connection.prepareStatement("SELECT * FROM medipest_stock WHERE TRADE_NAME = ? AND TECHNICAL_NAME= ?");
                    queryData.setString(1, tradeName);
                    queryData.setString(2, technicalName);
                    ResultSet records = queryData.executeQuery();
                    String data[] = new String[12];

                    if(records != null) {
                        while (records.next()) {
                            data[0] = records.getString("TRADE_NAME");
                            data[1] = records.getString("TECHNICAL_NAME");
                            data[2] = records.getString("COMPANY_NAME");
                            data[3] = records.getString("PACKAGE_SIZE");
                            data[4] = records.getString("BATCH_NO");
                            data[5] = records.getString("EXPIRY_DATE");
                            data[6] = String.valueOf(vatInPer);
                            Float assValue = quantity * Float.parseFloat(records.getString("RATE"));
                            Float totalVAT = (vatInPer / 100) * assValue;
                            data[7] = String.valueOf(quantity);
                            data[8] = records.getString("RATE");
                            data[9] = String.valueOf(assValue);
                            data[10] = String.valueOf(totalVAT);
                            data[11] = String.valueOf(assValue + totalVAT);

                            txtTotalQuantity.setText(String.valueOf(Float.parseFloat(txtTotalQuantity.getText()) + quantity));
                            txtTotalAssValue.setText(String.valueOf(Float.parseFloat(txtTotalAssValue.getText()) + assValue));
                            txtTotalVAT.setText(String.valueOf(Float.parseFloat(txtTotalVAT.getText()) + totalVAT));
                            txtTotalAmount.setText(String.valueOf(Float.parseFloat(txtTotalAmount.getText()) + (assValue + totalVAT)));

                            billTableModel.addRow(data);
                            flag = 1;
                        }
                    }else{
                        flag = 0;
                        JOptionPane.showInternalMessageDialog(getContentPane(), "Sorry, The TRADE not Found", "Not Found", JOptionPane.ERROR_MESSAGE);
                    }

                    if(flag == 1){
                        txtTechnicalName.setText("");
                        txtProductName.setText("");
                        txtVat.setText("");
                        txtQuantity.setText("");
                    }
                }catch (Exception exception){exception.printStackTrace();}
            }
        });

        addProductPanel.add(lblProductName);
        addProductPanel.add(txtProductName);
        addProductPanel.add(lblTechnicalName);
        addProductPanel.add(txtTechnicalName);
        addProductPanel.add(lblVat);
        addProductPanel.add(txtVat);
        addProductPanel.add(lblQuantity);
        addProductPanel.add(txtQuantity);
        addProductPanel.add(btnAdd);
        /****************************** end ******************************************************/

        /**
         * Final Bill Options
         */
        JPanel finalBillPanel = new JPanel(new GridLayout(5, 2));
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

        finalBillPanel.add(lblTotalQuantity);
        finalBillPanel.add(txtTotalQuantity);
        finalBillPanel.add(lblTotalAssValue);
        finalBillPanel.add(txtTotalAssValue);
        finalBillPanel.add(lblTotalVAT);
        finalBillPanel.add(txtTotalVAT);
        finalBillPanel.add(lblTotalAmount);
        finalBillPanel.add(txtTotalAmount);
        finalBillPanel.add(btnPrint);
        /********************************** end *********************************************************/

        add(customerDetailsPanel);
        add(billPanel);
        add(addProductPanel);
        add(finalBillPanel);
    }
}
