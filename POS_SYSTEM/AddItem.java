package POS_SYSTEM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class AddItem extends JPanel {
    private final User currentUser;
    private Product productToEdit;

    private JTextField nameField;
    private JComboBox<String> categoryComboBox;
    private JTextField priceField;
    private JTextArea descArea;
    private boolean placeholderSetByCode = false;
    private JButton actionButton;
    private JButton addImageButton; // New button for adding images

    // Constructor for adding new items
    public AddItem(User currentUser) {
        this(currentUser, null);
    }

    // Constructor for editing existing items
    public AddItem(User currentUser, Product productToEdit) {
        this.currentUser = currentUser;
        this.productToEdit = productToEdit;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(8, 28, 48));

        // Main content panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(8, 28, 48));
        mainPanel.setLayout(null);

        JLabel title = new JLabel(productToEdit == null ? "Add New Item" : "Edit Item");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Roboto", Font.BOLD, 32));
        title.setBounds(60, 30, 400, 40);
        mainPanel.add(title);

        // Item name
        JLabel nameLabel = new JLabel("Item Name:");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        nameLabel.setBounds(60, 90, 200, 25);
        mainPanel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(60, 120, 600, 35);
        nameField.setFont(new Font("Roboto", Font.PLAIN, 16));
        nameField.setBackground(new Color(8, 28, 48));
        nameField.setForeground(Color.WHITE); // Placeholder style
        nameField.setCaretColor(Color.WHITE);
        nameField.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 220)));
        nameField.setText("Enter item name"); // Show placeholder immediately

        nameField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (nameField.getText().equals("Enter item name")) {
                    nameField.setText("");
                    nameField.setForeground(Color.WHITE);
                }
            }

            public void focusLost(FocusEvent e) {
                if (nameField.getText().isEmpty()) {
                    nameField.setText("Enter item name");
                    nameField.setForeground(Color.WHITE);
                }
            }
        });

        mainPanel.add(nameField);

        // Item Category (now a dropdown)
        JLabel categoryLabel = new JLabel("Item Category: ");
        categoryLabel.setForeground(Color.WHITE);
        categoryLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        categoryLabel.setBounds(60, 170, 200, 25);
        mainPanel.add(categoryLabel);

        String[] categories = {"Meals", "Drinks", "Snacks"};
        categoryComboBox = new JComboBox<>(categories);
        categoryComboBox.setBounds(60, 200, 600, 35);
        categoryComboBox.setFont(new Font("Roboto", Font.PLAIN, 16));
        categoryComboBox.setBackground(new Color(8, 28, 48));
        categoryComboBox.setForeground(Color.WHITE);
        categoryComboBox.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 220)));
        categoryComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBackground(new Color(8, 28, 48));
                setForeground(Color.WHITE);
                return this;
            }
        });
        mainPanel.add(categoryComboBox);

        // Product Price
        JLabel priceLabel = new JLabel("Price: ");
        priceLabel.setForeground(Color.WHITE);
        priceLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        priceLabel.setBounds(60, 250, 200, 25);
        mainPanel.add(priceLabel);

        priceField = new JTextField();
        priceField.setBounds(60, 280, 600, 35);
        priceField.setFont(new Font("Roboto", Font.PLAIN, 16));
        priceField.setBackground(new Color(8, 28, 48));
        priceField.setForeground(Color.WHITE); // Placeholder color
        priceField.setCaretColor(Color.WHITE);
        priceField.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 220)));
        priceField.setText("Enter price"); // Show placeholder initially

        priceField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (priceField.getText().equals("Enter price")) {
                    priceField.setText("");
                    priceField.setForeground(Color.WHITE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (priceField.getText().isEmpty()) {
                    priceField.setText("Enter price");
                    priceField.setForeground(Color.WHITE);
                }
            }
        });

        mainPanel.add(priceField);

        // Product Description (used for size)
        JLabel descLabel = new JLabel("Size/Description: ");
        descLabel.setForeground(Color.WHITE);
        descLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        descLabel.setBounds(60, 330, 200, 25);

        mainPanel.add(descLabel);

        descArea = new JTextArea();
        descArea.setBounds(60, 360, 600, 60);
        descArea.setFont(new Font("Roboto", Font.PLAIN, 16));
        descArea.setBackground(new Color(8, 28, 48));
        descArea.setForeground(Color.WHITE); // Placeholder color
        descArea.setCaretColor(Color.WHITE);
        descArea.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 220)));
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setText("Enter size/description"); // Show placeholder initially

        descArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (descArea.getText().equals("Enter size/description")) {
                    descArea.setText("");
                    descArea.setForeground(Color.WHITE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (descArea.getText().isEmpty()) {
                    descArea.setText("Enter size/description");
                    descArea.setForeground(Color.WHITE);
                }
            }
        });
        mainPanel.add(descArea);

        // Add Image button - NEW BUTTON ADDED HERE
        addImageButton = new RoundedButton("Add Image", 20);
        addImageButton.setFont(new Font("Roboto", Font.BOLD, 18));
        addImageButton.setBackground(new Color(108, 117, 125)); // Gray color
        addImageButton.setForeground(Color.WHITE);
        addImageButton.setFocusPainted(false);
        addImageButton.setBounds(60, 440, 180, 45);
        addImageButton.addActionListener(this::handleImageUpload);
        mainPanel.add(addImageButton);

        // Action button (Add/Update) - MOVED DOWN
        actionButton = new RoundedButton(productToEdit == null ? "+ Add Item" : "Update Item", 20);
        actionButton.setFont(new Font("Roboto", Font.BOLD, 18));
        actionButton.setBackground(new Color(255, 193, 7));
        actionButton.setForeground(Color.BLACK);
        actionButton.setFocusPainted(false);
        actionButton.setBounds(600, 600, 180, 45); // Changed y-position from 440 to 500
        actionButton.addActionListener(this::saveProduct);
        mainPanel.add(actionButton);

        // Cancel button - MOVED DOWN
        RoundedButton cancelButton = new RoundedButton("Cancel",20);
        cancelButton.setFont(new Font("Roboto", Font.BOLD, 18));
        cancelButton.setBackground(new Color(220, 53, 69));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setBounds(800, 600, 180, 45); // Changed y-position from 440 to 500
        cancelButton.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (topFrame instanceof posSystem) {
                ((posSystem) topFrame).switchToPanel(new MenuManagement(currentUser));
            }
        });
        mainPanel.add(cancelButton);

        // If editing, populate fields with product data
        if (productToEdit != null) {
            nameField.setText(productToEdit.getName());
            categoryComboBox.setSelectedItem(productToEdit.getCategory());
            priceField.setText(String.valueOf(productToEdit.getPrice()));
            descArea.setText(productToEdit.getSize());
        }

        // Layout
        add(mainPanel, BorderLayout.CENTER);
    }

    // NEW METHOD: Handle image upload
    private String imagePath = ""; // Add this as a class field to store the image path

    private void handleImageUpload(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Product Image");

        // Filter for image files
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(java.io.File f) {
                String name = f.getName().toLowerCase();
                return f.isDirectory() ||
                        name.endsWith(".jpg") ||
                        name.endsWith(".jpeg") ||
                        name.endsWith(".png") ||
                        name.endsWith(".gif");
            }

            @Override
            public String getDescription() {
                return "Image Files (*.jpg, *.jpeg, *.png, *.gif)";
            }
        });

        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            java.io.File selectedFile = fileChooser.getSelectedFile();

            // Create an images directory if it doesn't exist
            File imagesDir = new File("images");
            if (!imagesDir.exists()) {
                imagesDir.mkdir();
            }

            try {
                // Copy the file to the images directory
                String newFileName = System.currentTimeMillis() + "_" + selectedFile.getName();
                File destination = new File(imagesDir, newFileName);

                Files.copy(selectedFile.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Store the relative path
                imagePath = "images/" + newFileName;

                JOptionPane.showMessageDialog(this,
                        "Image uploaded successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error saving image: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Then modify the saveProduct method to include the image path:
    private void saveProduct(ActionEvent e) {
        try {
            String name = nameField.getText();
            String category = (String) categoryComboBox.getSelectedItem();
            double price = Double.parseDouble(priceField.getText());
            String size = descArea.getText();

            if (name.isEmpty() || size.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (productToEdit == null) {
                // Add new product
                Product newProduct = new Product(name, size, imagePath, price, category, 0);
                ProductDBManager.addProduct(newProduct);
                JOptionPane.showMessageDialog(this, "Product added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Update existing product
                productToEdit.setName(name);
                productToEdit.setSize(size);
                productToEdit.setPrice(price);
                productToEdit.setCategory(category);
                if (!imagePath.isEmpty()) {
                    productToEdit.setImage(imagePath);
                }
                ProductDBManager.updateProduct(productToEdit);
                JOptionPane.showMessageDialog(this, "Product updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }

            // Fire property change to notify listeners
            firePropertyChange("productUpdated", false, true);

            // Return to menu management
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (topFrame instanceof posSystem) {
                ((posSystem) topFrame).switchToPanel(new MenuManagement(currentUser));
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid price", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error saving product: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Getters for the input fields
    public String getItemName() {
        return nameField.getText();
    }

    public String getItemCategory() {
        return (String) categoryComboBox.getSelectedItem();
    }

    public String getItemPrice() {
        return priceField.getText();
    }

    public String getItemDescription() {
        return descArea.getText();
    }

    // Method to clear all fields
    public void clearFields() {
        nameField.setText("Enter item name");
        nameField.setForeground(Color.WHITE);
        descArea.setText("Enter Size/Description");
        descArea.setForeground(Color.WHITE);
        priceField.setText("Enter price");
        priceField.setForeground(Color.WHITE);
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
}