package POS_SYSTEM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.plaf.metal.MetalScrollBarUI;

public class MenuManagement extends JPanel {
    private final User currentUser;
    private JPanel gridPanel;
    private final posSystem posFrame;
    private JTextField searchField;
    private boolean placeholderSetByCode = false;

    Font sz14 = FontUtils.loadFont(14f);
    Font sz16 = FontUtils.loadFont(16f);
    Font sz18 = FontUtils.loadFont(18f);



    public MenuManagement(User currentUser) {
        this(currentUser, null);
    }

    public MenuManagement(User currentUser, posSystem posFrame) {
        this.currentUser = currentUser;
        this.posFrame = posFrame;
        setLayout(new BorderLayout());
        setBackground(new Color(2, 21, 38));
        setPreferredSize(new Dimension(1280, 720));
        initializeUI();
    }

    private void initializeUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(2, 21, 38));

        JPanel contentPanel = createContentPanel();
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(2,21,38));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Search panel
        RoundedPanel searchPanel = new RoundedPanel(20);
        searchPanel.setLayout(new BorderLayout(0,0));
        searchPanel.setBackground(new Color(137,139,143));
        searchPanel.setPreferredSize(new Dimension(450, 40));
        searchPanel.setMaximumSize(new Dimension(450, 40));

        // Placeholder for search bar
        searchField = new JTextField("Search product item...");
        searchField.setBounds(60,120,600,35);
        searchField.setForeground(Color.darkGray); // Set placeholder color
        searchField.setCaretColor(Color.WHITE);
        searchField.setOpaque(false);

        searchField.setBackground(new Color(137, 139, 143));
        searchField.setFont(sz16);
        searchField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        searchField.setPreferredSize(new Dimension(350, 40));
        searchField.setMaximumSize(new Dimension(350, 40));

        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Search product item...")) {
                    searchField.setText("");
                    searchField.setForeground(new Color(255, 255, 255));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Search product item...");
                    searchField.setForeground(Color.DARK_GRAY);
                    clearFields();
                }
            }
        });

        searchPanel.add(searchField, BorderLayout.CENTER);

        RoundedButton searchBtn = new RoundedButton("Search", 20);
        searchBtn.setFont(sz16);
        searchBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setBackground(Color.DARK_GRAY);
        searchBtn.setPreferredSize(new Dimension(100, 40));
        searchBtn.setMaximumSize(new Dimension(100, 40));

        searchBtn.addActionListener(e -> {
            String searchText = searchField.getText().trim();
            if (!searchText.isEmpty()) {
                searchProducts(searchText);
            } else {
                refreshProductGrid();
            }
        });
        searchPanel.add(searchBtn, BorderLayout.EAST);

        // Date label
        JLabel dateLabel = new JLabel();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy h:mm a");
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setText(dateFormat.format(new Date()));
        dateLabel.setFont(new Font("Roboto", Font.PLAIN, 16));

        updateDateLabel(dateLabel, dateFormat);

        Timer timer = new Timer(1000, e -> updateDateLabel(dateLabel, dateFormat));
        timer.start();

        // Top bar containing search and date
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.add(searchPanel, BorderLayout.WEST);
        topBar.add(dateLabel, BorderLayout.EAST);
        topBar.setPreferredSize(new Dimension(1200,50));
        topBar.setMaximumSize(new Dimension(1200,50));
        contentPanel.add(topBar);
        contentPanel.add(Box.createVerticalStrut(20));

        // Categories
        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        categoryPanel.setBackground(new Color(2, 21, 38));
        categoryPanel.setPreferredSize(new Dimension(1200,150));
        categoryPanel.setMaximumSize(new Dimension(1200,150 ));

        List<Product> allProducts = ProductDBManager.getAllProducts();
        int allCount = allProducts.size();
        int mealsCount = countProductsByCategory(allProducts, "Meals");
        int snacksCount = countProductsByCategory(allProducts, "Snacks");
        int drinksCount = countProductsByCategory(allProducts, "Drinks");

        String[][] categories = {
                {"All", allCount + " Items"},
                {"Meals", mealsCount + " Items"},
                {"Snacks", snacksCount + " Items"},
                {"Drinks", drinksCount + " Items"}
        };

        for (String[] cat : categories) {
            RoundedPanel card = new RoundedPanel(10);
            card.setPreferredSize(new Dimension(150, 90));
            card.setBackground(new Color(48, 41, 57));
            card.setLayout(new BorderLayout());

            JLabel title = new JLabel(cat[0], SwingConstants.LEFT);
            title.setForeground(Color.WHITE);
            title.setFont(new Font("Roboto", Font.BOLD, 14));

            JLabel count = new JLabel(cat[1], SwingConstants.LEFT);
            count.setForeground(new Color(180, 200, 220));
            count.setFont(new Font("Roboto", Font.PLAIN, 12));

            card.add(title, BorderLayout.NORTH);
            card.add(count, BorderLayout.SOUTH);
            card.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 0));
            categoryPanel.add(card);
        }

        // Add Item button
        RoundedButton addItemBtn = new RoundedButton("+ Add Item", 20);
        addItemBtn.setBackground(new Color(255, 193, 7));
        addItemBtn.setForeground(Color.black);
        addItemBtn.setFont(new Font("Roboto", Font.BOLD, 14));
        addItemBtn.setFocusPainted(false);
        addItemBtn.setPreferredSize(new Dimension(130, 50));
        addItemBtn.addActionListener(e -> showAddItemPanel());

        categoryPanel.add(Box.createHorizontalStrut(10));
        categoryPanel.add(addItemBtn);

        contentPanel.add(categoryPanel);
        contentPanel.add(Box.createVerticalStrut(20));

        // Menu items grid - using the stored reference
        gridPanel = new RoundedPanel(20); // Rounded corners
        gridPanel.setLayout(new GridLayout(0, 3, 15, 15));
        gridPanel.setBackground(new Color(2,21,38));
        gridPanel.setOpaque(false);
        gridPanel.setBorder(null);

        for (Product product : allProducts) {
            JPanel card = createItemCard(product);
            gridPanel.add(card);
        }

        JScrollPane gridScroll = new JScrollPane(gridPanel);
        gridScroll.setBorder(null);
        gridScroll.getVerticalScrollBar().setUnitIncrement(16);
        gridScroll.setOpaque(false);
        gridScroll.getViewport().setOpaque(false);
        gridScroll.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        contentPanel.add(gridScroll);

        return contentPanel;

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

    private JPanel createItemCard(Product product) {
        RoundedPanel card = new RoundedPanel(20);
        card.setBackground(new Color(48, 41, 57));
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        card.setPreferredSize(new Dimension(200, 250)); // Increased height to accommodate image

        // Image panel
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setPreferredSize(new Dimension(170, 120));
        imagePanel.setBackground(new Color(60, 60, 60));

        try {
            // Load image if path exists
            if (product.getImage() != null && !product.getImage().isEmpty()) {
                File imageFile = new File(product.getImage());
                if (imageFile.exists()) {
                    BufferedImage originalImage = ImageIO.read(imageFile);

                    // Scale image to fit
                    Image scaledImage = originalImage.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                    ImageIcon imageIcon = new ImageIcon(scaledImage);
                    JLabel imageLabel = new JLabel(imageIcon);
                    imagePanel.add(imageLabel, BorderLayout.CENTER);
                } else {
                    // Show placeholder if image not found
                    JLabel placeholder = new JLabel("No Image", SwingConstants.CENTER);
                    placeholder.setForeground(Color.WHITE);
                    imagePanel.add(placeholder, BorderLayout.CENTER);
                }
            } else {
                // Show placeholder if no image path
                JLabel placeholder = new JLabel("No Image", SwingConstants.CENTER);
                placeholder.setForeground(Color.WHITE);
                imagePanel.add(placeholder, BorderLayout.CENTER);
            }
        } catch (IOException ex) {
            // Show error if image loading fails
            JLabel errorLabel = new JLabel("Image Error", SwingConstants.CENTER);
            errorLabel.setForeground(Color.RED);
            imagePanel.add(errorLabel, BorderLayout.CENTER);
        }

        // Product info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel nameLabel = new JLabel(product.getName());
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Roboto", Font.BOLD, 14));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel categoryLabel = new JLabel(product.getCategory());
        categoryLabel.setForeground(new Color(180, 180, 180));
        categoryLabel.setFont(new Font("Roboto", Font.PLAIN, 12));
        categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel priceLabel = new JLabel("₱ " + String.format("%.2f", product.getPrice()));
        priceLabel.setForeground(new Color(180, 200, 220));
        priceLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(categoryLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(priceLabel);

        // Button panel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnPanel.setOpaque(false);

        RoundedButton editBtn = new RoundedButton("Edit", 10);
//        JButton editBtn = new JButton("Edit");
        editBtn.setBackground(new Color(255, 193, 7));
        editBtn.setForeground(Color.black);
        editBtn.setFont(sz14);
        editBtn.setPreferredSize(new Dimension(70, 25));
        editBtn.addActionListener(e -> editProduct(product));

        RoundedButton delBtn = new RoundedButton("Delete", 10);
        delBtn.setBackground(new Color(220, 53, 69));
        delBtn.setForeground(Color.white);
        delBtn.setFont(sz14);
        delBtn.setPreferredSize(new Dimension(70, 25));
        delBtn.addActionListener(e -> deleteProduct(product));

        btnPanel.add(editBtn);
        btnPanel.add(delBtn);

        // Add all components to card
        card.add(imagePanel, BorderLayout.NORTH);
        card.add(infoPanel, BorderLayout.CENTER);
        card.add(btnPanel, BorderLayout.SOUTH);

        return card;
    }

    private void editProduct(Product product) {
        removeAll();
        AddItem editPanel = new AddItem(currentUser, product);
        editPanel.addPropertyChangeListener("productUpdated", evt -> {
            // When product is updated, refresh the order panel
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (topFrame instanceof posSystem) {
                ((posSystem) topFrame).refreshOrderItemPanel();
            }
        });
        add(editPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void deleteProduct(Product product) {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete " + product.getName() + "?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                ProductDBManager.deleteProduct(product.getItemId());
                JOptionPane.showMessageDialog(this, "Product deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Refresh the order panel after deletion
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                if (topFrame instanceof posSystem) {
                    ((posSystem) topFrame).refreshOrderItemPanel();
                }

                refreshProductGrid();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error deleting product: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void refreshProductGrid() {
        removeAll();
        initializeUI();
        revalidate();
        repaint();
    }

    private void clearFields() {
        searchField.setText("Search product item...");
        searchField.setForeground(Color.WHITE);
        searchField.transferFocus();
    }

    private int countProductsByCategory(List<Product> products, String category) {
        int count = 0;
        for (Product product : products) {
            if (product.getCategory().equalsIgnoreCase(category)) {
                count++;
            }
        }
        return count;
    }

    private void searchProducts(String searchText) {
        // Get all products from the database
        List<Product> allProducts = ProductDBManager.getAllProducts();

        // Clear the current product grid using our stored reference
        gridPanel.removeAll();

        boolean found = false;
        String searchLower = searchText.toLowerCase();

        for (Product product : allProducts) {
            if (product.getName().toLowerCase().contains(searchLower)) {
                found = true;
                gridPanel.add(createItemCard(product));
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(this, "No items found matching: " + searchText);
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private void showAddItemPanel() {
        removeAll();
        AddItem addItemPanel = new AddItem(currentUser);
        add(addItemPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
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
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arcs.width, arcs.height);
            g2d.setColor(getForeground());
            g2d.drawRoundRect(0, 0, getWidth(), getHeight(), arcs.width, arcs.height);
            g2d.dispose();
        }
    }

    static class RoundedButton extends JButton {
        private int radius;

        public RoundedButton(String text, int radius) {
            super(text);
            this.radius = radius;
            setOpaque(false); // Make the button transparent
            setContentAreaFilled(false); // Don't fill the content area
            setBorderPainted(false);    // Don't paint the border
            setForeground(new Color(2, 21, 38));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw a rounded rectangle for the button's background
            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

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
            g2d.setColor(getBackground());
            g2d.drawRoundRect(0, 0, getWidth(),getHeight(), radius, radius);
            g2d.dispose();
        }
    }



}