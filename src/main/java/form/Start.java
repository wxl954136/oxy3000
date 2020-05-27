package form;

import bean.DataEntity;
import bean.SettingField;
import common.CommonUtils;
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
    public JTable tableData;
    public DefaultTableModel dataModel;
    public JScrollPane scrollPanelData;
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
    private JButton btnQuery;
    public final static Color colorBackGround = new Color(47,63,80);
    public final static Color fontColor = new Color(173,206,47);
    String currentPort = "NONE";
//    CommonUtils commonUtils;
    SettingField settingField = new SettingField();
    public String resultDeviceName = "",resultDeviceId = "" ,
                  resultDeviceDate = "",resultDeviceVersion = "";
    public static Start start;
    public static Start getInstance(){
        if(start==null){
            start = new Start();
        }
        return start;
    }

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
/*
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

 */

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
        btnQuery.setText("");
        btnQuery.setIcon( ToolUtils.changeImage(new ImageIcon("./resources/img/search.png"),0.5));
        btnQuery.setToolTipText("Search");
        btnQuery.addActionListener(e -> {
            searchHistory();
        });

        btnExport.setIcon( ToolUtils.changeImage(new ImageIcon("./resources/img/export.png"),0.5));
        btnExport.setText("");
        btnExport.setToolTipText("Export");
        btnExport.addActionListener(e -> {
             exportPDF();
        });
        btnSetting.setIcon( ToolUtils.changeImage(new ImageIcon("./resources/img/settings.png"),0.5));
        btnSetting.setText("");
        btnSetting.addActionListener(e -> {
            /*
            测试代码
                if (true) {
                    FileUtil.setTxtFileData(Start.getInstance().getTableDataList());
                    return ;
                }
             */
                if (!PublicParameter.isReadRecordOver ){
                    JOptionPane.showMessageDialog(this, "数据正在处理，请等待数据处理完毕!", "提示信息", 1);
                    return ;
                }
                String debug = JsonRead.getInstance().getJsonTarget("debug");
                if (debug.equalsIgnoreCase("yes")) showConfig();
                else showSetting();

        });
        btnQuery.setBackground(colorBackGround);
        btnQuery.setBorder(null);
        btnExport.setToolTipText("Setting");
        btnExport.setBackground(colorBackGround);
        btnExport.setBorder(null);
        btnSetting.setBackground(colorBackGround);
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
    public synchronized void readCom()
    {
        PublicParameter.isReadRecordOver = false;
        PublicParameter.currentPort = "NONE";
        removeRowForDetailTable();
        ArrayList<Object> list= CommonUtils.getLocalHostPortNames();
        if (list.size() <= 0)
        {
            connectStatus.setText("Disconnected");
            return ;
        }
        currentPort = "NONE";

        boolean isUsePort = false;
        Collections.reverse(list); // 反过来，一般串口新插上去是最后一个，快速检测
        for (Object port:list){
            if (CommonUtils.portIsUsed(port.toString())){
                try {
                    currentPort = port.toString();
                    CommonUtils.commUtil = null;
                    PublicParameter.commonUtils = CommonUtils.getInstance(currentPort);
                    resultDeviceId = null;
                    String sendMsg =  "start:" + ToolUtils.getFormatMsg(JsonRead.getInstance().getJsonTarget("cxsbxlh","order")); //查询设备序列号
                    //PublicParameter.commonUtils.send(sendMsg);
                    this.serialPortSend(sendMsg);
                    Thread.sleep(1000);
                    if (null == resultDeviceId|| "ERROR".equalsIgnoreCase(resultDeviceId)){
                        PublicParameter.commonUtils.close();
                        isUsePort = false;
                        continue;
                    }
                    PublicParameter.commonUtils.close();
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
            PublicParameter.currentPort = currentPort;
            String sendMsg =  "start:" + ToolUtils.getFormatMsg(JsonRead.getInstance().getJsonTarget("cxsbmc","order")); //查询设备序列号
            serialPortSend(sendMsg);
            Thread.sleep(500);
            String cxsbmcOrderName = ToolUtils.getOrderName("cxsbmc");
            if (resultDeviceName.indexOf(cxsbmcOrderName)>=0 && resultDeviceName.indexOf("ERROR") < 0){
                deviceName.setText("Device Name " + resultDeviceName.substring((cxsbmcOrderName+"=").length() ));
            }else
            {
                deviceName.setText("Device Name " );
            }
            //查询日期
            sendMsg =  "start:" + ToolUtils.getFormatMsg(JsonRead.getInstance().getJsonTarget("cxrq","order")); //查询 设备使用记录
            serialPortSend(sendMsg);
            Thread.sleep(500);
            String cxrqOrderName = ToolUtils.getOrderName("cxrq");
            if (resultDeviceDate.indexOf(cxrqOrderName)>=0 && resultDeviceDate.indexOf("ERROR") < 0){
                deviceDate.setText("Device Date " + resultDeviceDate.substring((cxrqOrderName+"=").length() ));
            }else
            {
                deviceDate.setText("Device Date " );
            }
            sendMsg =  "start:" + ToolUtils.getFormatMsg(JsonRead.getInstance().getJsonTarget("cxgjbb","order")); //查询 设备使用记录
            serialPortSend(sendMsg);
            Thread.sleep(500);
            String cxgjbbOrderName = ToolUtils.getOrderName("cxgjbb");
            if (resultDeviceVersion.indexOf(cxgjbbOrderName)>=0 && resultDeviceVersion.indexOf("ERROR") < 0){
                deviceVersion.setText("Device Version " + resultDeviceVersion.substring((cxgjbbOrderName + "=").length() ));
            }else
            {
                deviceVersion.setText("Device Version " );
            }
            //CommonUtils.commUtil = null;
            //PublicParameter.commonUtils = CommonUtils.getInstance(currentPort);
            sendMsg =  "start:" + ToolUtils.getFormatMsg(JsonRead.getInstance().getJsonTarget("cxsbsyjl","order")); //查询 设备使用记录
            serialPortSend(sendMsg);

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
    public void serialPortSend(String sendMsg){
        PublicParameter.commonUtils = CommonUtils.getInstance(PublicParameter.currentPort);
        PublicParameter.commonUtils.send(ToolUtils.getFormatMsg(sendMsg));
    }
    private void removeRowForDetailTable()
    {
        dataModel.setRowCount( 0 );
    }

    public List<DataEntity> getTableDataList(){
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
    public void prcessTableModelData(String receive){
        try{
            receive = receive.replaceAll("\r","");
            receive = receive.replaceAll("\n","");
            if (receive.length() <  20) return ;
            String _year = receive.substring(0,4);  //年年年年
            String _month = receive.substring(5,7);  //月月
            String _day = receive.substring(7,9);  //日日
            String _hour = receive.substring(10,12);  //时时
            String _sec = receive.substring(12,14);  //分分
            String _use1 = receive.substring(15,17);  //用量1
            String _use2 = receive.substring(17,19);  //用量2
            String _time1 = receive.substring(20,22); //持续时间1
            String _time2 = receive.substring(22,24); //持续时间2
            DataEntity data = new DataEntity();
            data.setsTreatent("");
            data.setsDate(_day + "/" + _month + "/" + _year);
            data.setsTime(_hour + ":" + _sec);
            data.setsVolume(_use1 + _use2);
            data.setsDuration(_time1 + _time2);
            data.setsRoom("");
            data.setsContent("");
            dataModel.addRow(DataColumnsUtils.getListContent(data));
            dataModel.fireTableDataChanged();
            int maxHeight = scrollPanelData.getVerticalScrollBar().getMaximum();
            scrollPanelData.getViewport().setViewPosition(new Point(0,maxHeight));
        }catch(Exception eg){

        }

    }
    private void  showConfig()
    {
        Config config  = Config.getInstance();
        config.initComponentValue();
        config.setIconImage(new ImageIcon("./resources/img/start.png").getImage());//设置图
        config.setTitle(JsonRead.getInstance().getJsonTarget("title"));
        config.setSize(800,600);
        config.setResizable(false);
        config.setLocationRelativeTo(null);//窗体居中显示
        config.setVisible(true);
    }
    private void showSetting(){
        if (currentPort.equalsIgnoreCase("NONE")){
            JOptionPane.showMessageDialog(null,"本机无有效的串口" ,"提示信息", 1);
            return ;
        }
        settingField.setCurrentPort(currentPort);
        Setting setting = Setting.getInstance(settingField);
        setting.setIconImage(new ImageIcon("./resources/img/start.png").getImage());//设置图
        setting.setTitle(JsonRead.getInstance().getJsonTarget("title"));
        setting.setSize(300,200);
       // setting.setMinimumSize(new Dimension(600,500));
        setting.setLocationRelativeTo(null);//窗体居中显示
        //setting.setResizable(false);
        setting.setUndecorated(true);// 不绘制边框
        setting.initComponentValue();
        setting.setVisible(true);
        if (settingField.getAnswer()){

            //deviceDate.setText(settingField.getDeviceDate());
            //返回日期的取值
        }
        
    }
    private void searchHistory()
    {
        String fileFullPath=ToolUtils.getUserDir() + "\\resources\\txt\\history"  ;
        File dirs = new File( fileFullPath);
        File files[] = dirs.listFiles();
        //按查询条件生成需要的文件 ,查询规则待定义
        String searchFileName = "REC-222110228318-20200527095321.json";
        boolean isFound =false;
        for(File file:files){
            if (file.isFile()){
                if (file.getName().equalsIgnoreCase(searchFileName)){
                    isFound = true;
                    break;
                }
            }
        }
        if (!isFound) return ;
        String fileName = fileFullPath + "\\" + searchFileName;
        List<DataEntity>  list =JsonRead.getJsonRecordFileToEntity(fileName);

        removeRowForDetailTable();


        for(DataEntity data : list)
        {
            dataModel.addRow(DataColumnsUtils.getListContent(data));
            dataModel.fireTableDataChanged();
        }
        //保存保存在哪里



    }
    private void exportPDF()
    {
        if (!PublicParameter.isReadRecordOver || dataModel.getRowCount() <=0 ){
            JOptionPane.showMessageDialog(this, "数据正在采集或无数据时，不可导出数据", "提示信息", 1);
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
