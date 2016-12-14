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

        JPanel container,stockManagementPanel,stockManagementPanel1,stockManagementPanel2;
        Connection con;
        DefaultTableModel dm;
        PreparedStatement insert,update,queryData,delete;
        String tradeName,itemSerialNumber,technicalName,pkgSize,batchNo,companyName;
        String expDate;
        float vatPercent,rate,assValue,vatAmount,totalAmount;
        int quantity;
        JTextField tradeNameField,technicalNameField,pkgSizeField,serialNumberField,batchNoField,companyNameField,expDateField,vatPercentField,quantityField,rateField,assValueField,vatAmountField,totalAmountField;
        JLabel expDateLabel,vatPercentLabel,quantityLabel,rateLabel,assValueLabel,vatAmountLabel,totalAmountLabel;
        JButton addRecord;


    public StockManagement(int width, int height) {
        super("StockManagement", true, true, true, true);


        tradeName=null;itemSerialNumber=null;technicalName=null;pkgSize=null;batchNo=null;companyName=null;expDate=null;
        vatPercent=rate=assValue=vatAmount=totalAmount=0;
        quantity=0;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock_management","root","root");

        }catch (Exception e){
            System.out.println(" :" +e);
        }

        container = new JPanel();
        container.setLayout(null);

        stockManagementPanel = new JPanel();
        stockManagementPanel.setBounds(0, 0, width-900, height-90);
        stockManagementPanel.setBorder(new BorderUIResource.LineBorderUIResource(Color.gray,1));
        stockManagementPanel.setLayout(null);

        JLabel tradeNameLabel = new JLabel("Trade Name");
        tradeNameField = new JTextField();
        JLabel serialNumberLabel = new JLabel("Item Serial Number");
        serialNumberField = new JTextField();
        JLabel pkgSizeLabel = new JLabel("Pkg. Size");
        pkgSizeField =new JTextField();
        JLabel technicalNameLabel = new JLabel("Technical Name");
        technicalNameField = new JTextField();
        JLabel companyNameLabel = new JLabel("Company Name");
        companyNameField = new JTextField();
        JLabel batchNameLabel = new JLabel("Batch Name");
        batchNoField = new JTextField();
        expDateLabel = new JLabel("Exp. Date");
        expDateField = new JTextField();
        vatPercentLabel = new JLabel("VAT %");
        vatPercentField = new JTextField();
        quantityLabel = new JLabel("Quantity");
        quantityField = new JTextField();
        rateLabel = new JLabel("Rate");
        rateField = new JTextField();
        assValueLabel = new JLabel("Ass. Value");
        assValueField = new JTextField();
        vatAmountLabel = new JLabel("VAT Amount");
        vatAmountField = new JTextField();
        totalAmountLabel = new JLabel("Total Amount");
        totalAmountField = new JTextField();


        addRecord= new JButton("Add Record");


        tradeNameLabel.setBounds(50, 20, 150, 30);
        tradeNameField.setBounds(190, 20, 200, 30);
        serialNumberLabel.setBounds(50,60,150,30);
        serialNumberField.setBounds(190,60,200,30);
        pkgSizeLabel.setBounds(50,100,150,30);
        pkgSizeField.setBounds(190,100,200,30);
        technicalNameLabel.setBounds(50,150,150,30);
        technicalNameField.setBounds(190,150,200,30);
        companyNameLabel.setBounds(50,190,150,30);
        companyNameField.setBounds(190,190,200,30);
        batchNameLabel.setBounds(50,230,150,30);
        batchNoField.setBounds(190,230,200,30);
        expDateLabel.setBounds(50,270,150,30);
        expDateField.setBounds(190,270,200,30);
        vatPercentLabel.setBounds(50,310,150,30);
        vatPercentField.setBounds(190,310,200,30);
        quantityLabel.setBounds(50,350,150,30);
        quantityField.setBounds(190,350,200,30);
        rateLabel.setBounds(50,390,150,30);
        rateField.setBounds(190,390,200,30);
        assValueLabel.setBounds(50,430,150,30);
        assValueField.setBounds(190,430,200,30);
        vatAmountLabel.setBounds(50,470,150,30);
        vatAmountField.setBounds(190,470,200,30);
        totalAmountLabel.setBounds(50,510,150,30);
        totalAmountField.setBounds(190,510,200,30);

        addRecord.setBounds(100,550,150,30);


        stockManagementPanel.add(tradeNameLabel);
        stockManagementPanel.add(tradeNameField);
        stockManagementPanel.add(serialNumberLabel);
        stockManagementPanel.add(serialNumberField);
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
        stockManagementPanel.add(vatPercentLabel);
        stockManagementPanel.add(vatPercentField);
        stockManagementPanel.add(quantityLabel);
        stockManagementPanel.add(quantityField);
        stockManagementPanel.add(rateLabel);
        stockManagementPanel.add(rateField);
        stockManagementPanel.add(assValueLabel);
        stockManagementPanel.add(assValueField);
        stockManagementPanel.add(vatAmountLabel);
        stockManagementPanel.add(vatAmountField);
        stockManagementPanel.add(totalAmountLabel);
        stockManagementPanel.add(totalAmountField);

        stockManagementPanel.add(addRecord);



        stockManagementPanel1 = new JPanel();
        stockManagementPanel1.setLayout(null);
        stockManagementPanel1.setBounds(470,0,width-400, height-90);
        stockManagementPanel1.setBorder(new BorderUIResource.LineBorderUIResource(Color.gray,1));

        String column[] =  {"Trade Name ", " Item Serial Number ", " Pkg. Size ", " Technical Neme ", " Company Name ", "  Batch No. ", "  Exp. Dt. ", " VAT % ", " Qty ", " Rate ", "  Ass. Value ", "  VAT Amt ", "  Total Amount "};
        String data[] = new String[13];



        JTable stockRecordTable = new JTable();

        stockRecordTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        stockRecordTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        dm = new DefaultTableModel(0,0);
        dm.setColumnIdentifiers(column);


        stockRecordTable.setModel(dm);

        try {
            queryData  = con.prepareStatement("SELECT  * FROM stock_management;");
            ResultSet records = queryData.executeQuery();

            while (records.next())
            {
                data[0] = records.getString(1);
                data[1] = records.getString(2);
                data[2] =  records.getString(3);
                data[3] =  records.getString(4);
                data[4] = records.getString(5);
                data[5] = records.getString(6);
                data[6] = records.getString(7);
                data[7] = String.valueOf(records.getFloat(8));
                data[8] = String.valueOf(records.getInt(9));
                data[9] = String.valueOf(records.getFloat(10));
                data[10] = String.valueOf(records.getFloat(11));
                data[11] = String.valueOf(records.getFloat(12));
                data[12] = String.valueOf(records.getFloat(13));

                addDataToRow(data);
            }


        }catch (Exception ex){}


        JScrollPane tableScrollPane = new JScrollPane();
        tableScrollPane.setViewportView(stockRecordTable);
        tableScrollPane.setBounds(0,0,800,500);

        JButton editRecord = new JButton();
        JButton deleteRecord = new JButton();
        editRecord.setText("Edit Record");
        editRecord.setBounds(30,520,150,30);
        deleteRecord.setText("Delete Record");
        deleteRecord.setBounds(190,520,150,30);


        stockManagementPanel1.add(tableScrollPane);
        stockManagementPanel1.add(editRecord);
        stockManagementPanel1.add(deleteRecord);


        addRecord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    if(tradeNameField.getText().equals("") && serialNumberField.getText().equals("") && pkgSizeField.getText().equals("") && technicalNameField.getText().equals("") && companyNameField.getText().equals("") && batchNoField.getText().equals("") && expDateField.getText().equals("") && vatPercentField.getText().equals("") && quantityField.getText().equals("") && rateField.getText().equals("") && assValueField.getText().equals("") && vatAmountField.getText().equals("") && totalAmountField.getText().equals("")) {
                        JOptionPane.showInternalMessageDialog(getContentPane(),"Please Fill all the Field!!!");
                    }
                    else{

                        insert = con.prepareStatement("insert into stock_management values(?,?,?,?,?,?,?,?,?,?,?,?,?);");

                    tradeName = tradeNameField.getText();
                    itemSerialNumber = serialNumberField.getText();
                    pkgSize = pkgSizeField.getText();
                    technicalName = technicalNameField.getText();
                    companyName = companyNameField.getText();
                    batchNo = batchNoField.getText();
                    expDate = expDateField.getText();
                    vatPercent = Float.parseFloat(vatPercentField.getText());
                    quantity = Integer.parseInt(quantityField.getText());
                    rate = Float.parseFloat(rateField.getText());
                    assValue = Float.parseFloat(assValueField.getText());
                    vatAmount = Float.parseFloat(vatAmountField.getText());
                    totalAmount = Float.parseFloat(totalAmountField.getText());

                        insert.setString(1, tradeName);
                        insert.setString(2, itemSerialNumber);
                        insert.setString(3, pkgSize);
                        insert.setString(4, technicalName);
                        insert.setString(5, companyName);
                        insert.setString(6, batchNo);
                        insert.setString(7, expDate);
                        insert.setFloat(8, vatPercent);
                        insert.setInt(9, quantity);
                        insert.setFloat(10, rate);
                        insert.setFloat(11, assValue);
                        insert.setFloat(12, vatAmount);
                        insert.setFloat(13, totalAmount);


                        int result = insert.executeUpdate();
                        System.out.println(result);
                        if (result == 1) {
                            tradeNameField.setText("");
                            serialNumberField.setText("");
                            pkgSizeField.setText("");
                            technicalNameField.setText("");
                            companyNameField.setText("");
                            batchNoField.setText("");
                            expDateField.setText("");
                            vatPercentField.setText("");
                            quantityField.setText("");
                            rateField.setText("");
                            assValueField.setText("");
                            vatAmountField.setText("");
                            totalAmountField.setText("");
                        }

                        String data[] = {tradeName, itemSerialNumber, pkgSize, technicalName, companyName, batchNo, String.valueOf(expDate), String.valueOf(vatPercent), String.valueOf(quantity), String.valueOf(rate), String.valueOf(assValue), String.valueOf(vatAmount), String.valueOf(totalAmount)};
                        addDataToRow(data);

                        JOptionPane.showInternalMessageDialog(getContentPane(), " Record Added");

                    }
                }catch (Exception ex){System.out.println(ex);

                }
            }
        });


        editRecord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                int rowId = stockRecordTable.getSelectedRow();


                try {


                            tradeName = String.valueOf(stockRecordTable.getValueAt(rowId,0));
                            itemSerialNumber =  String.valueOf(stockRecordTable.getValueAt(rowId,1));
                            technicalName =  String.valueOf(stockRecordTable.getValueAt(rowId,2));
                            pkgSize =   String.valueOf(stockRecordTable.getValueAt(rowId,3));
                            companyName =  String.valueOf(stockRecordTable.getValueAt(rowId,4));
                            batchNo =  String.valueOf(stockRecordTable.getValueAt(rowId,5));
                            expDate = String.valueOf(stockRecordTable.getValueAt(rowId,6));
                            vatPercent = Float.parseFloat(stockRecordTable.getValueAt(rowId,7).toString());
                            quantity = Integer.parseInt(stockRecordTable.getValueAt(rowId,8).toString());
                            rate = Float.parseFloat(stockRecordTable.getValueAt(rowId,9).toString());
                            assValue = Float.parseFloat(stockRecordTable.getValueAt(rowId,10).toString());
                            vatAmount = Float.parseFloat(stockRecordTable.getValueAt(rowId,11).toString());
                            totalAmount = Float.parseFloat(stockRecordTable.getValueAt(rowId,12).toString());


                            update = con.prepareStatement("UPDATE  stock_management SET trade_name =?, item_serial_number=?, pkg_size=?, technical_name=?, company_name=?, batch_no=?, exp_date=?, vat_percent=?, quantity=?, rate=?,ass_value=?, vat_amount=?, total_amount=? where item_serial_number = ? ;");

                            update.setString(1, tradeName);
                            update.setString(2, itemSerialNumber);
                            update.setString(3, technicalName);
                            update.setString(4, technicalName);
                            update.setString(5, companyName);
                            update.setString(6, batchNo);
                            update.setString(7,expDate);
                            update.setFloat(8,vatPercent);
                            update.setInt(9,quantity);
                            update.setFloat(10,rate);
                            update.setFloat(11,assValue);
                            update.setFloat(12,vatAmount);
                            update.setFloat(13,totalAmount);

                            update.setString(14,itemSerialNumber);

                            int result = update.executeUpdate();

                            if (result ==1 ) {
                                System.out.println("record updated");
                                JOptionPane.showInternalMessageDialog(getContentPane()," Record Upadted");
                            }
                            else{
                                JOptionPane.showInternalMessageDialog(getContentPane(),"Sorry, Something went wrong!!!", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                }catch(Exception ex){
                    ex.printStackTrace();
                }

            }
        });

        deleteRecord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    delete = con.prepareStatement("delete from stock_management where item_serial_number = ?;");

                    int rowId = stockRecordTable.getSelectedRow();

                    delete.setString(1, String.valueOf(stockRecordTable.getValueAt(rowId,1)));

                    delete.executeUpdate();

                    dm.removeRow(stockRecordTable.getSelectedRow());

                    JOptionPane.showInternalMessageDialog(getContentPane()," Record Deleted");

                }catch(Exception ex){
                    System.out.println(ex);
                }
            }
        });


        container.add(stockManagementPanel);
        container.add(stockManagementPanel1);

        add(container);
        
    }

    public  void  addDataToRow(String data[])
    {
        dm.addRow(data);
    }


}
