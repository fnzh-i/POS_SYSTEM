import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class processOrderPanel extends JPanel {
    orderItemPanel oIPanel = new orderItemPanel();

    processOrderPanel() {
        Font sz16 = FontUtils.loadFont(16f);
        Font sz20 = FontUtils.loadFont(20f);



        JPanel processOrder = new JPanel();
        processOrder.setLayout(new FlowLayout(FlowLayout.LEFT, 10,10));
        processOrder.setPreferredSize(new Dimension(1240,980));
        processOrder.setMaximumSize(new Dimension(1240,980));
        processOrder.setBackground(Color.DARK_GRAY);



        JPanel orderSummarySide = new JPanel();
        orderSummarySide.setLayout(new BoxLayout(orderSummarySide, BoxLayout.Y_AXIS));
        orderSummarySide.setPreferredSize(new Dimension(500,800));
        orderSummarySide.setAlignmentX(Component.LEFT_ALIGNMENT);
        orderSummarySide.setOpaque(false);


        JPanel osPanel = new JPanel();
        osPanel.setOpaque(false);
        osPanel.setLayout(new BorderLayout());
        osPanel.setPreferredSize(new Dimension(500,40));
        osPanel.setMaximumSize(new Dimension(500,40));

        JLabel osLabel = new JLabel("Order Summary");
        osLabel.setForeground(Color.white);
        osLabel.setFont(sz20);
        osPanel.add(osLabel, BorderLayout.WEST);
        orderSummarySide.add(osPanel);


        JPanel plusBtnPanel = new JPanel();
        plusBtnPanel.setOpaque(false);
        plusBtnPanel.setLayout(new BorderLayout());
        plusBtnPanel.setPreferredSize(new Dimension(500,40));
        plusBtnPanel.setMaximumSize(new Dimension(500,40));

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


            JPanel subTotalPanel = new JPanel();
            subTotalPanel.setLayout(new BoxLayout(subTotalPanel, BoxLayout.Y_AXIS));
            subTotalPanel.setOpaque(false);


        JPanel stPanel = new JPanel();
        stPanel.setOpaque(false);
        stPanel.setLayout(new BorderLayout());
        stPanel.setPreferredSize(new Dimension(500,30));
        stPanel.setMaximumSize(new Dimension(500,30));
        JLabel subtotal = new JLabel("Sub total: ");
        subtotal.setFont(sz16);
        subtotal.setForeground(Color.WHITE);
        stPanel.add(subtotal,BorderLayout.WEST);
        subTotalPanel.add(stPanel);

        JPanel dPanel = new JPanel();
        dPanel.setOpaque(false);
        dPanel.setLayout(new BorderLayout());
        dPanel.setPreferredSize(new Dimension(500,30));
        dPanel.setMaximumSize(new Dimension(500,30));
        JLabel discount = new JLabel("Discount: ");
        discount.setFont(sz16);
        discount.setForeground(Color.WHITE);
        dPanel.add(discount, BorderLayout.WEST);
        subTotalPanel.add(dPanel);



        JPanel tPanel = new JPanel();
        tPanel.setOpaque(false);
        tPanel.setLayout(new BorderLayout());
        tPanel.setPreferredSize(new Dimension(500,30));
        tPanel.setMaximumSize(new Dimension(500,30));
        JLabel total = new JLabel("Total: ");
        total.setFont(sz16);
        total.setForeground(Color.WHITE);
        tPanel.add(total, BorderLayout.WEST);
        subTotalPanel.add(tPanel);

            JPanel cashTenderedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10,0));
            cashTenderedPanel.setOpaque(false);
            cashTenderedPanel.setMaximumSize(new Dimension(500,60));

            JLabel cashTendered = new JLabel("Cash Tendered: ");
            cashTendered.setForeground(Color.white);
            cashTendered.setFont(sz20);
            cashTenderedPanel.add(cashTendered);

            JTextField cTenderedTField = new JTextField("₱ ");
            cTenderedTField.setPreferredSize(new Dimension(150,50));
            cTenderedTField.setOpaque(false);
            cTenderedTField.setBorder(BorderFactory.createLineBorder(Color.white,1,true));
            cTenderedTField.setFont(sz20);
            cTenderedTField.setForeground(Color.WHITE);
            cashTenderedPanel.add(cTenderedTField);

            subTotalPanel.add(cashTenderedPanel);

            JPanel changePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10,0));

            changePanel.setPreferredSize(new Dimension(500,60));
            changePanel.setMaximumSize(new Dimension(500,60));
            changePanel.setOpaque(false);
            JLabel changeLabel1 = new JLabel("Change: ");
            changeLabel1.setForeground(Color.white);
            changeLabel1.setFont(sz20);
            changePanel.add(changeLabel1);

            JLabel changeLabel2 = new JLabel("₱ ");
            changeLabel2.setOpaque(false);
            changeLabel2.setFont(sz20);
            changeLabel2.setForeground(Color.WHITE);
            changePanel.add(changeLabel2);


            subTotalPanel.add(changePanel);


            orderSummarySide.add(subTotalPanel);


            //PROCESS BUTTONS FOP ORDER PROCESS SUMMARY:
            JPanel processButtonPanel = new JPanel();
            processButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
            processButtonPanel.setPreferredSize(new Dimension(600, getHeight()));
            processButtonPanel.setOpaque(false);


            JButton cancelBtn = new JButton("Cancel");
            cancelBtn.setForeground(Color.WHITE);
            cancelBtn.setBackground(Color.decode("#BD1212"));
            cancelBtn.setPreferredSize(new Dimension(140,50));
            cancelBtn.setBorderPainted(false);
            cancelBtn.setFocusPainted(false);
            cancelBtn.setFont(sz16);

            processButtonPanel.add(cancelBtn);

            JButton processBtn = new JButton("Confirm Payment");
            processBtn.setForeground(Color.WHITE);
            processBtn.setPreferredSize(new Dimension(160,50));
            processBtn.setBackground(Color.decode("#F9A61A"));
            processBtn.setBorderPainted(false);
            processBtn.setFocusPainted(false);
            processBtn.setFont(sz16);
            processButtonPanel.add(processBtn);

            orderSummarySide.add(processButtonPanel);


            processOrder.add(orderSummarySide);



            add(processOrder);



    }

}
