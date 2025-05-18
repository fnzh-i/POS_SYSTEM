package POS_SYSTEM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

public class processOrderPanel extends JPanel {
    // Payment processing fields
    private double discountTotal = 0.0;
    private double total = 0.0;
    private JTextField cTenderedTField;
    private JLabel changeLabel2;

    // Receipt fields
    private int itemId;
    private String itemName;
    private double itemPrice;
    private int itemQuantity;
    private double Total;
    private double tax;
    private double finalAmount;
    private JPanel receiptItemSummary;
    private JLabel cashTenderedLabel;
    private JLabel changeLabel;
    private JPanel vatReceipt;
    private Map<String, Integer> productQuantityMap = new LinkedHashMap<>();
    private String orderId = "ORD-" + generateOrderId();
    private String cashierName = "Admin"; // You can set this dynamically

    // Ordered products
    private List<Product> orderedProducts;

    public processOrderPanel(List<Product> orderedProducts) {
        this.orderedProducts = orderedProducts;
        Font sz15 = FontUtils.loadFont(15f);
        Font sz18 = FontUtils.loadFont(18f);
        setLayout(new BorderLayout());


        JPanel processOrder = new JPanel();
        processOrder.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));
        processOrder.setPreferredSize(new Dimension(1280, 720));
        processOrder.setMaximumSize(new Dimension(1280, 720));
        processOrder.setBackground(Color.DARK_GRAY);

        // Order Summary Side
        JPanel orderSummarySide = createOrderSummarySide(orderedProducts, sz15, sz18);
        processOrder.add(orderSummarySide);

        // Receipt Side
        JPanel receiptSide = createReceipt();
        processOrder.add(receiptSide);

        add(processOrder, BorderLayout.CENTER);
    }

    private JPanel createOrderSummarySide(List<Product> orderedProducts, Font sz15, Font sz18) {
        JPanel orderSummarySide = new JPanel();
        orderSummarySide.setLayout(new BoxLayout(orderSummarySide, BoxLayout.Y_AXIS));
        orderSummarySide.setPreferredSize(new Dimension(350, 600));
        orderSummarySide.setMaximumSize(new Dimension(350, 600));
        orderSummarySide.setAlignmentX(Component.CENTER_ALIGNMENT);
        orderSummarySide.setOpaque(false);

        // Order Summary Header
        JPanel osPanel = new JPanel();
        osPanel.setOpaque(false);
        osPanel.setLayout(new BorderLayout());
        osPanel.setPreferredSize(new Dimension(400, 35));
        osPanel.setMaximumSize(new Dimension(400, 35));

        JLabel osLabel = new JLabel("Order Summary");
        osLabel.setForeground(Color.white);
        osLabel.setFont(sz18);
        osPanel.add(osLabel, BorderLayout.WEST);
        orderSummarySide.add(osPanel);

        // Order Items
        RoundedPanel orderProcessSummary = new RoundedPanel(20);
        orderProcessSummary.setLayout(new BoxLayout(orderProcessSummary, BoxLayout.Y_AXIS));
        orderProcessSummary.setBackground(Color.decode("#2A273A"));

        JScrollPane orderSummaryScroll = new JScrollPane(orderProcessSummary);
        orderSummaryScroll.setBorder(null);
        orderSummaryScroll.setPreferredSize(new Dimension(380, 300));
        orderSummaryScroll.setAlignmentX(Component.CENTER_ALIGNMENT);
        orderSummaryScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        orderSummaryScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        orderSummaryScroll.getViewport().setOpaque(false);
        orderSummaryScroll.setOpaque(false);

        // Add order items
        productQuantityMap.clear();
        for (Product product : orderedProducts) {
            String key = product.getName() + " - " + product.getSize();
            productQuantityMap.put(key, productQuantityMap.getOrDefault(key, 0) + 1);
        }

        double subtotal = 0.0;
        for (Map.Entry<String, Integer> entry : productQuantityMap.entrySet()) {
            String productNameSize = entry.getKey();
            int quantity = entry.getValue();

            double price = 0.0;
            for (Product p : orderedProducts) {
                String key = p.getName() + " - " + p.getSize();
                if (key.equals(productNameSize)) {
                    price = p.getPrice();
                    break;
                }
            }
            subtotal += price * quantity;
            JLabel productLabel = new JLabel(quantity + "x | " + productNameSize + ", ₱" + String.format("%.2f", price * quantity));
            productLabel.setForeground(Color.WHITE);
            productLabel.setFont(sz18);
            orderProcessSummary.add(productLabel);
        }

        orderSummarySide.add(orderSummaryScroll);

        // Subtotal Panel
        JPanel subTotalPanel = createSubTotalPanel(subtotal, sz15);
        orderSummarySide.add(subTotalPanel);

        // Process Buttons
        JPanel processButtonPanel = createProcessButtons(sz15);
        orderSummarySide.add(processButtonPanel);

        return orderSummarySide;
    }

    private JPanel createSubTotalPanel(double subtotal, Font sz15) {
        JPanel subTotalPanel = new JPanel();
        subTotalPanel.setLayout(new BoxLayout(subTotalPanel, BoxLayout.Y_AXIS));
        subTotalPanel.setOpaque(false);

        // Subtotal
        JPanel stPanel = new JPanel();
        stPanel.setOpaque(false);
        stPanel.setLayout(new BorderLayout());
        stPanel.setPreferredSize(new Dimension(400, 25));
        stPanel.setMaximumSize(new Dimension(400, 25));

        JLabel subtotalLabel = new JLabel("Sub total: ₱" + String.format("%.2f", subtotal));
        subtotalLabel.setFont(sz15);
        subtotalLabel.setForeground(Color.WHITE);
        stPanel.add(subtotalLabel, BorderLayout.WEST);
        subTotalPanel.add(stPanel);

        // Discount
        JPanel dPanel = new JPanel();
        dPanel.setOpaque(false);
        dPanel.setLayout(new BorderLayout());
        dPanel.setPreferredSize(new Dimension(400, 25));
        dPanel.setMaximumSize(new Dimension(400, 25));
        JLabel discount = new JLabel("Discount: ");
        discount.setFont(sz15);
        discount.setForeground(Color.WHITE);
        dPanel.add(discount, BorderLayout.WEST);
        subTotalPanel.add(dPanel);

        // Total
        total = subtotal - discountTotal;
        if (total < 0) total = 0;

        JPanel tPanel = new JPanel();
        tPanel.setOpaque(false);
        tPanel.setLayout(new BorderLayout());
        tPanel.setPreferredSize(new Dimension(400, 25));
        tPanel.setMaximumSize(new Dimension(400, 25));
        JLabel totalLabel = new JLabel("Total: ₱" + String.format("%.2f", total));
        totalLabel.setFont(sz15);
        totalLabel.setForeground(Color.WHITE);
        tPanel.add(totalLabel, BorderLayout.WEST);
        subTotalPanel.add(tPanel);

        // Cash Tendered
        JPanel cashTenderedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        cashTenderedPanel.setOpaque(false);
        cashTenderedPanel.setMaximumSize(new Dimension(400, 50));

        JLabel cashTendered = new JLabel("Cash Tendered: ");
        cashTendered.setForeground(Color.white);
        cashTendered.setFont(sz15);
        cashTenderedPanel.add(cashTendered);

        cTenderedTField = new JTextField();
        cTenderedTField.setPreferredSize(new Dimension(120, 40));
        cTenderedTField.setOpaque(false);
        cTenderedTField.setBorder(BorderFactory.createLineBorder(Color.white, 1, true));
        cTenderedTField.setFont(sz15);
        cTenderedTField.setForeground(Color.WHITE);
        cTenderedTField.setText("");
        cashTenderedPanel.add(cTenderedTField);

        subTotalPanel.add(cashTenderedPanel);

        // Change
        JPanel changePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        changePanel.setPreferredSize(new Dimension(400, 50));
        changePanel.setMaximumSize(new Dimension(400, 50));
        changePanel.setOpaque(false);

        JLabel changeLabel1 = new JLabel("Change: ");
        changeLabel1.setForeground(Color.white);
        changeLabel1.setFont(sz15);
        changePanel.add(changeLabel1);

        changeLabel2 = new JLabel("₱ 0.00");
        changeLabel2.setOpaque(false);
        changeLabel2.setFont(sz15);
        changeLabel2.setForeground(Color.WHITE);
        changePanel.add(changeLabel2);

        subTotalPanel.add(changePanel);

        return subTotalPanel;
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
            g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcs.width, arcs.height);
            g2d.setColor(getForeground());
            g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcs.width, arcs.height);
            g2d.dispose();
        }
    }

    private JPanel createProcessButtons(Font sz15) {
        JPanel processButtonPanel = new JPanel();
        processButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        processButtonPanel.setPreferredSize(new Dimension(400, getHeight()));
        processButtonPanel.setOpaque(false);
        processButtonPanel.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));

        // Cancel Button
        RoundedButton cancelBtn = new RoundedButton("Cancel",20);
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setBackground(Color.decode("#BD1212"));
        cancelBtn.setPreferredSize(new Dimension(120, 40));
        cancelBtn.setBorderPainted(false);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setFont(sz15);
        cancelBtn.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (topFrame instanceof posSystem posFrame) {
                posFrame.switchToPanel(posFrame.getOrderItemPanel());
            }
        });

        // Process Button
        RoundedButton processBtn = new RoundedButton("Confirm Payment",20);
        processBtn.setForeground(Color.WHITE);
        processBtn.setPreferredSize(new Dimension(160, 40));
        processBtn.setBackground(Color.decode("#F9A61A"));
        processBtn.setBorderPainted(false);
        processBtn.setFocusPainted(false);
        processBtn.setFont(sz15);
        processBtn.addActionListener(e -> processPayment());

        processButtonPanel.add(cancelBtn);
        processButtonPanel.add(processBtn);

        return processButtonPanel;
    }

    private void processPayment() {
        String cashInput = cTenderedTField.getText().trim().replace("₱", "").trim();
        try {
            double cashTenderedAmount = Double.parseDouble(cashInput);
            if (cashTenderedAmount < total) {
                JOptionPane.showMessageDialog(this,
                        "Insufficient cash tendered. Please enter an amount equal or greater than the total.",
                        "Payment Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            double change = cashTenderedAmount - total;
            changeLabel2.setText("₱ " + String.format("%.2f", change));

            // Update receipt labels
            cashTenderedLabel.setText("Cash Tendered: ₱" + String.format("%.2f", cashTenderedAmount));
            changeLabel.setText("Change: ₱" + String.format("%.2f", change));

            // Save order to history
            saveOrderToHistory();

            JOptionPane.showMessageDialog(this,
                    "Payment successful!\nChange: ₱ " + String.format("%.2f", change),
                    "Payment Confirmed",
                    JOptionPane.INFORMATION_MESSAGE);

            // After payment, clear order and switch back to orderItemPanel
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (topFrame instanceof posSystem) {
                posSystem posFrame = (posSystem) topFrame;
                posFrame.getOrderItemPanel().clearOrder();
                posFrame.switchToPanel(posFrame.getOrderItemPanel());
                revalidate();
                repaint();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid numeric amount for cash tendered.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private int generateDatabaseOrderId() {
        // same timestamp but return just the numeric portion
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestampStr = dateFormat.format(new Date());

        // take the last 9 digits to ensure it fits in an int
        if (timestampStr.length() > 9) {
            timestampStr = timestampStr.substring(timestampStr.length() - 9);
        }
        return Integer.parseInt(timestampStr);
    }


    private String generateOrderId() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date());
    }

    private void saveOrderToHistory() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy hh:mm a");
        String formattedDate = dateFormat.format(new Date());

        int dbOrderId = generateDatabaseOrderId();

        for (Map.Entry<String, Integer> entry : productQuantityMap.entrySet()) {
            String productNameSize = entry.getKey();
            int quantity = entry.getValue();

            for (Product product : orderedProducts) {
                String key = product.getName() + " - " + product.getSize();
                if (key.equals(productNameSize)) {
                    orderHistoryDBManager.addProductToDatabase(
                            formattedDate,
                            dbOrderId,
                            product.getName(),
                            product.getSize(),
                            product.getPrice(),
                            quantity
                    );
                    break;
                }
            }
        }
    }

    public JPanel createReceipt() {
        Font sz13 = FontUtils.loadFont(13f);
        Font sz14 = FontUtils.loadFont(14f);
        Font sz15 = FontUtils.loadFont(15f);
        Font sz16 = FontUtils.loadFont(16f);
        Font sz17 = FontUtils.loadFont(17f);
        Font sz20 = FontUtils.loadFont(20f);
        Font sz25 = FontUtils.loadFont(25f);
        Font sz30 = FontUtils.loadFont(30f);

        JPanel receiptSide = new JPanel();
        receiptSide.setLayout(new BorderLayout());
        receiptSide.setBackground(Color.WHITE);
        receiptSide.setPreferredSize(new Dimension(500, 800));
        receiptSide.setMaximumSize(new Dimension(500, 800));

        // RECEIPT HEADER:
        JPanel receiptHeader = new JPanel();
        receiptHeader.setLayout(new BoxLayout(receiptHeader, BoxLayout.Y_AXIS));
        receiptHeader.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        receiptHeader.setAlignmentY(Component.CENTER_ALIGNMENT);
        receiptHeader.setPreferredSize(new Dimension(500, 150));

        ImageIcon systemLogo = new ImageIcon("Images/Logo/Philippine_Christian_University_logo.png");
        JLabel pcuLogo = new JLabel();
        Image originalImage = systemLogo.getImage();

        int imgWidth = 50;
        int imgHeight = 50;

        Image scaledImg = originalImage.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledLogo = new ImageIcon(scaledImg);
        pcuLogo.setIcon(scaledLogo);
        pcuLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        receiptHeader.add(pcuLogo);

        JLabel receiptTitle = new JLabel("PCU CANTEEN POS");
        receiptTitle.setForeground(Color.BLACK);
        receiptTitle.setFont(sz25);
        receiptHeader.add(receiptTitle);

        JLabel receiptWebsite = new JLabel("https://www.pcu.edu.ph/");
        receiptWebsite.setFont(sz14);
        receiptWebsite.setForeground(Color.BLACK);
        receiptHeader.add(receiptWebsite);

        JLabel receiptAddress = new JLabel("<html><p>" + "Philippine Christian University, 1648 Taft Avenue corner Pedro Gil St., Malate, Manila" + "</p></html>");
        receiptAddress.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        receiptAddress.setFont(sz14);
        receiptAddress.setForeground(Color.black);
        receiptAddress.setPreferredSize(new Dimension(400, 90));

        receiptHeader.add(receiptAddress);
        receiptSide.add(receiptHeader, BorderLayout.NORTH);

        // MAIN CENTER CONTAINER FOR THE OVERALL RECEIPT DETAILS:
        JPanel receiptDetailContainer = new JPanel();
        receiptDetailContainer.setLayout(new BoxLayout(receiptDetailContainer, BoxLayout.Y_AXIS));
        receiptDetailContainer.setPreferredSize(new Dimension(500, 500));
        receiptDetailContainer.setMaximumSize(new Dimension(500, 500));
        receiptDetailContainer.setBorder(BorderFactory.createLineBorder(Color.black, 1));

        // PRODUCT INFORMATION CONTAINER
        receiptItemSummary = new JPanel();
        receiptItemSummary.setLayout(new BoxLayout(receiptItemSummary, BoxLayout.Y_AXIS));
        receiptItemSummary.setPreferredSize(new Dimension(450, 300));
        receiptItemSummary.setMaximumSize(new Dimension(450, 300));

        JPanel receiptItemTitle = new JPanel(new GridLayout(0, 4));
        receiptItemTitle.setPreferredSize(new Dimension(450, 50));
        receiptItemTitle.setMaximumSize(new Dimension(450, 50));

        // COLUMN TITLES:
        JLabel itemTitle = new JLabel("ITEM");
        itemTitle.setForeground(Color.BLACK);
        itemTitle.setFont(sz17);
        receiptItemTitle.add(itemTitle);

        JLabel priceTitle = new JLabel("PRICE");
        priceTitle.setForeground(Color.BLACK);
        priceTitle.setFont(sz17);
        receiptItemTitle.add(priceTitle);

        JLabel quantityTitle = new JLabel("QTY");
        quantityTitle.setForeground(Color.BLACK);
        quantityTitle.setFont(sz17);
        receiptItemTitle.add(quantityTitle);

        JLabel totalTitle = new JLabel("TOTAL");
        totalTitle.setForeground(Color.BLACK);
        totalTitle.setFont(sz17);
        receiptItemTitle.add(totalTitle);

        receiptItemSummary.add(receiptItemTitle);

        int totalQuantity = 0;
        double totalCost = 0.0;
        double vatRate = 0.12;

        // Add ordered items to receipt
        for (Map.Entry<String, Integer> entry : productQuantityMap.entrySet()) {
            String productNameSize = entry.getKey();
            itemQuantity = entry.getValue();
            itemPrice = 0.0;

            // Find the product to get its price
            for (Product p : orderedProducts) {
                String key = p.getName() + " - " + p.getSize();
                if (key.equals(productNameSize)) {
                    itemPrice = p.getPrice();
                    // Add item details to the receipt
                    receiptItemSummary.add(createItemDetail(p.getItemId(), productNameSize, itemPrice, itemQuantity, setTotal(itemPrice, itemQuantity)));
                    break;
                }
            }

            // Update totals
            totalQuantity += itemQuantity;
            totalCost += itemPrice * itemQuantity;
            tax = totalCost * vatRate;
            finalAmount = totalCost + tax;
        }

        receiptDetailContainer.add(receiptItemSummary);

        // SUBTOTAL SECTION
        JPanel subTotalContainer = new JPanel(new BorderLayout());
        subTotalContainer.setPreferredSize(new Dimension(450, 150));
        subTotalContainer.setMaximumSize(new Dimension(450, 150));
        subTotalContainer.setOpaque(false);

        JPanel receiptSubtotal = new JPanel();
        receiptSubtotal.setLayout(new BoxLayout(receiptSubtotal, BoxLayout.Y_AXIS));
        subTotalContainer.add(receiptSubtotal, BorderLayout.WEST);

        JLabel totalItemsLabel = new JLabel("Total Items: " + totalQuantity);
        totalItemsLabel.setFont(sz13);
        totalItemsLabel.setForeground(Color.black);
        totalItemsLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        receiptSubtotal.add(totalItemsLabel);

        JLabel totalLabel = new JLabel("Subtotal: ₱" + String.format("%.2f", totalCost));
        totalLabel.setFont(sz16);
        totalLabel.setForeground(Color.black);
        totalLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        receiptSubtotal.add(totalLabel);

        cashTenderedLabel = new JLabel("Cash Tendered: ");
        cashTenderedLabel.setFont(sz13);
        cashTenderedLabel.setForeground(Color.black);
        cashTenderedLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        receiptSubtotal.add(cashTenderedLabel);

        changeLabel = new JLabel("Change: ");
        changeLabel.setFont(sz13);
        changeLabel.setForeground(Color.black);
        changeLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        receiptSubtotal.add(changeLabel);

        receiptDetailContainer.add(subTotalContainer);

        // VAT INFORMATION
        JPanel vatContainer = new JPanel(new BorderLayout());
        vatContainer.setOpaque(false);
        vatContainer.setPreferredSize(new Dimension(450, 180));
        vatContainer.setMaximumSize(new Dimension(450, 180));

        vatReceipt = new JPanel();
        vatReceipt.setLayout(new BoxLayout(vatReceipt, BoxLayout.Y_AXIS));
        vatReceipt.setOpaque(false);
        vatContainer.add(vatReceipt, BorderLayout.WEST);

        JLabel vatInformationLabel = new JLabel("VAT INFORMATION:");
        vatInformationLabel.setForeground(Color.BLACK);
        vatInformationLabel.setFont(sz16);
        vatInformationLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        vatReceipt.add(vatInformationLabel);

        JLabel vatSaleLabel = new JLabel("Vatable Sales: ₱" + String.format("%.2f", finalAmount));
        vatSaleLabel.setForeground(Color.BLACK);
        vatSaleLabel.setFont(sz13);
        vatSaleLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        vatReceipt.add(vatSaleLabel);

        JLabel vatExemptSaleLabel = new JLabel("Vat Exempt Sale: ");
        vatExemptSaleLabel.setForeground(Color.BLACK);
        vatExemptSaleLabel.setFont(sz13);
        vatExemptSaleLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        vatReceipt.add(vatExemptSaleLabel);

        JLabel vatZeroRatedLabel = new JLabel("Vat Zero Rated Sale: ");
        vatZeroRatedLabel.setForeground(Color.BLACK);
        vatZeroRatedLabel.setFont(sz13);
        vatZeroRatedLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        vatReceipt.add(vatZeroRatedLabel);

        JLabel vatPercentageLabel = new JLabel("12% Vat: ₱" + String.format("%.2f", tax));
        vatPercentageLabel.setForeground(Color.BLACK);
        vatPercentageLabel.setFont(sz13);
        vatPercentageLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        vatReceipt.add(vatPercentageLabel);

        receiptDetailContainer.add(vatContainer);

        // RECEIPT ADDITIONAL INFO
        JPanel receiptInfoCont = new JPanel(new BorderLayout());
        receiptInfoCont.setOpaque(false);
        receiptInfoCont.setPreferredSize(new Dimension(450, 100));
        receiptInfoCont.setMaximumSize(new Dimension(450, 100));

        JPanel receiptInfo = new JPanel();
        receiptInfo.setLayout(new BoxLayout(receiptInfo, BoxLayout.Y_AXIS));
        receiptInfo.setOpaque(false);
        receiptInfoCont.add(receiptInfo);

        // ORDER ID
        JLabel receiptOrderId = new JLabel("Order Id: " + orderId);
        receiptOrderId.setForeground(Color.BLACK);
        receiptOrderId.setFont(sz13);
        receiptOrderId.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        receiptInfo.add(receiptOrderId);

        // CASHIER NAME
        JLabel cashierNameLabel = new JLabel("Cashier Name: " + cashierName);
        cashierNameLabel.setForeground(Color.BLACK);
        cashierNameLabel.setFont(sz13);
        cashierNameLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        receiptInfo.add(cashierNameLabel);

        // DATE
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
        JLabel receiptDateLabel = new JLabel("Date: " + dateFormat.format(new Date()));
        receiptDateLabel.setForeground(Color.BLACK);
        receiptDateLabel.setFont(sz13);
        receiptDateLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        receiptInfo.add(receiptDateLabel);

        receiptDetailContainer.add(receiptInfoCont);
        receiptSide.add(receiptDetailContainer, BorderLayout.CENTER);

        // RECEIPT FOOTER
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new BorderLayout());
        footerPanel.setPreferredSize(new Dimension(500, 100));
        footerPanel.setMaximumSize(new Dimension(500, 100));
        footerPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));

        JLabel footerText = new JLabel("<html><p>" + "This serves as your SALES INVOICE. Thank you, and Please Come Again. " + "</p></html>");
        footerText.setForeground(Color.black);
        footerText.setFont(sz14);

        footerPanel.add(footerText, BorderLayout.CENTER);
        receiptSide.add(footerPanel, BorderLayout.SOUTH);

        return receiptSide;
    }

    public JPanel createItemDetail(int productId, String productName, double productPrice,
                                   int productQuantity, double priceTotal) {
        Font sz13 = FontUtils.loadFont(13f);
        Font sz15 = FontUtils.loadFont(15f);
        Font sz16 = FontUtils.loadFont(16f);

        JPanel itemDetail = new JPanel();
        itemDetail.setLayout(new GridLayout(0,4));
        itemDetail.setPreferredSize(new Dimension(450,65));
        itemDetail.setMaximumSize(new Dimension(450,65));

        JPanel itemNameId = new JPanel();
        itemNameId.setLayout(new BoxLayout(itemNameId, BoxLayout.Y_AXIS));

        JLabel prdctId = new JLabel("" + productId);
        prdctId.setForeground(Color.black);
        prdctId.setFont(sz13);
        itemNameId.add(prdctId);

        JLabel prdctName = new JLabel("<html><p>" + productName+"</p></html>");
        prdctName.setForeground(Color.black);
        prdctName.setFont(sz15);
        itemNameId.add(prdctName);

        itemDetail.add(itemNameId);

        JLabel prdctPrice = new JLabel("₱" + String.format("%.2f", productPrice));
        prdctPrice.setForeground(Color.black);
        prdctPrice.setFont(sz16);
        itemDetail.add(prdctPrice);

        JLabel prdctQuantity = new JLabel(""+productQuantity);
        prdctQuantity.setForeground(Color.black);
        prdctQuantity.setFont(sz16);
        itemDetail.add(prdctQuantity);

        JLabel prdctTotal = new JLabel("₱"+String.format("%.2f", priceTotal));
        prdctTotal.setForeground(Color.black);
        prdctTotal.setFont(sz16);
        itemDetail.add(prdctTotal);

        return itemDetail;
    }

    // Setters and Getters
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public double setTotal(double itemPrice, int itemQuantity) {
        this.Total = (itemPrice * itemQuantity);
        return this.Total;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public double getTotal() {
        return Total;
    }
}