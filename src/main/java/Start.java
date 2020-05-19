import bean.DataEntity;
import common.CommonUtils;
import form.Setting;
import pdf.PdfUtils;
import sun.swing.table.DefaultTableCellHeaderRenderer;
import utils.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * http://www.icosky.com/icon-search/save图标合集选择自己喜欢的图标
 *
 *
 *
 */
public class Start extends JFrame {
    private JPanel contentPane;
    private JPanel panelTop;
    private JTable tableData;
    private DefaultTableModel dataModel;
    private JScrollPane scrollPanelData;
    private JPanel panelBottom;
    private JPanel panelTopLeft;
    private JPanel panelTopCenter;
    private JPanel panelTopRight;
    private JLabel deviceName;
    private JLabel deviceDate;
    private JLabel softwareVersion;
    private JLabel deviceVersion;
    private JLabel connectStatus;

    private JLabel logoImage;
    private JButton btnExport;
    private JButton btnSetting;
    private JLabel softwareVersionValue;
    private JLabel deviceVersionValue;
    public final static Color colorBackGround = new Color(47,63,80);
    public final static Color fontColor = new Color(173,206,47);
    String currentPort = "NONE";
    CommonUtils commonUtils;

    public Start() {
        setContentPane(contentPane);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        initSystemStyle();
        initDetailTable();
    }

    public static void main(String[] args) {
        try {
            sysLoadLibraryForDLL();
            String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel";
            UIManager.setLookAndFeel(lookAndFeel);
            ToolUtils.initSystemFontStyle();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Start frame = new Start();
        frame.setIconImage(new ImageIcon("./resources/img/start.png").getImage());//设置图
        frame.setTitle(JsonRead.getInstance().getJsonTarget("title"));
        frame.setSize(860,600);
        frame.setMinimumSize(new Dimension(680,480));
        frame.setLocationRelativeTo(null);//窗体居中显示
        frame.setVisible(true);
        frame.readCom();
    }
    public static void sysLoadLibraryForDLL(){
        try{
            ClassLoaderUtils.loadSerialDynamically();
        }catch(Exception eg){
            eg.printStackTrace();
        }
    }
    private  void initSystemStyle()
    {
        connectStatus.setText("Disconnected");
        panelTop.setBackground(colorBackGround);
        panelTopLeft.setBackground(colorBackGround);
        panelTopCenter.setBackground(colorBackGround);
        panelTopRight.setBackground(colorBackGround);
        deviceName.setForeground(fontColor);
        deviceDate.setForeground(fontColor);
        softwareVersion.setForeground(fontColor);
        deviceVersion.setForeground(fontColor);
        softwareVersionValue.setForeground(fontColor);
        deviceVersionValue.setForeground(fontColor);
        panelBottom.setBackground(colorBackGround);
        connectStatus.setForeground(fontColor);
        scrollPanelData.setBackground(colorBackGround);
        logoImage.setText("");
        ImageIcon image = new ImageIcon("./resources/img/logo.jpg");
        logoImage.setIcon( ToolUtils.changeImage(image,0.5));
        Dimension dim = new Dimension(this.getWidth(),60);
        //panelTop.setPreferredSize(dim);
        panelTop.setSize(dim);
        panelTop.updateUI();
        panelBottom.setSize(dim);
        panelBottom.updateUI();
        btnExport.setIcon( ToolUtils.changeImage(new ImageIcon("./resources/img/export.png"),0.5));
        btnExport.setText("");
        btnExport.setToolTipText("Export");
        btnExport.addActionListener(e -> {
            if (true){
                exportPDF();
            }else
            {
                JOptionPane.showMessageDialog(null,"未正确采集数据或数据正在采集中，无法导出" ,"提示信息", 1);
            }
        });
        btnSetting.setIcon( ToolUtils.changeImage(new ImageIcon("./resources/img/settings.png"),0.5));
        btnSetting.setText("");
        btnSetting.addActionListener(e -> {
                showSetting();
        });
        btnExport.setToolTipText("Setting");
        btnExport.setBackground(colorBackGround);
        btnSetting.setBackground(colorBackGround);
        btnExport.setBorder(null);
        btnSetting.setBorder(null);
        scrollPanelData.setBorder(null);
        tableData.setBorder(null);
        softwareVersionValue.setText(JsonRead.getInstance().getJsonTarget("version"));
        deviceVersionValue.setText("");
    }


    public void initDetailTable() {
        Vector<Vector<Object>> rowDatas = new Vector<Vector<Object>>();
        dataModel = new DefaultTableModel(rowDatas, getTableColumnName()){
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        tableData.setModel(dataModel);
        DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
        cr.setHorizontalAlignment(JLabel.CENTER);
        tableData.setDefaultRenderer(Object.class, cr);
        DefaultTableCellHeaderRenderer hr = new DefaultTableCellHeaderRenderer();
        hr.setHorizontalAlignment(JLabel.CENTER);
        tableData.getTableHeader().setDefaultRenderer(hr);
        initDetailTableStyle(tableData);
    }
    private Vector<String> getTableColumnName(){
        Vector<String> columnNames = new Vector<String>();
        columnNames.add("Treatent");
        columnNames.add("Date");
        columnNames.add("Time");
        columnNames.add("Volume");
        columnNames.add("Duration");
        columnNames.add("Operator name");
        columnNames.add("Room");
        columnNames.add("Content");
        return columnNames;
    }
    private void initDetailTableStyle(JTable table)
    {
        table.setRowHeight(30);
        table.setForeground(Color.BLACK);
        table.setFont(new Font(null, Font.PLAIN, 14));
        table.setSelectionForeground(Color.DARK_GRAY);
        table.setSelectionBackground(Color.LIGHT_GRAY);
        table.setGridColor(Color.GRAY);
        table.getTableHeader().setBackground(fontColor);
        scrollPanelData.setBackground(colorBackGround);
        this.setBackground(colorBackGround);
        //table.getColumnModel().getColumn(0).setPreferredWidth(40); 设置列宽
    }
    private synchronized void readCom()
    {


        PublicParameter.isReadRecordOver = false;
        removeRowForDetailTable();
        ArrayList<Object> list= CommonUtils.getLocalHostPortNames();
        if (list.size() <= 0)
        {
            connectStatus.setText("Disconnected");
            return ;
        }
        currentPort = "NONE";
        boolean isUsePort = false;
        Collections.reverse(list); // 返过来，一般串口新插上去是最后一个，快速检测
        for (Object port:list){
            if (CommonUtils.portIsUsed(port.toString())){
                try {
                    currentPort = port.toString();
                    CommonUtils.commUtil = null;
                    commonUtils = CommonUtils.getInstance(currentPort);
                    String _sendContent = "at+deviceid=?";
                    String _endChar = "\r\n";
                    commonUtils.deviceId = null;
                    commonUtils.send(_sendContent + _endChar);
                    if (null == commonUtils.deviceId || "ERROR".equalsIgnoreCase(commonUtils.deviceId)){
                        commonUtils.close();
                        isUsePort = false;
                        continue;
                    }
                    commonUtils.close();
                }catch(Exception eg)
                {
                    isUsePort = false;
                    continue;
                }
                isUsePort = true;
                break;
            }
        }
        if (!isUsePort) {
            connectStatus.setText("Disconnected");
            return ;
        }
        connectStatus.setText("Connected");
        try{
            //获取DeviceId
            CommonUtils.commUtil = null;
            commonUtils = CommonUtils.getInstance(currentPort);
            String _sendContent = "at+deviceid=?";
            String _endChar = "\r\n";
            commonUtils.send(_sendContent + _endChar);
            deviceName.setText("Device Name " + commonUtils.deviceId.substring("at+deviceid=".length() ));
            // CommonUtils.commUtil = null;
            commonUtils = CommonUtils.getInstance(currentPort);
            commonUtils.dataModel = dataModel;
            _sendContent = "at+record=?";
            _endChar = "\r\n";
            commonUtils.send(_sendContent + _endChar);
        }catch(Exception e)
        {
            String className = e.getClass().toString();
            if (className.indexOf("gnu.io.PortInUseException") >=0){
                JOptionPane.showMessageDialog(null,"当前使用的串口被其它应用打开占用" ,"提示信息", 1);
            }else
            {
                e.printStackTrace();
            }
        }
    }
    private void removeRowForDetailTable()
    {
        dataModel.setRowCount( 0 );
    }

    private List<DataEntity> getTableDataList(){
        List<DataEntity> list = new ArrayList<DataEntity>();
        for (int row = 0 ; row< dataModel.getRowCount(); row++){
            DataEntity value = new DataEntity();
            for (int col = 0 ; col <dataModel.getColumnCount() ; col ++ )
            {
                String val = dataModel.getValueAt(row,col) == null?"":dataModel.getValueAt(row,col).toString();
                switch(col)
                {
                    case DataColumnsUtils.COL_TREATENT :
                        value.setsTreatent(val);
                        break;
                    case DataColumnsUtils.COL_DATE:
                        value.setsDate(val);
                        break;
                    case DataColumnsUtils.COL_TIME:
                        value.setsTime(val);
                        break;
                    case DataColumnsUtils.COL_VOLUME:
                        value.setsVolume(val);
                        break;
                    case DataColumnsUtils.COL_DURATION:
                        value.setsDuration(val);
                        break;
                    case DataColumnsUtils.COL_OPERATORNAME:
                        value.setsOperatorName(val);
                        break;
                    case DataColumnsUtils.COL_ROOM:
                        value.setsRoom(val);
                        break;
                    case DataColumnsUtils.COL_CONTENT:
                        value.setsContent(val);
                        break;
                }
            }
            list.add(value);

        }
        return list;

    }
    private void showSetting(){
        Setting setting = new Setting();
        setting.setIconImage(new ImageIcon("./resources/img/start.png").getImage());//设置图
        setting.setTitle(JsonRead.getInstance().getJsonTarget("title"));
        setting.setSize(300,200);
       // setting.setMinimumSize(new Dimension(600,500));
        setting.setLocationRelativeTo(null);//窗体居中显示
        //setting.setResizable(false);
        setting.setUndecorated(true);// 不绘制边框
        setting.setVisible(true);
        
    }
    private void exportPDF()
    {
        if (!PublicParameter.isReadRecordOver ){
            JOptionPane.showMessageDialog(this, "请稍等:数据正在采集，采集完后再进行导出", "提示信息", 1);
            return ;
        }
        JFileChooser jfc=new JFileChooser();
        //jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  //仅能选择目录
        int jfcOper = jfc.showDialog(new JLabel(), "请选择文件导出路径");
        if (jfcOper != JFileChooser.APPROVE_OPTION) return ;  //当点击取消按钮时

        File file=jfc.getSelectedFile();
        if (!file.exists()) {
            JOptionPane.showMessageDialog(this, "导出文件的路径不存在", "提示信息", 1);
            return;
        }
        if(file.isDirectory()){
            System.out.println("文件夹:"+file.getAbsolutePath());
        }else if(file.isFile()){
            System.out.println("java-文件:"+file.getAbsolutePath());
        }
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        String fileFullName = file.getAbsolutePath() + "\\" + sdf.format(currentDate) + ".pdf";
        fileFullName = fileFullName.replace("\\\\","\\"); //，当选择根目录的时候，可能会有异常,不替换windows下适配
        try{
            PdfUtils.createHardwarePDF(fileFullName,getTableDataList());
        }catch(Exception e){
            e.printStackTrace();
        }

    }



    private void onCancel() {


        if (CommonUtils.commUtil != null) {
            CommonUtils.commUtil.close();
            CommonUtils.commUtil = null;
        }
        dispose();
        System.exit(0);
    }
}
