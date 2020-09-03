package form;

import bean.PublicValue;
import bean.RememberEntity;
import bean.UserEntity;
import component.CheckBoxIcon;
import utils.FileUtil;
import utils.JsonRead;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private JCheckBox chkRemember;
    public Boolean answer;
    public final static Color colorBackGround = new Color(47,63,80);
    public final static Color fontColor = new Color(173,206,47);
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
        panelUserInfo.setPreferredSize(new Dimension(this.getWidth(),60));
        panelUserInfo.updateUI();
        this.labelUserName.setFont(font);
        this.labelPassword.setFont(font);
        RememberEntity rememberEntity =  getLoginRemeberUser();
        this.txtUserName.setText(rememberEntity.getRemember());
        txtUserName.setSelectionEnd(this.txtUserName.getText().length());
        this.txtUserName.setFont(font);
        this.txtPassword.setFont(font);
//        this.txtPassword.requestFocus();
//        this.txtPassword.requestFocusInWindow();
//        this.txtPassword.setRequestFocusEnabled(true);
        chkRemember.setSelected(true);
        chkRemember.setText("Remember me");
        this.chkRemember.setFont(new Font(null, Font.PLAIN, 17));
//        Icon checked = new CheckBoxIcon();
//        Icon unchecked = new CheckBoxIcon();
//        chkRemember.setIcon(unchecked);
//        chkRemember.setSelectedIcon(checked);
        this.txtNotes.setFont(new Font(null, Font.PLAIN, 19));
        this.txtNotes.setOpaque(false);
        setNotesContent();
        this.btnLogin.setFont(font);
        this.btnLogin.setPreferredSize(new Dimension(100,40));
        btnLogin.setBorder(null);
        btnLogin.setBackground(colorBackGround);
        btnLogin.setForeground(fontColor);
        this.btnLogin.addActionListener(e -> onOK());
        this.btnExit.setFont(font);
        this.btnExit.setPreferredSize(new Dimension(100,40));
        btnExit.setBorder(null);
        btnExit.setBackground(colorBackGround);
        btnExit.setForeground(fontColor);
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
    public void setNotesContent()
    {
        String content = "Note: The defalt username is admin ,user can't delete it.\n";
        content += "Pleasee change default password  after first login. If  \n";
        content += "you lost the admin's password please contact us";
        this.txtNotes.setText(content);
    }
    public RememberEntity getLoginRemeberUser()
    {
        String remember = "",times = "1";
        try{
            remember = JsonRead.getInstance().getJsonContentTarget(JsonRead.jsonLoginFile,"remember");
            if (remember == null )
            {
                remember = "";
            }
        }catch(Exception eg)
        {
             times = JsonRead.getInstance().getJsonContentTarget(JsonRead.jsonLoginFile,"times");
            if (times == null )
            {
                times = "";
            }
        }
        return new RememberEntity(remember,times);
    }
    public void readUserInfo()
    {
        List<UserEntity> listUsers = JsonRead.getJsonRecordFileToUserEntity(JsonRead.jsonUserFile);
        Map map = new HashMap<String,UserEntity>();
        for(UserEntity data:listUsers)
        {
            map.put(data.getUser(),data);
        }
        PublicValue.USERS = map;
    }
    private void onOK() {
        readUserInfo();
        String _user = this.txtUserName.getText().toLowerCase().trim();
        String _pwd = this.txtPassword.getText().toLowerCase().trim();
        UserEntity user = PublicValue.USERS.get(_user);
        if ( user != null)
        {
            if (user.getUser().equalsIgnoreCase(_user) &&
                 user.getPwd().equalsIgnoreCase(_pwd)
            ){
                PublicValue.CURRENT_USER = user;  //记录当前登录的用户
                this.setAnswer(true);
                this.setVisible(false);
                saveToRememberInfo();
                return ;
            }
        }
        Object []option= {"ok"};
        int sel = JOptionPane.showOptionDialog(this,
                "Invalid User name or Password!",
                "Warning",
                JOptionPane.YES_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,option,//按钮的标题
                option[0]);
//        JOptionPane.showMessageDialog(null,
//                "Invalid User name or Password " ,"Warning", 2);
    }
    private void saveToRememberInfo()
    {
        if (this.chkRemember.isSelected())
        {
            RememberEntity data = new RememberEntity();
            data.setRemember(this.txtUserName.getText().trim());
            data.setTimes("1");
            FileUtil.setJsonFileRememberData(data);
        }

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
