package POS_SYSTEM;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.plaf.metal.MetalScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class posSystem extends javax.swing.JFrame {

    private orderItemPanel orderItemSection;
    private final User currentUser;
    private JPanel currentCenterPanel; // tracks' the current center panel

    public posSystem(User user) {
        this.currentUser = user;

        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        this.setLocationRelativeTo(null);

        ImageIcon systemIcon = new ImageIcon("Images/Logo/Philippine_Christian_University_logo.png");
        setIconImage(systemIcon.getImage());
        setTitle("PCU-POS");
        getContentPane().setBackground(Color.decode("#021526"));

        navigationPanel navSection = new navigationPanel(user);
        add(navSection, BorderLayout.WEST);

        orderItemSection = new orderItemPanel(user);
        currentCenterPanel = orderItemSection;
        add(currentCenterPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public void refreshOrderItemPanel() {
        remove(currentCenterPanel);
        orderItemSection = new orderItemPanel(currentUser);
        currentCenterPanel = orderItemSection;
        add(currentCenterPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // Method to switch panel
    public void switchToPanel(JPanel newPanel) {
        remove(currentCenterPanel);

        // If switching back to order panel, refresh it
        if (newPanel instanceof orderItemPanel) {
            orderItemSection = new orderItemPanel(currentUser);
            currentCenterPanel = orderItemSection;
        } else {
            currentCenterPanel = newPanel;
        }

        add(currentCenterPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        new logInSection();
    }

    public orderItemPanel getOrderItemPanel() {
        return orderItemSection;
    }

    public User getCurrentUser() {
        return currentUser;
    }

}

class FontUtils {
    public static Font loadFont(String fontPath, float size) {
        Font font = null;
        try {
            File fontStyle = new File(fontPath);
            font = Font.createFont(Font.TRUETYPE_FONT, fontStyle).deriveFont(size);
        } catch (Exception e) {
            e.printStackTrace();
            font = new Font("ARIAL", Font.PLAIN, (int) size);
        }
        return font;
    }

    public static Font loadFont(float size) {
        return loadFont("Fonts/Roboto-VariableFont_wdth,wght.ttf", size);
    }
}

class navigationPanel extends JPanel {
    private final User currentUser;

    public navigationPanel(User user) {
        this.currentUser = user;
        Font navFont = FontUtils.loadFont(18f);
        setPreferredSize(new Dimension(250, getHeight()));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.decode("#00193b"));

        JPanel navHeader = new JPanel();
        navHeader.setLayout(new BoxLayout(navHeader, BoxLayout.X_AXIS));
        navHeader.setBackground(Color.decode("#00193b"));
        navHeader.setBorder(BorderFactory.createEmptyBorder(15, 15, 20, 15)); // Reduced padding
        navHeader.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70)); // Reduced height

        ImageIcon systemLogo = new ImageIcon("Images/Logo/Philippine_Christian_University_logo.png");
        JLabel pcuLogo = new JLabel();
        Image originalImage = systemLogo.getImage();

        int imgWidth = 50; // Reduced from 70
        int imgHeight = 50; // Reduced from 70

        Image scaledImg = originalImage.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledLogo = new ImageIcon(scaledImg);

        pcuLogo.setIcon(scaledLogo);
        pcuLogo.setAlignmentY(Component.CENTER_ALIGNMENT);
        navHeader.add(pcuLogo);

        JLabel navTitle = new JLabel();
        navTitle.setForeground(Color.WHITE);
        navTitle.setFont(navFont);
        navTitle.setText("<html><p>  DolphiMeals</p></html>");
        navTitle.setAlignmentY(Component.CENTER_ALIGNMENT);
        navHeader.add(navTitle);

        add(navHeader);

        JPanel navOptionsPanel = new JPanel();
        navOptionsPanel.setLayout(new BoxLayout(navOptionsPanel, BoxLayout.Y_AXIS));
        navOptionsPanel.setBackground(Color.decode("#00193b"));
        navOptionsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        navOptionsPanel.add(createNavButton("Order Item", navFont));
        navOptionsPanel.add(createNavButton("Order History", navFont));
        navOptionsPanel.add(createNavButton("Dashboard", navFont));
        navOptionsPanel.add(createNavButton("Menu Management", navFont));


        navOptionsPanel.add(Box.createVerticalGlue());
        navOptionsPanel.add(createNavButton("Logout", navFont));
        navOptionsPanel.add(Box.createVerticalStrut(15));

        add(navOptionsPanel, BorderLayout.CENTER);

        if (this.currentUser.isAdmin()) {
            JLabel adminLabel = new JLabel("(Admin)");
            adminLabel.setForeground(Color.YELLOW);
            adminLabel.setFont(navFont);
            navHeader.add(adminLabel);
        }
        if (this.currentUser.isManager()) {
            JLabel adminLabel = new JLabel("(Manager)");
            adminLabel.setForeground(Color.YELLOW);
            adminLabel.setFont(navFont);
            navHeader.add(adminLabel);
        }
        repaint();
    }

    private JButton createNavButton(String text, Font font) {
        JButton navButton = new JButton(text);
        navButton.setFont(font);

        navButton.setForeground(Color.WHITE);
        navButton.setBackground(Color.decode("#00193b"));
        navButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        navButton.setBorderPainted(false);
        navButton.setFocusPainted(false);
        navButton.setHorizontalAlignment(SwingConstants.LEFT);
        navButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        navButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        navButton.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 0));

        navButton.addMouseListener(new MouseAdapter() {
            private final Color originalColor = navButton.getBackground();
            private final Color hoverColor = Color.darkGray;

            @Override
            public void mouseEntered(MouseEvent e) {
                navButton.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                navButton.setBackground(originalColor);
            }
        });
        if (text.equals("Order Item")) {
            navButton.addActionListener(e -> {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                if (topFrame instanceof posSystem posFrame) {
                    posFrame.switchToPanel(posFrame.getOrderItemPanel());
                }
            });
        }
        if (text.equals("Order History")) {
            navButton.addActionListener(e -> {
                orderHistory oh = new orderHistory(); // Changed this line
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                if (topFrame instanceof posSystem) {
                    ((posSystem) topFrame).switchToPanel(oh);
                }
            });
        }
        if (text.equals("Logout")) {
            navButton.addActionListener(e -> logout());
        }
        if (text.equals("Menu Management")) {
            navButton.addActionListener(e -> {
                MenuManagement menuManagementPanel = new MenuManagement(currentUser);
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                if (topFrame instanceof posSystem) {
                    ((posSystem) topFrame).switchToPanel(menuManagementPanel);
                }
            });
        }

        if (text.equals("Dashboard")) {
            navButton.addActionListener(e -> {
                dashBoard dashboard = new dashBoard();
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                if (topFrame instanceof posSystem) {
                    ((posSystem) topFrame).switchToPanel(dashboard);
                }
            });
        }

        return navButton;
    }

    private void logout() {
        int response = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to log out?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (topFrame != null) {
                topFrame.dispose();
            }
            new logInSection();
        }
    }
}

class orderItemPanel extends JPanel {
    final Map<String, Integer> addedOrderItems = new HashMap<>();
    final Map<String, JPanel> orderItemPanels = new HashMap<>();
    final Map<String, JLabel> orderItemLabels = new HashMap<>();
    private final JPanel orderSummary;
    final subTotalPanel subtotalPanel;
    final JPanel orderList;
    private final JPanel productItemPanel;
    private final JTextField searchBar = new JTextField();
    private final User currentUser;

    Font sz11 = FontUtils.loadFont(11f);
    Font sz12 = FontUtils.loadFont(12f);
    Font sz13 = FontUtils.loadFont(13f);
    Font sz14 = FontUtils.loadFont(14f);
    Font sz15 = FontUtils.loadFont(15f);
    Font sz16 = FontUtils.loadFont(16f);
    Font sz17 = FontUtils.loadFont(17f);

    final List<Product> productList = new ArrayList<>();

    orderItemPanel(User currentUser) {
        this.currentUser = currentUser;
        Font oiFont = FontUtils.loadFont(15f);
        setBackground(Color.decode("#00132d"));
        setLayout(new BorderLayout());

        // Main container panel
        JPanel mainConts = new JPanel();
        mainConts.setLayout(new BoxLayout(mainConts, BoxLayout.Y_AXIS));
        mainConts.setBackground(Color.decode("#00132d"));
        mainConts.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0));

        // SEARCH BAR
        RoundedPanel searchPanel = new RoundedPanel(20);
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        searchPanel.setBackground(Color.decode("#898b8f"));
        searchPanel.setPreferredSize(new Dimension(450, 40));
        searchPanel.setMaximumSize(new Dimension(450, 40));
        searchPanel.setLayout(new BorderLayout());
        searchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField searchBarNameField = new JTextField("Search menu...");
        searchBarNameField.setBounds(60, 120, 600, 35);
        searchBarNameField.setForeground(Color.darkGray);  // Placeholder color
        searchBarNameField.setCaretColor(Color.WHITE);
        searchBarNameField.setOpaque(false);
        searchBarNameField.setFont(oiFont);
        searchBarNameField.setPreferredSize(new Dimension(350, 40));
        searchBarNameField.setMaximumSize(new Dimension(350, 40));
        searchBarNameField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));

        searchBarNameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchBarNameField.getText().equals("Search menu...")) {
                    searchBarNameField.setText("");
                    searchBarNameField.setForeground(Color.darkGray);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchBarNameField.getText().isEmpty()) {
                    searchBarNameField.setText("Search menu...");
                    searchBarNameField.setForeground(Color.darkGray);
                }
            }
        });

        searchPanel.add(searchBarNameField, BorderLayout.CENTER);

        RoundedButton searchButton = new RoundedButton("Search",20);
        searchButton.setFont(oiFont);
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setForeground(Color.white);
        searchButton.setBackground(Color.darkGray);
        searchButton.setPreferredSize(new Dimension(100, 40));
        searchButton.setMaximumSize(new Dimension(100, 40));
        searchPanel.add(searchButton, BorderLayout.EAST);
        searchButton.addMouseListener(new MouseAdapter() {
            private final Color originalColor = searchButton.getBackground();
            private final Color hoverColor = Color.GRAY;

            @Override
            public void mouseEntered(MouseEvent e) {
                searchButton.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                searchButton.setBackground(originalColor);
            }
        });

        searchButton.addActionListener(evt -> searchButton());

        mainConts.add(searchPanel);

        // CATEGORY PANEL
        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        categoryPanel.setBackground(Color.decode("#00132d"));
        categoryPanel.setPreferredSize(new Dimension(500, 60));
        categoryPanel.setMaximumSize(new Dimension(500, 60));
        categoryPanel.setBorder(BorderFactory.createEmptyBorder(5,0,0,0));
        categoryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        categoryPanel.add(createCategoryButton("All", sz15, Color.gray));
        categoryPanel.add(createCategoryButton("Meals", sz14, Color.gray));
        categoryPanel.add(createCategoryButton("Snacks", sz14, Color.gray));
        categoryPanel.add(createCategoryButton("Drinks", sz14, Color.gray));

        mainConts.add(categoryPanel);

        // PRODUCT ITEMS PANEL
        productItemPanel = new JPanel(new GridLayout(0, 4, 8, 8));
        productItemPanel.setBackground(Color.decode("#00132d"));
        productItemPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        productItemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        refreshProducts();

        // Order summary panel
        orderSummary = new JPanel();
        orderSummary.setPreferredSize(new Dimension(350, 720));
        orderSummary.setBackground(Color.decode("#00132d"));
        orderSummary.setLayout(new BoxLayout(orderSummary, BoxLayout.Y_AXIS));

        // Date panel
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        datePanel.setOpaque(false);
        JLabel dateLabel = new JLabel();
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setFont(sz15);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy h:mm a");
        dateLabel.setText(dateFormat.format(new Date()));
        updateDateLabel(dateLabel, dateFormat);

        Timer timer = new Timer(1000, e -> updateDateLabel(dateLabel, dateFormat));
        timer.start();

        datePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10));
        datePanel.add(dateLabel);
        orderSummary.add(datePanel);

        // Order list panel
        orderList = new RoundedPanel(20);
        orderList.setLayout(new BoxLayout(orderList, BoxLayout.Y_AXIS));
        orderList.setBackground(Color.decode("#424040"));
        orderList.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
        JScrollPane orderListScroll = new JScrollPane(orderList);
        orderListScroll.setBorder(null);
        orderListScroll.setPreferredSize(new Dimension(300, 300));
        orderListScroll.setAlignmentX(Component.CENTER_ALIGNMENT);
        orderListScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        orderListScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        orderListScroll.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        orderListScroll.getViewport().setOpaque(false);
        orderListScroll.setOpaque(false);
        orderSummary.add(orderListScroll);

        subtotalPanel = new subTotalPanel();

        JPanel orderButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        orderButton.setOpaque(false);
        orderButton.setPreferredSize(new Dimension(280, getHeight()));
        orderButton.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));

        RoundedButton cancelBtn = new RoundedButton("Cancel Order", 20);
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setBackground(Color.decode("#BD1212"));
        cancelBtn.setPreferredSize(new Dimension(130, 45));
        cancelBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cancelBtn.setBorderPainted(false);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setFont(sz15);

        cancelBtn.addMouseListener(new MouseAdapter() {
            private final Color originalColor = cancelBtn.getBackground();
            private final Color hoverColor = Color.decode("#971A1A");

            @Override
            public void mouseEntered(MouseEvent e) {
                cancelBtn.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cancelBtn.setBackground(originalColor);
            }
        });

        cancelBtn.addActionListener(evt -> cancelButton());
        orderButton.add(cancelBtn);

        RoundedButton processBtn = new RoundedButton("Process Order",20);
        processBtn.setForeground(Color.WHITE);
        processBtn.setBackground(Color.decode("#F9A61A"));
        processBtn.setPreferredSize(new Dimension(130, 45));
        processBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        processBtn.setBorderPainted(false);
        processBtn.setFocusPainted(false);
        processBtn.setFont(sz15);
        orderButton.add(processBtn);

        processBtn.addMouseListener(new MouseAdapter() {
            private final Color originalColor = processBtn.getBackground();
            private final Color hoverColor = Color.decode("#A9741A");

            @Override
            public void mouseEntered(MouseEvent e) {
                processBtn.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                processBtn.setBackground(originalColor);
            }
        });

        processBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Product> orderedProducts = new ArrayList<>();
                for (String key : addedOrderItems.keySet()) {
                    int qty = addedOrderItems.get(key);
                    for (Product product : productList) {
                        String productKey = product.getName() + product.getSize();
                        if (productKey.equals(key)) {
                            for (int i = 0; i < qty; i++) {
                                orderedProducts.add(product);
                            }
                            break;
                        }
                    }
                }
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(orderItemPanel.this);
                if (topFrame != null && topFrame instanceof posSystem) {
                    posSystem posFrame = (posSystem) topFrame;
                    posFrame.switchToPanel(new processOrderPanel(orderedProducts));
                }
            }
        });

        orderSummary.add(orderButton);

        addItemsByCategory("All");

        JScrollPane productScrollPane = new JScrollPane(productItemPanel);
        productScrollPane.setBorder(null);
        productScrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        productScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        productScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JScrollBar verticalScrollBar1 = productScrollPane.getVerticalScrollBar();
        verticalScrollBar1.setUnitIncrement(20);
        verticalScrollBar1.setBlockIncrement(80);

        mainConts.add(productScrollPane);
        add(mainConts, BorderLayout.CENTER);
        add(orderSummary, BorderLayout.EAST);
    }

//    public void clearFields() {
//        searchBar.setText("Enter Product Name");
//        searchBar.setForeground(Color.WHITE);
//        searchBar.transferFocus();
//
//    }


        class RoundedPanel extends JPanel {
        private int radius;

        public RoundedPanel(int radius) {
            this.radius = radius;
            setOpaque(false); // Make the panel transparent
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Dimension arcs = new Dimension(radius, radius);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw a rounded rectangle with the specified background color
            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcs.width, arcs.height);
            g2d.setColor(getForeground());
            g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcs.width, arcs.height);
            g2d.dispose();
        }
    }

    class RoundedButton extends JButton {
        private int radius;

        public RoundedButton(String text, int radius) {
            super(text);
            this.radius = radius;
            setOpaque(false); // Make the button transparent
            setContentAreaFilled(false); // Don't fill the content area
            setBorderPainted(false);    // Don't paint the border
            setForeground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw a rounded rectangle for the button's background
            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);

            // Draw the button's text
            FontMetrics fm = g2d.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(getText())) / 2;
            int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
            g2d.setColor(getForeground());
            g2d.drawString(getText(), x, y);

            g2d.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(getForeground());
            g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2d.dispose();
        }
    }


    class ModernScrollBarUI extends MetalScrollBarUI {

        private final Dimension THUMB_SIZE = new Dimension(8, 50); //make it thinner

        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            //super.paintTrack(g, g2d, trackBounds);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(240, 240, 240)); // Light gray track
            g2.fillRoundRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height, 10, 10);
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            if (thumbBounds.isEmpty() || !c.isEnabled()) {
                return;
            }

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Define colors
            Color color = new Color(150, 150, 150); // Medium gray
            Color hoverColor = new Color(130, 130, 130); // Darker gray on hover
            Color dragColor = new Color(110, 110, 110); // Even darker when dragging

            // State-based color selection
            Color drawColor = color;
            if (isDragging) {
                drawColor = dragColor;
            } else if (isThumbRollover()) {
                drawColor = hoverColor;
            }

            // Draw rounded thumb
            g2.setColor(drawColor);
            g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);

            // Optional: Subtle shadow for depth
            g2.setColor(new Color(0, 0, 0, 50)); // Semi-transparent black
            g2.drawRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
        }

        @Override
        protected Dimension getMinimumThumbSize() {
            return THUMB_SIZE;
        }

        @Override
        public Dimension getPreferredSize(JComponent c) {
            return new Dimension(12, super.getPreferredSize(c).height); //make it thinner
        }
    }

    private void updateDateLabel(JLabel dateLabel, SimpleDateFormat dateFormat) {
        Date now = new Date();
        String formattedDate = dateFormat.format(now);
        dateLabel.setText(formattedDate);
    }

    private final List<Product> displayedProducts = new ArrayList<>();

    private boolean isProductDisplayed(Product product) {
        for (Product p : displayedProducts) {
            if (p.getItemId() == product.getItemId()) {
                return true;
            }
        }
        return false;
    }

    private void refreshDisplayedProducts() {
        clearProductItems();
        for (Product product : displayedProducts) {
            productItemPanel.add(createProductItem(
                    product.getName(),
                    product.getSize(),
                    product.getImage(),
                    sz15,
                    product.getPrice()
            ));
        }
        productItemPanel.revalidate();
        productItemPanel.repaint();
    }

    private void addProductToCurrentOrder(Product product) {
        String productKey = product.getName() + product.getSize();
        double price = product.getPrice();

        if (addedOrderItems.containsKey(productKey)) {
            int currentQty = addedOrderItems.get(productKey) + 1;
            addedOrderItems.put(productKey, currentQty);

            JPanel existingPanel = orderItemPanels.get(productKey);
            if (existingPanel != null) {
                JLabel label = orderItemLabels.get(productKey);
                if (label != null) {
                    label.setText(String.format("%dx | %s, %s ₱%.2f",
                            currentQty, product.getName(), product.getSize(), price));
                }
            }
        } else {
            addedOrderItems.put(productKey, 1);
            createNewOrderItemPanel(product, productKey, price);
        }

        if (subtotalPanel != null) {
            subtotalPanel.updateSubtotal(price);
        }
        orderList.revalidate();
        orderList.repaint();
    }

    private void createNewOrderItemPanel(Product product, String productKey, double price) {
        JPanel orderPanel = new JPanel(new BorderLayout());
        orderPanel.setOpaque(false);
        orderPanel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);

        JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        itemPanel.setOpaque(false);
        itemPanel.setPreferredSize(new Dimension(220, 25));

        JLabel orderLabel = new JLabel(String.format("%dx | %s, %s ₱%.2f",
                1, product.getName(), product.getSize(), price));
        orderLabel.setFont(sz13);
        orderLabel.setForeground(Color.WHITE);
        itemPanel.add(orderLabel);
        contentPanel.add(itemPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 3, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setPreferredSize(new Dimension(100, 25));

        // Add button
        JButton incr = new JButton("+");
        incr.setFont(sz13.deriveFont(Font.BOLD));
        incr.setForeground(Color.WHITE);
        incr.setBackground(Color.decode("#F9A61A"));
        incr.setBorderPainted(false);
        incr.setFocusPainted(false);
        incr.setPreferredSize(new Dimension(45, 20));
        incr.addActionListener(ae -> {
            int qty = addedOrderItems.get(productKey) + 1;
            addedOrderItems.put(productKey, qty);
            orderLabel.setText(String.format("%dx | %s, %s ₱%.2f",
                    qty, product.getName(), product.getSize(), price));
            if (subtotalPanel != null) subtotalPanel.updateSubtotal(price);
        });

        // Delete button
        String buttonText = (currentUser.isAdmin() || currentUser.isManager()) ? "x" : "-";
        JButton decr = new JButton(buttonText);
        decr.setFont(sz13.deriveFont(Font.BOLD));
        decr.setForeground(Color.WHITE);
        decr.setBackground(Color.decode("#BD1212"));
        decr.setBorderPainted(false);
        decr.setFocusPainted(false);
        decr.setPreferredSize(new Dimension(45, 20));
        decr.addActionListener(ae -> {
            int currentQty = addedOrderItems.get(productKey);
            if (currentQty > 1) {
                addedOrderItems.put(productKey, currentQty - 1);
                orderLabel.setText(String.format("%dx | %s, %s ₱%.2f",
                        currentQty - 1, product.getName(), product.getSize(), price));
                if (subtotalPanel != null) subtotalPanel.updateSubtotal(-price);
            } else {
                if (currentUser.isAdmin() || currentUser.isManager()) {
                    addedOrderItems.remove(productKey);
                    orderList.remove(orderPanel);
                    orderItemLabels.remove(productKey);
                    orderItemPanels.remove(productKey);
                    if (subtotalPanel != null) subtotalPanel.updateSubtotal(-price);
                    orderList.revalidate();
                    orderList.repaint();
                }
            }
        });

        buttonPanel.add(incr);
        buttonPanel.add(decr);
        contentPanel.add(buttonPanel, BorderLayout.EAST);

        orderPanel.add(contentPanel, BorderLayout.CENTER);
        orderList.add(orderPanel);
        orderItemPanels.put(productKey, orderPanel);
        orderItemLabels.put(productKey, orderLabel);
    }

    private JButton createCategoryButton(String text, Font font, Color bgColor) {
        RoundedButton categoryButton = new RoundedButton(text,30);
        categoryButton.setFont(font);
        categoryButton.setBackground(bgColor);
        categoryButton.setBorderPainted(false);
        categoryButton.setFocusPainted(false);
        categoryButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        categoryButton.setPreferredSize(new Dimension(80,30));
        categoryButton.setMaximumSize(new Dimension(80,30));
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

        categoryButton.addActionListener(e -> {
            clearProductItems();
            addItemsByCategory(text);
        });

        return categoryButton;
    }

    private void searchButton() {
        String searchText = searchBar.getText().toLowerCase();
        clearProductItems();

        boolean found = false;

        for (Product product : productList) {
            if (product.getName().toLowerCase().contains(searchText)) {
                found = true;
                productItemPanel.add(createProductItem(product.getName(), product.getSize(), product.getImage(), sz15, product.getPrice()));
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(null, "No Item Found");
            searchBar.setText("");
        }

        productItemPanel.revalidate();
        productItemPanel.repaint();
    }

    private void clearProductItems() {
        productItemPanel.removeAll();
        productItemPanel.revalidate();
        productItemPanel.repaint();
    }

    private void addItemsByCategory(String category) {
        List<Product> filteredProducts;
        if (category.equalsIgnoreCase("All")) {
            filteredProducts = productList;
        } else {
            filteredProducts = new ArrayList<>();
            for (Product p : productList) {
                if (p.getCategory().equalsIgnoreCase(category)) {
                    filteredProducts.add(p);
                }
            }
        }
        for (Product p : filteredProducts) {
            productItemPanel.add(createProductItem(p.getName(), p.getSize(), p.getImage(), sz15, p.getPrice()));
        }
        productItemPanel.revalidate();
        productItemPanel.repaint();
    }

    private void initializeProducts() {
        productList.clear();
        displayedProducts.clear();

        List<Product> dbProducts = ProductDBManager.getAllProducts();
        productList.addAll(dbProducts);

        if (productList.isEmpty()) {
            productList.add(new Product("null", "null", "null", 0, "null", 0));
        }

        // Refresh the product display
        clearProductItems();
        addItemsByCategory("All");
        refreshDisplayedProducts();
    }

    private void cancelButton() {
        if (currentUser.isAdmin() || currentUser.isManager()) {
            int response = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to cancel order?",
                    "Confirm Cancel Order",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (response == JOptionPane.YES_OPTION) {
                orderList.removeAll();
                orderList.revalidate();
                orderList.repaint();
                if (subtotalPanel != null)
                    subtotalPanel.resetSubtotal();
            }
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Only admin/manager can fully cancel orders.\n" +
                            "Please remove items individually using the +/- buttons.",
                    "Permission Denied",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    public JPanel createProductItem(String text, String productSize, String productImg, Font font, double price) {
        JPanel productItemCont = new JPanel();
        productItemCont.setPreferredSize(new Dimension(150, 200));
        productItemCont.setOpaque(false);
        productItemCont.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        RoundedPanel productItem = new RoundedPanel(20);
        productItem.setBackground(Color.darkGray);
        productItem.setPreferredSize(new Dimension(140, 190));
        productItem.setMaximumSize(new Dimension(140, 190));
        productItem.setLayout(new BorderLayout(0, 0));


        ImageIcon originalIcon = new ImageIcon(productImg);
        Image scaledImage = originalIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        ImageIcon productPic = new ImageIcon(scaledImage);

        JLabel imgFrame = new JLabel(productPic, SwingConstants.CENTER);
        imgFrame.setAlignmentX(Component.CENTER_ALIGNMENT);
        imgFrame.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        productItem.add(imgFrame, BorderLayout.NORTH);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));

        JLabel productName = new JLabel(text);
        productName.setFont(sz15);
        productName.setForeground(Color.WHITE);
        productName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel itemSize = new JLabel(productSize);
        itemSize.setFont(sz13);
        itemSize.setForeground(Color.gray);
        itemSize.setAlignmentX(Component.CENTER_ALIGNMENT);

        textPanel.add(productName);
        textPanel.add(itemSize);
        productItem.add(textPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel productPrice = new JLabel();
        productPrice.setText("₱" + String.format("%.2f", price));
        productPrice.setFont(sz15);
        productPrice.setForeground(Color.WHITE);
        bottomPanel.add(productPrice, BorderLayout.WEST);

        RoundedButton addProduct = new RoundedButton("+",20);
        addProduct.setForeground(Color.BLACK);
        addProduct.setBackground(Color.decode("#686AF5"));
        addProduct.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addProduct.setBorderPainted(false);
        addProduct.setFocusPainted(false);
        addProduct.setPreferredSize(new Dimension(42, 40));
        addProduct.setFont(sz11);
        addProduct.setHorizontalAlignment(SwingConstants.CENTER);
        addProduct.setVerticalAlignment(SwingConstants.CENTER);

        addProduct.addMouseListener(new MouseAdapter() {
            private final Color originalColor = addProduct.getBackground();
            private final Color hoverColor = Color.decode("#5456AC");

            @Override
            public void mouseEntered(MouseEvent e) {
                addProduct.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addProduct.setBackground(originalColor);
            }
        });

        addProduct.addActionListener(e -> {
            String productKey = text + productSize;
            if (addedOrderItems.containsKey(productKey)) {
                int currentQty = addedOrderItems.get(productKey) + 1;
                addedOrderItems.put(productKey, currentQty);

                JPanel existingPanel = orderItemPanels.get(productKey);
                if (existingPanel != null) {
                    JLabel label = orderItemLabels.get(productKey);
                    if (label != null) {
                        label.setText(String.format("%dx | %s, %s ₱%.2f",
                                currentQty, text, productSize, price));
                    }
                }
            } else {
                addedOrderItems.put(productKey, 1);
                JPanel orderPanel = new JPanel(new BorderLayout());
                orderPanel.setOpaque(false);
                orderPanel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));

                // Main panel with BorderLayout
                JPanel contentPanel = new JPanel(new BorderLayout());
                contentPanel.setOpaque(false);

                // Item panel with fixed width
                JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
                itemPanel.setOpaque(false);
                itemPanel.setPreferredSize(new Dimension(220, 25));

                JLabel orderLabel = new JLabel(String.format("%dx | %s, %s ₱%.2f",
                        1, text, productSize, price));
                orderLabel.setFont(sz13);
                orderLabel.setForeground(Color.WHITE);
                itemPanel.add(orderLabel);
                contentPanel.add(itemPanel, BorderLayout.CENTER);

                // Button panel moved left by reducing its width and adjusting insets
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 3, 0));
                buttonPanel.setOpaque(false);
                buttonPanel.setPreferredSize(new Dimension(100, 25)); // Reduced width

                // Add button
                JButton incr = new JButton("+");
                incr.setFont(sz13.deriveFont(Font.BOLD));
                incr.setForeground(Color.WHITE);
                incr.setBackground(Color.decode("#F9A61A"));
                incr.setBorderPainted(false);
                incr.setFocusPainted(false);
                incr.setPreferredSize(new Dimension(45, 20)); // Slightly smaller
                incr.addActionListener(ae -> {
                    int qty = addedOrderItems.get(productKey) + 1;
                    addedOrderItems.put(productKey, qty);
                    orderLabel.setText(String.format("%dx | %s, %s ₱%.2f",
                            qty, text, productSize, price));
                    if (subtotalPanel != null) subtotalPanel.updateSubtotal(price);
                });

                // Delete button
                String buttonText = (currentUser.isAdmin() || currentUser.isManager()) ? "x" : "-";
                JButton decr = new JButton(buttonText);
                decr.setFont(sz13.deriveFont(Font.BOLD));
                decr.setForeground(Color.WHITE);
                decr.setBackground(Color.decode("#BD1212"));
                decr.setBorderPainted(false);
                decr.setFocusPainted(false);
                decr.setPreferredSize(new Dimension(45, 20)); // Slightly smaller
                decr.addActionListener(ae -> {
                    int currentQty = addedOrderItems.get(productKey);
                    if (currentQty > 1) {
                        addedOrderItems.put(productKey, currentQty - 1);
                        orderLabel.setText(String.format("%dx | %s, %s ₱%.2f",
                                currentQty - 1, text, productSize, price));
                        if (subtotalPanel != null) subtotalPanel.updateSubtotal(-price);
                    } else {
                        if (currentUser.isAdmin() || currentUser.isManager()) {
                            addedOrderItems.remove(productKey);
                            orderList.remove(orderPanel);
                            orderItemLabels.remove(productKey);
                            orderItemPanels.remove(productKey);
                            if (subtotalPanel != null) subtotalPanel.updateSubtotal(-price);
                            orderList.revalidate();
                            orderList.repaint();
                        } else {
                            JOptionPane.showMessageDialog(
                                    orderItemPanel.this,
                                    "You need manager privileges to fully remove items",
                                    "Permission Required",
                                    JOptionPane.WARNING_MESSAGE
                            );
                        }
                    }
                });

                buttonPanel.add(incr);
                buttonPanel.add(decr);
                contentPanel.add(buttonPanel, BorderLayout.EAST);

                orderPanel.add(contentPanel, BorderLayout.CENTER);
                orderList.add(orderPanel);
                orderItemPanels.put(productKey, orderPanel);
                orderItemLabels.put(productKey, orderLabel);
            }

            subtotalPanel.updateSubtotal(price);
            orderList.revalidate();
            orderList.repaint();
        });

        bottomPanel.add(addProduct, BorderLayout.EAST);
        productItem.add(bottomPanel, BorderLayout.SOUTH);

        productItemCont.add(productItem);
        return productItemCont;
    }

    public void addProductToOrder(Product product) {
        // Check if product already exists in our list
        boolean productExists = false;
        for (Product p : productList) {
            if (p.getName().equals(product.getName())) {
                productExists = true;
                break;
            }
        }

        // If not in our list, add it
        if (!productExists) {
            productList.add(product);
        }

        // Add to order UI
        String productKey = product.getName() + product.getSize();
        if (addedOrderItems.containsKey(productKey)) {
            int currentQty = addedOrderItems.get(productKey) + 1;
            addedOrderItems.put(productKey, currentQty);

            JPanel existingPanel = orderItemPanels.get(productKey);
            if (existingPanel != null) {
                JLabel label = orderItemLabels.get(productKey);
                if (label != null) {
                    label.setText(String.format("%dx | %s, %s ₱%.2f",
                            currentQty, product.getName(), product.getSize(), product.getPrice()));
                }
            }
        } else {
            addedOrderItems.put(productKey, 1);
            JPanel orderPanel = new JPanel(new BorderLayout());
            orderPanel.setOpaque(false);
            orderPanel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));

            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.setOpaque(false);

            JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            itemPanel.setOpaque(false);
            itemPanel.setPreferredSize(new Dimension(220, 25));

            JLabel orderLabel = new JLabel(String.format("%dx | %s, %s ₱%.2f",
                    1, product.getName(), product.getSize(), product.getPrice()));
            orderLabel.setFont(sz13);
            orderLabel.setForeground(Color.WHITE);
            itemPanel.add(orderLabel);
            contentPanel.add(itemPanel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 3, 0));
            buttonPanel.setOpaque(false);
            buttonPanel.setPreferredSize(new Dimension(100, 25));

            // Add button
            JButton incr = new JButton("+");
            incr.setFont(sz13.deriveFont(Font.BOLD));
            incr.setForeground(Color.WHITE);
            incr.setBackground(Color.decode("#F9A61A"));
            incr.setBorderPainted(false);
            incr.setFocusPainted(false);
            incr.setPreferredSize(new Dimension(45, 20));
            incr.addActionListener(ae -> {
                int qty = addedOrderItems.get(productKey) + 1;
                addedOrderItems.put(productKey, qty);
                orderLabel.setText(String.format("%dx | %s, %s ₱%.2f",
                        qty, product.getName(), product.getSize(), product.getPrice()));
                if (subtotalPanel != null) subtotalPanel.updateSubtotal(product.getPrice());
            });

            // Delete button
            String buttonText = (currentUser.isAdmin() || currentUser.isManager()) ? "x" : "-";
            JButton decr = new JButton(buttonText);
            decr.setFont(sz13.deriveFont(Font.BOLD));
            decr.setForeground(Color.WHITE);
            decr.setBackground(Color.decode("#BD1212"));
            decr.setBorderPainted(false);
            decr.setFocusPainted(false);
            decr.setPreferredSize(new Dimension(45, 20));
            decr.addActionListener(ae -> {
                int currentQty = addedOrderItems.get(productKey);
                if (currentQty > 1) {
                    addedOrderItems.put(productKey, currentQty - 1);
                    orderLabel.setText(String.format("%dx | %s, %s ₱%.2f",
                            currentQty - 1, product.getName(), product.getSize(), product.getPrice()));
                    if (subtotalPanel != null) subtotalPanel.updateSubtotal(-product.getPrice());
                } else {
                    if (currentUser.isAdmin() || currentUser.isManager()) {
                        addedOrderItems.remove(productKey);
                        orderList.remove(orderPanel);
                        orderItemLabels.remove(productKey);
                        orderItemPanels.remove(productKey);
                        if (subtotalPanel != null) subtotalPanel.updateSubtotal(-product.getPrice());
                        orderList.revalidate();
                        orderList.repaint();
                    }
                }
            });

            buttonPanel.add(incr);
            buttonPanel.add(decr);
            contentPanel.add(buttonPanel, BorderLayout.EAST);

            orderPanel.add(contentPanel, BorderLayout.CENTER);
            orderList.add(orderPanel);
            orderItemPanels.put(productKey, orderPanel);
            orderItemLabels.put(productKey, orderLabel);
        }


        subtotalPanel.updateSubtotal(product.getPrice());
        orderList.revalidate();
        orderList.repaint();
    }

    class subTotalPanel extends JPanel {
        private double subtotal = 0.0;
        private double tax = 0.0;
        private double total = 0.0;
        private final JLabel subtotalLabel;
        private final JLabel taxLabel;
        private final JLabel totalLabel;

        subTotalPanel() {
            RoundedPanel subTotal = new RoundedPanel(20);
            subTotal.setBackground(Color.decode("#424040"));
            subTotal.setPreferredSize(new Dimension(330, 180)); // Increased from 280
            subTotal.setMaximumSize(new Dimension(330, 180));
            subTotal.setAlignmentX(Component.CENTER_ALIGNMENT);
            subTotal.setLayout(new BoxLayout(subTotal, BoxLayout.Y_AXIS));
            subTotal.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 10)); // Reduced padding

            JLabel stText = new JLabel("Sub Total: ");
            stText.setFont(sz15);
            stText.setBorder(BorderFactory.createEmptyBorder(15,0,5,0));
            stText.setForeground(Color.WHITE);
            subTotal.add(stText);

            subtotalLabel = new JLabel("₱" + String.format("%.2f", subtotal));
            subtotalLabel.setFont(sz15);
            subtotalLabel.setForeground(Color.WHITE);
            subtotalLabel.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
            subTotal.add(subtotalLabel);

            taxLabel = new JLabel("Total Tax: ₱" + String.format("%.2f", tax));
            taxLabel.setFont(sz15);
            taxLabel.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
            taxLabel.setForeground(Color.WHITE);
            subTotal.add(taxLabel);

            JLabel stLine = new JLabel("___________________________");
            stLine.setFont(sz15);
            stLine.setForeground(Color.WHITE);
            stLine.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
            subTotal.add(stLine);

            JLabel stTotalText = new JLabel("Total: ");
            stTotalText.setFont(sz15);
            stTotalText.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
            stTotalText.setForeground(Color.WHITE);
            subTotal.add(stTotalText);

            totalLabel = new JLabel("₱" + String.format("%.2f", total));
            totalLabel.setFont(sz15);
            totalLabel.setForeground(Color.WHITE);
            subTotal.add(totalLabel);

            orderSummary.add(subTotal);
        }

        public void updateSubtotal(double amount) {
            double vatRate = 0.12;
            subtotal += amount;
            tax = subtotal * vatRate;
            total = subtotal + tax;

            if (subtotal < 0) subtotal = 0;
            if (total < 0) total = 0;
            if (tax < 0) tax = 0;

            subtotalLabel.setText("₱" + String.format("%.2f", subtotal));
            taxLabel.setText("Total Tax: ₱" + String.format("%.2f", tax));
            totalLabel.setText("₱" + String.format("%.2f", total));
        }

        public void resetSubtotal() {
            subtotal = 0.0;
            tax = 0.0;
            total = 0.0;

            subtotalLabel.setText("₱0.00");
            taxLabel.setText("Total Tax: ₱0.00");
            totalLabel.setText("₱0.00");
        }
    }

    void clearOrder() {
        addedOrderItems.clear();
        orderItemPanels.clear();
        orderItemLabels.clear();
        if (subtotalPanel != null)
            subtotalPanel.resetSubtotal();
        orderList.removeAll();
        orderList.revalidate();
        orderList.repaint();
    }


    public void refreshProducts() {
        productList.clear();
        displayedProducts.clear();

        // fresh data from database
        List<Product> dbProducts = ProductDBManager.getAllProducts();
        productList.addAll(dbProducts);

        if (productList.isEmpty()) {
            productList.add(new Product("null", "null", "null", 0, "null", 0));
        }

        clearProductItems();
        addItemsByCategory("All");
        refreshDisplayedProducts();
    }
}