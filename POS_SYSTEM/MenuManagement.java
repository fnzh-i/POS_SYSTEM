package POS_SYSTEM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;

public class MenuManagement extends JPanel {
    private final User currentUser;
    private JPanel gridPanel;
    private final posSystem posFrame;

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
        setBackground(Color.decode("#021526"));
        setPreferredSize(new Dimension(1280, 720));
        initializeUI();
    }

    private void initializeUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.decode("#021526"));

        JPanel contentPanel = createContentPanel();
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.decode("#021526"));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Search panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(Color.decode("#898b8f"));
        searchPanel.setPreferredSize(new Dimension(500, 40));
        searchPanel.setMaximumSize(new Dimension(500, 40));

        JTextField searchField = new JTextField();
        searchField.setBackground(Color.decode("#898b8f"));
        searchField.setFont(sz16);
        searchField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        searchField.setPreferredSize(new Dimension(400, 40));
        searchField.setMaximumSize(new Dimension(400, 40));
        searchPanel.add(searchField, BorderLayout.CENTER);

        JButton searchBtn = new JButton("Search");
        searchBtn.setFont(sz16);
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
        categoryPanel.setBackground(new Color(20, 28, 38));

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
            JPanel card = new JPanel();
            card.setPreferredSize(new Dimension(140, 60));
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
        JButton addItemBtn = new JButton("+ Add Item");
        addItemBtn.setBackground(new Color(48, 41, 57));
        addItemBtn.setForeground(Color.WHITE);
        addItemBtn.setFont(new Font("Roboto", Font.BOLD, 14));
        addItemBtn.setFocusPainted(false);
        addItemBtn.setPreferredSize(new Dimension(120, 40));
        addItemBtn.addActionListener(e -> showAddItemPanel());

        categoryPanel.add(Box.createHorizontalStrut(10));
        categoryPanel.add(addItemBtn);

        contentPanel.add(categoryPanel);
        contentPanel.add(Box.createVerticalStrut(20));

        // Menu items grid - using the stored reference
        gridPanel = new JPanel(new GridLayout(0, 3, 15, 15));
        gridPanel.setBackground(new Color(20, 28, 38));

        for (Product product : allProducts) {
            JPanel card = createItemCard(product);
            gridPanel.add(card);
        }

        JScrollPane gridScroll = new JScrollPane(gridPanel);
        gridScroll.setBorder(null);
        gridScroll.getVerticalScrollBar().setUnitIncrement(16);
        contentPanel.add(gridScroll);

        return contentPanel;
    }

    private void updateDateLabel(JLabel dateLabel, SimpleDateFormat dateFormat) {
        Date now = new Date();
        String formattedDate = dateFormat.format(now);
        dateLabel.setText(formattedDate);
    }

    private JPanel createItemCard(Product product) {
        JPanel card = new JPanel();
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
                    Image scaledImage = originalImage.getScaledInstance(170, 120, Image.SCALE_SMOOTH);
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

        JButton editBtn = new JButton("Edit");
        editBtn.setBackground(new Color(255, 193, 7));
        editBtn.setForeground(Color.BLACK);
        editBtn.setFont(sz14);
        editBtn.setPreferredSize(new Dimension(70, 25));
        editBtn.addActionListener(e -> editProduct(product));

        JButton delBtn = new JButton("Delete");
        delBtn.setBackground(new Color(220, 53, 69));
        delBtn.setForeground(Color.WHITE);
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
}