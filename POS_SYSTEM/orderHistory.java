package POS_SYSTEM;


import javax.swing.*;
import javax.swing.plaf.metal.MetalScrollBarUI;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

class orderHistory extends JPanel {
    JTextField searchField;
    private boolean placeholderSetByCode = false;
    JPanel orderListHistory;
    private List<OrderData> allOrders = new ArrayList<>();


    orderHistory() {
        Map<Integer, OrderData> orderMap = orderHistoryDBManager.getOrderHistory();
        allOrders.addAll(orderMap.values());


        Font sz11 = FontUtils.loadFont(11f);
        Font sz12 = FontUtils.loadFont(12f);
        Font sz13 = FontUtils.loadFont(13f);
        Font sz15 = FontUtils.loadFont(15f);
        Font sz16 = FontUtils.loadFont(16f);
        Font sz17 = FontUtils.loadFont(17f);
        Font sz25 = FontUtils.loadFont(25f);

        setPreferredSize(new Dimension(1080, 720));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.decode("#021526"));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.decode("#021526"));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        // Search panel
        RoundedPanel searchPanel = new RoundedPanel(20);
        searchPanel.setLayout(new BorderLayout());
        searchPanel.setBackground(Color.decode("#898b8f"));
        searchPanel.setPreferredSize(new Dimension(450, 50));
        searchPanel.setMaximumSize(new Dimension(450, 50));

        searchField = new JTextField("Search product item...");
        searchField.setBackground(Color.decode("#898b8f"));
        searchField.setPreferredSize(new Dimension(350, 50));
        searchField.setMaximumSize(new Dimension(350, 50));
        searchField.setFont(sz16);
        searchField.setOpaque(false);
        searchField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
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
                    searchField.setForeground(Color.WHITE);
                    clearFields();
                }
            }
        });

        searchPanel.add(searchField, BorderLayout.CENTER);

        RoundedButton searchBtn = new RoundedButton("Search",20);
        searchBtn.setFont(sz16);
        searchBtn.setPreferredSize(new Dimension(100, 50));
        searchBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchBtn.setBackground(Color.darkGray);
        searchBtn.setForeground(Color.white);
        searchPanel.add(searchBtn, BorderLayout.EAST);

        searchBtn.addMouseListener(new MouseAdapter() {
            private final Color originalColor = searchBtn.getBackground();
            private final Color hoverColor = Color.GRAY;

            @Override
            public void mouseEntered(MouseEvent e) {
                searchBtn.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                searchBtn.setBackground(originalColor);
            }
        });

        searchBtn.addActionListener(e -> {
            String query = searchField.getText().trim().toLowerCase();


            String numericQuery = query.replaceAll("[^0-9]", ""); // keeps only digits

            orderListHistory.removeAll();

            for (OrderData order : allOrders) {
                boolean matchesOrderId = String.valueOf(order.getOrderId()).contains(numericQuery);
                boolean matchesProduct = order.getProductNames().stream()
                        .anyMatch(name -> name.toLowerCase().contains(query));

                if (matchesOrderId || matchesProduct || query.isEmpty()) {
                    orderListHistory.add(createOHItem(
                            order.getOrderId(),
                            order.getTotal(),
                            order.getProductNames(),
                            order.getDate() // Use getter method
                    ));
                }
            }

            orderListHistory.revalidate();
            orderListHistory.repaint();


        });

        // Date label
        JLabel dateLabel = new JLabel(new SimpleDateFormat("EEEE, MMMM dd, yyyy hh:mm a").format(new Date()));
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setFont(new Font("Roboto", Font.PLAIN, 16));

        // Top bar containing search and date
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setPreferredSize(new Dimension(1080, 60));
        topBar.setMaximumSize(new Dimension(1080, 60));
        topBar.add(searchPanel, BorderLayout.WEST);
        topBar.add(dateLabel, BorderLayout.EAST);
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(topBar);


        JPanel oHTitleContainer = new JPanel(new BorderLayout());
        oHTitleContainer.setPreferredSize(new Dimension(1080, 50));
        oHTitleContainer.setMaximumSize(new Dimension(1080, 50));
        oHTitleContainer.setOpaque(false);

        JLabel oHTitle = new JLabel("Order History");
        oHTitle.setFont(sz25);
        oHTitle.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        oHTitle.setForeground(Color.white);
        oHTitle.setAlignmentX(LEFT_ALIGNMENT);
        oHTitleContainer.add(oHTitle, BorderLayout.WEST);

        add(oHTitleContainer);

        orderListHistory = new JPanel();
        orderListHistory.setLayout(new BoxLayout(orderListHistory, BoxLayout.Y_AXIS));
        orderListHistory.setBackground(Color.decode("#021526"));


        JScrollPane orderListScrollPane = new JScrollPane(orderListHistory);
        orderListScrollPane.setBorder(null);
        orderListScrollPane.setPreferredSize(new Dimension(1000, 400)); // Adjusted dimensions
        orderListScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        orderListScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        orderListScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        orderListScrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        orderListScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        orderListScrollPane.setOpaque(false);


        for (OrderData order : orderMap.values()) {
            JPanel ohItem = createOHItem(order.getOrderId(), order.getTotal(), order.getProductNames(), order.getDate());
            orderListHistory.add(ohItem);
        }


        add(orderListScrollPane);
    }

    public JPanel createOHItem(int orderId, double totalAmount, List<String> productList, String date) {
        Font sz15 = FontUtils.loadFont(15f);
        Font sz16 = FontUtils.loadFont(16f);
        Font sz17 = FontUtils.loadFont(17f);
        Font sz20 = FontUtils.loadFont(20f);
        Font sz25 = FontUtils.loadFont(25f);

        JPanel createOHItemPanel = new JPanel();
        createOHItemPanel.setOpaque(false);
        createOHItemPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 0));
        createOHItemPanel.setPreferredSize(new Dimension(1000, 100));
        createOHItemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JLabel dateLabel = new JLabel(date);
        dateLabel.setFont(sz20);
        dateLabel.setForeground(Color.white);
        createOHItemPanel.add(dateLabel);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        JLabel orderIdLabel = new JLabel("Order Number " + orderId);
        orderIdLabel.setForeground(Color.white);
        orderIdLabel.setFont(sz20);
        centerPanel.add(orderIdLabel);

        JPanel productListPanel = new JPanel();
        productListPanel.setLayout(new BoxLayout(productListPanel, BoxLayout.Y_AXIS));
        productListPanel.setOpaque(false);

        for (String product : productList) {
            JLabel productLabel = new JLabel(product);
            productLabel.setForeground(Color.white);
            productLabel.setFont(sz17);
            productListPanel.add(productLabel);
        }

        centerPanel.add(productListPanel);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        createOHItemPanel.add(centerPanel);

        JLabel total = new JLabel("Total: " + String.format("%.2f", totalAmount));
        total.setForeground(Color.white);
        total.setFont(sz25);
        createOHItemPanel.add(total);

        // Dynamically adjust panel height based on number of products
        int baseHeight = 100;
        int productHeight = productList.size() * 20;
        createOHItemPanel.setPreferredSize(new Dimension(1000, Math.max(baseHeight, productHeight + 60)));

        return createOHItemPanel;
    }

    public void clearFields() {
        searchField.setText("Search product item...");
        searchField.setForeground(Color.WHITE);
        searchField.transferFocus();
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
