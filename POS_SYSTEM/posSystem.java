import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;

public class posSystem extends JFrame {

    private navigationPanel navSection;
    private orderItemPanel orderItemSection;

    public posSystem(){


        //JFRAME:
        setSize(1280,720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);


        //SYSTEM ICON
        ImageIcon systemIcon = new ImageIcon("cropped-PCU-logo.png");
        setIconImage(systemIcon.getImage());
        setTitle("PCU-POS");

        //SYSTEM BG COLOR
        getContentPane().setBackground(Color.decode("#021526"));


        //ADDING SECTIONS & SYSTEM COMPONENTS:
        navSection = new navigationPanel();
        add(navSection);
        orderItemSection = new orderItemPanel();
        add(orderItemSection);
        setVisible(true);

    }

    public static void main(String[] args){
        new posSystem();
    }




}

class FontUtils{
    public  static Font loadFont(String fontPath, float size){
        Font font = null;
        try{
            File fontStyle = new File("Fonts/Roboto-VariableFont_wdth,wght.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, fontStyle).deriveFont(size);
        }catch (Exception e){
            e.printStackTrace();
            font = new Font ("ARIAL", Font.PLAIN, (int) size);
        }
        return font;
    }
    public static Font loadFont(float size){
        return loadFont("Fonts/Roboto-VariableFont_wdth,wght.ttf", size);
    }

}


class navigationPanel extends JPanel{

    public navigationPanel(){

        Font navFont = FontUtils.loadFont(17f);


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


        orderLabel.addMouseListener(new MouseAdapter() { // to log out
            @Override
            public void mouseClicked(MouseEvent e) {
                new orderSummary();

            }
        });

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

        logoutLabel.addMouseListener(new MouseAdapter() { // to log out
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(logoutLabel);
                if (topFrame != null) {
                    topFrame.dispose();
                }
             new logInSection();

            }
        });

    }

}



class orderItemPanel extends JPanel{

    orderItemPanel(){
        Font cattegoryFont = FontUtils.loadFont(13f);
        Font dateFont = FontUtils.loadFont(12f);
        Font processBtnFont = FontUtils.loadFont(16f);

        //ORDER ITEM PANEL
        Font oiFont = FontUtils.loadFont(17f);
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


        //JBUTTON FOR PRODUCT CATEGORY
        JButton allButton = new JButton();
        allButton.setText("All");
        allButton.setFont(cattegoryFont);
        allButton.setBackground(Color.gray);
        allButton.setBorder(BorderFactory.createLineBorder(Color.decode("#5b7bf0"),3));
        allButton.setForeground(Color.WHITE);
        allButton.setBounds(70,95, 45,30);
        add(allButton);

        JButton mealsButton = new JButton();
        mealsButton.setText("Meals");
        mealsButton.setFont(cattegoryFont);
        mealsButton.setBackground(Color.gray);
//        mealsButton.setBorder(BorderFactory.createLineBorder(Color.WHITE,2));
        mealsButton.setForeground(Color.WHITE);
        mealsButton.setBounds(120,95, 80,30);
        add(mealsButton);

        JButton snacksButton = new JButton();
        snacksButton.setText("Snacks");
        snacksButton.setFont(cattegoryFont);
        snacksButton.setBackground(Color.gray);
//        mealsButton.setBorder(BorderFactory.createLineBorder(Color.WHITE,2));
        snacksButton.setForeground(Color.WHITE);
        snacksButton.setBounds(210,95, 80,30);
        add(snacksButton);

        JButton drinksButton = new JButton();
        drinksButton.setText("Drinks");
        drinksButton.setFont(cattegoryFont);
        drinksButton.setBackground(Color.gray);
//        mealsButton.setBorder(BorderFactory.createLineBorder(Color.WHITE,2));
        drinksButton.setForeground(Color.WHITE);
        drinksButton.setBounds(300,95, 80,30);
        add(drinksButton);


        JLabel datelabel = new JLabel();
        datelabel.setForeground(Color.WHITE);
        datelabel.setFont(dateFont);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, yyyy-MM-dd");
        Date date = new Date();
        String formattedDate = dateFormat.format(date);
        datelabel.setText(formattedDate);
        datelabel.setBounds(700, 40, 250, 20); // Set the bounds for datelabel
        add(datelabel);


        productItem product = new productItem();
        orderSummary os = new orderSummary();
        subTotal st= new subTotal();

        add(product);
        add(os);
        add(st);

    }

}

class productItem extends JPanel{

    public productItem(){
        setBackground(Color.decode("#111010"));
        setLayout(null);
        setBounds(70,150, 150,250);
        setBorder(BorderFactory.createLineBorder(Color.WHITE,2));

    }

}

class orderSummary extends JPanel{
    public orderSummary(){
        setBackground(Color.decode("#383737"));
        setLayout(null);
        setBounds(670,95, 260,300);

    }

}

class subTotal extends  JPanel{
    public subTotal(){
        setBackground(Color.decode("#383737"));
        setLayout(null);
        setBounds(670,400, 260,150);
    }
}
