package form;

import common.CommonUtils;
import utils.JsonRead;
import utils.PublicParameter;
import utils.ToolUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Console  extends JDialog{
    private JTextArea txtCommand;
    private JTextField txtOrder;
    private JButton btnSend;
    private JPanel panel1;
    private JLabel labelTitle;
    private JPanel panelTop;
    public  JScrollPane scrollPaneCommand;
    public final static Color colorBackGround = new Color(47,63,80);
    public final static Color fontColor = new Color(173,206,47);
    public static Console console;

    public static Console getInstance(){

        if(console==null){
            console = new Console();
        }
        return console;
    }
    public Console() {
        this.setSize(500,500);
        this.setMinimumSize(new Dimension(500,500));
        this.setLocationRelativeTo(null);//窗体居中显示
        setContentPane(panel1);
        this.setIconImage(new ImageIcon("./resources/img/start.png").getImage());//设置图
        this.setTitle(JsonRead.getInstance().getJsonTarget("title"));
        setModal(true);
//        initTableDataModel();
//        initDetailTable(this.tableUser);
//        initDetailTableStyle();
//        readUserInfo();
//        setNotesContent();
//        txtNotes.setFont(new Font(null, Font.PLAIN, 13));
//        txtNotes.setOpaque(false);

        this.btnSend.addActionListener(e -> onSend());
        initComponent();
    }
    public void initComponent()
    {
        txtCommand.setEditable(false);
        btnSend.setBorder(null);
        btnSend.setBackground(colorBackGround);
        btnSend.setForeground(fontColor);
        btnSend.setText("Send");
        this.labelTitle.setText("Device  Terminal");
        labelTitle.setForeground(fontColor);
        labelTitle.setBackground(colorBackGround);

        labelTitle.setFont(new Font(null, Font.PLAIN, 18));
        panelTop.setBackground(colorBackGround);
    }
    public void onSend()
    {
        String order = this.txtOrder.getText().trim();
        txtCommand.setText(this.txtCommand.getText().trim() + "\n" + "->" + order);
        String sendMsg =  "console:" + order + "\r\n"; //查询设备序列号
        try{
            Thread.sleep(400);
        }catch(Exception eg){}

        serialPortSend(sendMsg);


    }
    public void serialPortSend(String sendMsg){
        waitExecute();
        PublicParameter.commonUtils = CommonUtils.getInstance(PublicParameter.currentPort);
        PublicParameter.commonUtils.send(ToolUtils.getFormatMsg(sendMsg));
    }
    private void waitExecute()
    {
        try{
            Thread.sleep(400);
        }catch(Exception e){}
    }

    public static void showConsoleValue(String sendMessage,String result)
    {
        console.txtCommand.setText(console.txtCommand.getText().trim() + "\n" + ":>" + result);
        console.txtCommand.setCaretPosition(console.txtCommand.getText().length());
    }
}
