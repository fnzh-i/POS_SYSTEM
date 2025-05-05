package POS_SYSTEM;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class AddItem extends JFrame {
    private JButton createNavButton(String text, Font font, boolean isSelected) {
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
        return navButton;
    }
    public AddItem() {
        setTitle("PCU POS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1920,1080);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        setResizable(false);
        ImageIcon systemIcon = new ImageIcon("C:/Users/sammy/IdeaProjects/wowsabawww/Logo (1).jpg");
        setIconImage(systemIcon.getImage());

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(13,52, 110));
        sidebar.setPreferredSize(new Dimension(250, getHeight()));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20,20,30,20));
        sidebar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        // pcu logo before canteen POS kinemerutzikels
        ImageIcon systemLogo = new ImageIcon("C:/Users/sammy/IdeaProjects/wowsabawww/Logo (1).jpg");
        JLabel pcuLogo = new JLabel();
        Image originalImage = systemLogo.getImage();

        int imgWidth = 70;
        int imgHeight = 70;
        Image ScaledImg = originalImage.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledLogo = new ImageIcon(ScaledImg);

        pcuLogo.setIcon(scaledLogo);
        pcuLogo.setAlignmentY(Component.CENTER_ALIGNMENT);
        sidebar.add(pcuLogo);

        // Sidebar buttons (NavOptions kay puno)
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(Color.decode("#03346E"));

        Font navFont = new Font("Roboto", Font.PLAIN, 16);
        String[] navItems = {"Order Item", "Order History", "Dashboard", "Menu Management", "Inventory"};

        for (String item : navItems) {
            boolean isSelected = item.equals("Menu Management");
            sidebar.add(Box.createVerticalStrut(10));
            sidebar.add(createNavButton(item, navFont, isSelected));
        }

        sidebar.add(Box.createVerticalGlue());

// Logout button (navOptionsPanel)
        JButton logoutBtn = createNavButton("Log Out", navFont, false);
        logoutBtn.setBackground(new Color(3, 52, 110));
        logoutBtn.setForeground(Color.WHITE);
        sidebar.add(logoutBtn);
        sidebar.add(Box.createVerticalStrut(20));

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

        JTextField nameField = new JTextField();
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

        JTextField categoryField = new JTextField();
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

        JTextField priceField = new JTextField();
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
        JTextArea descArea = new JTextArea();
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
        add(sidebar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            new AddItem().setVisible(true);
//        });
//    }
} 