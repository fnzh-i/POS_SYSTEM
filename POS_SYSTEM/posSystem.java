    package POS_SYSTEM;

    import javax.swing.*;
    import javax.swing.border.Border;
    import java.awt.*;
    import java.awt.event.*;
    import java.io.File;
    import java.text.SimpleDateFormat;
    import java.util.*;
    import java.util.List;

    public class posSystem extends javax.swing.JFrame {

        private navigationPanel navSection;
        private orderItemPanel orderItemSection;
        private User currentUser;

        public posSystem(User user) {
            this.currentUser = user;

            setSize(1920, 1080);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setLayout(new BorderLayout());
            setResizable(false);

            ImageIcon systemIcon = new ImageIcon("Images/Logo/Philippine_Christian_University_logo.png");
            setIconImage(systemIcon.getImage());
            setTitle("PCU-POS");
            getContentPane().setBackground(Color.decode("#021526"));

            navSection = new navigationPanel(user);
            add(navSection, BorderLayout.WEST);
            orderItemSection = new orderItemPanel(user);
            add(orderItemSection, BorderLayout.CENTER);

            // Assuming this exists and needs to be here
            // orderSummaryFile order = new orderSummaryFile();
            // order.orderSumm(200.00, 120.00);

            setVisible(true);


        }

        public User getCurrentUser() {
            return currentUser;
        }

        public static void main(String[] args) {
            new logInSection();
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
            Font navFont = FontUtils.loadFont(20f);
            setPreferredSize(new Dimension(300, getHeight()));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            setBackground(Color.decode("#03346E"));

            JPanel navHeader = new JPanel();
            navHeader.setLayout(new BoxLayout(navHeader, BoxLayout.X_AXIS));
            navHeader.setBackground(Color.decode("#03346E"));
            navHeader.setBorder(BorderFactory.createEmptyBorder(20, 20, 30, 20));
            navHeader.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

            ImageIcon systemLogo = new ImageIcon("Images/Logo/Philippine_Christian_University_logo.png");
            JLabel pcuLogo = new JLabel();
            Image originalImage = systemLogo.getImage();

            int imgWidth = 70;
            int imgHeight = 70;

            Image scaledImg = originalImage.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledLogo = new ImageIcon(scaledImg);

            pcuLogo.setIcon(scaledLogo);
            pcuLogo.setAlignmentY(Component.CENTER_ALIGNMENT);
            navHeader.add(pcuLogo);

            JLabel navTitle = new JLabel();
            navTitle.setForeground(Color.WHITE);
            navTitle.setFont(navFont);
            navTitle.setText("   PCU CANTEEN POS");
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
            navOptionsPanel.add(Box.createVerticalStrut(20));

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
            navButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            navButton.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 0));

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

            if (text.equals("Logout")) {
                navButton.addActionListener(e -> logout());
            }
            if (text.equals("Menu Management")) {
                navButton.addActionListener(e -> new MenuManagement().setVisible(true));
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
        private subTotalPanel subtotalPanel;
        private final JPanel orderList;
        private final JPanel productItemPanel;
        private final JTextField searchBar;
        private final User currentUser;

        Font sz12 = FontUtils.loadFont(12f);
        Font sz13 = FontUtils.loadFont(13f);
        Font sz15 = FontUtils.loadFont(15f);
        Font sz16 = FontUtils.loadFont(16f);
        Font sz17 = FontUtils.loadFont(17f);

        private final List<Product> productList = new ArrayList<>();

        orderItemPanel(User currentUser) {
            this.currentUser = currentUser;
            Font oiFont = FontUtils.loadFont(17f);
            setBackground(Color.decode("#021526"));
            setPreferredSize(new Dimension(980, getHeight()));
            setLayout(new BorderLayout());

            // Main container panel
            JPanel mainConts = new JPanel();
            mainConts.setLayout(new BoxLayout(mainConts, BoxLayout.Y_AXIS));
            mainConts.setBackground(Color.decode("#021526"));
            mainConts.setBorder(BorderFactory.createEmptyBorder(10, 60, 0, 0));

            // SEARCH BAR
            JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            searchPanel.setBackground(Color.decode("#898b8f"));
            searchPanel.setPreferredSize(new Dimension(550, 43));
            searchPanel.setMaximumSize(new Dimension(550, 43));
            searchPanel.setLayout(new BorderLayout());
            searchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            searchBar = new JTextField();
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
            searchButton.addActionListener(evt -> searchButton());




            mainConts.add(searchPanel);

            // CATEGORY PANEL
            JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            categoryPanel.setBackground(Color.decode("#021526"));
            categoryPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            categoryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            categoryPanel.add(createCategoryButton("All", sz13, Color.gray));
            categoryPanel.add(createCategoryButton("Meals", sz13, Color.gray));
            categoryPanel.add(createCategoryButton("Snacks", sz13, Color.gray));
            categoryPanel.add(createCategoryButton("Drinks", sz13, Color.gray));

            mainConts.add(categoryPanel);

            // PRODUCT ITEMS PANEL
            productItemPanel = new JPanel(new GridLayout(0, 3, 10, 10));
            productItemPanel.setBackground(Color.decode("#021526"));
            productItemPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
            productItemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            initializeProducts();

            // Order summary panel
            orderSummary = new JPanel();
            orderSummary.setPreferredSize(new Dimension(550, 1080));
            orderSummary.setBackground(Color.decode("#021526"));
            orderSummary.setLayout(new BoxLayout(orderSummary, BoxLayout.Y_AXIS));

            // Date panel
            JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
            datePanel.setOpaque(false);
            JLabel dateLabel = new JLabel();
            dateLabel.setForeground(Color.WHITE);
            dateLabel.setFont(sz16);
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy h:mm a");
            dateLabel.setText(dateFormat.format(new Date()));
            datePanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 0, 10));
            datePanel.add(dateLabel);
            orderSummary.add(datePanel);

            // Order list panel
            orderList = new JPanel();
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
            orderSummary.add(orderListScroll);

            subtotalPanel = new subTotalPanel();



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

            cancelBtn.addActionListener(evt -> cancelButton());
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
                    productItemPanel.add(createProductItem(product.getName(), product.getSize(), product.getImage(), sz16, product.getPrice()));
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
                productItemPanel.add(createProductItem(p.getName(), p.getSize(), p.getImage(), sz16, p.getPrice()));
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
            productItemCont.setPreferredSize(new Dimension(100, 260));
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

            JLabel itemSize = new JLabel(productSize);
            itemSize.setFont(font);
            itemSize.setForeground(Color.gray);
            itemSize.setAlignmentX(Component.CENTER_ALIGNMENT);

            textPanel.add(productName);
            textPanel.add(itemSize);
            productItem.add(textPanel, BorderLayout.CENTER);

            JPanel bottomPanel = new JPanel();
            bottomPanel.setLayout(new BorderLayout());
            bottomPanel.setBackground(Color.decode("#111010"));

            JLabel productPrice = new JLabel();
            productPrice.setText("₱" + String.format("%.2f", price));
            productPrice.setFont(font);
            productPrice.setForeground(Color.decode("#686AF5"));
            bottomPanel.add(productPrice, BorderLayout.WEST);

            JButton addProduct = new JButton("+");
            addProduct.setForeground(Color.BLACK);
            addProduct.setBackground(Color.decode("#686AF5"));
            addProduct.setBorderPainted(false);
            addProduct.setFocusPainted(false);
            addProduct.setPreferredSize(new Dimension(42, 42));
            addProduct.setFont(font.deriveFont(Font.PLAIN, 14f));
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
                    orderLabel.setFont(sz16);
                    orderLabel.setForeground(Color.WHITE);

                    JButton incr = new JButton("Add");
                    incr.setFont(sz16);
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
                    decr.setFont(sz16);
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
                if (subtotalPanel != null)
                    subtotalPanel.updateSubtotal(price);
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
                    if (subtotalPanel != null)
                        subtotalPanel.updateSubtotal(-price);
                } else if (quantity[0] == 1) {
                    orderItemPanel.setVisible(false);
                }
            });

            orderItemPanel.add(orderItemLabel);
            orderItemPanel.add(incr);
            orderItemPanel.add(decr);

            return orderItemPanel;
        }

        class subTotalPanel extends JPanel {
            private double subtotal = 0.0;
            private double tax = 0.0;
            private double total = 0.0;
            private JLabel subtotalLabel;
            private JLabel taxLabel;
            private JLabel totalLabel;

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

                subtotalLabel = new JLabel("₱" + String.format("%.2f", subtotal));
                subtotalLabel.setFont(sz15);
                subtotalLabel.setForeground(Color.WHITE);
                subTotal.add(subtotalLabel);

                taxLabel = new JLabel("Total Tax: ₱" + String.format("%.2f", tax));
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
    }

    class Product {
        private final String name;
        private final String size;
        private final String image;
        private final double price;
        private final String category;

        public Product(String name, String size, String image, double price, String category) {
            this.name = name;
            this.size = size;
            this.image = image;
            this.price = price;
            this.category = category;
        }

        public String getName() {
            return name;
        }
        public String getSize() {
            return size;
        }
        public String getImage() {
            return image;
        }
        public double getPrice() {
            return price;
        }
        public String getCategory() {
            return category;
        }
    }
