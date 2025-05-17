package POS_SYSTEM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddItem extends JPanel {
    private final User currentUser;
    private Product productToEdit;

    private JTextField nameField;
    private JComboBox<String> categoryComboBox;
    private JTextField priceField;
    private JTextArea descArea;
    private JButton actionButton;

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
        nameField.setForeground(Color.WHITE);
        nameField.setCaretColor(Color.WHITE);
        nameField.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 220)));
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
        priceField.setForeground(Color.WHITE);
        priceField.setCaretColor(Color.WHITE);
        priceField.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 220)));
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
        descArea.setForeground(Color.WHITE);
        descArea.setCaretColor(Color.WHITE);
        descArea.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 220)));
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        mainPanel.add(descArea);

        // Action button (Add/Update)
        actionButton = new JButton(productToEdit == null ? "+ Add Item" : "Update Item");
        actionButton.setFont(new Font("Roboto", Font.BOLD, 18));
        actionButton.setBackground(new Color(255, 193, 7));
        actionButton.setForeground(Color.BLACK);
        actionButton.setFocusPainted(false);
        actionButton.setBounds(60, 440, 180, 45);
        actionButton.addActionListener(this::saveProduct);
        mainPanel.add(actionButton);

        // Cancel button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Roboto", Font.BOLD, 18));
        cancelButton.setBackground(new Color(220, 53, 69));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setBounds(260, 440, 180, 45);
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
                Product newProduct = new Product(name, size, "", price, category, 0);
                ProductDBManager.addProduct(newProduct);
                JOptionPane.showMessageDialog(this, "Product added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Update existing product
                productToEdit.setName(name);
                productToEdit.setSize(size);
                productToEdit.setPrice(price);
                productToEdit.setCategory(category);
                ProductDBManager.updateProduct(productToEdit);
                JOptionPane.showMessageDialog(this, "Product updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }

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
        nameField.setText("");
        categoryComboBox.setSelectedIndex(0);
        priceField.setText("");
        descArea.setText("");
    }
}