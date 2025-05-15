package POS_SYSTEM;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class processOrderPanel extends JPanel {


    private double discountTotal = 0.0; // You can implement discount logic if needed
    private double total = 0.0;
    private JTextField cTenderedTField;
    private JLabel changeLabel2;
    private int itemId;
    private String itemName;
    private double itemPrice;
    private int itemQuantity;
    private double Total;

    processOrderPanel(List<Product> orderedProducts) {
        Font sz14 = FontUtils.loadFont(14f);
        Font sz16 = FontUtils.loadFont(16f);
        Font sz20 = FontUtils.loadFont(20f);
        Font sz30 = FontUtils.loadFont(30f);
        setLayout(new BorderLayout());



        JPanel processOrder = new JPanel();
        processOrder.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        processOrder.setPreferredSize(new Dimension(1620, 1100));
        processOrder.setMaximumSize(new Dimension(1240, 980));
        processOrder.setBackground(Color.DARK_GRAY);


        JPanel orderSummarySide = new JPanel();
        orderSummarySide.setLayout(new BoxLayout(orderSummarySide, BoxLayout.Y_AXIS));
        orderSummarySide.setPreferredSize(new Dimension(500, 800));
        orderSummarySide.setAlignmentX(Component.CENTER_ALIGNMENT);
        orderSummarySide.setOpaque(false);


        JPanel osPanel = new JPanel();
        osPanel.setOpaque(false);
        osPanel.setLayout(new BorderLayout());
        osPanel.setPreferredSize(new Dimension(500, 40));
        osPanel.setMaximumSize(new Dimension(500, 40));

        JLabel osLabel = new JLabel("Order Summary");
        osLabel.setForeground(Color.white);
        osLabel.setFont(sz20);
        osPanel.add(osLabel, BorderLayout.WEST);
        orderSummarySide.add(osPanel);


        JPanel plusBtnPanel = new JPanel();
        plusBtnPanel.setOpaque(false);
        plusBtnPanel.setLayout(new BorderLayout());
        plusBtnPanel.setPreferredSize(new Dimension(500, 40));
        plusBtnPanel.setMaximumSize(new Dimension(500, 40));

        JButton plusButton = new JButton("+");
        plusButton.setBorderPainted(false);
        plusButton.setFocusPainted(false);
        plusButton.setForeground(Color.WHITE);
        plusButton.setBackground(Color.gray);
        plusBtnPanel.add(plusButton, BorderLayout.EAST);
        orderSummarySide.add(plusBtnPanel);


        JPanel orderProcessSummary = new JPanel();
        orderProcessSummary.setLayout(new BoxLayout(orderProcessSummary, BoxLayout.Y_AXIS));
        orderProcessSummary.setBackground(Color.decode("#2A273A"));

        JScrollPane orderSummaryScroll = new JScrollPane(orderProcessSummary);
        orderSummaryScroll.setBorder(null);
        orderSummaryScroll.setPreferredSize(new Dimension(300, 350));
        orderSummaryScroll.setAlignmentX(Component.CENTER_ALIGNMENT);
        orderSummaryScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        orderSummaryScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        orderSummaryScroll.setOpaque(false);


        JScrollBar verticalScrollBar2 = orderSummaryScroll.getVerticalScrollBar();
        verticalScrollBar2.setUnitIncrement(30);
        verticalScrollBar2.setBlockIncrement(100);
        orderSummarySide.add(orderSummaryScroll);

        Map<String, Integer> productQuantityMap = new LinkedHashMap<>();
        for (Product product : orderedProducts) {
            String key = product.getName() + " - " + product.getSize();
            productQuantityMap.put(key, productQuantityMap.getOrDefault(key, 0) + 1);
        }

        // getting the products that ordered

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
            productLabel.setFont(sz20);
            productLabel.setBorder(BorderFactory.createEmptyBorder(10,10,0,0));
            orderProcessSummary.add(productLabel);
        }

        orderSummarySide.add(orderSummaryScroll);



        JPanel subTotalPanel = new JPanel();
        subTotalPanel.setLayout(new BoxLayout(subTotalPanel, BoxLayout.Y_AXIS));
        subTotalPanel.setOpaque(false);


        JPanel stPanel = new JPanel();
        stPanel.setOpaque(false);
        stPanel.setLayout(new BorderLayout());
        stPanel.setPreferredSize(new Dimension(500, 30));
        stPanel.setMaximumSize(new Dimension(500, 30));

        JLabel subtotalLabel = new JLabel("Sub total: ₱" + String.format("%.2f", subtotal));
        subtotalLabel.setFont(sz16);
        subtotalLabel.setForeground(Color.WHITE);
        stPanel.add(subtotalLabel, BorderLayout.WEST);
        subTotalPanel.add(stPanel);

        JPanel dPanel = new JPanel();
        dPanel.setOpaque(false);
        dPanel.setLayout(new BorderLayout());
        dPanel.setPreferredSize(new Dimension(500, 30));
        dPanel.setMaximumSize(new Dimension(500, 30));
        JLabel discount = new JLabel("Discount: ");
        discount.setFont(sz16);
        discount.setForeground(Color.WHITE);
        dPanel.add(discount, BorderLayout.WEST);
        subTotalPanel.add(dPanel);

        total = subtotal - discountTotal;
        if(total < 0) total = 0;

        JPanel tPanel = new JPanel();
        tPanel.setOpaque(false);
        tPanel.setLayout(new BorderLayout());
        tPanel.setPreferredSize(new Dimension(500, 30));
        tPanel.setMaximumSize(new Dimension(500, 30));
        JLabel totalLabel = new JLabel("Total: ₱" + String.format("%.2f", total));
        totalLabel.setFont(sz16);
        totalLabel.setForeground(Color.WHITE);
        tPanel.add(totalLabel, BorderLayout.WEST);
        subTotalPanel.add(tPanel);

        JPanel cashTenderedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        cashTenderedPanel.setOpaque(false);
        cashTenderedPanel.setMaximumSize(new Dimension(500, 60));

        JLabel cashTendered = new JLabel("Cash Tendered: ");
        cashTendered.setForeground(Color.white);
        cashTendered.setFont(sz20);
        cashTenderedPanel.add(cashTendered);

        cTenderedTField = new JTextField();
        cTenderedTField.setPreferredSize(new Dimension(150, 50));
        cTenderedTField.setOpaque(false);
        cTenderedTField.setBorder(BorderFactory.createLineBorder(Color.white, 1, true));
        cTenderedTField.setFont(sz20);
        cTenderedTField.setForeground(Color.WHITE);
        cTenderedTField.setText("");
        cashTenderedPanel.add(cTenderedTField);

        subTotalPanel.add(cashTenderedPanel);

        JPanel changePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        changePanel.setPreferredSize(new Dimension(500, 60));
        changePanel.setMaximumSize(new Dimension(500,60));
        changePanel.setOpaque(false);

        JLabel changeLabel1 = new JLabel("Change: ");
        changeLabel1.setForeground(Color.white);
        changeLabel1.setFont(sz20);
        changePanel.add(changeLabel1);

        changeLabel2 = new JLabel("₱ 0.00");
        changeLabel2.setOpaque(false);
        changeLabel2.setFont(sz20);
        changeLabel2.setForeground(Color.WHITE);
        changePanel.add(changeLabel2);

        subTotalPanel.add(changePanel);

        orderSummarySide.add(subTotalPanel);

        // Process Buttons for order confirmation and cancellation
        JPanel processButtonPanel = new JPanel();
        processButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
        processButtonPanel.setPreferredSize(new Dimension(600, getHeight()));
        processButtonPanel.setOpaque(false);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setBackground(Color.decode("#BD1212"));
        cancelBtn.setPreferredSize(new Dimension(140, 50));
        cancelBtn.setBorderPainted(false);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setFont(sz16);
        cancelBtn.addActionListener(e -> {

            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (topFrame instanceof posSystem posFrame) {
                posFrame.switchToPanel(posFrame.getOrderItemPanel());
            }
        });

        JButton processBtn = new JButton("Confirm Payment");
        processBtn.setForeground(Color.WHITE);
        processBtn.setPreferredSize(new Dimension(160, 50));
        processBtn.setBackground(Color.decode("#F9A61A"));
        processBtn.setBorderPainted(false);
        processBtn.setFocusPainted(false);
        processBtn.setFont(sz16);
        processBtn.addActionListener(e -> {
            String cashInput = cTenderedTField.getText().trim().replace("₱", "").trim();
            try {
                double cashTenderedAmount = Double.parseDouble(cashInput);
                if (cashTenderedAmount < total) {
                    JOptionPane.showMessageDialog(this, "Insufficient cash tendered. Please enter an amount equal or greater than the total.", "Payment Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                double change = cashTenderedAmount - total;
                changeLabel2.setText("₱ " + String.format("%.2f", change));
                JOptionPane.showMessageDialog(this, "Payment successful!\nChange: ₱ " + String.format("%.2f", change), "Payment Confirmed", JOptionPane.INFORMATION_MESSAGE);

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
                JOptionPane.showMessageDialog(this, "Please enter a valid numeric amount for cash tendered.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        processButtonPanel.add(cancelBtn);
        processButtonPanel.add(processBtn);















        orderSummarySide.add(processButtonPanel);

        processOrder.add(orderSummarySide);

        //receipt call:
        processOrder.add(createReceipt());

        add(processOrder, BorderLayout.CENTER);



    }

    //RECEIPT METHOD, PWEDE MO GAWING PAREMETARIZED IKAW NA BAHALA IF PAANO MO AYUSIN
    public JPanel createReceipt(){
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
        receiptSide.setPreferredSize(new Dimension(500,800));
        receiptSide.setMaximumSize(new Dimension(500,800));


        //RECEIPT HEADER:
        JPanel receiptHeader = new JPanel();
        receiptHeader.setLayout(new BoxLayout(receiptHeader, BoxLayout.Y_AXIS));
        receiptHeader.setBorder(BorderFactory.createLineBorder(Color.black,1));
        receiptHeader.setAlignmentY(Component.CENTER_ALIGNMENT);
        receiptHeader.setPreferredSize(new Dimension(500,150));



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

        JLabel receiptAddress = new JLabel("<html><p>"+"Philippine Christian University, 1648 Taft Avenue corner Pedro Gil St., Malate, Manila"+"</p></html>");
        receiptAddress.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
        receiptAddress.setFont(sz14);
        receiptAddress.setForeground(Color.black);
        receiptAddress.setPreferredSize(new Dimension(400,90));

        receiptHeader.add(receiptAddress);

        receiptSide.add(receiptHeader, BorderLayout.NORTH);

        //MAIN CENTER CONTAINER FOR THE OVERALL RECEIPT DETAILS:
        JPanel receiptDetailContainer = new JPanel();
        receiptDetailContainer.setLayout(new BoxLayout(receiptDetailContainer, BoxLayout.Y_AXIS));
        receiptDetailContainer.setPreferredSize(new Dimension(500,500));
        receiptDetailContainer.setMaximumSize(new Dimension(500,500));
        receiptDetailContainer.setBorder(BorderFactory.createLineBorder(Color.black,1));



        //PRODUCT KET INFORMATION CONTAINER: FOR PRODUCT NAME,PRICE,QTY, AND TOTAL
        JPanel receiptItemSummary = new JPanel();
        receiptItemSummary.setLayout(new BoxLayout(receiptItemSummary, BoxLayout.Y_AXIS));
        receiptItemSummary.setPreferredSize(new Dimension(450,300));
        receiptItemSummary.setMaximumSize(new Dimension(450,300));




        JPanel receiptItemTitle = new JPanel(new GridLayout(0,4));
        receiptItemTitle.setPreferredSize(new Dimension(450,50));
        receiptItemTitle.setMaximumSize(new Dimension(450,50));


        //COLUMN TITLES:
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

        //SAMPLE DATA SETTING
        setItemId(123456789);
        setItemName("Jack n' Jill Piattos");
        setItemPrice(15.00);
        setItemQuantity(4);
        setTotal(getItemPrice(),getItemQuantity());

        //ADDING DETAILS:
        receiptItemSummary.add(createItemDetail(getItemId(),getItemName(),getItemPrice(),getItemQuantity(),getTotal()));

        receiptDetailContainer.add(receiptItemSummary);



        //SUBTOTAL:
        JPanel subTotalContainer = new JPanel(new BorderLayout());
        subTotalContainer.setPreferredSize(new Dimension(450,150));
        subTotalContainer.setMaximumSize(new Dimension(450,150));
        subTotalContainer.setOpaque(false);

        JPanel receiptSubtotal = new JPanel();
        receiptSubtotal.setLayout(new BoxLayout(receiptSubtotal, BoxLayout.Y_AXIS));
        subTotalContainer.add(receiptSubtotal, BorderLayout.WEST);



        JLabel subTotalLabel = new JLabel("SUBTOTAL:");
        subTotalLabel.setForeground(Color.BLACK);
        subTotalLabel.setFont(sz16);
        subTotalLabel.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
        receiptSubtotal.add(subTotalLabel);

        JLabel totalItemsLabel = new JLabel("Total Items: ");
        totalItemsLabel.setFont(sz13);
        totalItemsLabel.setForeground(Color.black);
        totalItemsLabel.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
        receiptSubtotal.add(totalItemsLabel);

        JLabel totalLabel = new JLabel("Total Price: ");
        totalLabel.setFont(sz16);
        totalLabel.setForeground(Color.black);
        totalLabel.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
        receiptSubtotal.add(totalLabel);

        JLabel cashTenderedLabel = new JLabel("Cash Tendered: ");
        cashTenderedLabel.setFont(sz13);
        cashTenderedLabel.setForeground(Color.black);
        cashTenderedLabel.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
        receiptSubtotal.add(cashTenderedLabel);

        JLabel changeLabel = new JLabel("Change: ");
        changeLabel.setFont(sz13);
        changeLabel.setForeground(Color.black);
        changeLabel.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
        receiptSubtotal.add(changeLabel);

        receiptDetailContainer.add(subTotalContainer);


        //VAT INFORMATION:
        JPanel vatContainer = new JPanel(new BorderLayout());
        vatContainer.setOpaque(false);
        vatContainer.setPreferredSize(new Dimension(450,180));
        vatContainer.setMaximumSize(new Dimension(450,180));

        JPanel vatReceipt = new JPanel();
        vatReceipt.setLayout(new BoxLayout(vatReceipt, BoxLayout.Y_AXIS));
        vatReceipt.setOpaque(false);
        vatContainer.add(vatReceipt, BorderLayout.WEST);

        //JUST A TITLE NO VALUE
        JLabel vatInformationLabel = new JLabel("VAT INFORMATION:");
        vatInformationLabel.setForeground(Color.BLACK);
        vatInformationLabel.setFont(sz16);
        vatInformationLabel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        vatReceipt.add(vatInformationLabel);


        JLabel vatSaleLabel = new JLabel("Vat Sale: ");
        vatSaleLabel.setForeground(Color.BLACK);
        vatSaleLabel.setFont(sz13);
        vatSaleLabel.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
        vatReceipt.add(vatSaleLabel);

        JLabel vatExemptSaleLabel = new JLabel("Vat Exempt Sale: ");
        vatExemptSaleLabel.setForeground(Color.BLACK);
        vatExemptSaleLabel.setFont(sz13);
        vatExemptSaleLabel.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
        vatReceipt.add(vatExemptSaleLabel);

        JLabel vatZeroRatedLabel = new JLabel("Vat Zero Rated Sale: ");
        vatZeroRatedLabel.setForeground(Color.BLACK);
        vatZeroRatedLabel.setFont(sz13);
        vatZeroRatedLabel.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
        vatReceipt.add(vatZeroRatedLabel);

        JLabel vatPercentageLabel = new JLabel("12% Vat: ");
        vatPercentageLabel.setForeground(Color.BLACK);
        vatPercentageLabel.setFont(sz13);
        vatPercentageLabel.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
        vatReceipt.add(vatPercentageLabel);

        receiptDetailContainer.add(vatContainer);


        //RECEIPT ADDITIONAL INFO:
        JPanel receiptInfoCont = new JPanel(new BorderLayout());
        receiptInfoCont.setOpaque(false);
        receiptInfoCont.setPreferredSize(new Dimension(450,100));
        receiptInfoCont.setMaximumSize(new Dimension(450,100));

        JPanel receiptInfo = new JPanel();
        receiptInfo.setLayout(new BoxLayout(receiptInfo, BoxLayout.Y_AXIS));
        receiptInfo.setOpaque(false);
        receiptInfoCont.add(receiptInfo);

        //ORDER ID:
        JLabel receiptOrderId = new JLabel("Order Id: ");
        receiptOrderId.setForeground(Color.BLACK);
        receiptOrderId.setFont(sz13);
        receiptOrderId.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
        receiptInfo.add(receiptOrderId);

        //CASHIER NAME:
        JLabel cashierNameLabel = new JLabel("Cashier Name: ");
        cashierNameLabel.setForeground(Color.BLACK);
        cashierNameLabel.setFont(sz13);
        cashierNameLabel.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
        receiptInfo.add(cashierNameLabel);

        //DATE:
        JLabel receiptDateLabel = new JLabel("Date: ");
        receiptDateLabel.setForeground(Color.BLACK);
        receiptDateLabel.setFont(sz13);
        receiptDateLabel.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
        receiptInfo.add(receiptDateLabel);

        receiptDetailContainer.add(receiptInfoCont);

        receiptSide.add(receiptDetailContainer, BorderLayout.CENTER);





        //RECEIPT FOOTER:
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new BorderLayout());
        footerPanel.setPreferredSize(new Dimension(500,100));
        footerPanel.setMaximumSize(new Dimension(500,100));
        footerPanel.setBorder(BorderFactory.createLineBorder(Color.black,1));

        JLabel footerText = new JLabel("<html><p>"+"This serves as your SALES INVOICE. Thank you, and Please Come Again. "+"</p></html>");
        footerText.setForeground(Color.black);
        footerText.setFont(sz14);

        footerPanel.add(footerText, BorderLayout.CENTER);

        receiptSide.add(footerPanel, BorderLayout.SOUTH);


        return receiptSide;
    }
    //FOR METHOD PRODUCT DETAILS ITO PARA DYNAMIC AND ROW BY ROW YUNG PAG LAGAY NG PRODUCTS:
    public JPanel createItemDetail(int productId,String productName, double productPrice, int productQuantity, double priceTotal){
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

        JLabel prdctPrice = new JLabel("" + productPrice);
        prdctPrice.setForeground(Color.black);
        prdctPrice.setFont(sz16);
        itemDetail.add(prdctPrice);

        JLabel prdctQuantity = new JLabel(""+productQuantity);
        prdctQuantity.setForeground(Color.black);
        prdctQuantity.setFont(sz16);
        itemDetail.add(prdctQuantity);

        JLabel prdctTotal = new JLabel(""+priceTotal);
        prdctTotal.setForeground(Color.black);
        prdctTotal.setFont(sz16);
        itemDetail.add(prdctTotal);



        return itemDetail;

    }


    //SETTERS FOR PRODUCT DETAILS:

    public void setItemId(int itemId){
        this.itemId = itemId;
    }
    public void setItemName(String itemName){
        this.itemName = itemName;
    }

    public void setItemPrice(double itemPrice){
        this.itemPrice = itemPrice;
    }

    public void setItemQuantity(int itemQuantity){
        this.itemQuantity = itemQuantity;
    }

    public void setTotal(double itemPrice, int itemQuantity){
        this.Total = (itemPrice * itemQuantity);
    }




    //GETTERS FOR PRODUCT DETAILS:

    public int getItemId(){
        return itemId;
    }

    public String getItemName(){
        return itemName;
    }

    public double getItemPrice(){
        return itemPrice;
    }

    public int getItemQuantity(){
        return itemQuantity;
    }

    public double getTotal(){
        return Total;
    }




}
