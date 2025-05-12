package POS_SYSTEM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class posSystem extends javax.swing.JFrame {

    private final orderItemPanel orderItemSection;
    private final User currentUser;
    private JPanel currentCenterPanel; // Track the current center panel

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
        currentCenterPanel = orderItemSection; // Initialize the current center panel
        add(currentCenterPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Method to switch panel
    public void switchToPanel(JPanel newPanel) {
        remove(currentCenterPanel); // Remove the current center panel
        currentCenterPanel = newPanel; // Set the new panel as the current one
        add(currentCenterPanel, BorderLayout.CENTER); // Add the new panel
        revalidate(); // Refresh the layout
        repaint(); // Repaint the frame
    }

    public static void main(String[] args) {
        User user = new User("username", "password");
        new posSystem(user);
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
        Font navFont = FontUtils.loadFont(18f); // Slightly reduced font size
        setPreferredSize(new Dimension(250, getHeight())); // Reduced width from 300 to 250
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.decode("#03346E"));

        JPanel navHeader = new JPanel();
        navHeader.setLayout(new BoxLayout(navHeader, BoxLayout.X_AXIS));
        navHeader.setBackground(Color.decode("#03346E"));
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
        navTitle.setText(" PCU CANTEEN POS"); // Reduced spacing
        navTitle.setAlignmentY(Component.CENTER_ALIGNMENT);
        navHeader.add(navTitle);

        add(navHeader);

        JPanel navOptionsPanel = new JPanel();
        navOptionsPanel.setLayout(new BoxLayout(navOptionsPanel, BoxLayout.Y_AXIS));
        navOptionsPanel.setBackground(Color.decode("#03346E"));
        navOptionsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        navOptionsPanel.add(createNavButton("Order Item", navFont));
        navOptionsPanel.add(createNavButton("Order History", navFont));
        navOptionsPanel.add(createNavButton("Dashboard", navFont));
        navOptionsPanel.add(createNavButton("Menu Management", navFont));
        navOptionsPanel.add(createNavButton("Inventory", navFont));

        navOptionsPanel.add(Box.createVerticalGlue());
        navOptionsPanel.add(createNavButton("Logout", navFont));
        navOptionsPanel.add(Box.createVerticalStrut(15)); // Reduced spacing

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
        navButton.setBackground(Color.decode("#03346E"));
        navButton.setBorderPainted(false);
        navButton.setFocusPainted(false);
        navButton.setHorizontalAlignment(SwingConstants.LEFT);
        navButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        navButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35)); // Reduced height
        navButton.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 0)); // Reduced padding

        navButton.addMouseListener(new MouseAdapter() {
            private final Color originalColor = navButton.getBackground();
            private final Color hoverColor = Color.decode("#035096");

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
    private final Map<String, Integer> addedOrderItems = new HashMap<>();
    private final Map<String, JPanel> orderItemPanels = new HashMap<>();
    private final Map<String, JLabel> orderItemLabels = new HashMap<>();
    private final JPanel orderSummary;
    private final subTotalPanel subtotalPanel;
    private final JPanel orderList;
    private final JPanel productItemPanel;
    private final JTextField searchBar;
    private final User currentUser;

    Font sz11 = FontUtils.loadFont(11f);
    Font sz12 = FontUtils.loadFont(12f);
    Font sz13 = FontUtils.loadFont(13f);
    Font sz15 = FontUtils.loadFont(15f);
    Font sz16 = FontUtils.loadFont(16f);
    Font sz17 = FontUtils.loadFont(17f);


    private final List<Product> productList = new ArrayList<>();

    orderItemPanel(User currentUser) {
        this.currentUser = currentUser;
        Font oiFont = FontUtils.loadFont(15f); // Reduced from 17f
        setBackground(Color.decode("#021526"));
        setLayout(new BorderLayout());

        // Main container panel
        JPanel mainConts = new JPanel();
        mainConts.setLayout(new BoxLayout(mainConts, BoxLayout.Y_AXIS));
        mainConts.setBackground(Color.decode("#021526"));
        mainConts.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 0)); // Reduced left padding

        // SEARCH BAR
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        searchPanel.setBackground(Color.decode("#898b8f"));
        searchPanel.setPreferredSize(new Dimension(450, 40)); // Reduced dimensions
        searchPanel.setMaximumSize(new Dimension(450, 40));
        searchPanel.setLayout(new BorderLayout());
        searchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        searchBar = new JTextField();
        searchBar.setBackground(Color.decode("#898b8f"));
        searchBar.setFont(oiFont);
        searchBar.setPreferredSize(new Dimension(350, 40)); // Reduced dimensions
        searchBar.setMaximumSize(new Dimension(350, 40));
        searchPanel.add(searchBar, BorderLayout.CENTER);

        JButton searchButton = new JButton();
        searchButton.setText("Search");
        searchButton.setFont(oiFont);
        searchButton.setPreferredSize(new Dimension(100, 40)); // Reduced dimensions
        searchButton.setMaximumSize(new Dimension(100, 40));
        searchPanel.add(searchButton, BorderLayout.EAST);
        searchButton.addActionListener(evt -> searchButton());

        mainConts.add(searchPanel);

        // CATEGORY PANEL
        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5)); // Reduced spacing
        categoryPanel.setBackground(Color.decode("#021526"));
        categoryPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35)); // Reduced height
        categoryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        categoryPanel.add(createCategoryButton("All", sz13, Color.gray));
        categoryPanel.add(createCategoryButton("Meals", sz13, Color.gray));
        categoryPanel.add(createCategoryButton("Snacks", sz13, Color.gray));
        categoryPanel.add(createCategoryButton("Drinks", sz13, Color.gray));

        mainConts.add(categoryPanel);

        // PRODUCT ITEMS PANEL
        productItemPanel = new JPanel(new GridLayout(0, 4, 2, 2)); // Reduced spacing
        productItemPanel.setBackground(Color.decode("#021526"));
        productItemPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // Reduced padding
        productItemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        initializeProducts();

        // Order summary panel
        orderSummary = new JPanel();
        orderSummary.setPreferredSize(new Dimension(300, 720)); // Reduced from 400
        orderSummary.setBackground(Color.decode("#021526"));
        orderSummary.setLayout(new BoxLayout(orderSummary, BoxLayout.Y_AXIS));

        // Date panel
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        datePanel.setOpaque(false);
        JLabel dateLabel = new JLabel();
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setFont(sz15); // Reduced from sz16
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy h:mm a");
        dateLabel.setText(dateFormat.format(new Date()));
        datePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        datePanel.add(dateLabel);
        orderSummary.add(datePanel);

        // Order list panel
        orderList = new JPanel();
        orderList.setLayout(new BoxLayout(orderList, BoxLayout.Y_AXIS));
        orderList.setBackground(Color.decode("#424040"));
        JScrollPane orderListScroll = new JScrollPane(orderList);
        orderListScroll.setBorder(null);
        orderListScroll.setPreferredSize(new Dimension(280, 320));   // Reduced width
        orderListScroll.setAlignmentX(Component.CENTER_ALIGNMENT);
        orderListScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        orderListScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        orderListScroll.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 10));
        orderListScroll.setOpaque(false);
        orderSummary.add(orderListScroll);

        subtotalPanel = new subTotalPanel();

        JPanel orderButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0)); // Reduced spacing
        orderButton.setOpaque(false);
        orderButton.setPreferredSize(new Dimension(280, getHeight())); // Reduced width
        orderButton.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0)); // Reduced padding

        JButton cancelBtn = new JButton("Cancel Order");
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setBackground(Color.decode("#BD1212"));
        cancelBtn.setPreferredSize(new Dimension(130, 45)); // Reduced dimensions
        cancelBtn.setBorderPainted(false);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setFont(sz15); // Reduced from sz16

        cancelBtn.addActionListener(evt -> cancelButton());
        orderButton.add(cancelBtn);

        JButton processBtn = new JButton("Process Order");
        processBtn.setForeground(Color.WHITE);
        processBtn.setBackground(Color.decode("#F9A61A"));
        processBtn.setPreferredSize(new Dimension(130, 45)); // Reduced dimensions
        processBtn.setBorderPainted(false);
        processBtn.setFocusPainted(false);
        processBtn.setFont(sz15); // Reduced from sz16
        orderButton.add(processBtn);
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

        // Scroll pane for product items panel
        JScrollPane productScrollPane = new JScrollPane(productItemPanel);
        productScrollPane.setBorder(null);
        productScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        productScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        mainConts.add(productScrollPane);
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
                productItemPanel.add(createProductItem(product.getName(), product.getSize(), product.getImage(), sz15, product.getPrice())); // Reduced font
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
            productItemPanel.add(createProductItem(p.getName(), p.getSize(), p.getImage(), sz15, p.getPrice())); // Reduced font
        }
        productItemPanel.revalidate();
        productItemPanel.repaint();
    }

    private void initializeProducts() {
        productList.add(new Product("Jack n' Jill Piattos", "40g", "Images/Sample Product Images/Piattos-Cheese-40g.png", 15.00, "Snacks"));
        productList.add(new Product("Jack n' Jill Nova", "40g", "Images/Sample Product Images/Jack n Jill Nova .png", 15.00, "Snacks"));
        productList.add(new Product("Burger", "1pc", "Images/Sample Product Images/Burger .png", 20.00, "Snacks"));
        productList.add(new Product("Hotdog", "1pc", "Images/Sample Product Images/Hotdog.png", 20.00, "Snacks"));
        productList.add(new Product("Coke Mismo", "295ml", "Images/Sample Product Images/Coke Mismo.png", 15.00, "Drinks"));
        productList.add(new Product("Royal Mismo", "295ml", "Images/Sample Product Images/Royal Mismo.png", 15.00, "Drinks"));
        productList.add(new Product("Caldereta", "1 serving", "Images/Sample Product Images/Caldereta.png", 50.00, "Meals"));
        productList.add(new Product("Rice", "1 cup", "Images/Sample Product Images/Rice.png", 10.00, "Meals"));
    }

    private void cancelButton() {
        if (currentUser.isAdmin() || currentUser.isManager()) {
            // Admin and Manager can fully cancel
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
            // Regular user can only remove items one by one
            JOptionPane.showMessageDialog(
                    null,
                    "Only admin/manager can fully cancel orders.\n" +
                            "Please remove items individually using the Delete buttons.",
                    "Permission Denied",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    public JPanel createProductItem(String text, String productSize, String productImg, Font font, double price) {
        JPanel productItemCont = new JPanel();
        productItemCont.setPreferredSize(new Dimension(150, 200)); // Reduced height
        productItemCont.setOpaque(false);
        productItemCont.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // Reduced padding

        JPanel productItem = new JPanel();
        productItem.setBackground(Color.decode("#111010"));
        productItem.setPreferredSize(new Dimension(140, 190)); // Reduced dimensions
        productItem.setMaximumSize(new Dimension(140, 190));
        productItem.setLayout(new BorderLayout(0, 0)); // Reduced gaps between components
        productItem.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        ImageIcon originalIcon = new ImageIcon(productImg);
        Image scaledImage = originalIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH); // Smaller image
        ImageIcon productPic = new ImageIcon(scaledImage);

        JLabel imgFrame = new JLabel(productPic, SwingConstants.CENTER);
        imgFrame.setAlignmentX(Component.CENTER_ALIGNMENT);
        imgFrame.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0)); // Reduced padding
        productItem.add(imgFrame, BorderLayout.NORTH);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.decode("#111010"));
        textPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5)); // Reduced padding

        JLabel productName = new JLabel(text);
        productName.setFont(sz15); // Using slightly smaller font
        productName.setForeground(Color.WHITE);
        productName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel itemSize = new JLabel(productSize);
        itemSize.setFont(sz13); // Using smaller font for size
        itemSize.setForeground(Color.gray);
        itemSize.setAlignmentX(Component.CENTER_ALIGNMENT);

        textPanel.add(productName);
        textPanel.add(itemSize);
        productItem.add(textPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBackground(Color.decode("#111010"));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Reduced padding

        JLabel productPrice = new JLabel();
        productPrice.setText("₱" + String.format("%.2f", price));
        productPrice.setFont(sz15);
        productPrice.setForeground(Color.decode("#686AF5"));
        bottomPanel.add(productPrice, BorderLayout.WEST);

        JButton addProduct = new JButton("+");
        addProduct.setForeground(Color.BLACK);
        addProduct.setBackground(Color.decode("#686AF5"));
        addProduct.setBorderPainted(false);
        addProduct.setFocusPainted(false);
        addProduct.setPreferredSize(new Dimension(40, 40)); // Smaller button
        addProduct.setFont(sz11); // Smaller font for button
        addProduct.setHorizontalAlignment(SwingConstants.CENTER);
        addProduct.setVerticalAlignment(SwingConstants.CENTER);

        addProduct.addActionListener(e -> {
            String productKey = text + productSize;
            if (addedOrderItems.containsKey(productKey)) {
                int currentQty = addedOrderItems.get(productKey) + 1;
                addedOrderItems.put(productKey, currentQty);

                JPanel existingPanel = orderItemPanels.get(productKey);
                if (existingPanel != null) {
                    JLabel label = orderItemLabels.get(productKey);
                    if (label != null) {
                        label.setText(currentQty + "x | " + text + ", " + productSize + ", ₱ " + String.format("%.2f", price));
                    }
                }
            } else {
                addedOrderItems.put(productKey, 1);
                JPanel orderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
                orderPanel.setOpaque(false);

                JLabel orderLabel = new JLabel("1x | " + text + ", " + productSize + ", ₱ " + String.format("%.2f", price));
                orderLabel.setFont(sz12); // Reduced font
                orderLabel.setForeground(Color.WHITE);

                JButton incr = new JButton("Add");
                incr.setFont(sz12); // Reduced font
                incr.setForeground(Color.decode("#F9A61A"));
                incr.setBackground(Color.gray);
                incr.setBorderPainted(false);
                incr.setFocusPainted(false);
                incr.addActionListener(ae -> {
                    int qty = addedOrderItems.get(productKey) + 1;
                    addedOrderItems.put(productKey, qty);
                    orderLabel.setText(qty + "x | " + text + ", " + productSize + ", ₱ " + String.format("%.2f", price));
                    if (subtotalPanel != null) subtotalPanel.updateSubtotal(price);
                });

                JButton decr = new JButton("Delete");
                decr.setFont(sz12); // Reduced font
                decr.setForeground(Color.decode("#BD1212"));
                decr.setBackground(Color.gray);
                decr.setBorderPainted(false);
                decr.setFocusPainted(false);
                decr.addActionListener(ae -> {
                    int qty = addedOrderItems.get(productKey) - 1;
                    if (qty > 0) {
                        addedOrderItems.put(productKey, qty);
                        orderLabel.setText(qty + "x | " + text + ", " + productSize + ", ₱ " + String.format("%.2f", price));
                        if (subtotalPanel != null) subtotalPanel.updateSubtotal(-price);
                    } else {
                        addedOrderItems.remove(productKey);
                        orderList.remove(orderPanel);
                        orderItemLabels.remove(productKey);
                        orderItemPanels.remove(productKey);
                        if (subtotalPanel != null) subtotalPanel.updateSubtotal(-price);
                        orderList.revalidate();
                        orderList.repaint();
                    }
                });

                orderPanel.add(orderLabel);
                orderPanel.add(incr);
                orderPanel.add(decr);

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

    class subTotalPanel extends JPanel {
        private double subtotal = 0.0;
        private double tax = 0.0;
        private double total = 0.0;
        private final JLabel subtotalLabel;
        private final JLabel taxLabel;
        private final JLabel totalLabel;

        subTotalPanel() {
            JPanel subTotal = new JPanel();
            subTotal.setBackground(Color.decode("#424040"));
            subTotal.setPreferredSize(new Dimension(280, 180)); // Reduced dimensions
            subTotal.setMaximumSize(new Dimension(280, 180));
            subTotal.setAlignmentX(Component.CENTER_ALIGNMENT);
            subTotal.setLayout(new BoxLayout(subTotal, BoxLayout.Y_AXIS));
            subTotal.setBorder(BorderFactory.createEmptyBorder(15, 10, 0, 10)); // Reduced padding

            JLabel stText = new JLabel("Sub Total: ");
            stText.setFont(sz15);
            stText.setForeground(Color.WHITE);
            subTotal.add(stText);

            subtotalLabel = new JLabel("₱" + String.format("%.2f", subtotal));
            subtotalLabel.setFont(sz15);
            subtotalLabel.setForeground(Color.WHITE);
            subTotal.add(subtotalLabel);

            taxLabel = new JLabel("Total Tax: ₱" + String.format("%.2f", tax));
            taxLabel.setFont(sz15);
            taxLabel.setForeground(Color.WHITE);
            subTotal.add(taxLabel);

            JLabel stLine = new JLabel("___________________________"); // Shorter line
            stLine.setFont(sz15);
            stLine.setForeground(Color.WHITE);
            subTotal.add(stLine);

            JLabel stTotalText = new JLabel("Total: ");
            stTotalText.setFont(sz15); // Reduced from sz16
            stTotalText.setForeground(Color.WHITE);
            subTotal.add(stTotalText);

            totalLabel = new JLabel("₱" + String.format("%.2f", total));
            totalLabel.setFont(sz15); // Reduced from sz16
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

    static class processOrderPanel extends JPanel {
        private double discountTotal = 0.0;
        private double total = 0.0;
        private JTextField cTenderedTField;
        private JLabel changeLabel2;

        processOrderPanel(List<Product> orderedProducts) {
            Font sz15 = FontUtils.loadFont(15f); // Reduced from 16f
            Font sz18 = FontUtils.loadFont(18f); // Reduced from 20f

            JPanel processOrder = new JPanel();
            processOrder.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5)); // Reduced spacing
            processOrder.setPreferredSize(new Dimension(1280, 720));
            processOrder.setMaximumSize(new Dimension(1280, 720));
            processOrder.setBackground(Color.DARK_GRAY);

            JPanel orderSummarySide = new JPanel();
            orderSummarySide.setLayout(new BoxLayout(orderSummarySide, BoxLayout.Y_AXIS));
            orderSummarySide.setPreferredSize(new Dimension(400, 650)); // Reduced dimensions
            orderSummarySide.setAlignmentX(Component.LEFT_ALIGNMENT);
            orderSummarySide.setOpaque(false);

            JPanel osPanel = new JPanel();
            osPanel.setOpaque(false);
            osPanel.setLayout(new BorderLayout());
            osPanel.setPreferredSize(new Dimension(400, 35)); // Reduced height
            osPanel.setMaximumSize(new Dimension(400, 35));

            JLabel osLabel = new JLabel("Order Summary");
            osLabel.setForeground(Color.white);
            osLabel.setFont(sz18); // Reduced font
            osPanel.add(osLabel, BorderLayout.WEST);
            orderSummarySide.add(osPanel);

            JPanel plusBtnPanel = new JPanel();
            plusBtnPanel.setOpaque(false);
            plusBtnPanel.setLayout(new BorderLayout());
            plusBtnPanel.setPreferredSize(new Dimension(400, 35)); // Reduced height
            plusBtnPanel.setMaximumSize(new Dimension(400, 35));

            JButton plusButton = new JButton("+");
            plusButton.setBorderPainted(false);
            plusButton.setFocusPainted(false);
            plusButton.setForeground(Color.WHITE);
            plusButton.setBackground(Color.gray);
            plusBtnPanel.add(plusButton, BorderLayout.EAST);
            orderSummarySide.add(plusBtnPanel);

            JPanel orderProcessSummary = new JPanel();
            orderProcessSummary.setLayout(new BoxLayout(orderProcessSummary, BoxLayout.Y_AXIS));
            orderProcessSummary.setBackground(Color.decode("#2A273A"));

            JScrollPane orderSummaryScroll = new JScrollPane(orderProcessSummary);
            orderSummaryScroll.setBorder(null);
            orderSummaryScroll.setPreferredSize(new Dimension(380, 300)); // Adjusted dimensions
            orderSummaryScroll.setAlignmentX(Component.CENTER_ALIGNMENT);
            orderSummaryScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            orderSummaryScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            orderSummaryScroll.setOpaque(false);

            JScrollBar verticalScrollBar2 = orderSummaryScroll.getVerticalScrollBar();
            verticalScrollBar2.setUnitIncrement(30);
            verticalScrollBar2.setBlockIncrement(100);
            orderSummarySide.add(orderSummaryScroll);

            Map<String, Integer> productQuantityMap = new LinkedHashMap<>();
            for (Product product : orderedProducts) {
                String key = product.getName() + " - " + product.getSize();
                productQuantityMap.put(key, productQuantityMap.getOrDefault(key, 0) + 1);
            }

            double subtotal = 0.0;
            for (Map.Entry<String, Integer> entry : productQuantityMap.entrySet()) {
                String productNameSize = entry.getKey();
                int quantity = entry.getValue();

                double price = 0.0;
                for (Product p : orderedProducts) {
                    String key = p.getName() + " - " + p.getSize();
                    if (key.equals(productNameSize)) {
                        price = p.getPrice();
                        break;
                    }
                }
                subtotal += price * quantity;
                JLabel productLabel = new JLabel(quantity + "x | " + productNameSize + ", ₱" + String.format("%.2f", price * quantity));
                productLabel.setForeground(Color.WHITE);
                productLabel.setFont(sz18); // Reduced font
                orderProcessSummary.add(productLabel);
            }

            orderSummarySide.add(orderSummaryScroll);

            JPanel subTotalPanel = new JPanel();
            subTotalPanel.setLayout(new BoxLayout(subTotalPanel, BoxLayout.Y_AXIS));
            subTotalPanel.setOpaque(false);

            JPanel stPanel = new JPanel();
            stPanel.setOpaque(false);
            stPanel.setLayout(new BorderLayout());
            stPanel.setPreferredSize(new Dimension(400, 25)); // Reduced height
            stPanel.setMaximumSize(new Dimension(400, 25));

            JLabel subtotalLabel = new JLabel("Sub total: ₱" + String.format("%.2f", subtotal));
            subtotalLabel.setFont(sz15); // Reduced font
            subtotalLabel.setForeground(Color.WHITE);
            stPanel.add(subtotalLabel, BorderLayout.WEST);
            subTotalPanel.add(stPanel);

            JPanel dPanel = new JPanel();
            dPanel.setOpaque(false);
            dPanel.setLayout(new BorderLayout());
            dPanel.setPreferredSize(new Dimension(400, 25)); // Reduced height
            dPanel.setMaximumSize(new Dimension(400, 25));
            JLabel discount = new JLabel("Discount: ");
            discount.setFont(sz15); // Reduced font
            discount.setForeground(Color.WHITE);
            dPanel.add(discount, BorderLayout.WEST);
            subTotalPanel.add(dPanel);

            total = subtotal - discountTotal;
            if (total < 0) total = 0;

            JPanel tPanel = new JPanel();
            tPanel.setOpaque(false);
            tPanel.setLayout(new BorderLayout());
            tPanel.setPreferredSize(new Dimension(400, 25)); // Reduced height
            tPanel.setMaximumSize(new Dimension(400, 25));
            JLabel totalLabel = new JLabel("Total: ₱" + String.format("%.2f", total));
            totalLabel.setFont(sz15); // Reduced font
            totalLabel.setForeground(Color.WHITE);
            tPanel.add(totalLabel, BorderLayout.WEST);
            subTotalPanel.add(tPanel);

            JPanel cashTenderedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0)); // Reduced spacing
            cashTenderedPanel.setOpaque(false);
            cashTenderedPanel.setMaximumSize(new Dimension(400, 50)); // Reduced height

            JLabel cashTendered = new JLabel("Cash Tendered: ");
            cashTendered.setForeground(Color.white);
            cashTendered.setFont(sz18); // Reduced font
            cashTenderedPanel.add(cashTendered);

            cTenderedTField = new JTextField();
            cTenderedTField.setPreferredSize(new Dimension(120, 40)); // Reduced dimensions
            cTenderedTField.setOpaque(false);
            cTenderedTField.setBorder(BorderFactory.createLineBorder(Color.white, 1, true));
            cTenderedTField.setFont(sz18); // Reduced font
            cTenderedTField.setForeground(Color.WHITE);
            cTenderedTField.setText("");
            cashTenderedPanel.add(cTenderedTField);

            subTotalPanel.add(cashTenderedPanel);

            JPanel changePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0)); // Reduced spacing
            changePanel.setPreferredSize(new Dimension(400, 50)); // Reduced height
            changePanel.setMaximumSize(new Dimension(400, 50));
            changePanel.setOpaque(false);

            JLabel changeLabel1 = new JLabel("Change: ");
            changeLabel1.setForeground(Color.white);
            changeLabel1.setFont(sz18); // Reduced font
            changePanel.add(changeLabel1);

            changeLabel2 = new JLabel("₱ 0.00");
            changeLabel2.setOpaque(false);
            changeLabel2.setFont(sz18); // Reduced font
            changeLabel2.setForeground(Color.WHITE);
            changePanel.add(changeLabel2);

            subTotalPanel.add(changePanel);

            orderSummarySide.add(subTotalPanel);

            // Process Buttons for order confirmation and cancellation
            JPanel processButtonPanel = new JPanel();
            processButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0)); // Reduced spacing
            processButtonPanel.setPreferredSize(new Dimension(400, getHeight()));
            processButtonPanel.setOpaque(false);

            JButton cancelBtn = new JButton("Cancel");
            cancelBtn.setForeground(Color.WHITE);
            cancelBtn.setBackground(Color.decode("#BD1212"));
            cancelBtn.setPreferredSize(new Dimension(120, 40)); // Reduced dimensions
            cancelBtn.setBorderPainted(false);
            cancelBtn.setFocusPainted(false);
            cancelBtn.setFont(sz15); // Reduced font
            cancelBtn.addActionListener(e -> {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                if (topFrame instanceof posSystem posFrame) {
                    posFrame.switchToPanel(posFrame.getOrderItemPanel());
                }
            });

            JButton processBtn = new JButton("Confirm Payment");
            processBtn.setForeground(Color.WHITE);
            processBtn.setPreferredSize(new Dimension(150, 40)); // Reduced dimensions
            processBtn.setBackground(Color.decode("#F9A61A"));
            processBtn.setBorderPainted(false);
            processBtn.setFocusPainted(false);
            processBtn.setFont(sz15); // Reduced font
            processBtn.addActionListener(e -> {
                String cashInput = cTenderedTField.getText().trim().replace("₱", "").trim();
                try {
                    double cashTenderedAmount = Double.parseDouble(cashInput);
                    if (cashTenderedAmount < total) {
                        JOptionPane.showMessageDialog(this, "Insufficient cash tendered. Please enter an amount equal or greater than the total.", "Payment Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    double change = cashTenderedAmount - total;
                    changeLabel2.setText("₱ " + String.format("%.2f", change));
                    JOptionPane.showMessageDialog(this, "Payment successful!\nChange: ₱ " + String.format("%.2f", change), "Payment Confirmed", JOptionPane.INFORMATION_MESSAGE);

                    // After payment, clear order and switch back to orderItemPanel
                    JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                    if (topFrame instanceof posSystem) {
                        posSystem posFrame = (posSystem) topFrame;

                        posFrame.getOrderItemPanel().clearOrder();
                        posFrame.switchToPanel(posFrame.getOrderItemPanel());
                        revalidate();
                        repaint();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid numeric amount for cash tendered.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            processButtonPanel.add(cancelBtn);
            processButtonPanel.add(processBtn);

            JPanel receiptSide = new JPanel();
            receiptSide.setBackground(Color.WHITE);
            receiptSide.setPreferredSize(new Dimension(300, 600)); // Reduced dimensions
            receiptSide.setMaximumSize(new Dimension(300, 600));

            orderSummarySide.add(processButtonPanel);

            processOrder.add(orderSummarySide);
            processOrder.add(receiptSide);
            add(processOrder);
        }
    }

    private void clearOrder() {
        addedOrderItems.clear();
        orderItemPanels.clear();
        orderItemLabels.clear();
        if (subtotalPanel != null)
            subtotalPanel.resetSubtotal();
        orderList.removeAll();
        orderList.revalidate();
        orderList.repaint();
    }
}