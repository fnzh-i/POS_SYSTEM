import jakarta.xml.bind.JAXBContext;

import javax.swing.*;
import java.awt.*;
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
      setLayout(new BorderLayout());
      setResizable(false);


      //SYSTEM ICON
      ImageIcon systemIcon = new ImageIcon("Images/Logo/Philippine_Christian_University_logo.png");
      setIconImage(systemIcon.getImage());
      setTitle("PCU-POS");

      //SYSTEM BG COLOR
        getContentPane().setBackground(Color.decode("#021526"));


      //ADDING SECTIONS & SYSTEM COMPONENTS:
        navSection = new navigationPanel();
        add(navSection, BorderLayout.WEST);
        orderItemSection = new orderItemPanel();
        add(orderItemSection, BorderLayout.CENTER);


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

        Font navFont = FontUtils.loadFont(20f);
        setPreferredSize(new Dimension(300, getHeight()));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));



        //NAV PANEL
        setBackground(Color.decode("#03346E"));


        JPanel navHeader = new JPanel();
        navHeader.setLayout(new BoxLayout(navHeader, BoxLayout.X_AXIS));
        navHeader.setBackground(Color.decode("#03346E"));
        navHeader.setBorder(BorderFactory.createEmptyBorder(20,20,30,20));
        navHeader.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));


        //PCU LOGO FOR NAV
        ImageIcon systemLogo = new ImageIcon("Images/Logo/Philippine_Christian_University_logo.png");
        JLabel pcuLogo = new JLabel();
        Image originalImage = systemLogo.getImage();

        int imgWidth = 70;
        int imgHeight = 70;

        Image ScaledImg = originalImage.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledLogo = new ImageIcon(ScaledImg);

        pcuLogo.setIcon(scaledLogo);
        pcuLogo.setAlignmentY(Component.CENTER_ALIGNMENT);
        navHeader.add(pcuLogo);

        //NAV SYSTEM TITLE
        JLabel navTitle = new JLabel();
        navTitle.setForeground(Color.WHITE);
        navTitle.setFont(navFont);
        navTitle.setText("   PCU CANTEEN POS");
        navTitle.setAlignmentY(Component.CENTER_ALIGNMENT);
        navHeader.add(navTitle);

        add(navHeader);

        //NAV OPTIONS

        // NAV OPTIONS - in a separate JPanel
        JPanel navOptionsPanel = new JPanel();
        navOptionsPanel.setLayout(new BoxLayout(navOptionsPanel, BoxLayout.Y_AXIS));
        navOptionsPanel.setBackground(Color.decode("#03346E"));
        navOptionsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        navOptionsPanel.add(createNavButton("Order Item", navFont));
        navOptionsPanel.add(createNavButton("Order History", navFont));
        navOptionsPanel.add(createNavButton("Dashboard", navFont));
        navOptionsPanel.add(createNavButton("Menu Management", navFont));
        navOptionsPanel.add(createNavButton("Inventory", navFont));

        navOptionsPanel.add(Box.createVerticalGlue()); // Push Log out to the bottom
        navOptionsPanel.add(createNavButton("Logout", navFont));
        navOptionsPanel.add(Box.createVerticalStrut(20));

        add(navOptionsPanel, BorderLayout.CENTER); // Add the options panel to the center


        repaint();


    }

    private JButton createNavButton(String text, Font font){

        JButton navButton = new JButton(text);
        navButton.setFont(font);
        navButton.setForeground(Color.WHITE);
        navButton.setBackground(Color.decode("#03346E"));
        navButton.setBorderPainted(false);
        navButton.setFocusPainted(false);
        navButton.setHorizontalAlignment(SwingConstants.LEFT); // Align text to the left
        navButton.setAlignmentX(Component.CENTER_ALIGNMENT);   // Align button to the left in BoxLayout
        navButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        navButton.setBorder(BorderFactory.createEmptyBorder(20,40,20,0));




        //HOVER EFFECTS:
        navButton.addMouseListener(new MouseAdapter() {
            private Color originalColor = navButton.getBackground();
            private Color hoverColor = Color.decode("#035096");

            @Override
            public void mouseEntered(MouseEvent e) {
                navButton.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                navButton.setBackground(originalColor);
            }
        });

        return navButton;

    }


}



class orderItemPanel extends JPanel{

    orderItemPanel(){
        Font cattegoryFont = FontUtils.loadFont(13f);
        Font productItemFont = FontUtils.loadFont(15f);
        Font dateFont = FontUtils.loadFont(12f);
        Font processBtnFont = FontUtils.loadFont(16f);

        //ORDER ITEM PANEL
        Font oiFont = FontUtils.loadFont(17f);
        setBackground(Color.decode("#021526"));
        setPreferredSize(new Dimension(980, getHeight()));
        setLayout(new BorderLayout());

        JPanel mainConts = new JPanel();
        mainConts.setLayout(new BoxLayout(mainConts, BoxLayout.Y_AXIS));
        mainConts.setBackground(Color.decode("#021526"));
        mainConts.setBorder(BorderFactory.createEmptyBorder(10,70,10,0));





        //SEARCH BAR:
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        searchPanel.setBackground(Color.decode("#898b8f"));
        searchPanel.setPreferredSize(new Dimension(550,43));
        searchPanel.setLayout(new BorderLayout());
        searchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField searchBar = new JTextField();
        searchBar.setBackground(Color.decode("#898b8f"));
        searchBar.setFont(oiFont);
        searchBar.setPreferredSize(new Dimension(450,43));
        searchPanel.add(searchBar, BorderLayout.CENTER);

        JButton searchButton = new JButton();
        searchButton.setText("Search");
        searchButton.setFont(oiFont);
        searchButton.setPreferredSize(new Dimension(100,43));
        searchPanel.add(searchButton, BorderLayout.EAST);


        mainConts.add(searchPanel);


        //JBUTTON FOR PRODUCT CATEGORY

        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
        categoryPanel.setBackground(Color.decode("#021526"));
        categoryPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        categoryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        categoryPanel.add(createCategoryButton("All", cattegoryFont, Color.gray));
        categoryPanel.add(createCategoryButton("Meals", cattegoryFont, Color.gray));
        categoryPanel.add(createCategoryButton("Snacks", cattegoryFont, Color.gray));
        categoryPanel.add(createCategoryButton("Drinks", cattegoryFont, Color.gray));

        mainConts.add(categoryPanel);


        //PRODUCT ITEM PANEL
        JPanel productItemPanel = new JPanel(new GridLayout(0,3,20,20));
        productItemPanel.setBackground(Color.decode("#021526"));
        productItemPanel.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
        productItemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        productItemPanel.add(createProductItem("Jack'n Jill Piattos", "40g", "Images/Sample Product Images/Piattos-Cheese-40g.png", productItemFont , 15.00));
        productItemPanel.add(createProductItem("Jack'n Jill Piattos", "40g", "Images/Sample Product Images/Piattos-Cheese-40g.png", productItemFont , 15.00));
        productItemPanel.add(createProductItem("Jack'n Jill Piattos", "40g", "Images/Sample Product Images/Piattos-Cheese-40g.png", productItemFont , 15.00));
        productItemPanel.add(createProductItem("Jack'n Jill Piattos", "40g", "Images/Sample Product Images/Piattos-Cheese-40g.png", productItemFont , 15.00));
        productItemPanel.add(createProductItem("Jack'n Jill Piattos", "40g", "Images/Sample Product Images/Piattos-Cheese-40g.png", productItemFont , 15.00));
        productItemPanel.add(createProductItem("Jack'n Jill Piattos", "40g", "Images/Sample Product Images/Piattos-Cheese-40g.png", productItemFont , 15.00));
        productItemPanel.add(createProductItem("Jack'n Jill Piattos", "40g", "Images/Sample Product Images/Piattos-Cheese-40g.png", productItemFont , 15.00));

        JScrollPane productScrollPane = new JScrollPane(productItemPanel);
        productScrollPane.setBorder(null);
        productScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        productScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JScrollBar verticalScrollBar = productScrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(30);
        verticalScrollBar.setBlockIncrement(100);

        mainConts.add(productScrollPane);




        JPanel orderSummary = new JPanel();
        orderSummary.setPreferredSize(new Dimension(294, getHeight()));
        orderSummary.setBackground(Color.decode("#021526"));


        JLabel datelabel = new JLabel();
        datelabel.setForeground(Color.WHITE);
        datelabel.setFont(dateFont);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, yyyy-MM-dd");
        Date date = new Date();
        String formattedDate = dateFormat.format(date);
        datelabel.setText(formattedDate);
        datelabel.setBounds(700, 40, 250, 20); // Set the bounds for datelabel
        orderSummary.add(datelabel);


        productItem product = new productItem();
        orderSummary os = new orderSummary();
        subTotal st= new subTotal();


        add(mainConts, BorderLayout.CENTER);
        orderSummary.add(os);
        orderSummary.add(st);
        add(orderSummary, BorderLayout.EAST);

//

    }

    private JButton createCategoryButton(String text, Font font, Color bgColor){
        JButton categoryButton = new JButton(text);
        categoryButton.setFont(font);
        categoryButton.setBackground(bgColor);
        categoryButton.setBorderPainted(false);
        categoryButton.setFocusPainted(false);
        categoryButton.setForeground(Color.WHITE);


        categoryButton.addMouseListener(new MouseAdapter() {
            private Color originalColor = categoryButton.getBackground();
            private Color hoverColor = Color.decode("#b1b5b2");

            @Override
            public void mouseEntered(MouseEvent e) {
                categoryButton.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                categoryButton.setBackground(originalColor);
            }
        });

        return categoryButton;
    }

    private JPanel createProductItem (String text, String productSize,String productImg, Font font, double price){
        JPanel productItem = new JPanel();
        productItem.setBackground(Color.decode("#111010"));
        productItem.setPreferredSize(new Dimension(150,250));
        productItem.setLayout(new BorderLayout(5,5));
        productItem.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        productItem.setBorder(BorderFactory.createLineBorder(Color.WHITE,1));

        ImageIcon originalIcon = new ImageIcon(productImg);
        Image scaledImage = originalIcon.getImage().getScaledInstance(130,130, Image.SCALE_SMOOTH);
        ImageIcon productPic = new ImageIcon(scaledImage);

        //Product image:
        JLabel imgFrame = new JLabel(productPic, SwingConstants.CENTER);
        imgFrame.setAlignmentX(Component.CENTER_ALIGNMENT);
        productItem.add(imgFrame, BorderLayout.NORTH);


        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.decode("#111010"));

        JLabel productName = new JLabel(text);
        productName.setFont(font);
        productName.setForeground(Color.WHITE);
        productName.setAlignmentX(Component.CENTER_ALIGNMENT);
        productItem.add(productName);
        JLabel itemSize = new JLabel(productSize);
        itemSize.setFont(font);
        itemSize.setForeground(Color.gray);
        itemSize.setAlignmentX(Component.CENTER_ALIGNMENT);
        textPanel.add(productName);
        textPanel.add(itemSize);
        productItem.add(textPanel, BorderLayout.CENTER);

        JPanel bottomPannel = new JPanel();
        bottomPannel.setLayout(new BorderLayout());
        bottomPannel.setBackground(Color.decode("#111010"));

        JLabel productPrice = new JLabel();
        productPrice.setText("₱" + String.format("%.2f", price));
        productPrice.setFont(font);
        productPrice.setForeground(Color.decode("#686AF5"));
        bottomPannel.add(productPrice, BorderLayout.WEST);

        JButton addProduct = new JButton("+");
        addProduct.setForeground(Color.BLACK);
        addProduct.setBackground(Color.decode("#686AF5"));
        addProduct.setBorderPainted(false);
        addProduct.setFocusPainted(false);
        addProduct.setPreferredSize(new Dimension(42, 42)); // Use the size you mentioned
        addProduct.setFont(font.deriveFont(Font.PLAIN, 14f)); // Try a slightly larger, plain font
        addProduct.setHorizontalAlignment(SwingConstants.CENTER);
        addProduct.setVerticalAlignment(SwingConstants.CENTER); // Keep a reasonable size
        bottomPannel.add(addProduct, BorderLayout.EAST);
        productItem.add(bottomPannel, BorderLayout.SOUTH);


        return productItem;
    }

    private JPanel productItemSummary(String productName, String imgPath, String productSize, Font font, double price){

        JPanel productItemSum = new JPanel();
        productItemSum.setLayout(new BorderLayout());
        productItemSum.setOpaque(false);


        ImageIcon originalIcon = new ImageIcon(imgPath);
        Image scaledImage = originalIcon.getImage().getScaledInstance(70,70, Image.SCALE_SMOOTH);
        ImageIcon productPic = new ImageIcon(scaledImage);



        //Product image (WEST PART):
        JLabel imgFrame = new JLabel(productPic, SwingConstants.CENTER);
        imgFrame.setAlignmentX(Component.CENTER_ALIGNMENT);
        productItemSum.add(imgFrame, BorderLayout.WEST);



        //PRODUCT DETAILS (CENTER PART):
        JLabel itemName = new JLabel(productName);
        itemName.setFont(font);
        itemName.setForeground(Color.WHITE);
        productItemSum.add(itemName);

        JLabel itemSize = new JLabel("Size: " + productSize);
        itemSize.setFont(font);
        itemSize.setForeground(Color.WHITE);
        productItemSum.add(itemSize);

        JLabel itemPrize = new JLabel("₱" + String.format("%.2f", price));
        itemPrize.setFont(font);
        itemPrize.setForeground(Color.decode("#686AF5"));
        productItemSum.add(itemPrize);



        //BUTTONS (EAST PART):

        JButton trashButton = new JButton("x");
        trashButton.setForeground(Color.decode("#BD1212"));
        trashButton.setPreferredSize(new Dimension(17,17));
        trashButton.setBackground(Color.gray);

        JPanel quantityButton = new JPanel();
        quantityButton.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));

        JButton addProduct = new JButton("-");
        addProduct.setForeground(Color.WHITE);
        addProduct.setBackground(Color.gray);
        addProduct.setBorderPainted(false);
        addProduct.setFocusPainted(false);
        addProduct.setPreferredSize(new Dimension(42, 42)); // Use the size you mentioned
        addProduct.setFont(font.deriveFont(Font.PLAIN, 14f)); // Try a slightly larger, plain font
        addProduct.setHorizontalAlignment(SwingConstants.CENTER);
        addProduct.setVerticalAlignment(SwingConstants.CENTER); // Keep








    }


}



class productItem extends JPanel{

    public productItem(){
        setBackground(Color.decode("#111010"));
        setPreferredSize(new Dimension(150,250));
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



