package POS_SYSTEM;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class AddItem extends JPanel {
    private JTextField nameField;
    private JTextField categoryField;
    private JTextField priceField;
    private JTextArea descArea;

    public AddItem() {
        setLayout(new BorderLayout());
        setBackground(new Color(8, 28, 48));

        // Main content panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(8, 28, 48));
        mainPanel.setLayout(null);

        JLabel title = new JLabel("Add New Item");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Roboto", Font.BOLD, 32));
        title.setBounds(60, 30, 400, 40);
        mainPanel.add(title);

        // item name
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

        //Item Category
        JLabel categoryLabel = new JLabel("Item Category: ");
        categoryLabel.setForeground(Color.WHITE);
        categoryLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        categoryLabel.setBounds(60, 170, 200, 25);
        mainPanel.add(categoryLabel);

        categoryField = new JTextField();
        categoryField.setBounds(60, 200, 600, 35);
        categoryField.setFont(new Font("Roboto", Font.PLAIN, 16));
        categoryField.setBackground(new Color(8, 28, 48));
        categoryField.setForeground(Color.WHITE);
        categoryField.setCaretColor(Color.WHITE);
        categoryField.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 220)));
        mainPanel.add(categoryField);

        // Product Price
        JLabel priceLabel = new JLabel("Price: " );
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

        //Product Description
        JLabel descLabel = new JLabel("Description: ");
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

        // Add Item button
        JButton addItemBtn = new JButton("+ Add Item");
        addItemBtn.setFont(new Font("Roboto", Font.BOLD, 18));
        addItemBtn.setBackground(new Color(255, 193, 7));
        addItemBtn.setForeground(Color.BLACK);
        addItemBtn.setFocusPainted(false);
        addItemBtn.setBounds(60, 440, 180, 45);
        mainPanel.add(addItemBtn);

        // Layout
        add(mainPanel, BorderLayout.CENTER);
    }

    // Getters for the input fields
    public String getItemName() {
        return nameField.getText();
    }

    public String getItemCategory() {
        return categoryField.getText();
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
        categoryField.setText("");
        priceField.setText("");
        descArea.setText("");
    }
}