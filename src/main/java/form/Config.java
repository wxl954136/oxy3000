package form;

import common.CommonUtils;
import utils.JsonRead;
import utils.PublicParameter;
import utils.ToolUtils;

import javax.swing.*;

public class Config extends JDialog {
    private JPanel contentPane;
    private JButton btnCxpwsl;
    private JTextField txtCxpwsl;
    private JButton btnCxrq;
    private JTextField txtCxrq;
    private JButton btnCxgjbb;
    private JTextField txtCxgjbb;
    private JButton btnFwsb;
    private JButton btnGbmfq;
    private JButton btnDkfmq;
    private JButton btnGbsb;
    private JButton btnDksb;
    private JButton btnSdpwsl;
    private JTextField txtSdpwsl;
    private JButton btnSdrq;
    private JTextField txtSdrq;
    private JButton btnCxsbsyjl;
    private JEditorPane txtCxsbsyjl;
    private JButton btnCxyl;
    private JButton btnSdyl;
    private JTextField txtCxyl;
    private JTextField txtSdyl;
    private JButton btnCxsj;
    private JTextField txtCxsj;
    private JTextField txtSdsj;
    private JButton btnSdsj;
    private JButton btnCxys;
    private JButton btnSdys;
    private JTextField txtCxys;
    private JTextField txtSdys;
    private JButton btnCxsbxlh;
    private JButton btnSdsbxlh;
    private JTextField txtCxsbxlh;
    private JTextField txtSdsbxlh;
    private JScrollPane scrollPaneCxsbsyjl;
    private JButton btnCxsbmc;
    private JTextField txtCxsbmc;
    public static Config config;
    public static Config getInstance(){

        if(config==null){
            config = new Config();
        }
        return config;
    }

    public Config() {
        setContentPane(contentPane);
        setModal(true);
        btnCxsbmc.addActionListener(e -> onCxsbmc());
        btnCxgjbb.addActionListener(e -> onCxgjbb());
        btnFwsb.addActionListener(e -> onFwsb());
        btnDksb.addActionListener(e -> onDksb());
        btnGbsb.addActionListener(e -> onGbsb());
        btnDkfmq.addActionListener(e -> onDkfmq());
        btnGbmfq.addActionListener(e -> onGbfmq());
        btnCxpwsl.addActionListener(e -> onCxpwsl());
        btnSdpwsl.addActionListener(e -> onSdpwsl());
        btnCxrq.addActionListener(e -> onCxrq());
        btnSdrq.addActionListener(e -> onSdrq());
        btnCxyl.addActionListener(e -> onCxyl());
        btnSdyl.addActionListener(e -> onSdyl());
        btnCxsj.addActionListener(e -> onCxsj());
        btnSdsj.addActionListener(e -> onSdsj());
        btnCxys.addActionListener(e -> onCxys());
        btnSdys.addActionListener(e -> onSdys());
        btnCxsbxlh.addActionListener(e -> onCxsbxlh());
        btnSdsbxlh.addActionListener(e -> onSdsbxlh());
        btnCxsbsyjl.addActionListener(e -> onCxsbsyjl());

    }

    public void initComponentValue()
    {
        txtCxsbmc.setText("");
        txtCxpwsl.setText("");
        txtCxrq.setText("");
        txtCxgjbb.setText("");
        txtSdpwsl.setText("");
        txtSdrq.setText("");
        txtCxsbsyjl.setText("");
        txtCxyl.setText("");
        txtSdyl.setText("");
        txtCxsj.setText("");
        txtSdsj.setText("");
        txtCxys.setText("");
        txtSdys.setText("");
        txtCxsbxlh.setText("");
        txtSdsbxlh.setText("");
    }
    public void serialPortSend(String sendMsg){
        PublicParameter.commonUtils = CommonUtils.getInstance(PublicParameter.currentPort);
        PublicParameter.commonUtils.send(ToolUtils.getFormatMsg(sendMsg));
    }
    //查询固件版本  无返回值
    private void onCxgjbb(){
        String debugOrder = "debug:" + JsonRead.getInstance().getJsonTarget("cxgjbb","order");
        serialPortSend(debugOrder);
    }
    //查询固件版本  无返回值
    private void onCxsbmc(){
        String debugOrder = "debug:" + JsonRead.getInstance().getJsonTarget("cxsbmc","order");
        System.out.println("x=====" + debugOrder);
        serialPortSend(debugOrder);
    }
    //复位设备事件
    private void onFwsb()
    {
        String debugOrder = "debug:" + JsonRead.getInstance().getJsonTarget("fwsb","order");
        serialPortSend(debugOrder);
    }

    //打开设备
    private void onDksb(){
        String debugOrder = "debug:" + JsonRead.getInstance().getJsonTarget("dksb","order");
        serialPortSend(debugOrder);
    }
    //关闭设备
    private void onGbsb(){
        String debugOrder = "debug:" + JsonRead.getInstance().getJsonTarget("gbsb","order");
        serialPortSend(debugOrder);
    }
    //打开蜂鸣器
    private void onDkfmq(){
        String debugOrder = "debug:" + JsonRead.getInstance().getJsonTarget("dkfmq","order");
        serialPortSend(debugOrder);
    }
    //关闭蜂鸣器
    private void onGbfmq(){
        String debugOrder = "debug:" + JsonRead.getInstance().getJsonTarget("gbfmq","order");
        serialPortSend(debugOrder);
    }
    //查询喷雾速率
    private void onCxpwsl(){
        String debugOrder = "debug:" + JsonRead.getInstance().getJsonTarget("cxpwsl","order");
        serialPortSend(debugOrder);
    }
    //设定喷雾速率
    private void onSdpwsl(){
        String debugOrder = "debug:" + JsonRead.getInstance().getJsonTarget("sdpwsl","order");
        System.out.println("a1==========看法嗎：" + debugOrder);
        //serialPortSend(debugOrder);

    }
    //查询 日期
    private void onCxrq(){
        String debugOrder = "debug:" + JsonRead.getInstance().getJsonTarget("cxrq","order");
        serialPortSend(debugOrder);
    }
    //设定日期
    private void onSdrq(){

    }
    //查询 用量
    private void onCxyl(){
        String debugOrder = "debug:" + JsonRead.getInstance().getJsonTarget("cxyl","order");
        serialPortSend(debugOrder);
    }
    //设定用量
    private void onSdyl(){

    }
    //查询时间
    private void onCxsj(){
        String debugOrder = "debug:" + JsonRead.getInstance().getJsonTarget("cxsj","order");
        serialPortSend(debugOrder);
    }
    //设定时间
    private void onSdsj(){

    }
    //查询延时
    private void onCxys(){
        String debugOrder = "debug:" + JsonRead.getInstance().getJsonTarget("cxys","order");
        serialPortSend(debugOrder);
    }
    //设定延时
    private void onSdys(){

    }
    //查询设备序列号
    private void onCxsbxlh(){
        String debugOrder = "debug:" + JsonRead.getInstance().getJsonTarget("cxsbxlh","order");
        serialPortSend(debugOrder);
    }
    //设定设备序列号
    private void onSdsbxlh(){

    }

    //查询设备使用记录
    private void onCxsbsyjl(){
        config.txtCxsbsyjl.setText("");
        String debugOrder = "debug:" + JsonRead.getInstance().getJsonTarget("cxsbsyjl","order");
        serialPortSend(debugOrder);
    }

    public static void showCollectValue(String sendMessage,String result){
        if (sendMessage.equalsIgnoreCase( JsonRead.getInstance().getJsonTarget("fwsb","order")))
        {
            System.out.println("复位设备:" + result);
        }
        if (sendMessage.equalsIgnoreCase( JsonRead.getInstance().getJsonTarget("dksb","order")))
        {
            System.out.println("打开设备成功:" + result);
        }
        if (sendMessage.equalsIgnoreCase( JsonRead.getInstance().getJsonTarget("cxsbmc","order")))
        {
            config.txtCxsbmc.setText(result);
        }
        if (sendMessage.equalsIgnoreCase( JsonRead.getInstance().getJsonTarget("cxpwsl","order")))
        {
            config.txtCxpwsl.setText(result);
        }
        if (sendMessage.equalsIgnoreCase( JsonRead.getInstance().getJsonTarget("cxrq","order")))
        {
            config.txtCxrq.setText(result);
        }
        if (sendMessage.equalsIgnoreCase( JsonRead.getInstance().getJsonTarget("cxyl","order")))
        {
            config.txtCxyl.setText(result);
        }
        if (sendMessage.equalsIgnoreCase( JsonRead.getInstance().getJsonTarget("cxsj","order")))
        {
            config.txtCxsj.setText(result);
        }
        if (sendMessage.equalsIgnoreCase( JsonRead.getInstance().getJsonTarget("cxys","order")))
        {
            config.txtCxys.setText(result);
        }
        if (sendMessage.equalsIgnoreCase( JsonRead.getInstance().getJsonTarget("cxsbxlh","order")))
        {
            config.txtCxsbxlh.setText(result);
        }
        if (sendMessage.equalsIgnoreCase( JsonRead.getInstance().getJsonTarget("cxsbsyjl","order")))
        {
            String text = config.txtCxsbsyjl.getText();
            config.txtCxsbsyjl.setText(text + result );
            /*
            JScrollBar   sbar = config.scrollPaneCxsbsyjl.getVerticalScrollBar();
            sbar.setValue(sbar.getMaximum());

             */
            config.txtCxsbsyjl.setCaretPosition(config.txtCxsbsyjl.getDocument().getLength());
        }
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
