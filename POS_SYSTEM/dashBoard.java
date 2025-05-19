package POS_SYSTEM;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Collections;
import java.util.Map;

import fonts.fontUtils;

public class dashBoard extends JPanel {

    private JPanel monthlyGraphPanel; // Add this declaration

    dashBoard() {
        // Main panel configuration
        setPreferredSize(new Dimension(1240, 800));
        setMaximumSize(new Dimension(1240, 800));
        setLayout(new BorderLayout(20, 20));
        setBackground(Color.decode("#00132d"));

        // Upper panel with sales metrics
        JPanel upperPanel = createUpperPanel();

        // Lower panel with the monthly graph
        JPanel lowerPanel = createLowerPanel();

        add(upperPanel, BorderLayout.NORTH);
        add(lowerPanel, BorderLayout.CENTER);
    }

    private JPanel createUpperPanel() {
        JPanel upperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        upperPanel.setBackground(Color.decode("#00132d"));
        upperPanel.setPreferredSize(new Dimension(1200, 200));
        upperPanel.setMaximumSize(new Dimension(1200, 200));
        upperPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 40, 10));

        upperPanel.add(createSaleSummaryPanel("Number of Customer", "", 0));
        upperPanel.add(createSaleSummaryPanel("Today's Income", "₱ ", (int) orderHistoryDBManager.getTodaysIncome()));
        upperPanel.add(createSaleSummaryPanel("Total Income", "₱ ", (int) orderHistoryDBManager.getTotalIncome()));
        upperPanel.add(createSaleSummaryPanel("<html><p>Number of Sold Products</p></html>", "", orderHistoryDBManager.getTotalSoldProducts()));

        return upperPanel;
    }

    private JPanel createLowerPanel() {
        JPanel lowerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        lowerPanel.setPreferredSize(new Dimension(1200, 580));
        lowerPanel.setBackground(Color.decode("#00132d"));
        lowerPanel.setOpaque(false);

        // Create monthly graph panel
        monthlyGraphPanel = new RoundedPanel(10); // Now properly declared
        monthlyGraphPanel.setPreferredSize(new Dimension(450, 400));
        monthlyGraphPanel.setMaximumSize(new Dimension(450, 400));
        monthlyGraphPanel.setBackground(Color.white);
        monthlyGraphPanel.setLayout(new BorderLayout());

        // Get monthly income data
        Map<String, Double> monthlyIncome = orderHistoryDBManager.getMonthlyIncome();

        // Create and add graph panel
        RoundedPanel.IncomeGraphPanel graphPanel = new RoundedPanel.IncomeGraphPanel(monthlyIncome, "Monthly Income Report");
        graphPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        monthlyGraphPanel.add(graphPanel, BorderLayout.CENTER);

        lowerPanel.add(monthlyGraphPanel);

        return lowerPanel;
    }

    public JPanel createSaleSummaryPanel(String text, String sign, int Data) {
        Font sz16 = fontUtils.loadFont(16f);
        Font sz35 = registerUser.FontUtils.loadFont(35f);

        RoundedPanel saleSummaryPanel = new RoundedPanel(30);
        saleSummaryPanel.setPreferredSize(new Dimension(200, 120));
        saleSummaryPanel.setBackground(Color.decode("#D9D9D9"));
        saleSummaryPanel.setLayout(new BorderLayout());

        JLabel dataLabel = new JLabel();
        dataLabel.setText(sign + Data);
        dataLabel.setFont(sz35);
        dataLabel.setForeground(Color.black);
        dataLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        saleSummaryPanel.add(dataLabel, BorderLayout.CENTER);

        JLabel dataTagName = new JLabel(text);
        dataTagName.setFont(sz16);
        dataTagName.setForeground(Color.black);
        dataTagName.setAlignmentX(RIGHT_ALIGNMENT);
        dataTagName.setBorder(BorderFactory.createEmptyBorder(0, 20, 15, 0));
        saleSummaryPanel.add(dataTagName, BorderLayout.SOUTH);

        return saleSummaryPanel;
    }
}

class RoundedPanel extends JPanel {
    private int radius;

    public RoundedPanel(int radius) {
        this.radius = radius;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));

        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getForeground());
        g2.setStroke(new BasicStroke(3));
        g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, radius, radius));
        g2.dispose();
    }

    static class IncomeGraphPanel extends JPanel {
        private Map<String, Double> incomeData;
        private String graphTitle;
        private Color lineColor = new Color(44, 102, 230);
        private Color pointColor = new Color(230, 60, 60);
        private Color gridColor = new Color(200, 200, 200);
        private Font labelFont = new Font("Roboto", Font.PLAIN, 12);
        private static final String[] MONTH_NAMES = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        public IncomeGraphPanel(Map<String, Double> incomeData, String graphTitle) {
            this.incomeData = incomeData;
            this.graphTitle = graphTitle;
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (incomeData == null || incomeData.isEmpty()) {
                drawNoDataMessage(g2);
                return;
            }

            drawTitle(g2);
            drawGrid(g2);
            drawAxesLabels(g2);
            drawLineGraph(g2);
        }

        private void drawNoDataMessage(Graphics2D g2) {
            g2.setColor(Color.GRAY);
            g2.setFont(new Font("Roboto", Font.BOLD, 16));
            String message = "No sales data available";
            int messageWidth = g2.getFontMetrics().stringWidth(message);
            g2.drawString(message, getWidth()/2 - messageWidth/2, getHeight()/2);
        }

        private void drawTitle(Graphics2D g2) {
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Roboto", Font.BOLD, 16));
            int titleWidth = g2.getFontMetrics().stringWidth(graphTitle);
            g2.drawString(graphTitle, getWidth()/2 - titleWidth/2, 30);
        }

        private void drawGrid(Graphics2D g2) {
            g2.setColor(gridColor);

            // Vertical grid lines
            int xStep = getWidth() / (incomeData.size() + 1);
            for (int i = 1; i <= incomeData.size(); i++) {
                int x = i * xStep;
                g2.drawLine(x, 50, x, getHeight() - 50);
            }

            // Horizontal grid lines
            double maxValue = getMaxValue();
            int yStep = (getHeight() - 100) / 5;
            for (int i = 0; i <= 5; i++) {
                int y = getHeight() - 50 - (i * yStep);
                g2.drawLine(50, y, getWidth() - 50, y);
            }
        }

        private void drawAxesLabels(Graphics2D g2) {
            g2.setColor(Color.BLACK);
            g2.setFont(labelFont);

            // X-axis labels (months)
            int xStep = getWidth() / (incomeData.size() + 1);
            int labelIndex = 0;

            // Sort the months in order
            java.util.List<String> sortedMonths = new java.util.ArrayList<>(incomeData.keySet());
            Collections.sort(sortedMonths, (m1, m2) -> Integer.compare(Integer.parseInt(m1), Integer.parseInt(m2)));

            for (String monthNum : sortedMonths) {
                int x = (labelIndex + 1) * xStep;
                int y = getHeight() - 30;

                // Convert month number to month name
                String label;
                try {
                    int monthIndex = Integer.parseInt(monthNum) - 1;
                    label = (monthIndex >= 0 && monthIndex < MONTH_NAMES.length)
                            ? MONTH_NAMES[monthIndex]
                            : "M" + monthNum;
                } catch (NumberFormatException e) {
                    label = monthNum;
                }

                int labelWidth = g2.getFontMetrics().stringWidth(label);
                g2.drawString(label, x - labelWidth/2, y);
                labelIndex++;
            }

            // Y-axis labels (amounts)
            double maxValue = getMaxValue();
            int yStep = (getHeight() - 100) / 5;
            for (int i = 0; i <= 5; i++) {
                int y = getHeight() - 50 - (i * yStep);
                String label = String.format("₱%,.0f", maxValue * i / 5);
                int labelWidth = g2.getFontMetrics().stringWidth(label);
                g2.drawString(label, 40 - labelWidth, y + 5);
            }
        }

        private void drawLineGraph(Graphics2D g2) {
            double maxValue = getMaxValue();
            int xStep = getWidth() / (incomeData.size() + 1);
            int prevX = 0, prevY = 0;
            int pointIndex = 0;

            // Sort the months in order
            java.util.List<String> sortedMonths = new java.util.ArrayList<>(incomeData.keySet());
            Collections.sort(sortedMonths, (m1, m2) -> Integer.compare(Integer.parseInt(m1), Integer.parseInt(m2)));

            // Draw line and points
            g2.setStroke(new BasicStroke(2.5f));
            for (String month : sortedMonths) {
                Double value = incomeData.get(month);
                if (value == null) continue;

                int x = (pointIndex + 1) * xStep;
                int y = getHeight() - 50 - (int)((value / maxValue) * (getHeight() - 100));

                // Draw point
                g2.setColor(pointColor);
                g2.fillOval(x - 5, y - 5, 10, 10);

                // Draw line connecting to previous point
                if (pointIndex > 0) {
                    g2.setColor(lineColor);
                    g2.drawLine(prevX, prevY, x, y);
                }

                // Draw value label
                g2.setColor(Color.BLACK);
                String valueLabel = String.format("₱%,.0f", value);
                int labelWidth = g2.getFontMetrics().stringWidth(valueLabel);
                g2.drawString(valueLabel, x - labelWidth/2, y - 15); // Moved up slightly

                prevX = x;
                prevY = y;
                pointIndex++;
            }
        }

        private double getMaxValue() {
            return Collections.max(incomeData.values());
        }
    }
}