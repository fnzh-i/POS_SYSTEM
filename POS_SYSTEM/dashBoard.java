import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class dashBoard extends JPanel {

    dashBoard() {

        //FOR THE JPANEL MAIN PARENT CONTAINER:
        setPreferredSize(new Dimension(1240, 800));
        setMaximumSize(new Dimension(1240, 800));
        setLayout(new BorderLayout(20, 20));
        setBackground(Color.decode("#00132d"));


        // UPPER PART PANEL FOR THE SALES DETAILS:
        JPanel upperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        upperPanel.setBackground(Color.decode("#00132d"));
        upperPanel.setPreferredSize(new Dimension(1200, 200));
        upperPanel.setMaximumSize(new Dimension(1200, 200));
        upperPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 40, 10));


        upperPanel.add(createSaleSummaryPanel("Number of Customer", "", 20));
        upperPanel.add(createSaleSummaryPanel("Today's Income", "₱ ", 15000));
        upperPanel.add(createSaleSummaryPanel("Total Income", "₱ ", 150200));
        upperPanel.add(createSaleSummaryPanel("Number of Sold Products", "", 1002));



        // LOWER PART PANEL:

        JPanel lowerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        lowerPanel.setPreferredSize(new Dimension(1200, 580)); // Adjust height
        lowerPanel.setBackground(Color.decode("#00132d"));
        lowerPanel.setOpaque(false);

        // PANEL FOR MONTHLY GRAPH:
        JPanel monthlyGraphPanel = new RoundedPanel(80);
        monthlyGraphPanel.setPreferredSize(new Dimension(800, 500));
        monthlyGraphPanel.setMaximumSize(new Dimension(800, 500));
        monthlyGraphPanel.setBackground(Color.white);
        lowerPanel.add(monthlyGraphPanel);


        // PANEL FOR OTHER DETAILS FOR DASHBOARD
        JPanel sidePanel = new RoundedPanel(80);
        sidePanel.setPreferredSize(new Dimension(380, 500));
        sidePanel.setMaximumSize(new Dimension(380, 500));
        sidePanel.setBackground(Color.white);
        lowerPanel.add(sidePanel);

        add(upperPanel, BorderLayout.NORTH);
        add(lowerPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.getContentPane().setBackground(Color.decode("#00132d"));

        dashBoard dashboardPanel = new dashBoard();
        frame.getContentPane().add(dashboardPanel, BorderLayout.CENTER);

        frame.setSize(1240, 800); // Adjust frame size
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // METHOD TO CREATE THE 4 SALES DETAIL PANEL:
    public JPanel createSaleSummaryPanel(String text, String sign, int Data) {
        Font sz16 = FontUtils.loadFont(16f);
        Font sz35 = FontUtils.loadFont(35f);

        RoundedPanel saleSummaryPanel = new RoundedPanel(30);
        saleSummaryPanel.setPreferredSize(new Dimension(250, 130));
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

// Custom JPanel with rounded corners
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
        g2.setStroke(new BasicStroke(3)); // Set border thickness
        g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, radius, radius));
        g2.dispose();
    }

    @Override
    public void setForeground(Color fg) {
        super.setForeground(fg);
        repaint(); 
    }
}