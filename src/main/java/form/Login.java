package form;

import com.sun.awt.AWTUtilities;
import utils.JsonRead;
import utils.ToolUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.text.SimpleDateFormat;

public class Login extends JDialog{
    private JPanel panel1;
    private JTextField txtUserName;
    private JPasswordField txtPassword;
    private JTextPane txtNotes;
    private JButton btnLogin;
    private JPanel panelUserInfo;
    private JLabel labelUserName;
    private JLabel labelPassword;
    private JButton btnExit;
    public Boolean answer;
    public static Login login;
    public static Login getInstance(){
        if(login==null){
            login = new Login();
        }
        return login;
    }
    public Login()
    {
        setModal(true);
        setContentPane(panel1);
        Font font = new Font("微软雅黑", Font.PLAIN, 18);
        //panelUserInfo.setFont(new Font("微软雅黑", Font.PLAIN, 32));
        panelUserInfo.setPreferredSize(new Dimension(this.getWidth(),60));
        panelUserInfo.updateUI();
        this.labelUserName.setFont(font);
        this.labelPassword.setFont(font);
        this.txtUserName.setFont(font);
        this.txtPassword.setFont(font);
        this.txtNotes.setFont(new Font("微软雅黑", Font.PLAIN, 19));
        this.txtNotes.setOpaque(false);
        this.btnLogin.setFont(font);
        this.btnLogin.setPreferredSize(new Dimension(100,40));

        this.btnLogin.addActionListener(e -> onOK());
        this.btnExit.setFont(font);
        this.btnExit.setPreferredSize(new Dimension(100,40));
        this.btnExit.addActionListener(e -> onCancel());
        this.setUndecorated(true);// 不绘制边框
        this.setBounds(new Rectangle(0,0,1,1));
        this.setSize(500,380);
        this.setMinimumSize(new Dimension(500,380));
        this.setLocationRelativeTo(null);//窗体居中显示
        this.panel1.setBorder(new LineBorder(Color.DARK_GRAY));
//        this.setDefaultLookAndFeelDecorated(true);
//        this.panel1.setBorder(new LineBorder(PanelColor, 5, true));
//        AWTUtilities.setWindowShape(this, new RoundRectangle2D.Double(
//                0.0D, 0.0D, this.getWidth(), this.getHeight(), 26.0D,
//                26.0D));

        this.setVisible(true);
    }
    private void onOK() {
        // add your code here
        this.setAnswer(true);
        this.setVisible(false);


    }
    private void onCancel() {
        // add your code here
        System.exit(0);


    }
    public Boolean getAnswer() {
        return answer;
    }

    public void setAnswer(Boolean answer) {
        this.answer = answer;
    }
}
