import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by abmiro on 17/12/16.
 */
public class ShowStock extends JInternalFrame {

    Connection con = null;

    public ShowStock(int width, int height){
        super("Available Stock", true, true, true, true);

        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock_management","root","root");

        }catch (Exception e){
            System.out.println(" :" +e);
        }

        JPanel stockManagementPanel1 = new JPanel();
        stockManagementPanel1.setLayout(null);
        //stockManagementPanel1.setBounds(470,0,width-400, height-90);
        stockManagementPanel1.setBorder(new BorderUIResource.LineBorderUIResource(Color.gray,1));

        String column[] =  {"Trade Name", "Technical Name", "Company Name", "Pkg. Size", "Batch No.", "Exp. Date", "Rate"};
        String data[] = new String[7];



        JTable stockRecordTable = new JTable();

        stockRecordTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        stockRecordTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        DefaultTableModel defaultTableModel = new DefaultTableModel(0,0);
        defaultTableModel.setColumnIdentifiers(column);


        stockRecordTable.setModel(defaultTableModel);

        try {
            PreparedStatement queryData  = con.prepareStatement("SELECT  * FROM medipest_stock;");
            ResultSet records = queryData.executeQuery();

            while (records.next())
            {
                data[0] = records.getString("TRADE_NAME");
                data[1] = records.getString("TECHNICAL_NAME");
                data[2] =  records.getString("COMPANY_NAME");
                data[3] =  records.getString("PACKAGE_SIZE");
                data[4] = records.getString("BATCH_NO");
                data[5] = records.getString("EXPIRY_DATE");
                data[6] = records.getString("RATE");

                defaultTableModel.addRow(data);
            }


        }catch (Exception ex){}


        JScrollPane tableScrollPane = new JScrollPane(stockRecordTable);
        stockRecordTable.setFillsViewportHeight(true);
        tableScrollPane.setBounds(0,0, width-12, height-100);

        JButton editRecord = new JButton();
        JButton deleteRecord = new JButton();
        editRecord.setText("Edit Record");
        editRecord.setBounds(30,height-80,150,30);
        deleteRecord.setText("Delete Record");
        deleteRecord.setBounds(190,height-80,150,30);


        stockManagementPanel1.add(tableScrollPane);
        stockManagementPanel1.add(editRecord);
        stockManagementPanel1.add(deleteRecord);


        editRecord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                int rowId = stockRecordTable.getSelectedRow();


                try {


                    String tradeName = String.valueOf(stockRecordTable.getValueAt(rowId,0));
                    String technicalName =  String.valueOf(stockRecordTable.getValueAt(rowId,1));
                    String companyName =  String.valueOf(stockRecordTable.getValueAt(rowId,2));
                    String pkgSize =   String.valueOf(stockRecordTable.getValueAt(rowId,3));
                    String batchNo =  String.valueOf(stockRecordTable.getValueAt(rowId,4));
                    String expDate = String.valueOf(stockRecordTable.getValueAt(rowId,5));
                    String rate = stockRecordTable.getValueAt(rowId,6).toString();

                    PreparedStatement update = con.prepareStatement("UPDATE  medipest_stock SET TRADE_NAME =?," +
                            "TECHNICAL_NAME=?, COMPANY_NAME=?, PACKAGE_SIZE=?, BATCH_NO=?, EXPIRY_DATE=?, RATE=? WHERE TRADE_NAME = ? AND TECHNICAL_NAME = ?");

                    update.setString(1, tradeName);
                    update.setString(2, technicalName);
                    update.setString(3, companyName);
                    update.setString(4, pkgSize);
                    update.setString(5, batchNo);
                    update.setString(6, expDate);
                    update.setString(7, rate);
                    update.setString(8, tradeName);
                    update.setString(9, technicalName);

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

                    PreparedStatement delete = con.prepareStatement("DELETE FROM medipest_stock WHERE TRADE_NAME = ? AND TECHNICAL_NAME = ?");

                    int rowId = stockRecordTable.getSelectedRow();

                    delete.setString(1, String.valueOf(stockRecordTable.getValueAt(rowId,0)));
                    delete.setString(2, String.valueOf(stockRecordTable.getValueAt(rowId,1)));

                    delete.executeUpdate();

                    defaultTableModel.removeRow(stockRecordTable.getSelectedRow());

                    JOptionPane.showInternalMessageDialog(getContentPane()," Record Deleted");

                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        add(stockManagementPanel1);

    }


}
