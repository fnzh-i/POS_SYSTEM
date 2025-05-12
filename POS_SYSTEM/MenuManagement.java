package POS_SYSTEM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MenuManagement extends JPanel {
    private final User currentUser;

    Font sz14 = FontUtils.loadFont(14f);
    Font sz16 = FontUtils.loadFont(16f);
    Font sz18 = FontUtils.loadFont(18f);

    public MenuManagement(User currentUser) {
        this.currentUser = currentUser;
        setLayout(new BorderLayout());
        setBackground(Color.decode("#021526"));
        setPreferredSize(new Dimension(1280, 720));
        initializeUI();
    }

    private void initializeUI() {
        // Main panel with sidebar and content
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.decode("#021526"));

        // Create content panel
        JPanel contentPanel = createContentPanel();
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JButton createNavButton(String text, Font font, boolean isSelected) {
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
        return navButton;
    }

    private JPanel createContentPanel() {
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
        topBar.add(searchPanel, BorderLayout.WEST);
        topBar.add(dateLabel, BorderLayout.EAST);
        contentPanel.add(topBar);
        contentPanel.add(Box.createVerticalStrut(20));

        // Categories
        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Reduced horizontal gap from 15 to 10
        categoryPanel.setBackground(new Color(20, 28, 38));

        String[][] categories = {
                {"All", "12 Items"},
                {"Meals", "4 Items"},
                {"Snacks", "4 Items"},
                {"Drinks", "4 Items"}
        };

        for (String[] cat : categories) {
            JPanel card = new JPanel();
            card.setPreferredSize(new Dimension(140, 60)); // Reduced from 180x80 to 140x60
            card.setBackground(new Color(48, 41, 57));
            card.setLayout(new BorderLayout());

            JLabel title = new JLabel(cat[0], SwingConstants.LEFT);
            title.setForeground(Color.WHITE);
            title.setFont(new Font("Roboto", Font.BOLD, 14)); // Reduced from 16 to 14

            JLabel count = new JLabel(cat[1], SwingConstants.LEFT);
            count.setForeground(new Color(180, 200, 220));
            count.setFont(new Font("Roboto", Font.PLAIN, 12)); // Reduced from 14 to 12

            card.add(title, BorderLayout.NORTH);
            card.add(count, BorderLayout.SOUTH);
            card.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 0)); // Reduced padding
            categoryPanel.add(card);
        }

        // Add Item button with working functionality
        JButton addItemBtn = new JButton("+ Add Item");
        addItemBtn.setBackground(new Color(48, 41, 57));
        addItemBtn.setForeground(Color.WHITE);
        addItemBtn.setFont(new Font("Roboto", Font.BOLD, 14));
        addItemBtn.setFocusPainted(false);
        addItemBtn.setPreferredSize(new Dimension(120, 40));

        // Add action listener for the button
        addItemBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Remove current content and add AddItem panel
                removeAll();
                AddItem addItemPanel = new AddItem();
                add(addItemPanel, BorderLayout.CENTER);

                // Create back button to return to menu management
                JButton backButton = new JButton("Back to Menu");
                backButton.addActionListener(ev -> {
                    removeAll();
                    initializeUI();
                    revalidate();
                    repaint();
                });

                add(backButton, BorderLayout.NORTH);
                revalidate();
                repaint();
            }
        });

        categoryPanel.add(Box.createHorizontalStrut(10)); // Reduced from 20 to 10
        categoryPanel.add(addItemBtn);

        contentPanel.add(categoryPanel);
        contentPanel.add(Box.createVerticalStrut(20));

        // Menu items grid (3x4 layout)
        JPanel gridPanel = new JPanel(new GridLayout(0, 3, 15, 15));
        gridPanel.setBackground(new Color(20, 28, 38));

        String[][] items = {
                {"Jack 'n Jill Piattos Verde", "₱ 16.00"},
                {"Jack 'n Jill Piattos Pula", "₱ 16.00"},
                {"Vcute Pula", "₱ 16.00"},
                {"Vcute Asul", "₱ 16.00"},
                {"Tapsilog", "₱ 16.00"},
                {"Bangsilog", "₱ 16.00"},
                {"Hamsilog", "₱ 16.00"},
                {"Hotsilog", "₱ 16.00"},
                {"Mt. Dew", "₱ 16.00"},
                {"Coke", "₱ 16.00"},
                {"La Mineral", "₱ 16.00"},
                {"Nawasa", "₱ 16.00"}
        };

        for (String[] item : items) {
            JPanel card = createItemCard(item[0], item[1]);
            gridPanel.add(card);
        }

        JScrollPane gridScroll = new JScrollPane(gridPanel);
        gridScroll.setBorder(null);
        gridScroll.getVerticalScrollBar().setUnitIncrement(16);
        contentPanel.add(gridScroll);

        return contentPanel;
    }

    private JPanel createItemCard(String name, String price) {
        JPanel card = new JPanel();
        card.setBackground(new Color(48, 41, 57));
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        card.setPreferredSize(new Dimension(200, 180));

        JLabel nameLabel = new JLabel(name);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Roboto", Font.BOLD, 14));

        JLabel priceLabel = new JLabel(price);
        priceLabel.setForeground(new Color(180, 200, 220));
        priceLabel.setFont(new Font("Roboto", Font.PLAIN, 14));

        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setOpaque(false);
        infoPanel.add(nameLabel, BorderLayout.NORTH);
        infoPanel.add(priceLabel, BorderLayout.SOUTH);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnPanel.setOpaque(false);

        JButton editBtn = new JButton("Edit");
        editBtn.setBackground(new Color(255, 193, 7));
        editBtn.setForeground(Color.BLACK);
        editBtn.setFont(sz14);

        JButton addBtn = new JButton("Add");
        addBtn.setBackground(new Color(255, 193, 7));
        addBtn.setForeground(Color.BLACK);
        addBtn.setFont(sz14);

        JButton delBtn = new JButton("Delete");
        delBtn.setBackground(new Color(220, 53, 69));
        delBtn.setForeground(Color.WHITE);
        delBtn.setFont(sz14);

        btnPanel.add(editBtn);
        btnPanel.add(addBtn);
        btnPanel.add(delBtn);

        card.add(infoPanel, BorderLayout.CENTER);
        card.add(btnPanel, BorderLayout.SOUTH);

        return card;
    }
}