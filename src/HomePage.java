import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by abmiro on 9/12/16.
 */
public class HomePage extends JFrame {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     *
     * @param frameName
     */
    public HomePage(String frameName){
        super(frameName);
        setLayout(null);
        setDefaultTheme();
        setSize(screenSize);
        JMenuBar mainMenuBar = mainMenuBar();
        setJMenuBar(mainMenuBar);


        /**
         * Customer Details Panel
         */
        JPanel customerDetailsPanel = new JPanel();
        customerDetailsPanel.setBounds(5, 5, ((int)screenSize.getWidth())-75, 100);
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
        customerDetailsPanel.add(lblCustomerAddress);
        customerDetailsPanel.add(txtCustomerAddress);
        customerDetailsPanel.add(lblCustomerMobile);
        customerDetailsPanel.add(txtCustomerMobile);
        customerDetailsPanel.setBorder(BorderFactory.createTitledBorder("Customer Details"));
        /******************************end Customer Details************************************************************/

        /**
         * The table for Bill
         */
        JPanel billPanel = new JPanel(null);
        String[] columnNames = {"First Name",
                "Last Name",
                "Sport",
                "# of Years",
                "Vegetarian"};
        Object[][] data = {
                {"Kathy", "Smith",
                        "Snowboarding", new Integer(5), new Boolean(false)},
                {"John", "Doe",
                        "Rowing", new Integer(3), new Boolean(true)},
                {"Sue", "Black",
                        "Knitting", new Integer(2), new Boolean(false)},
                {"Jane", "White",
                        "Speed reading", new Integer(20), new Boolean(true)},
                {"Joe", "Brown",
                        "Pool", new Integer(10), new Boolean(false)}
        };
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        scrollPane.setBounds(100, 100, (billPanel.getWidth())-75, 290);

        billPanel.add(scrollPane);
        billPanel.setBorder(BorderFactory.createTitledBorder("Bill"));
        billPanel.setBounds(5, 105, ((int)screenSize.getWidth())-75, 300);
        /*************************** end ****************************************************/


        this.add(customerDetailsPanel);
        this.add(billPanel);

    }

    /**
     *
     * @param a
     */
    public static void main(String a[]){

        JFrame homePage = new HomePage("MediPest");
        //homePage.setSize();
        System.out.println("Width :" +homePage.getWidth() +" Height :"+ homePage.getHeight() );
        homePage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homePage.setVisible(true);
    }

    /**
     * Main Menu Bar
     * @return MenuBar
     */
    public JMenuBar mainMenuBar(){
        JMenuBar jMainMenuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenu stockMenu = new JMenu("Stock");
        JMenu accountingMenu = new JMenu("Accounting");
        JMenu stockReport = new JMenu("Stock Report");

        JMenuItem item1 = new JMenuItem("1-----------------");
        JMenuItem item2 = new JMenuItem("1=s=d=d=d=d=d=d==d");
        JMenuItem item3 = new JMenuItem("1");
        JMenuItem item4 = new JMenuItem("1");
        JMenuItem item5 = new JMenuItem("1d-d-d---d-d-d-d-d");
        JMenuItem item6 = new JMenuItem("1");
        JMenuItem item7 = new JMenuItem("1s=s=s=s=s");

        fileMenu.add(item1);
        fileMenu.add(item2);
        fileMenu.add(item3);

        stockMenu.add(item4);
        stockMenu.add(item5);
        stockMenu.add(item6);

        accountingMenu.add(item7);

        jMainMenuBar.add(Box.createRigidArea(new Dimension(15, 30)));
        jMainMenuBar.add(fileMenu);
        jMainMenuBar.add(Box.createRigidArea(new Dimension(15, 30)));
        jMainMenuBar.add(stockMenu);
        jMainMenuBar.add(Box.createRigidArea(new Dimension(15, 30)));
        jMainMenuBar.add(accountingMenu);
        jMainMenuBar.add(Box.createRigidArea(new Dimension(15, 30)));
        jMainMenuBar.add(stockReport);

        return jMainMenuBar;
    }

    /**
     * Setting Default window Theme
     */
    public void setDefaultTheme(){

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
    }

    public JLabel getCurrentTime()
    {
        final JLabel timeLabel = new JLabel();


        final DateFormat timeFormat = new SimpleDateFormat("yyy/MM/dd HH:mm:ss");
        ActionListener timerListener = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Date date = new Date();
                String time = timeFormat.format(date);
                timeLabel.setText(time);
            }
        };
        Timer timer = new Timer(1000, timerListener);
        // to make sure it doesn't wait one second at the start
        timer.setInitialDelay(0);
        timer.start();

        return timeLabel;
    }
}
