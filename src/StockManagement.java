import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Objects;


/**
 * Created by meraj on 9/12/16.
 */
public class StockManagement extends JInternalFrame {

        Connection con;

    public StockManagement(int width, int height) {
        super("StockManagement", true, true, true, true);

        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock_management","root","root");

        }catch (Exception e){
            System.out.println(" :" +e);
        }

        JPanel container = new JPanel();
        container.setLayout(new GridLayout(1, 2));

        JPanel stockManagementPanel = new JPanel();
        stockManagementPanel.setLayout(new GridLayout(14, 2));

        JLabel tradeNameLabel = new JLabel("Trade Name");
        JTextField tradeNameField = new JTextField();
        JLabel pkgSizeLabel = new JLabel("Pkg. Size");
        JTextField pkgSizeField =new JTextField();
        JLabel technicalNameLabel = new JLabel("Technical Name");
        JTextField technicalNameField = new JTextField();
        JLabel companyNameLabel = new JLabel("Company Name");
        JTextField companyNameField = new JTextField();
        JLabel batchNameLabel = new JLabel("Batch Name");
        JTextField batchNoField = new JTextField();
        JLabel expDateLabel = new JLabel("Exp. Date");
        JTextField expDateField = new JTextField();
        JLabel rateLabel = new JLabel("Rate");
        JTextField rateField = new JTextField();

        JButton addRecord= new JButton("Add Record");


        stockManagementPanel.add(tradeNameLabel);
        stockManagementPanel.add(tradeNameField);
        stockManagementPanel.add(pkgSizeLabel);
        stockManagementPanel.add(pkgSizeField);
        stockManagementPanel.add(technicalNameLabel);
        stockManagementPanel.add(technicalNameField);
        stockManagementPanel.add(companyNameLabel);
        stockManagementPanel.add(companyNameField);
        stockManagementPanel.add(batchNameLabel);
        stockManagementPanel.add(batchNoField);
        stockManagementPanel.add(expDateLabel);
        stockManagementPanel.add(expDateField);
        stockManagementPanel.add(rateLabel);
        stockManagementPanel.add(rateField);

        stockManagementPanel.add(addRecord);

        addRecord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    if(tradeNameField.getText().equals("") && pkgSizeField.getText().equals("") && technicalNameField.getText().equals("") && companyNameField.getText().equals("") && batchNoField.getText().equals("") && expDateField.getText().equals("") && rateField.getText().equals("")) {
                        JOptionPane.showInternalMessageDialog(getContentPane(),"Please Fill all the Field!!!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    else{

                        PreparedStatement insert = con.prepareStatement("INSERT INTO medipest_stock(TRADE_NAME, TECHNICAL_NAME, COMPANY_NAME, PACKAGE_SIZE" +
                                ", BATCH_NO, EXPIRY_DATE, RATE) values(?, ?, ?, ?, ?, ?, ?)");

                    String tradeName = tradeNameField.getText();
                    String pkgSize = pkgSizeField.getText();
                    String technicalName = technicalNameField.getText();
                    String companyName = companyNameField.getText();
                    String batchNo = batchNoField.getText();
                    String expDate = expDateField.getText();
                    String rate = rateField.getText();

                        insert.setString(1, tradeName);
                        insert.setString(2, technicalName);
                        insert.setString(3, companyName);
                        insert.setString(4, pkgSize);
                        insert.setString(5, batchNo);
                        insert.setString(6, expDate);
                        insert.setString(7, rate);


                        int result = insert.executeUpdate();
                        System.out.println(result);
                        if (result == 1) {
                            tradeNameField.setText("");
                            pkgSizeField.setText("");
                            technicalNameField.setText("");
                            companyNameField.setText("");
                            batchNoField.setText("");
                            expDateField.setText("");
                            rateField.setText("");
                        }
                        JOptionPane.showInternalMessageDialog(getContentPane(), " Record Added");

                    }
                }catch (Exception ex){System.out.println(ex);

                }
            }
        });

        JPanel stockManagementPanel1 = new JPanel();
        container.add(stockManagementPanel);
        container.add(stockManagementPanel1);

        add(container);
        
    }
}
