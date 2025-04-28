package POS_SYSTEM;
import fonts.fontUtils;
import javax.swing.*;
import java.awt.*;

public class posSystem extends javax.swing.JFrame {

    private navigationPanel navSection;
    private orderItemPanel orderItemSection;

    public posSystem(){
        //JFRAME:
      setSize(1280,720);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLayout(null);
      setResizable(false);
      setVisible(true);
      this.setLocationRelativeTo(null);

      //SYSTEM ICON
      ImageIcon systemIcon = new ImageIcon("Images/Logo/Philippine_Christian_University_logo.png");
      setIconImage(systemIcon.getImage());
      setTitle("PCU-POS");

      //SYSTEM BG COLOR
        getContentPane().setBackground(Color.decode("#021526"));


      //ADDING SECTIONS & SYSTEM COMPONENTS:
        navSection = new navigationPanel();
        add(navSection);
        orderItemSection = new orderItemPanel();
        add(orderItemSection);


    }

    public static void main(String[] args){
        new logInSection();
    }
}

class navigationPanel extends JPanel{

    public navigationPanel(){

        Font navFont = fontUtils.loadFont(17f);

        //NAV PANEL
        setBackground(Color.decode("#03346E"));
        setBounds(0, 0, 300, 720);
        setLayout(null);

        //PCU LOGO FOR NAV
        ImageIcon systemLogo = new ImageIcon("Images/Logo/Philippine_Christian_University_logo.png");
        JLabel pcuLogo = new JLabel();
        Image originalImage = systemLogo.getImage();

        int imgWidth = 70;
        int imgHeight = 70;

        Image ScaledImg = originalImage.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledLogo = new ImageIcon(ScaledImg);

        pcuLogo.setIcon(scaledLogo);
        pcuLogo.setBounds(6,15,70,70);
        add(pcuLogo);

        //NAV SYSTEM TITLE
        JLabel navTitle = new JLabel();
        navTitle.setForeground(Color.WHITE);
        navTitle.setFont(navFont);
        navTitle.setText("PCU CANTEEN POS");
        navTitle.setBounds(88,43,150,26);
        add(navTitle);

        //NAV OPTIONS
        JLabel orderLabel = new JLabel();
        orderLabel.setForeground(Color.decode("#ffffff"));
        orderLabel.setFont(navFont);
        orderLabel.setText("Order Item");
        orderLabel.setBounds(57, 150, 113, 18);
        add(orderLabel);

        JLabel ohLabel = new JLabel();
        ohLabel.setForeground(Color.decode("#ffffff"));
        ohLabel.setFont(navFont); // Corrected: Use ohLabel.setFont()
        ohLabel.setText("Order History");
        ohLabel.setBounds(57, 200, 192, 18);
        add(ohLabel);

        JLabel dbLabel = new JLabel();
        dbLabel.setForeground(Color.decode("#ffffff"));
        dbLabel.setFont(navFont);
        dbLabel.setText("Dashboard");
        dbLabel.setBounds(57, 250, 126, 18);
        add(dbLabel);

        JLabel menuLabel = new JLabel();
        menuLabel.setForeground(Color.decode("#ffffff"));
        menuLabel.setFont(navFont); // Corrected: Use menuLabel.setFont()
        menuLabel.setText("Menu Management");
        menuLabel.setBounds(57, 300, 213, 18);
        add(menuLabel);

        JLabel invLabel = new JLabel();
        invLabel.setForeground(Color.decode("#ffffff"));
        invLabel.setFont(navFont);
        invLabel.setText("Inventory");
        invLabel.setBounds(57, 350, 192, 18);
        add(invLabel);

        JLabel logoutLabel = new JLabel();
        logoutLabel.setForeground(Color.decode("#ffffff"));
        logoutLabel.setFont(navFont); // Corrected: Use logoutLabel.setFont()
        logoutLabel.setText("Log out");
        logoutLabel.setBounds(57, 600, 57, 18); // Adjusted height to 18 for consistency
        add(logoutLabel);

        repaint();
    }
}

class orderItemPanel extends JPanel{

    orderItemPanel(){

        //ORDER ITEM PANEL
        Font oiFont = fontUtils.loadFont(17f);
        setBackground(Color.decode("#021526"));
        setBounds(300,0,980,720);
        setLayout(null);

        //SEARCH BAR:
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(Color.decode("#898b8f"));
        searchPanel.setBounds(70,30,550,43);
        searchPanel.setLayout(null);

        add(searchPanel);

        JButton searchButton = new JButton();
        searchButton.setText("Search");
        searchButton.setFont(oiFont);
        searchButton.setBounds(450,0,100,43);
        searchPanel.add(searchButton);

        JTextField searchBar = new JTextField();
        searchBar.setBackground(Color.decode("#898b8f"));
        searchBar.setFont(oiFont);
        searchBar.setBounds(0,0,450,43);
        searchPanel.add(searchBar);

        searchBar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                if (searchBar.getText().isEmpty()) {
                    searchBar.setText("Search Menu...");
                }
            }
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (searchBar.getText().equals("Search Menu...")) {
                    searchBar.setText("");
                }
            }
        });
    }
}




