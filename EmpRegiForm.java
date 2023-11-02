
//Experiment 8: Write a Program to search, Insert, update, delete records from database using MYSQL database.
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.JScrollBar;
import java.sql.*;
import javax.swing.JScrollPane;

public class EmpRegiForm extends JFrame {
    private JPanel contentPane;
    private JTextField id;
    private JTextField name;
    private JTextField mno;
    private JTable Employee_Data;
    private JTextField search;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    EmpRegiForm frame = new EmpRegiForm();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void refreshandLoadTable() { // we create globle function for calling in other area
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emp_data", "root", "root");
            Statement st = con.createStatement();
            String query = "select * from employee_details";
            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            DefaultTableModel model = (DefaultTableModel) Employee_Data.getModel();
            model.setRowCount(0); // For Refresh table for every time after any operation
            int cols = rsmd.getColumnCount();
            String[] colName = new String[cols];
            for (int i = 0; i < cols; i++)
                colName[i] = rsmd.getColumnName(i + 1);
            model.setColumnIdentifiers(colName);
            String empSno, empid1, empname1, empmno1;
            int i = 1; // order Change s no always for table view data don't change the order in
                       // database
            while (rs.next()) {
                empSno = String.valueOf(i);
                empid1 = rs.getString(2);
                empname1 = rs.getString(3);
                empmno1 = rs.getString(4);
                String[] row = { empSno, empid1, empname1, empmno1 };
                model.addRow(row);
                i++;
            }
            st.close();
            con.close();
        } catch (Exception ea) {
            System.out.print(ea);
        }
    }

    /**
     * Create the frame.
     */
    public EmpRegiForm() {
        setTitle("Emplyoee Registration Desk");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 812, 504);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        JLabel lblNewLabel = new JLabel("Employee Registration By Nehul");
        lblNewLabel.setBounds(196, 28, 368, 29);
        lblNewLabel.setFont(new Font("Swis721 WGL4 BT", Font.BOLD, 18));
        lblNewLabel.setForeground(Color.BLUE);
        contentPane.add(lblNewLabel);
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "Fill Employee Details", TitledBorder.CENTER,
                TitledBorder.TOP, null, Color.RED));
        panel.setBounds(46, 76, 337, 282);
        contentPane.add(panel);
        panel.setLayout(null);
        JLabel p1 = new JLabel("Employee Id");
        p1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        p1.setBounds(21, 53, 102, 23);
        panel.add(p1);
        JLabel lblNewLabel_1_1 = new JLabel("Employee Name");
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_1_1.setBounds(21, 103, 136, 32);
        panel.add(lblNewLabel_1_1);
        JLabel lblNewLabel_1_2 = new JLabel("Mobile No.");
        lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_1_2.setBounds(21, 152, 102, 23);
        panel.add(lblNewLabel_1_2);
        id = new JTextField();
        id.setBounds(144, 57, 159, 32);
        panel.add(id);
        id.setColumns(10);
        name = new JTextField();
        name.setColumns(10);
        name.setBounds(144, 103, 159, 32);
        panel.add(name);
        mno = new JTextField();
        mno.setColumns(10);
        mno.setBounds(144, 150, 159, 32);
        panel.add(mno);
        JButton btnNewButton = new JButton("Save");
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String eid = id.getText();
                String ename = name.getText();
                String emno = mno.getText();
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emp_data", "root",
                            "root");
                    String query = "INSERT INTO employee_details(empid,empname,empmno) VALUES(?,?,?)";
                    PreparedStatement pst = con.prepareStatement(query);
                    pst.setString(1, eid);
                    pst.setString(2, ename);
                    pst.setString(3, emno);
                    int k = pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Emplyoee Registration Sucessfully...");

                    // table load after one recored complete
                    id.setText("");
                    name.setText("");
                    mno.setText("");
                    id.requestFocus();
                    refreshandLoadTable(); // after save add new data in table
                    con.close();
                } catch (Exception ea) {
                    System.out.print(ea);
                }
            }
        });
        btnNewButton.setBounds(95, 213, 136, 42);
        panel.add(btnNewButton);
        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String enterid = search.getText();
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emp_data", "root",
                            "root");
                    PreparedStatement pst1 = con
                            .prepareStatement("SELECT empid,empname,empmno from employee_details where empid=?");
                    pst1.setString(1, enterid);
                    ResultSet rs = pst1.executeQuery();
                    if (rs.next() == false) {
                        JOptionPane.showMessageDialog(null, "Recored Not Found...");
                    } else {
                        String empid = rs.getString(1);
                        String empname = rs.getString(2);
                        String empmno = rs.getString(3);
                        id.setText(empid);
                        name.setText(empname);
                        mno.setText(empmno);
                    }
                    pst1.close();
                    con.close();
                } catch (Exception ea) {
                    System.out.print(ea);
                }
            }
        });
        btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnSearch.setBounds(87, 425, 136, 42);
        contentPane.add(btnSearch);
        JButton btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String eid = id.getText();
                String ename = name.getText();
                String emno = mno.getText();
                String enterid = search.getText();
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emp_data", "root",
                            "root");
                    PreparedStatement pst2 = con
                            .prepareStatement("UPDATE employee_details set empid=?,empname=?,empmno=? where empid=?");
                    pst2.setString(1, eid);
                    pst2.setString(2, ename);
                    pst2.setString(3, emno);
                    pst2.setString(4, enterid);
                    pst2.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Update Sucessfully...");

                    // table load after update recored complete
                    id.setText("");
                    name.setText("");
                    mno.setText("");
                    id.requestFocus();
                    refreshandLoadTable(); // after update data in table load table
                    pst2.close();
                    con.close();
                } catch (Exception ea) {
                    System.out.print(ea);
                }
            }
        });
        btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnUpdate.setBounds(264, 425, 136, 42);
        contentPane.add(btnUpdate);
        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String enterid = search.getText();
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emp_data", "root",
                            "root");
                    PreparedStatement pst3 = con.prepareStatement("DELETE from employee_details where empid=?");
                    pst3.setString(1, enterid);
                    pst3.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Delete Sucessfully...");

                    // table load after Delete recored complete
                    id.setText("");
                    name.setText("");
                    mno.setText("");
                    id.requestFocus();
                    refreshandLoadTable(); // after delete data in table load table
                    pst3.close();
                    con.close();
                } catch (Exception ea) {
                    System.out.print(ea);
                }
            }
        });
        btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnDelete.setBounds(427, 425, 136, 42);
        contentPane.add(btnDelete);
        JButton btnLoad = new JButton("Load");
        btnLoad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /*
                 * try {
                 * Class.forName("com.mysql.cj.jdbc.Driver");
                 * Connection
                 * con=DriverManager.getConnection("jdbc:mysql://localhost:3306/emp_data","root"
                 * ,"root");
                 * Statement st=con.createStatement();
                 * String query ="select * from employee_details";
                 * ResultSet rs=st.executeQuery(query);
                 * ResultSetMetaData rsmd=rs.getMetaData();
                 * DefaultTableModel model=(DefaultTableModel)
                 * Emplyoee_Data.getModel();
                 * model.setRowCount(0); //For Refresh table for every time after any
                 * operation
                 * int cols=rsmd.getColumnCount();
                 * String [] colName=new String[cols];
                 * for(int i=0;i<cols;i++)
                 * colName[i]=rsmd.getColumnName(i+1);
                 * model.setColumnIdentifiers(colName);
                 * String empSno,empid1,empname1,empmno1;
                 * int i=1; //order Change s no always for table view data don't
                 * change the order in database
                 * while(rs.next()) {
                 * empSno=String.valueOf(i);
                 * empid1=rs.getString(2);
                 * empname1=rs.getString(3);
                 * empmno1=rs.getString(4);
                 * String[] row= {empSno, empid1,empname1,empmno1};
                 * model.addRow(row);
                 * i++;
                 * }
                 * st.close();
                 * con.close();
                 * }
                 * catch(Exception ea)
                 * {
                 * System.out.print(ea);
                 * }
                 */
                refreshandLoadTable(); // for load table
            }
        });
        btnLoad.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnLoad.setBounds(588, 425, 136, 42);
        contentPane.add(btnLoad);
        JLabel lblNewLabel_1_3 = new JLabel("Enter Id");
        lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_1_3.setBounds(10, 381, 102, 23);
        contentPane.add(lblNewLabel_1_3);
        search = new JTextField();
        search.setText(" ");
        search.setColumns(10);
        search.setBounds(87, 383, 136, 32);
        contentPane.add(search);
        JPanel p2 = new JPanel();
        p2.setBounds(395, 76, 393, 281);
        contentPane.add(p2);
        p2.setBorder(new TitledBorder(null, "Employee Database", TitledBorder.CENTER,
                TitledBorder.TOP, null, Color.RED));
        p2.setLayout(null);
        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(10, 20, 373, 240);
        p2.add(scrollPane_1);
        Employee_Data = new JTable();
        scrollPane_1.setViewportView(Employee_Data);
        Employee_Data.setForeground(Color.BLUE);
        Employee_Data.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                }));
        Employee_Data.setBorder(new LineBorder(new Color(0, 0, 0)));
    }
}
