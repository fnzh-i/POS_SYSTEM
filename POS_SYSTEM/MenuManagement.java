package POS_SYSTEM;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MenuManagement extends JFrame {
    //Styled Sidebar bttns
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

     /*   btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFocusPainted(false);
        btn.setFont(font);
        btn.setBackground(isSelected ? new Color(3,52,110) : new Color(3, 52, 110)); //invisible box
        btn.setForeground(isSelected ? Color.WHITE : new Color(180, 200, 220)); //for font pag hindi siya selected
        btn.setBorder(BorderFactory.createEmptyBorder(20,40,20,0));
       */
        return navButton;
    }

    public MenuManagement() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1920,1080);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        setResizable(false);

        // Set application icon and title
        ImageIcon systemIcon = new ImageIcon("C:/Users/sammy/IdeaProjects/wowsabawww/Logo (1).jpg");
        setIconImage(systemIcon.getImage());
        setTitle("PCU-POS");

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

        // PCU CANTEEN POS
       JLabel navTitle = new JLabel("PCU CANTEEN POS", SwingConstants.CENTER);
        navTitle.setForeground(Color.WHITE);
        navTitle.setFont(new Font("Roboto", Font.BOLD, 18));
        navTitle.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        navTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(navTitle);

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
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.decode("#021526"));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 60, 0, 0));

/*        // Top bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(137, 139, 143));
        topBar.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel searchPanel = new JTextField("Search menu.....");
        searchPanel.setPreferredSize(new Dimension(500, 13));
        searchPanel.setFont(new Font("Roboto", Font.PLAIN, 16));
        searchPanel.setMaximumSize(new Dimension(550, 43));
        searchPanel.setLayout(new BorderLayout());
        searchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        topBar.add(searchPanel, BorderLayout.WEST);
        JLabel dateLabel = new JLabel(new SimpleDateFormat("EEEE, MMMM dd, yyyy hh:mm a").format(new Date()));
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setFont(new Font("Roboto", Font.PLAIN, 16));
        topBar.add(dateLabel, BorderLayout.EAST);
        mainPanel.add(topBar, BorderLayout.NORTH);

        JButton searchButton = new JButton();
        searchButton.setText("Search");
        searchButton.setFont(new Font("Roboto", Font.BOLD, 18));
        searchButton.setPreferredSize(new Dimension(100, 43));
        searchButton.setMaximumSize(new Dimension(100, 43));
        searchPanel.add(searchButton, BorderLayout.EAST);
*/

//SEARCH BAR and DATE LABEL (FIXED):

        JPanel topbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        topbar.setOpaque(false);
        topbar.setPreferredSize(new Dimension(1200, 50));
        topbar.setMaximumSize(new Dimension(1200, 50));




        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setMaximumSize(new Dimension(550, 43));
        searchPanel.setOpaque(false);
        searchPanel.setLayout(new BorderLayout());
        searchPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0,10,0,100));



        JTextField searchBar = new JTextField();
        searchBar.setBackground(Color.decode("#898b8f"));
        searchBar.setFont(new Font("Roboto", Font.PLAIN, 16));
        searchBar.setPreferredSize(new Dimension(450, 43));
        searchBar.setMaximumSize(new Dimension(450, 43));
        searchPanel.add(searchBar, BorderLayout.CENTER);


        JButton searchButton = new JButton();
        searchButton.setText("Search");
        searchButton.setFont(new Font("Roboto", Font.PLAIN, 16));
        searchButton.setPreferredSize(new Dimension(100, 43));
        searchButton.setMaximumSize(new Dimension(100, 43));
        searchPanel.add(searchButton, BorderLayout.EAST);


        //date and time nyaurr

        JLabel dateLabel = new JLabel(new SimpleDateFormat("EEEE, MMMM dd, yyyy hh:mm a").format(new Date()));
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setFont(new Font("Roboto", Font.PLAIN, 16));


        topbar.add(searchPanel);
        topbar.add(dateLabel);
        mainPanel.add(topbar);


        //DATE AND TIME:
      /*  JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        datePanel.setOpaque(false);
        JLabel datelabel = new JLabel();
        datelabel.setForeground(Color.WHITE);
        datelabel.setFont(new Font("Roboto", Font.PLAIN, 14));
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy h:mm a");
        Date date = new Date();
        String formattedDate = dateFormat.format(date);
        datelabel.setText(formattedDate);
        datePanel.add(datelabel);
        datePanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 0, 10));
        datePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(datePanel);
*/

        // Category (all, meals, drinks kineme)
        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        categoryPanel.setBackground(new Color(20, 28, 38));
        String[][] categories = {
                {"All", "12 Items"},
                {"Meals", "4 Items"},
                {"Snacks", "4 Items"},
                {"Drinks", "4 Items"}
        };
        for (String[] cat : categories) {
            JPanel card = new JPanel();
            card.setPreferredSize(new Dimension(205, 100));
            card.setBackground(new Color(48, 41, 57));
            card.setLayout(new BorderLayout());
            JLabel title = new JLabel(cat[0], SwingConstants.LEFT);
            title.setForeground(Color.WHITE);
            title.setFont(new Font("Roboto", Font.BOLD, 18));
            JLabel count = new JLabel(cat[1], SwingConstants.LEFT);
            count.setForeground(new Color(180, 200, 220));
            count.setFont(new Font("Roboto", Font.PLAIN, 14));
            card.add(title, BorderLayout.NORTH);
            card.add(count, BorderLayout.SOUTH);
            card.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 0));
            categoryPanel.add(card);
        }
        // Add Item floating button
        JButton addItemBtn = new JButton("+ Add Item");
        addItemBtn.setBackground(new Color(48, 41, 57));
        addItemBtn.setForeground(Color.WHITE);
        addItemBtn.setFont(new Font("Roboto", Font.BOLD, 16));
        addItemBtn.setFocusPainted(false);
        addItemBtn.setPreferredSize(new Dimension(130, 50));
        categoryPanel.add(Box.createHorizontalStrut(40));
        categoryPanel.add(addItemBtn);
        mainPanel.add(categoryPanel, BorderLayout.CENTER);

        // Menu items grid
        JPanel gridPanel = new JPanel(new GridLayout(3, 4, 20, 20));
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
            JPanel card = new JPanel();
            card.setBackground(new Color(48, 41, 57));
            card.setLayout(new BorderLayout());
            card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            JLabel name = new JLabel(item[0]);
            name.setForeground(Color.WHITE);
            name.setFont(new Font("Roboto", Font.BOLD, 16));
            JLabel price = new JLabel(item[1]);
            price.setForeground(new Color(180, 200, 220));
            price.setFont(new Font("Roboto", Font.PLAIN, 14));
            JPanel infoPanel = new JPanel(new BorderLayout());
            infoPanel.setOpaque(false);
            infoPanel.add(name, BorderLayout.NORTH);
            infoPanel.add(price, BorderLayout.SOUTH);
            card.add(infoPanel, BorderLayout.CENTER);
            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
            btnPanel.setOpaque(false);
            JButton editBtn = new JButton("Edit");
            editBtn.setBackground(new Color(255, 193, 7));
            editBtn.setForeground(Color.BLACK);
            JButton addBtn = new JButton("Add");
            addBtn.setBackground(new Color(255, 193, 7));
            addBtn.setForeground(Color.BLACK);
            JButton delBtn = new JButton("Delete");
            delBtn.setBackground(new Color(220, 53, 69));
            delBtn.setForeground(Color.WHITE);
            btnPanel.add(editBtn);
            btnPanel.add(addBtn);
            btnPanel.add(delBtn);
            card.add(btnPanel, BorderLayout.SOUTH);
            gridPanel.add(card);
        }
        JScrollPane gridScroll = new JScrollPane(gridPanel);
        gridScroll.setBorder(null);
        gridScroll.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(gridScroll, BorderLayout.SOUTH);

        // Layout
        add(sidebar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            new MenuManagement().setVisible(true);
//        });
//    }
}