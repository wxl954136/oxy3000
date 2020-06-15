package form;

/*
手动布局 https://www.roseindia.net/tutorial/java/swing/datePicker.html
https://how2j.cn/k/gui/gui-datepicker/421.html#nowhere
http://www.java2s.com/Code/Jar/s/Downloadswingxcore1651sourcesjar.htm
 */
import bean.SettingField;
import common.CommonUtils;
import org.jdesktop.swingx.JXDatePicker;
import utils.JsonRead;
import utils.PublicParameter;
import utils.ToolUtils;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Setting extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel settings;
    private JPanel settingPanel;
    private JTextField txtName;
    private JComboBox comboxHour;
    private JComboBox comboxMinute;
    private JXDatePicker datepicker;
    private JPanel datePanel;
    private SettingField settingField;
    public Map<String,String> mapOrder = new HashMap<>();
    public Map<String,String> mapResult = new HashMap<>();
    public static Setting setting;
    public static Setting getInstance(SettingField settingField){
        if(setting==null){
            setting = new Setting(settingField);
        }
        return setting;
    }
    public Setting(SettingField settingField) {
        this.settingField = settingField;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        /*
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
         */
        contentPane.setBorder(BorderFactory.createEtchedBorder() );
        initComponentStyle();

    }
    private void initComponentStyle()
    {
        settings.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        Dimension dim = new Dimension(this.getWidth(),20);
        //panelTop.setPreferredSize(dim);
        settingPanel.setSize(dim);
        settingPanel.updateUI();
        buttonOK.setIcon( ToolUtils.changeImage(new ImageIcon("./resources/img/save.png"),0.3));
        buttonOK.setText("Save");
        buttonOK.setPreferredSize(new Dimension(80, 30));
        buttonOK.setBorder(null);
        buttonCancel.setIcon( ToolUtils.changeImage(new ImageIcon("./resources/img/cancel.png"),0.2));
        buttonCancel.setPreferredSize(new Dimension(80, 30));
        buttonCancel.setText("Cancel");
        buttonCancel.setBorder(null);

        for (int i = 0 ; i <=24; i++)
        {
            String item = (i < 10? "0"+i : String.valueOf(i));
            comboxHour.addItem(item);
        }
        for (int i = 0 ; i <=59; i++)
        {
            String item = (i < 10? "0"+i : String.valueOf(i));
            comboxMinute.addItem(item);
        }
        comboxHour.setSelectedItem(new SimpleDateFormat("HH").format(new Date()));
        comboxMinute.setSelectedItem(new SimpleDateFormat("mm").format(new Date()));
        datepicker.setFormats( new SimpleDateFormat("yyyy-MM-dd"));
        // 设置 date日期
        datepicker.setDate(new Date());
        datepicker.setBounds(137, 83, 177, 24);
        mapResult.clear();
        mapResult.put("sdsbmc","NONE");
        mapResult.put("sdrq","NONE");
        mapResult.put("sdsj","NONE");
    }
    private void onOK() {
        // add your code here
        settingField.setAnswer(true);
        settingField.setDeviceName(txtName.getText());
        settingField.setDeviceDate(new SimpleDateFormat("yyyyMMdd").format(datepicker.getDate()));
        settingField.setDeviceHour(comboxHour.getSelectedItem().toString());
        settingField.setDeviceMinute(comboxMinute.getSelectedItem().toString());
        //设定设备名称
        String sdsbmcOrder = "setting:" + JsonRead.getInstance().getJsonTarget("sdsbmc","order");
        String sdsbmcKey =  ToolUtils.getSendOrderKey(sdsbmcOrder);
        sdsbmcOrder = sdsbmcOrder.replaceAll(sdsbmcKey ,settingField.getDeviceName());
        mapOrder.put(JsonRead.getInstance().getJsonTarget("sdsbmc","order"),sdsbmcOrder.replaceAll("setting:",""));
        serialPortSend(sdsbmcOrder);
        try {
            Thread.sleep(1000);
        }catch(Exception e){}
        //设定日期
        String sdrqOrder = "setting:" + JsonRead.getInstance().getJsonTarget("sdrq","order");
        String sdrqKey =  ToolUtils.getSendOrderKey(sdrqOrder);
        sdrqOrder = sdrqOrder.replaceAll(sdrqKey ,settingField.getDeviceDate());
        mapOrder.put(JsonRead.getInstance().getJsonTarget("sdrq","order"),sdrqOrder.replaceAll("setting:",""));
        serialPortSend(sdrqOrder);
        try {
            Thread.sleep(1000);
        }catch(Exception e){}
        //设定时间
        String sdsjOrder = "setting:" + JsonRead.getInstance().getJsonTarget("sdsj","order");
        String sdsjKey =  ToolUtils.getSendOrderKey(sdsjOrder);
        sdsjOrder = sdsjOrder.replaceAll(sdsjKey ,settingField.getAtTime());
        mapOrder.put(JsonRead.getInstance().getJsonTarget("sdsj","order"),sdsjOrder.replaceAll("setting:",""));
        serialPortSend(sdsjOrder);

    }
    public void serialPortSend(String sendMsg){
        PublicParameter.commonUtils = CommonUtils.getInstance(PublicParameter.currentPort);
        PublicParameter.commonUtils.send(ToolUtils.getFormatMsg(sendMsg));
    }
    public static void showSettingValue(String sendMessage,String result){
        String sdsbmc = setting.mapOrder.get(JsonRead.getInstance().getJsonTarget("sdsbmc","order"));
        if (sendMessage.equalsIgnoreCase( sdsbmc))
        {
            String sdsbmcSuccess = JsonRead.getInstance().getJsonTarget("sdsbmc","success").trim();
            setting.mapResult.put("sdsbmc",result.indexOf(sdsbmcSuccess) >=0?"success":"error");
            return;
        }
        String sdrq = setting.mapOrder.get(JsonRead.getInstance().getJsonTarget("sdrq","order"));
        if (sendMessage.equalsIgnoreCase(sdrq))
        {
            String sdrqSuccess = JsonRead.getInstance().getJsonTarget("sdrq","success").trim();
            setting.mapResult.put("sdrq",result.indexOf(sdrqSuccess) >=0?"success":"error");
            return;
        }
        String sdsj = setting.mapOrder.get(JsonRead.getInstance().getJsonTarget("sdsj","order"));
        if (sendMessage.equalsIgnoreCase( sdsj))
        {
            String sdsjSuccess = JsonRead.getInstance().getJsonTarget("sdsj","success").trim();
            setting.mapResult.put("sdsj",result.indexOf(sdsjSuccess) >=0?"success":"error");
            setting.showResultDialog();  //注意更新顺序，最后一个更新的是时间，完毕后弹出对话框
            return;
        }
    }
    public void showResultDialog()
    {
        String result = "";
        for (Map.Entry<String, String> map : setting.mapResult.entrySet()) {
            if (map.getKey().equalsIgnoreCase("sdsbmc"))
            {
                result +=  "Device name:" + (map.getValue().equalsIgnoreCase("success")?"update success":"update failed") + "\n";
            }
            if (map.getKey().equalsIgnoreCase("sdrq"))
            {
                result += "Device date:" + (map.getValue().equalsIgnoreCase("success")?"update success":"update failed") + "\n";
            }
            if (map.getKey().equalsIgnoreCase("sdsj"))
            {
                result += "Device time:" + (map.getValue().equalsIgnoreCase("success")?"update success":"update failed") + "\n";
            }
        }

        JOptionPane.showMessageDialog(this,result ,"Information", 1);
    }
    private void onCancel() {
        settingField.setAnswer(false);
        dispose();
    }
    public void initComponentValue()
    {
        txtName.setText("");
        comboxHour.setSelectedItem(new SimpleDateFormat("HH").format(new Date()));
        comboxMinute.setSelectedItem(new SimpleDateFormat("mm").format(new Date()));
        datepicker.setFormats( new SimpleDateFormat("yyyy-MM-dd"));
        // 设置 date日期
        datepicker.setDate(new Date());
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
