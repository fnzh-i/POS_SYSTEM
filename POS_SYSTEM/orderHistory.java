package POS_SYSTEM;


import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

class orderHistory extends JPanel {

    orderHistory(){

        Font sz11 = FontUtils.loadFont(11f);
        Font sz12 = FontUtils.loadFont(12f);
        Font sz13 = FontUtils.loadFont(13f);
        Font sz15 = FontUtils.loadFont(15f);
        Font sz16 = FontUtils.loadFont(16f);
        Font sz17 = FontUtils.loadFont(17f);
        Font sz25 = FontUtils.loadFont(25f);

        setPreferredSize(new Dimension(1080,720));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.decode("#021526"));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.decode("#021526"));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Search panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(Color.decode("#898b8f"));
        searchPanel.setMaximumSize(new Dimension(500, 40));

        JTextField searchField = new JTextField();
        searchField.setBackground(Color.decode("#898b8f"));
        searchField.setPreferredSize(new Dimension(400, 40));
        searchField.setMaximumSize(new Dimension(400, 40));
        searchField.setFont(sz16);
        searchField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        searchPanel.add(searchField, BorderLayout.CENTER);

        JButton searchBtn = new JButton("Search");
        searchBtn.setFont(sz16);
        searchBtn.setPreferredSize(new Dimension(100, 40));
        searchPanel.add(searchBtn, BorderLayout.EAST);

        // Date label
        JLabel dateLabel = new JLabel(new SimpleDateFormat("EEEE, MMMM dd, yyyy hh:mm a").format(new Date()));
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setFont(new Font("Roboto", Font.PLAIN, 16));

        // Top bar containing search and date
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setPreferredSize(new Dimension(1080, 50));
        topBar.setMaximumSize(new Dimension(1080, 50));
        topBar.add(searchPanel, BorderLayout.WEST);
        topBar.add(dateLabel, BorderLayout.EAST);
        topBar.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(topBar);


        JPanel oHTitleContainer = new JPanel(new BorderLayout());
        oHTitleContainer.setPreferredSize(new Dimension(1080,50));
        oHTitleContainer.setMaximumSize(new Dimension(1080,50));
        oHTitleContainer.setOpaque(false);

        JLabel oHTitle = new JLabel("Order History");
        oHTitle.setFont(sz25);
        oHTitle.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
        oHTitle.setForeground(Color.white);
        oHTitle.setAlignmentX(LEFT_ALIGNMENT);
        oHTitleContainer.add(oHTitle, BorderLayout.WEST);

        add(oHTitleContainer);

        JPanel orderListHistory = new JPanel();
        orderListHistory.setLayout(new BoxLayout(orderListHistory, BoxLayout.Y_AXIS));
        orderListHistory.setBackground(Color.decode("#021526"));


        JScrollPane orderListScrollPane = new JScrollPane(orderListHistory);
        orderListScrollPane.setBorder(null);
        orderListScrollPane.setPreferredSize(new Dimension(1000, 400)); // Adjusted dimensions
        orderListScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        orderListScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        orderListScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        orderListScrollPane.setOpaque(false);


        orderListHistory.add(createOHItem(1, 140, List.of("Coke Mismo", "Jack n' Jill Piattos", "Rice", "Rice", "Rice")));
        orderListHistory.add(createOHItem(2, 280, List.of("Burger", "Fries", "Soda")));
        orderListHistory.add(createOHItem(3, 120, List.of("Coffee", "Donut")));
        orderListHistory.add(createOHItem(4, 500, List.of("Pizza", "Pasta", "Salad", "Breadsticks", "Wine")));
        orderListHistory.add(createOHItem(5, 100, List.of("Water")));
        add(orderListScrollPane);
    }

    public JPanel createOHItem(int orderId, double Total, List<String> productList){
        Font sz15 = FontUtils.loadFont(15f);
        Font sz16 = FontUtils.loadFont(16f);
        Font sz17 = FontUtils.loadFont(17f);
        Font sz20 = FontUtils.loadFont(20f);
        Font sz25 = FontUtils.loadFont(25f);

        JPanel createOHItemPanel = new JPanel();
        createOHItemPanel.setOpaque(false);
        createOHItemPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 200,0));
        createOHItemPanel.setPreferredSize(new Dimension(1000, 100)); // Start with a base height

        createOHItemPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));

        JLabel Date = new JLabel("15/05/2025");
        Date.setFont(sz20);
        Date.setForeground(Color.white);
        createOHItemPanel.add(Date);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        JLabel orderIdLabel = new JLabel("Order Number " + orderId);
        orderIdLabel.setForeground(Color.white);
        orderIdLabel.setFont(sz20);
        centerPanel.add(orderIdLabel);

        JPanel productListPanel = new JPanel();
        productListPanel.setLayout(new BoxLayout(productListPanel, BoxLayout.Y_AXIS));
        productListPanel.setOpaque(false);


        // Dynamically add product labels
        for (String product : productList) {
            JLabel productLabel = new JLabel(product);
            productLabel.setForeground(Color.white);
            productLabel.setFont(sz17);
            productListPanel.add(productLabel);
        }
        centerPanel.add(productListPanel);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        createOHItemPanel.add(centerPanel);

        JLabel total = new JLabel("Total: " + Total);
        total.setForeground(Color.white);
        total.setFont(sz25);
        createOHItemPanel.add(total);

        // Calculate and set the height of createOHItemPanel based on the number of products
        int baseHeight = 100; // Minimum height
        int productHeight = productList.size() * 20; // Adjust 20 based on your label's typical height
        createOHItemPanel.setPreferredSize(new Dimension(1000, Math.max(baseHeight, productHeight + 60))); //add 60 padding.


        return createOHItemPanel;










    }

    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setSize(new Dimension(1080,720));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);


        orderHistory orderHistoryPanel = new orderHistory();
        frame.add(orderHistoryPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }



}
