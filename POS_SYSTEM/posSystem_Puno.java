import jakarta.xml.bind.JAXBContext;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;

public class posSystem_Puno extends JFrame {

    private navigationPanel navSection;
    private orderItemPanel orderItemSection;

    public posSystem_Puno(){


        //JFRAME:
      setSize(1920,1080);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setExtendedState(JFrame.MAXIMIZED_BOTH);
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
        new posSystem_Puno();
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



class orderItemPanel extends JPanel {

    orderItemPanel() {
        Font sz12 = FontUtils.loadFont(12f);
        Font sz13 = FontUtils.loadFont(13f);
        Font sz15 = FontUtils.loadFont(15f);
        Font sz16 = FontUtils.loadFont(16f);
        Font sz17 = FontUtils.loadFont(17f);

        //ORDER ITEM PANEL
        Font oiFont = FontUtils.loadFont(17f);
        setBackground(Color.decode("#021526"));
        setPreferredSize(new Dimension(980, getHeight()));
        setLayout(new BorderLayout());

        JPanel mainConts = new JPanel();
        mainConts.setLayout(new BoxLayout(mainConts, BoxLayout.Y_AXIS));
        mainConts.setBackground(Color.decode("#021526"));
        mainConts.setBorder(BorderFactory.createEmptyBorder(10, 60, 0, 0));


        //SEARCH BAR:
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        searchPanel.setBackground(Color.decode("#898b8f"));
        searchPanel.setPreferredSize(new Dimension(550, 43));
        searchPanel.setMaximumSize(new Dimension(550, 43));
        searchPanel.setLayout(new BorderLayout());
        searchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField searchBar = new JTextField();
        searchBar.setBackground(Color.decode("#898b8f"));
        searchBar.setFont(oiFont);
        searchBar.setPreferredSize(new Dimension(450, 43));
        searchBar.setMaximumSize(new Dimension(450, 43));
        searchPanel.add(searchBar, BorderLayout.CENTER);

        JButton searchButton = new JButton();
        searchButton.setText("Search");
        searchButton.setFont(oiFont);
        searchButton.setPreferredSize(new Dimension(100, 43));
        searchButton.setMaximumSize(new Dimension(100, 43));
        searchPanel.add(searchButton, BorderLayout.EAST);


        mainConts.add(searchPanel);


        //JBUTTON FOR PRODUCT CATEGORY

        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        categoryPanel.setBackground(Color.decode("#021526"));
        categoryPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        categoryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        categoryPanel.add(createCategoryButton("All", sz13, Color.gray));
        categoryPanel.add(createCategoryButton("Meals", sz13, Color.gray));
        categoryPanel.add(createCategoryButton("Snacks", sz13, Color.gray));
        categoryPanel.add(createCategoryButton("Drinks", sz13, Color.gray));

        mainConts.add(categoryPanel);


        //PRODUCT ITEM PANEL
        JPanel productItemPanel = new JPanel(new GridLayout(0, 3, 20, 20));
        productItemPanel.setBackground(Color.decode("#021526"));

        productItemPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        productItemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);



        JScrollPane productScrollPane = new JScrollPane(productItemPanel);
        productScrollPane.setPreferredSize(new Dimension(getWidth(),980));
        productScrollPane.setMaximumSize(new Dimension(1500,500));
        productScrollPane.setBorder(null);
        productScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        productScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JScrollBar verticalScrollBar1 = productScrollPane.getVerticalScrollBar();
        verticalScrollBar1.setUnitIncrement(30);
        verticalScrollBar1.setBlockIncrement(100);

        mainConts.add(productScrollPane);


        //FOR ORDER SUMMARY AND ORDER PROCESS:

        JPanel orderSummary = new JPanel();
        orderSummary.setPreferredSize(new Dimension(550, 1080));
        orderSummary.setBackground(Color.decode("#021526"));
        orderSummary.setLayout(new BoxLayout(orderSummary, BoxLayout.Y_AXIS));


        //DATE AND TIME:
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEADING)); // Align to the left
        datePanel.setOpaque(false);
        JLabel datelabel = new JLabel();
        datelabel.setForeground(Color.WHITE);
        datelabel.setFont(sz16);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy h:mm a");
        Date date = new Date();
        String formattedDate = dateFormat.format(date);
        datelabel.setText(formattedDate);
        datePanel.add(datelabel);
        datePanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 0, 10));
        orderSummary.add(datePanel);

        //FOR ORDER LIST:
        JPanel orderList = new JPanel();
        orderList.setLayout(new BoxLayout(orderList, BoxLayout.Y_AXIS));
        orderList.setBackground(Color.decode("#424040"));

        JScrollPane orderListScroll = new JScrollPane(orderList);
        orderListScroll.setBorder(null);
        orderListScroll.setPreferredSize(new Dimension(300, 300));
        orderListScroll.setAlignmentX(Component.CENTER_ALIGNMENT);
        orderListScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        orderListScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        orderListScroll.setBorder(BorderFactory.createEmptyBorder(0, 30, 20, 30));
        orderListScroll.setOpaque(false);





        JScrollBar verticalScrollBar2 = orderListScroll.getVerticalScrollBar();
        verticalScrollBar2.setUnitIncrement(30);
        verticalScrollBar2.setBlockIncrement(100);
        orderSummary.add(orderListScroll);


        //FOR SUBTOTAL:
        JPanel subTotal = new JPanel();
        subTotal.setBackground(Color.decode("#424040"));
        subTotal.setPreferredSize(new Dimension(485, 200));
        subTotal.setMaximumSize(new Dimension(485, 200));
        subTotal.setAlignmentX(Component.CENTER_ALIGNMENT);
        subTotal.setLayout(new BoxLayout(subTotal, BoxLayout.Y_AXIS));
        subTotal.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));


        JLabel stText = new JLabel("Sub Total: ");
        stText.setFont(sz15);
        stText.setForeground(Color.WHITE);
        subTotal.add(stText);

        JLabel stTax = new JLabel("Tax: ");
        stTax.setFont(sz15);
        stTax.setForeground(Color.WHITE);
        subTotal.add(stTax);

        JLabel stLine = new JLabel("__________________________________");
        stTax.setFont(sz15);
        stLine.setForeground(Color.WHITE);
        subTotal.add(stLine);

        JLabel stTotal = new JLabel("Total: ");
        stTotal.setFont(sz16);
        stTotal.setForeground(Color.WHITE);
        subTotal.add(stTotal);
        orderSummary.add(subTotal);


        //PROCESS BUTTONS:
        JPanel orderButton = new JPanel();
        orderButton.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
        orderButton.setPreferredSize(new Dimension(600, getHeight()));
        orderButton.setOpaque(false);
        orderButton.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));


        JButton cancelBtn = new JButton("Cancel Order");
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setBackground(Color.decode("#BD1212"));
        cancelBtn.setPreferredSize(new Dimension(210, 50));
        cancelBtn.setBorderPainted(false);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setFont(sz16);
        orderButton.add(cancelBtn);

        JButton processBtn = new JButton("Process Order");
        processBtn.setForeground(Color.WHITE);
        processBtn.setBackground(Color.decode("#F9A61A"));
        processBtn.setPreferredSize(new Dimension(210, 50));
        processBtn.setBorderPainted(false);
        processBtn.setFocusPainted(false);
        processBtn.setFont(sz16);
        orderButton.add(processBtn);

        orderSummary.add(orderButton);


        productItemPanel.add(createProductItem("Jack'n Jill Piattos", "40g", "Images/Sample Product Images/Piattos-Cheese-40g.png", sz15, 15.00,orderList));
        productItemPanel.add(createProductItem("Jack'n Jill Piattos", "40g", "Images/Sample Product Images/Piattos-Cheese-40g.png", sz15, 15.00,orderList));
        productItemPanel.add(createProductItem("Jack'n Jill Piattos", "40g", "Images/Sample Product Images/Piattos-Cheese-40g.png", sz15, 15.00,orderList));
        productItemPanel.add(createProductItem("Jack'n Jill Piattos", "40g", "Images/Sample Product Images/Piattos-Cheese-40g.png", sz15, 15.00,orderList));
        productItemPanel.add(createProductItem("Jack'n Jill Piattos", "40g", "Images/Sample Product Images/Piattos-Cheese-40g.png", sz15, 15.00,orderList));
        productItemPanel.add(createProductItem("Jack'n Jill Piattos", "40g", "Images/Sample Product Images/Piattos-Cheese-40g.png", sz15, 15.00,orderList));
        productItemPanel.add(createProductItem("Jack'n Jill Piattos", "40g", "Images/Sample Product Images/Piattos-Cheese-40g.png", sz15, 15.00,orderList));
        productItemPanel.add(createProductItem("Jack'n Jill Piattos", "40g", "Images/Sample Product Images/Piattos-Cheese-40g.png", sz15, 15.00,orderList));






        add(mainConts, BorderLayout.CENTER);
        add(orderSummary, BorderLayout.EAST);

    }

    private JButton createCategoryButton(String text, Font font, Color bgColor) {
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

    public JPanel createProductItem(String text, String productSize, String productImg, Font font, double price, JPanel orderList) {
        Font sz17 = FontUtils.loadFont(17f);
        Font sz16 = FontUtils.loadFont(16f);
        Font sz15 = FontUtils.loadFont(15f);

        JPanel productItemCont = new JPanel();
        productItemCont.setPreferredSize(new Dimension(100,260));
        productItemCont.setOpaque(false);

        JPanel productItem = new JPanel();
        productItem.setBackground(Color.decode("#111010"));
        productItem.setPreferredSize(new Dimension(180, 250));
        productItem.setMaximumSize(new Dimension(300, 250));
        productItem.setLayout(new BorderLayout(5, 5));
        productItem.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        ImageIcon originalIcon = new ImageIcon(productImg);
        Image scaledImage = originalIcon.getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH);
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
        addProduct.setPreferredSize(new Dimension(42, 42));
        addProduct.setFont(font.deriveFont(Font.PLAIN, 14f));
        addProduct.setHorizontalAlignment(SwingConstants.CENTER);
        addProduct.setVerticalAlignment(SwingConstants.CENTER);
        addProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderList.add(productItemSummary("Jack n' Jill Piattos CHEESE", "40g", sz15, 15.00));
                orderList.revalidate();
                orderList.repaint();
            }
        });
        bottomPannel.add(addProduct, BorderLayout.EAST);
        productItem.add(bottomPannel, BorderLayout.SOUTH);

        productItemCont.add(productItem);


        return productItemCont;
    }

    public JPanel productItemSummary(String productName, String productSize, Font font, double price) {
        final int[] Quantity = {1};

        JPanel orderItemPanel = new JPanel();
        orderItemPanel.setOpaque(false);
        orderItemPanel.setLayout(new FlowLayout(FlowLayout.LEFT,10,0));
        orderItemPanel.setBorder(BorderFactory.createEmptyBorder(10,5,0,0));

        JLabel orderItem = new JLabel();

        orderItem.setFont(font);
        orderItem.setForeground(Color.WHITE);
        orderItem.setText(Quantity[0] + "x" + " | " + productName + ", " + productSize + ", " + "₱ " + price);




        JButton incr = new JButton("Add");
        incr.setFont(font);
        incr.setForeground(Color.decode("#F9A61A"));
        incr.setBackground(Color.gray);
        incr.setBorderPainted(false);
        incr.setFocusPainted(false);
        incr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderItem.setText( (Quantity[0]++) + "x" +  " | " + productName + ", " + productSize + ", " + "₱ " + price);
            }
        });

        JButton decr = new JButton("Delete");
        decr.setFont(font);
        decr.setForeground(Color.decode("#BD1212"));
        decr.setBackground(Color.gray);
        decr.setBorderPainted(false);
        decr.setFocusPainted(false);
        decr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderItem.setText( (Quantity[0]--) + "x" +  " | " + productName + ", " + productSize + ", " + "₱ " + price);

                if(Quantity[0] == 0){
                    orderItemPanel.setVisible(false);
                }
            }
        });






        orderItemPanel.add(orderItem);
        orderItemPanel.add(incr);
        orderItemPanel.add(decr);
        return orderItemPanel;

    }
}










