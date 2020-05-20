package form;

/*
手动布局 https://www.roseindia.net/tutorial/java/swing/datePicker.html
https://how2j.cn/k/gui/gui-datepicker/421.html#nowhere
http://www.java2s.com/Code/Jar/s/Downloadswingxcore1651sourcesjar.htm
 */
import bean.SettingField;
import org.jdesktop.swingx.JXDatePicker;
import utils.ToolUtils;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Setting extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel settings;
    private JPanel settingPanel;
    private JTextField txtName;
    private JComboBox comboxStartHour;
    private JComboBox comboxEndHour;
    private JXDatePicker datepicker;
    private JPanel datePanel;
    private SettingField settingField;


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


        for (int i = 1 ; i <=12; i++)
        {
            String item = (i < 10? "0"+i : String.valueOf(i));
            comboxStartHour.addItem(item);
            comboxEndHour.addItem(item);
        }

        datepicker.setFormats( new SimpleDateFormat("yyyy-MM-dd"));
        // 设置 date日期
        datepicker.setDate(new Date());
        datepicker.setBounds(137, 83, 177, 24);



        //comboxStartHour.getModel().setSelectedItem(items);
        //Device Date Device hour Device Name
        /*
        dim = new Dimension(10,comboHour1.getHeight());
        comboHour1.setSize(dim);
        comboHour1.updateUI();
        */

        //txtStartHour.setSize(dim);






    }
    private void onOK() {
        // add your code here
        settingField.setAnswer(true);
        settingField.setDeviceName(txtName.getText());
        settingField.setDeviceDate(new SimpleDateFormat("yyyy-MM-dd").format(datepicker.getDate()));
        dispose();
    }

    private void onCancel() {
        settingField.setAnswer(false);
        dispose();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
