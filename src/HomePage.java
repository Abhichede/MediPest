import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;

/**
 * Created by abmiro on 9/12/16.
 */
public class HomePage extends JFrame implements Printable{

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    JDesktopPane desktopPane;
    JInternalFrame internalFrame;
    /**
     *
     * @param frameName
     */
    public HomePage(String frameName){
        super(frameName);
        //setLayout(null);
        setDefaultTheme();
        setSize(screenSize);
        JMenuBar mainMenuBar = mainMenuBar();
        desktopPane = new JDesktopPane();
        setJMenuBar(mainMenuBar);


        //this.add(desktopPane);
        setContentPane(desktopPane);

    }

    /**
     *
     * @param a
     */
    public static void main(String a[]){

        JFrame homePage = new HomePage("MediPest");
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
        JMenuItem item4 = new JMenuItem("Stock Management");
        JMenuItem item5 = new JMenuItem("1d-d-d---d-d-d-d-d");
        JMenuItem item6 = new JMenuItem("1");
        JMenuItem item7 = new JMenuItem("1s=s=s=s=s");
        JMenuItem showReport = new JMenuItem("Show Report");

        fileMenu.add(item1);
        fileMenu.add(item2);
        fileMenu.add(item3);

        stockMenu.add(item4);
        stockMenu.add(item5);
        stockMenu.add(item6);

        item4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(internalFrame != null){
                    internalFrame.dispose();
                }
                internalFrame = new StockManagement(desktopPane.getWidth(), desktopPane.getHeight());
                internalFrame.setSize(desktopPane.getWidth(), desktopPane.getHeight());
                internalFrame.setVisible(true);
                desktopPane.add(internalFrame);
            }
        });

        accountingMenu.add(item7);
        item7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(internalFrame != null){
                    internalFrame.dispose();
                }
                internalFrame = new MakeBill(desktopPane.getSize());
                internalFrame.setSize(desktopPane.getWidth(), desktopPane.getHeight());
                internalFrame.setVisible(true);
                desktopPane.add(internalFrame);
            }
        });

        stockReport.add(showReport);


        jMainMenuBar.add(Box.createRigidArea(new Dimension(15, 30)));
        jMainMenuBar.add(fileMenu);
        jMainMenuBar.add(Box.createRigidArea(new Dimension(15, 30)));
        jMainMenuBar.add(stockMenu);
        jMainMenuBar.add(Box.createRigidArea(new Dimension(15, 30)));
        jMainMenuBar.add(accountingMenu);
        jMainMenuBar.add(Box.createRigidArea(new Dimension(15, 30)));
        jMainMenuBar.add(stockReport);

        showReport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(internalFrame != null){
                        internalFrame.dispose();
                    }
                    internalFrame = new StockReport("Stock Report");
                    internalFrame.setSize(((int) screenSize.getWidth()) - 70, ((int) screenSize.getHeight()) - 65);
                    System.out.println("Stock Clicked");
                    desktopPane.add(internalFrame);
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        });

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

    public int print(Graphics g, PageFormat pf, int page) throws
            PrinterException {

        if (page > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }

        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        /* Now we perform our rendering */
        g.drawString("Hello world!", 100, 100);

        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }

}
