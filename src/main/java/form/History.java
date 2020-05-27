package form;

import component.AutoCompleteExtender;
import org.jdesktop.swingx.JXSearchField;
import utils.FileUtil;
import utils.ToolUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class History extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField txtDeviceId;
    private JTextField txtDate;
    public String searchFileName = "";
    public boolean answer = false;

    public static History history;

    public static History getInstance(){

        if(history==null){
            history = new History();
        }
        return history;
    }
    public History() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());
        txtDate.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                onDateFocusGained();
            }
        });
        initComponentStyle();
    }
    public void initComponentStyle()
    {
        buttonOK.setIcon( ToolUtils.changeImage(new ImageIcon("./resources/img/search.png"),0.3));
        buttonOK.setText("Search");
        buttonOK.setPreferredSize(new Dimension(80, 30));
        buttonOK.setBorder(null);
        buttonCancel.setIcon( ToolUtils.changeImage(new ImageIcon("./resources/img/cancel.png"),0.2));
        buttonCancel.setPreferredSize(new Dimension(80, 30));
        buttonCancel.setText("Cancel");
        buttonCancel.setBorder(null);
    }
    public void initComponentValue()
    {
        searchFileName = "";
        answer = false;
        txtDeviceId.setText("");
        Object dataIds[] = getDeivceIds();
        AutoCompleteExtender autoCompleteExtenderId = new AutoCompleteExtender(txtDeviceId, dataIds, null);
        autoCompleteExtenderId.setMatchDataAsync(true);
        autoCompleteExtenderId.setSizeFitComponent();
        autoCompleteExtenderId.setMaxVisibleRows(10);
        // 值最终被选中时会触发该函数
        autoCompleteExtenderId.setCommitListener(value ->
                System.out.println("commit:" + value)
        );
        txtDate.setText("");

    }

    public  Object[] getDeivceIds()
    {
        ArrayList<String> list=new ArrayList<>();
        Map map = FileUtil.getRecordHistory();
        for(Object key:map.keySet()){
            list.add(key.toString());
        }
        String[] strings = new String[list.size()];
        return  list.toArray(strings);
    }
    public  Object[] getDeivceDates(String deviceid)
    {
        ArrayList<String> list=new ArrayList<>();
        Map<String,List<String>> map = FileUtil.getRecordHistory();

        List<String> listGet = map.get(deviceid);
        if (listGet == null) return null;
        for(String val:listGet)
        {
            list.add(val);
        }
        String[] strings = new String[list.size()];
        return  list.toArray(strings);
    }
    public void onDateFocusGained()
    {
 //wxl lmodi

        Object dataDates[] = getDeivceDates(txtDeviceId.getText().trim());
        if (dataDates == null) return ;
        AutoCompleteExtender autoCompleteExtenderDate = new AutoCompleteExtender(txtDate, dataDates, null);
        autoCompleteExtenderDate.setMatchDataAsync(true);
        autoCompleteExtenderDate.setSizeFitComponent();
        autoCompleteExtenderDate.setMaxVisibleRows(10);
        // 值最终被选中时会触发该函数
        autoCompleteExtenderDate.setCommitListener(value ->
                System.out.println("commit:" + value)
        );


    }
    private void onOK() {
        String fileName = FileUtil.getSearchFile(txtDeviceId.getText(),txtDate.getText());
        if (ToolUtils.isEmpty(fileName))
        {
            JOptionPane.showMessageDialog(this, "查询结果无数据", "提示信息", 1);
        }
        this.searchFileName = fileName ;
        answer = true;
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        answer = false;
        dispose();
    }
}
