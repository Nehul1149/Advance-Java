
//Experiment 7 – Write a Program to create connectivity using JDBC.
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
public class Loginwithjdbc implements ActionListener {
    JFrame f;
    JLabel title, username, password;
    JButton login;
    JTextField inuser;
    JPasswordField inpass;

    Loginwithjdbc() {
        f = new JFrame("Login Authentification Page Through JDBC");
        title = new JLabel("User Login");
        username = new JLabel("User Name");
        password = new JLabel("Password");
        inuser = new JTextField();
        inpass = new JPasswordField();
        login = new JButton("Login");
        title.setBounds(200, 20, 100, 20);
        username.setBounds(50, 50, 100, 50);
        password.setBounds(50, 100, 100, 50);
        inuser.setBounds(150, 70, 200, 20);
        inpass.setBounds(150, 110, 200, 20);
        login.setBounds(150, 200, 80, 20);
        f.add(title);
        f.add(username);
        f.add(password);
        f.add(inuser);
        f.add(inpass);
        f.add(login);
        f.setSize(450, 300);
        f.setLayout(null);
        f.setVisible(true);
        f.setResizable(false);
        login.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbcconnection", "root", "root");
            Statement stmt = con.createStatement();
            String sql = "Select * from loginuser where username='" + inuser.getText() + "' and password='"
                    + inpass.getText() + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next())
                JOptionPane.showMessageDialog(null, "Login Sucessfully....");
            else
                JOptionPane.showMessageDialog(null, "Login UnSucessfully....");
            con.close();
        } catch (Exception ea) {
            System.out.print(ea);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Loginwithjdbc();
            }
        });
    }
}
