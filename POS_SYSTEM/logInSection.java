package POS_SYSTEM;
import fonts.fontUtils;
// import org.slf4j.Logger;  not yet needed atm
// import org.slf4j.LoggerFactory; not yet needed atm
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class logInSection extends JFrame {


    public logInSection(){
        setSize(1280,720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        this.setLocationRelativeTo(null);

        Font loginTitleFont = fontUtils.loadFont(36f);
        Font loginLabelFont = fontUtils.loadFont(16f);
        Font loginBtnFont = fontUtils.loadFont(17f);

        //SYSTEM ICON
        ImageIcon systemIcon = new ImageIcon("img/Logo.jpg");
        setIconImage(systemIcon.getImage());
        setTitle("PCU-POS");

        getContentPane().setBackground(Color.decode("#021526"));

        JLabel loginTitle = new JLabel();
        loginTitle.setText("PCU CANTEEN POS");
        loginTitle.setForeground(Color.WHITE);
        loginTitle.setBounds(500,65,365,45);
        loginTitle.setFont(loginTitleFont);
        add(loginTitle);


        RoundedPanel logInCont = new RoundedPanel(20); //wow bago
        logInCont.setBackground(Color.decode("#03346E"));
        logInCont.setLayout(null);
        logInCont.setBounds(401,122,500,493);
        add(logInCont);

        //PCU LOGO
        ImageIcon systemLogo = new ImageIcon("img/Logo.jpg");
        JLabel pcuLogo = new JLabel();
        Image originalImage = systemLogo.getImage();

        int imgWidth = 137;
        int imgHeight = 137;

        Image ScaledImg = originalImage.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledLogo = new ImageIcon(ScaledImg);

        pcuLogo.setIcon(scaledLogo);
        pcuLogo.setBounds(190,10,137,137);
        logInCont.add(pcuLogo);

        //USER/PASS LABELS AND TEXTFIELDS:
        JLabel userFieldLabel = new JLabel();
        userFieldLabel.setText("Username");
        userFieldLabel.setForeground(Color.WHITE);
        userFieldLabel.setFont(loginLabelFont);
        userFieldLabel.setBounds(120,180,126,24);
        logInCont.add(userFieldLabel);

        JLabel passFieldLabel = new JLabel();
        passFieldLabel.setText("Password");
        passFieldLabel.setForeground(Color.WHITE);
        passFieldLabel.setFont(loginLabelFont);
        passFieldLabel.setBounds(120,280,126,24);
        logInCont.add(passFieldLabel);

        JTextField userTextField = new JTextField();
        userTextField.setBackground(Color.WHITE);
        userTextField.setFont(loginLabelFont);
        userTextField.setBounds(110, 210,300,53);
        logInCont.add(userTextField);

        JPasswordField passTextField = new   JPasswordField();
        passTextField.setBackground(Color.WHITE);
        passTextField.setFont(loginLabelFont);
        passTextField.setBounds(110, 310,300,53);
        logInCont.add(passTextField );

        //LOGIN BUTTON
        RoundedButton logInBtn = new RoundedButton("LOG IN", 20);
//        logInBtn.setText("LOG IN");
        logInBtn.setForeground(Color.WHITE);
        logInBtn.setBackground(Color.decode("#F9A61A"));
        logInBtn.setFont(loginBtnFont);
        logInBtn.setBounds(140,390,225,42);
        logInCont.add(logInBtn);

        //REGISTER ACC LABEL
        JLabel registerAcc = new JLabel();
        registerAcc.setText("Register Account");
        registerAcc.setForeground(Color.gray);
        registerAcc.setFont(loginLabelFont);
        registerAcc.setBounds(200,430,200,53);
        logInCont.add(registerAcc);

        logInBtn.addActionListener(e -> {
            String userName = userTextField.getText().trim();
            String password = new String(passTextField.getPassword()).trim();

            if (userName.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both username and password",
                        "Login Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Get authenticated user with role from logInPage
            User authenticatedUser = logInPage.authenticateUser(userName, password);

            if (authenticatedUser != null) {
                JOptionPane.showMessageDialog(this, "Login successful!",
                        "Login Success", JOptionPane.INFORMATION_MESSAGE);

                if (userName != null && !userName.isEmpty()) {
                    orderSummary.getInstance().setUsrname(userName);
                }

                // Pass the authenticated user with role to posSystem
                new posSystem(authenticatedUser);
                this.dispose();
            }
            else {
                JOptionPane.showMessageDialog(this, "Invalid username or password",
                        "Login Failed", JOptionPane.ERROR_MESSAGE);
                passTextField.setText("");
                userTextField.requestFocus();
            }
        });


        registerAcc.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e){
                new registerUser(); // CHANGE TO REGISRATION FORM
                dispose();

            }
            @Override
            public void mouseEntered(MouseEvent e){
            }
            @Override
            public void mouseExited(MouseEvent e){
            }
            @Override
            public void mousePressed(MouseEvent e){
            }
            @Override
            public void mouseReleased(MouseEvent e){
            }
        });
        setVisible(true);


    }

    public static void main (String[] args){

        JFrame frame = new JFrame();
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        new logInSection();

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
