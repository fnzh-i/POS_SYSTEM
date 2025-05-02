package POS_SYSTEM;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;

public class posSystem extends javax.swing.JFrame {
    public static void main(String[] args){
        new logInSection();

    }
    private navigationPanel navSection;
    private orderItemPanel orderItemSection;

    public posSystem(){


        //JFRAME:
        setSize(1280,720);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
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

        orderSummary order = new orderSummary();
        order.orderSumm(200.00, 120.00);

        setVisible(true);
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

        JScrollBar verticalScrollBar1 = productScrollPane.getVerticalScrollBar();
        verticalScrollBar1.setUnitIncrement(30);
        verticalScrollBar1.setBlockIncrement(100);

        mainConts.add(productScrollPane);


        //FOR ORDER SUMMARY AND ORDER PROCESS:

        JPanel orderSummary = new JPanel();
        orderSummary.setPreferredSize(new Dimension(250, 432));
        orderSummary.setBackground(Color.decode("#021526"));
        orderSummary.setAlignmentY(Component.CENTER_ALIGNMENT);
//        orderSummary.setLayout(new BoxLayout(orderSummary, BoxLayout.Y_AXIS));

        //DATE AND TIME:
        JLabel datelabel = new JLabel();
        datelabel.setForeground(Color.WHITE);
        datelabel.setFont(dateFont);
        datelabel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, yyyy-MM-dd");
        Date date = new Date();
        String formattedDate = dateFormat.format(date);
        datelabel.setText(formattedDate);
        datelabel.setBounds(700, 40, 250, 20); // Set the bounds for datelabel
        orderSummary.add(datelabel);

        //FOR ORDER LIST:
        JPanel orderList = new JPanel();
        orderList.setLayout(new BoxLayout(orderList, BoxLayout.Y_AXIS));
        orderList.setBackground(Color.decode("#424040"));
        orderList.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));




        orderList.add(productItemSummary("Piattos", "Images/Sample Product Images/Piattos-Cheese-40g.png", "40g", cattegoryFont, 15.00));
        orderList.add(productItemSummary("Piattos", "Images/Sample Product Images/Piattos-Cheese-40g.png", "40g", cattegoryFont, 15.00));
        orderList.add(productItemSummary("Piattos", "Images/Sample Product Images/Piattos-Cheese-40g.png", "40g", cattegoryFont, 15.00));
        orderList.add(productItemSummary("Piattos", "Images/Sample Product Images/Piattos-Cheese-40g.png", "40g", cattegoryFont, 15.00));


        JScrollPane orderListScroll = new JScrollPane(orderList);
        orderListScroll.setBorder(null);
        orderListScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        orderListScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JScrollBar verticalScrollBar2 = orderListScroll.getVerticalScrollBar();
        verticalScrollBar2.setUnitIncrement(30);
        verticalScrollBar2.setBlockIncrement(100);
        orderSummary.add(orderListScroll);


        //FOR SUBTOTAL:
        JPanel subTotal = new JPanel();
        subTotal.setBackground(Color.decode("#424040"));
        subTotal.setPreferredSize(new Dimension(250, 144));
        subTotal.setLayout(new BoxLayout(subTotal, BoxLayout.Y_AXIS));

        JLabel stText = new JLabel("Sub Total: ");
        stText.setFont(dateFont);
        stText.setForeground(Color.WHITE);
        subTotal.add(stText);

        JLabel stTax = new JLabel("Tax: ");
        stTax.setFont(dateFont);
        stTax.setForeground(Color.WHITE);
        subTotal.add(stTax);

        JLabel stLine = new JLabel("__________________________________");
        stTax.setFont(dateFont);
        stLine.setForeground(Color.WHITE);
        subTotal.add(stLine);

        JLabel stTotal = new JLabel("Total: ");
        stTotal.setFont(cattegoryFont);
        stTotal.setForeground(Color.WHITE);
        subTotal.add(stTotal);

        orderSummary.add(subTotal);


        //PROCESS BUTTONS:
        JPanel orderButton = new JPanel();
        orderButton.setLayout(new FlowLayout(FlowLayout.CENTER, 10,0));
        orderButton.setOpaque(false);


        JButton cancelBtn = new JButton("Cancel Order");
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setBackground(Color.decode("#BD1212"));
        cancelBtn.setPreferredSize(new Dimension(130,42));
        cancelBtn.setFont(productItemFont);
        orderButton.add(cancelBtn);

        JButton processBtn = new JButton("Process Order");
        processBtn.setForeground(Color.WHITE);
        processBtn.setBackground(Color.decode("#F9A61A"));
        processBtn.setPreferredSize(new Dimension(130,42));
        processBtn.setFont(productItemFont);
        orderButton.add(processBtn);

        orderSummary.add(orderButton);













        add(mainConts, BorderLayout.CENTER);
        add(orderSummary, BorderLayout.EAST);

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
        productItemSum.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));


        ImageIcon originalIcon = new ImageIcon(imgPath);
        Image scaledImage = originalIcon.getImage().getScaledInstance(70,70, Image.SCALE_SMOOTH);
        ImageIcon productPic = new ImageIcon(scaledImage);



        //Product image (WEST PART):
        JLabel imgFrame = new JLabel(productPic, SwingConstants.CENTER);
        imgFrame.setAlignmentX(Component.CENTER_ALIGNMENT);
        productItemSum.add(imgFrame, BorderLayout.WEST);



        //PRODUCT DETAILS (CENTER PART):
        JPanel centerPart = new JPanel();
        centerPart.setOpaque(false);
        centerPart.setLayout(new BorderLayout());

        JLabel itemName = new JLabel(productName);
        itemName.setFont(font);
        itemName.setForeground(Color.WHITE);
        centerPart.add(itemName, BorderLayout.NORTH);

        JLabel itemSize = new JLabel("Size: " + productSize);
        itemSize.setFont(font);
        itemSize.setForeground(Color.WHITE);
        centerPart.add(itemSize, BorderLayout.CENTER);

        JLabel itemPrize = new JLabel("₱" + String.format("%.2f", price));
        itemPrize.setFont(font);
        itemPrize.setForeground(Color.decode("#686AF5"));
        centerPart.add(itemPrize, BorderLayout.SOUTH);

        productItemSum.add(centerPart, BorderLayout.CENTER);

        //BUTTONS (EAST PART):

        JPanel eastPanel = new JPanel();
        eastPanel.setOpaque(false);
        eastPanel.setLayout(new BorderLayout());


        JButton trashButton = new JButton("x");
        trashButton.setForeground(Color.decode("#BD1212"));
        trashButton.setPreferredSize(new Dimension(17,17));
        trashButton.setBorderPainted(false);
        trashButton.setFocusPainted(false);
        trashButton.setBackground(Color.gray);
        trashButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        eastPanel.add(trashButton, BorderLayout.NORTH);


        JPanel quantityButton = new JPanel();
        quantityButton.setOpaque(false);
        quantityButton.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        quantityButton.setAlignmentX(Component.LEFT_ALIGNMENT);



        JButton minusBtn = new JButton("-");
        minusBtn.setForeground(Color.WHITE);
        minusBtn.setBackground(Color.gray);
        minusBtn.setBorderPainted(false);
        minusBtn.setFocusPainted(false);
        minusBtn.setPreferredSize(new Dimension(23, 23));
        minusBtn.setHorizontalAlignment(SwingConstants.CENTER);
        minusBtn.setVerticalAlignment(SwingConstants.CENTER);
        quantityButton.add(minusBtn);

        int quantity = 1;
        JLabel quantityLabel = new JLabel(String.valueOf(quantity));
        quantityLabel.setForeground(Color.WHITE);
        quantityButton.add(quantityLabel);

        JButton plusBtn = new JButton("+");
        plusBtn.setForeground(Color.WHITE);
        plusBtn.setBackground(Color.gray);
        plusBtn.setBorderPainted(false);
        plusBtn.setFocusPainted(false);
        plusBtn.setPreferredSize(new Dimension(23, 23));
        plusBtn.setHorizontalAlignment(SwingConstants.CENTER);
        plusBtn.setVerticalAlignment(SwingConstants.CENTER);
        quantityButton.add(plusBtn);

        eastPanel.add(quantityButton, BorderLayout.SOUTH);

        productItemSum.add(eastPanel, BorderLayout.EAST);

        JLabel lineText = new JLabel("__________________________________");
        lineText.setForeground(Color.WHITE);
        lineText.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
        productItemSum.add(lineText, BorderLayout.SOUTH);






        return productItemSum;







    }


}


