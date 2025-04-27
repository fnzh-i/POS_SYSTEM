package POS_SYSTEM;
import fonts.fontUtils;
// import org.slf4j.Logger;  not yet needed atm
// import org.slf4j.LoggerFactory; not yet needed atm

import javax.swing.*;
import java.awt.*;

public class logInSection extends javax.swing.JFrame {
    // private static final Logger log = LoggerFactory.getLogger(logInSection.class); not yet needed atm

    public logInSection(){

        Font loginTitleFont = fontUtils.loadFont(36f);
        Font loginLabelFont = fontUtils.loadFont(16f);
        Font loginBtnFont = fontUtils.loadFont(17f);
        setSize(1280,720);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

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


        JPanel logInCont = new JPanel();
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
        JButton logInBtn = new JButton();
        logInBtn.setText("LOG IN");
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
            String password = new String (passTextField.getPassword()).trim();

            if (userName.isEmpty() && password.isEmpty()){
                JOptionPane.showMessageDialog(this,"Please enter both username and password","Login Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (logInPage.authenticateUser(userName, password)){
                JOptionPane.showMessageDialog(this,"Login successful!", "Login Success", JOptionPane.INFORMATION_MESSAGE);

               new posSystem().show();
               dispose();
            }
            else {
                JOptionPane.showMessageDialog(this,"Invalid username or password","Login Failed", JOptionPane.ERROR_MESSAGE);
                passTextField.setText("");
                userTextField.requestFocus();
            }
        });
    }
}
