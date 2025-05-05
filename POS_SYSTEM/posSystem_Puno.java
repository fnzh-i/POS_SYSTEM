

import POS_SYSTEM.productItemsList;

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

public class posSystem extends javax.swing.JFrame {

    private navigationPanel navSection;
    private orderItemPanel orderItemSection;

    public posSystem(){

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

        POS.orderSummaryFile order = new POS.orderSummaryFile();
        order.orderSumm(200.00, 120.00);

        setVisible(true);
    }

    public static void main(String[] args){
//        new logInSection();
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

        if (text.equals("Menu Management")){

        }

        return navButton;

    }




}



class orderItemPanel extends JPanel {

    private final JPanel processPanel;
    private final JPanel orderSummary;
    private subTotalPanel subtotalPanel;

    int[] quantity = {1};
    Font sz12 = FontUtils.loadFont(12f);
    Font sz13 = FontUtils.loadFont(13f);
    Font sz15 = FontUtils.loadFont(15f);
    Font sz16 = FontUtils.loadFont(16f);
    Font sz17 = FontUtils.loadFont(17f);


    orderItemPanel() {


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
        JPanel productItemPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        productItemPanel.setBackground(Color.decode("#021526"));
        productItemPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        productItemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);


        JScrollPane productScrollPane = new JScrollPane(productItemPanel);
        productScrollPane.setBorder(null);
        productScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        productScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JScrollBar verticalScrollBar1 = productScrollPane.getVerticalScrollBar();
        verticalScrollBar1.setUnitIncrement(30);
        verticalScrollBar1.setBlockIncrement(100);

        mainConts.add(productScrollPane);

        //FOR ORDER SUMMARY AND ORDER PROCESS:

        orderSummary = new JPanel();
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
        new subTotalPanel();







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

        new productItemsList();

        String[] productName = {"Jack n' Jill Piattos", "Jack n' Jill Nova","Burger","Hotdog","Coke Mismo", "Royal Mismo","Caldereta","Rice"};
        String[] productSize = {"40g", "40g","1pc","1pc","295ml", "295ml","1 serving","1 cup"};
        String[] productImg = {"Images/Sample Product Images/Piattos-Cheese-40g.png", "Images/Sample Product Images/Jack n Jill Nova .png","Images/Sample Product Images/Burger .png","Images/Sample Product Images/Hotdog.png","Images/Sample Product Images/Coke Mismo.png", "Images/Sample Product Images/Royal Mismo.png","Images/Sample Product Images/Caldereta.png","Images/Sample Product Images/Rice.png"};
        double[] productPrice = {15.00,15.00,20.00,20.00,15.00,15.00,50.00,10.00};
        String[] productCategory = {"Snack","Snack","Snack","Snack","Drinks","Drinks","Meal", "Meal"};


        productItemsList.add(String.valueOf(productItemPanel.add(createProductItem(productName[0], productSize[0], productImg[0], sz16, productPrice[0], orderList))));
        productItemsList.add(String.valueOf(productItemPanel.add(createProductItem(productName[1], productSize[1], productImg[1], sz16, productPrice[1], orderList))));
        productItemsList.add(String.valueOf(productItemPanel.add(createProductItem(productName[2], productSize[2], productImg[2], sz16, productPrice[2], orderList))));
        productItemsList.add(String.valueOf(productItemPanel.add(createProductItem(productName[3], productSize[3], productImg[3], sz16, productPrice[3], orderList))));
        productItemsList.add(String.valueOf(productItemPanel.add(createProductItem(productName[4], productSize[4], productImg[4], sz16, productPrice[4], orderList))));
        productItemsList.add(String.valueOf(productItemPanel.add(createProductItem(productName[5], productSize[5], productImg[5], sz16, productPrice[5], orderList))));
        productItemsList.add(String.valueOf(productItemPanel.add(createProductItem(productName[6], productSize[6], productImg[6], sz16, productPrice[6], orderList))));
        productItemsList.add(String.valueOf(productItemPanel.add(createProductItem(productName[7], productSize[7], productImg[7], sz16, productPrice[7], orderList))));


        processPanel = new JPanel();
        processPanel.setPreferredSize(new Dimension(780,780));
        processPanel.setMaximumSize(new Dimension(780,780));



        add(mainConts, BorderLayout.CENTER);
        add(orderSummary, BorderLayout.EAST);
        add(processPanel);


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
        imgFrame.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
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
        productPrice.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
        bottomPannel.add(productPrice, BorderLayout.WEST);

        JButton addProduct = new JButton("+");
        addProduct.setForeground(Color.BLACK);
        addProduct.setBackground(Color.decode("#686AF5"));
        addProduct.setBorderPainted(false);
        addProduct.setFocusPainted(false);
        addProduct.setPreferredSize(new Dimension(42, 42));
        addProduct.setFont(font.deriveFont(Font.PLAIN, 14f));

        addProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update the order list
                orderList.add(productItemSummary(text, productSize, sz17, price));
                orderList.revalidate();
                orderList.repaint();



                subTotalPanel.updateSubtotal(price);


            }
        });
        bottomPannel.add(addProduct, BorderLayout.EAST);
        productItem.add(bottomPannel, BorderLayout.SOUTH);

        productItemCont.add(productItem);

        return productItemCont;
    }

    public JPanel productItemSummary(String productName, String productSize, Font font, double price) {
        JPanel orderItemPanel = new JPanel();
        orderItemPanel.setOpaque(false);
        orderItemPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        orderItemPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 0, 0));

        final int[] quantity = {1};
        JLabel orderItemLabel = new JLabel();
        orderItemLabel.setFont(font);
        orderItemLabel.setForeground(Color.WHITE);
        orderItemLabel.setText(quantity[0] + "x | " + productName + ", " + productSize + ", ₱ " + price);

        JButton incr = new JButton("Add");
        incr.setFont(font);
        incr.setForeground(Color.decode("#F9A61A"));
        incr.setBackground(Color.gray);
        incr.setBorderPainted(false);
        incr.setFocusPainted(false);
        incr.addActionListener(e -> {
            quantity[0]++;
            orderItemLabel.setText(quantity[0] + "x | " + productName + ", " + productSize + ", ₱ " + price);
            subTotalPanel.updateSubtotal(price);
        });

        JButton decr = new JButton("Delete");
        decr.setFont(font);
        decr.setForeground(Color.decode("#BD1212"));
        decr.setBackground(Color.gray);
        decr.setBorderPainted(false);
        decr.setFocusPainted(false);
        decr.addActionListener(e -> {
            if (quantity[0] > 1) {
                quantity[0]--;
                orderItemLabel.setText(quantity[0] + "x | " + productName + ", " + productSize + ", ₱ " + price);
                subTotalPanel.updateSubtotal(-price);
            }
        });


        orderItemPanel.add(orderItemLabel);
        orderItemPanel.add(incr);
        orderItemPanel.add(decr);

        return orderItemPanel;

    }

    class subTotalPanel extends JPanel {
        private static double subtotal = 0.0;
        private static double tax = 0.0;
        private static double total = 0.0;
        private static JLabel subtotalLabel;
        private static JLabel taxLabel;
        private static JLabel totalLabel;

        subTotalPanel() {
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

            subtotalLabel = new JLabel("₱" + String.format("%.2f", subtotal)); //
            subtotalLabel.setFont(sz15);
            subtotalLabel.setForeground(Color.WHITE);
            subTotal.add(subtotalLabel);

            taxLabel = new JLabel("Tax: ₱" + String.format("%.2f", tax)); //
            taxLabel.setFont(sz15);
            taxLabel.setForeground(Color.WHITE);
            subTotal.add(taxLabel);

            JLabel stLine = new JLabel("__________________________________");
            stLine.setFont(sz15);
            stLine.setForeground(Color.WHITE);
            subTotal.add(stLine);

            JLabel stTotalText = new JLabel("Total: ");
            stTotalText.setFont(sz16);
            stTotalText.setForeground(Color.WHITE);
            subTotal.add(stTotalText);

            totalLabel = new JLabel("₱" + String.format("%.2f", total));
            totalLabel.setFont(sz16);
            totalLabel.setForeground(Color.WHITE);
            subTotal.add(totalLabel);

            orderSummary.add(subTotal);
        }

        // Method to update the subtotal, tax, and total
        public static void updateSubtotal(double amount) {
            double vatRate = 0.12;
            subtotal += amount;
            tax = subtotal * vatRate;
            total = subtotal + tax;

            if(subtotal < 0) subtotal = 0; // to avoid negative
            if(total < 0) total = 0;
            if(tax < 0) tax = 0;

            subtotalLabel.setText("₱" + String.format("%.2f", subtotal));
            taxLabel.setText("Tax: ₱" + String.format("%.2f", tax));
            totalLabel.setText("₱" + String.format("%.2f", total));
        }


    }

    class processOrderPanel extends JPanel{

        processOrderPanel() {
            Font sz16 = FontUtils.loadFont(16f);
            Font sz20 = FontUtils.loadFont(20f);



            JPanel processOrder = new JPanel();
            processOrder.setLayout(new FlowLayout(FlowLayout.CENTER, 10,0));
            processOrder.setPreferredSize(new Dimension(1000,1000));
            processOrder.setMaximumSize(new Dimension(1000,1000));
            processOrder.setBackground(Color.decode("#2D303E"));
            processOrder.setBorder(BorderFactory.createLineBorder(Color.blue,2));


            JPanel orderSummarySide = new JPanel();
            orderSummarySide.setLayout(new BoxLayout(orderSummarySide, BoxLayout.Y_AXIS));
            JLabel osLabel = new JLabel("Order Summary");
            osLabel.setForeground(Color.white);
            osLabel.setFont(sz20);

            JButton plusButton = new JButton("+");
            plusButton.setBorderPainted(false);
            plusButton.setFocusPainted(false);
            plusButton.setForeground(Color.WHITE);
            plusButton.setBackground(Color.gray);
            orderSummarySide.add(plusButton);


            JPanel orderProcessSummary = new JPanel();
            orderProcessSummary.setLayout(new BoxLayout(orderProcessSummary, BoxLayout.Y_AXIS));
            orderProcessSummary.setBackground(Color.decode("#424040"));

            JScrollPane orderSummaryScroll = new JScrollPane(orderProcessSummary);
            orderSummaryScroll.setBorder(null);
            orderSummaryScroll.setPreferredSize(new Dimension(300, 300));
            orderSummaryScroll.setAlignmentX(Component.CENTER_ALIGNMENT);
            orderSummaryScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            orderSummaryScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            orderSummaryScroll.setBorder(BorderFactory.createEmptyBorder(0, 30, 20, 30));
            orderSummaryScroll.setOpaque(false);


            JScrollBar verticalScrollBar2 = orderSummaryScroll.getVerticalScrollBar();
            verticalScrollBar2.setUnitIncrement(30);
            verticalScrollBar2.setBlockIncrement(100);
            orderSummarySide.add(orderSummaryScroll);


//            JPanel subTotalPanel = new JPanel();
//            subTotalPanel.setLayout(new BoxLayout(subTotalPanel, BoxLayout.Y_AXIS));
//            subTotalPanel.setOpaque(false);
//            JLabel subtotal = new JLabel("Sub total: ");
//            subtotal.setFont(sz16);
//            subtotal.setForeground(Color.WHITE);
//            subtotal.add(subTotalPanel);
//
//            JLabel discount = new JLabel("Discount: ");
//            discount.setFont(sz16);
//            discount.setForeground(Color.WHITE);
//            discount.add(subTotalPanel);
//
//            JLabel total = new JLabel("Total: ");
//            total.setFont(sz16);
//            total.setForeground(Color.WHITE);
//            total.add(subTotalPanel);
//
//            JPanel cashTenderedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10,0));
//            JLabel cashTendered = new JLabel("Cash Tendered: ");
//            cashTendered.setForeground(Color.white);
//            cashTendered.setFont(sz20);
//            cashTendered.add(cashTenderedPanel);
//
//            JTextField cTenderedTField = new JTextField("₱ ");
//            cTenderedTField.setOpaque(false);
//            cTenderedTField.setFont(sz20);
//            cTenderedTField.setForeground(Color.WHITE);
//            cashTenderedPanel.add(cTenderedTField);
//
//            subTotalPanel.add(cashTenderedPanel);
//
//            JPanel changePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10,0));
//            JLabel changeLabel1 = new JLabel("Cash Tendered: ");
//            changeLabel1.setForeground(Color.white);
//            changeLabel1.setFont(sz20);
//            changePanel.add(changeLabel1);
//
//            JLabel changeLabel2 = new JLabel("₱ ");
//            changeLabel2.setOpaque(false);
//            changeLabel2.setFont(sz20);
//            changeLabel2.setForeground(Color.WHITE);
//            changePanel.add(changeLabel2);
//
//            subTotalPanel.add(changePanel);
//
//            orderSummarySide.add(subTotalPanel);
//
//
//            //PROCESS BUTTONS FOP ORDER PROCESS SUMMARY:
//            JPanel processButtonPanel = new JPanel();
//            processButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
//            processButtonPanel.setPreferredSize(new Dimension(600, getHeight()));
//            processButtonPanel.setOpaque(false);
//            processButtonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
//
//
//            JButton cancelBtn = new JButton("Cancel");
//            cancelBtn.setForeground(Color.WHITE);
//            cancelBtn.setBackground(Color.decode("#BD1212"));
//            cancelBtn.setBorderPainted(false);
//            cancelBtn.setFocusPainted(false);
//            cancelBtn.setFont(sz16);
//            processButtonPanel.add(cancelBtn);
//
//            JButton processBtn = new JButton("Confirm Payment");
//            processBtn.setForeground(Color.WHITE);
//            processBtn.setBackground(Color.decode("#F9A61A"));
//            processBtn.setBorderPainted(false);
//            processBtn.setFocusPainted(false);
//            processBtn.setFont(sz16);
//            processButtonPanel.add(processBtn);
//
//            orderSummarySide.add(processButtonPanel);

            processOrder.add(orderSummarySide);

            processPanel.add(processOrder);



        }

    }


}
