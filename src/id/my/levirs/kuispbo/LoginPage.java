package id.my.levirs.kuispbo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class LoginPage extends JFrame {
    private JLabel mTitleLabel, mUsernameLabel, mPasswordLabel;
    private JTextField mUsernameTextField, mPasswordTextField;
    private JButton mLoginButton;

    public LoginPage() {
        setLayout(new GridBagLayout());

        var c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.insets = new Insets(5, 5, 5, 5);

        mTitleLabel = new JLabel("Selamat Datang di Aplikasi (123230127)");
        add(mTitleLabel, c);

        c.insets.top = 0;

        c.gridy = 1;
        c.gridwidth = 1;
        mUsernameLabel = new JLabel("Username");
        add(mUsernameLabel, c);

        c.gridy = 2;
        mPasswordLabel = new JLabel("Password");
        add(mPasswordLabel, c);

        c.gridx = 1;
        c.gridy = 3;
        c.anchor = GridBagConstraints.LINE_END;
        mLoginButton = new JButton("Login");
        add(mLoginButton, c);

        c.gridx = 1;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        mUsernameTextField = new JTextField();
        add(mUsernameTextField, c);

        c.gridy = 2;
        mPasswordTextField = new JTextField();
        add(mPasswordTextField, c);

        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Halaman Login");

        configLabelSize(mUsernameLabel);
        configLabelSize(mPasswordLabel);

        setSize(300, 144);

        mLoginButton.addActionListener((e) -> {
            var username = mUsernameTextField.getText();
            var password = mPasswordTextField.getText();
            if (username.trim().isEmpty() || password.trim().isEmpty() || !password.equals("pbo-d")) {
                JOptionPane.showMessageDialog(this,
                        "Username kosong atau password kosong atau password salah",
                        "Gagal Login",
                        JOptionPane.ERROR_MESSAGE);
                mUsernameTextField.setText("");
                mPasswordTextField.setText("");
                return;
            }

            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

            new MainPage();
        });
    }

    void configLabelSize(Component c) {
        c.setPreferredSize(new Dimension(75, c.getSize().height));
    }
}
