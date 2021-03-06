package form;

import bean.DataEntity;
import bean.PublicValue;
import bean.SettingField;
import common.CommonUtils;
import pdf.PdfUtils;
import sun.swing.table.DefaultTableCellHeaderRenderer;
import utils.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

/**
 * http://www.icosky.com/icon-search/save图标合集选择自己喜欢的图标
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
    public JLabel deviceName;
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
    public JComboBox comboBoxDeviceId;
    private JLabel labelDeviceID;
    public JTabbedPane tabbedPane1;
    private JTable tableHistory;
    private JScrollPane scrollPanelHIstory;
    private JButton btnUsers;
    private JButton btnConsole;
    public DefaultTableModel dataModelHistory;
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
        initTableDataModel();
        initDetailTable(this.tableData);
        initDetailTable(this.tableHistory);
        initDetailTableStyle();


    }


    private  void initSystemStyle()
    {
        connectStatus.setText("Disconnected");
        panelTop.setBackground(colorBackGround);
        panelTopLeft.setBackground(colorBackGround);
        panelTopCenter.setBackground(colorBackGround);
        panelTopRight.setBackground(colorBackGround);
        deviceName.setForeground(fontColor);
        labelDeviceID.setForeground(fontColor);
        tabbedPane1.setBackground(colorBackGround);
        tabbedPane1.setForeground(fontColor);
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
        panelTop.setPreferredSize(new Dimension(this.getWidth(),30));
        //panelTopLeft.setSize(dim);
        //panelTopLeft.setPreferredSize(new Dimension(this.getWidth(),60));

        panelTop.updateUI();
        panelBottom.setSize(dim);
        panelBottom.updateUI();
        btnUsers.setText("");
        btnUsers.setIcon( ToolUtils.changeImage(new ImageIcon("./resources/img/user.png"),0.5));
        btnUsers.setToolTipText("User");
        btnUsers.addActionListener(e -> {
            showUsers();
        });

        btnConsole.setText("");
        btnConsole.setIcon( ToolUtils.changeImage(new ImageIcon("./resources/img/console.png"),0.5));
        btnConsole.setToolTipText("Console");
        btnConsole.addActionListener(e -> {
            showConsole();
        });

        btnQuery.setText("");
        btnQuery.setIcon( ToolUtils.changeImage(new ImageIcon("./resources/img/search.png"),0.5));
        btnQuery.setToolTipText("Search");
        btnQuery.addActionListener(e -> {
            if (!PublicParameter.isReadRecordOver && !PublicParameter.currentPort.equalsIgnoreCase("NONE")){
                JOptionPane.showMessageDialog(this, "数据正在处理，请等待数据处理完毕!", "提示信息", 1);
                return ;
            }
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

                if (ToolUtils.isPower().equalsIgnoreCase("HIGH") ||
                        ToolUtils.isPower().equalsIgnoreCase("NORMAL"))
                {
                    //正常可以操作
                }else
                {
                    JOptionPane.showMessageDialog(null,
                            " No operation permission"
                            ,"Information", 1);
                    return ;
                }


                if (!PublicParameter.isReadRecordOver ){
                    if (PublicParameter.currentPort.equalsIgnoreCase("NONE"))
                    {
                        JOptionPane.showMessageDialog(this, "串口信息连接异常!", "提示信息", 1);
                        return ;
                    }
                    JOptionPane.showMessageDialog(this, "数据正在处理，请等待数据处理完毕!", "提示信息", 1);
                    return ;
                }
                String debug = JsonRead.getInstance().getJsonTarget("debug");
                if (debug.equalsIgnoreCase("yes")) showConfig();
                else showSetting();

        });
        btnConsole.setBackground(colorBackGround);
        btnConsole.setBorder(null);
        btnUsers.setBackground(colorBackGround);
        btnUsers.setBorder(null);

        btnQuery.setBackground(colorBackGround);
        btnQuery.setBorder(null);
        btnExport.setToolTipText("Setting");
        btnExport.setBackground(colorBackGround);
        btnExport.setBorder(null);
        btnSetting.setBackground(colorBackGround);
        btnSetting.setBorder(null);
        scrollPanelData.setBorder(null);
        tabbedPane1.setBorder(null);
        tableData.setBorder(null);
        softwareVersionValue.setText(JsonRead.getInstance().getJsonTarget("version"));
        deviceVersionValue.setText("");
        tabbedPane1.setTitleAt(0,"Records");
        tabbedPane1.setTitleAt(1,"Local Records");
        tabbedPane1.setSelectedIndex(0);
        initComboBoxDeviceId();
        initHistoryTable();
        this.addComponentListener(new ComponentAdapter(){
            @Override
            public void componentResized(ComponentEvent e){
               // setTablePanelSize();
                initFrameLayoutSize();
            }});
    }

    public void initFrameLayoutSize()
    {



    }

    public void initComboBoxDeviceId() {
        comboBoxDeviceId.addItem("Select Device Id");

        Map map = FileUtil.getRecordHistory();
        for(Object key:map.keySet()){
            comboBoxDeviceId.addItem(key.toString());
        }

    }

    public void initTableDataModel()
    {

        Vector<Vector<Object>> rowDatas = new Vector<Vector<Object>>();

        dataModel = new DefaultTableModel(rowDatas, getTableColumnName()) {
            public boolean isCellEditable(int row, int column) {
                boolean result = false;
                switch (column) {
                    case DataColumnsUtils.COL_OPERATORNAME:
                        result = true;
                        break;
                    case DataColumnsUtils.COL_ROOM:
                        result = true;
                        break;
                    case DataColumnsUtils.COL_CONTENT:
                        result = true;
                        break;

                }
                return result;
            }
        };
        tableData.setModel(dataModel);


        Vector<Vector<Object>> rowDatasHistory = new Vector<Vector<Object>>();
        dataModelHistory = new DefaultTableModel(rowDatasHistory, getTableColumnName()){
            public boolean isCellEditable(int row, int column)
            {
                boolean result = false;
                switch(column)
                {
                    case  DataColumnsUtils.COL_OPERATORNAME:
                        result =  false;
                        break;
                    case DataColumnsUtils.COL_ROOM:
                        result =  false;
                        break;
                    case DataColumnsUtils.COL_CONTENT:
                        result =  false;
                        break;
                }
                return result;
            }
        };
        tableHistory.setModel(dataModelHistory);

    }
    public void initDetailTable(JTable table) {
        DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
        cr.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, cr);
        DefaultTableCellHeaderRenderer hr = new DefaultTableCellHeaderRenderer();
        hr.setHorizontalAlignment(JLabel.CENTER);
        table.getTableHeader().setDefaultRenderer(hr);

    }
    private Vector<String> getTableColumnName(){
        Vector<String> columnNames = new Vector<String>();
//        columnNames.add("");
        columnNames.add("Treatent");
        columnNames.add("Date");
        columnNames.add("Time");
        columnNames.add("Volume(ml)");
        columnNames.add("Duration");
        columnNames.add("Del");
        columnNames.add("Operator name");
        columnNames.add("Room");
        columnNames.add("Content");
        return columnNames;
    }
    private void initDetailTableStyle()
    {
        tableData.setRowHeight(30);
        tableData.setForeground(Color.BLACK);
        tableData.setFont(new Font(null, Font.PLAIN, 14));
        tableData.setSelectionForeground(Color.DARK_GRAY);
        tableData.setSelectionBackground(Color.LIGHT_GRAY);
        tableData.setGridColor(Color.GRAY);
        tableData.getTableHeader().setBackground(fontColor);
        scrollPanelData.setBackground(colorBackGround);
//        tableData.getColumnModel().getColumn(DataColumnsUtils.COL_ID).setPreferredWidth(20); //设置列宽
//隐藏一列
       tableData.getColumnModel().getColumn(DataColumnsUtils.COL_DEL).setMinWidth(0);
       tableData.getColumnModel().getColumn(DataColumnsUtils.COL_DEL).setMaxWidth(0);
        tableHistory.setRowHeight(30);
        tableHistory.setForeground(Color.BLACK);
        tableHistory.setFont(new Font(null, Font.PLAIN, 14));
        tableHistory.setSelectionForeground(Color.DARK_GRAY);
        tableHistory.setSelectionBackground(Color.LIGHT_GRAY);
        tableHistory.setGridColor(Color.GRAY);
        tableHistory.getTableHeader().setBackground(fontColor);
//隐藏一列
        tableHistory.getColumnModel().getColumn(DataColumnsUtils.COL_DEL).setMinWidth(0);
        tableHistory.getColumnModel().getColumn(DataColumnsUtils.COL_DEL).setMaxWidth(0);
        this.scrollPanelHIstory.setBackground(colorBackGround);
        this.setBackground(colorBackGround);
        this.setResizable(false);

    }
    public void initHistoryTable()
    {

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
        if (resultDeviceId.indexOf("=") >=0)
        {
            int index = resultDeviceId.indexOf("=") + 1;
            tabbedPane1.setTitleAt(0,"Records:"   +  resultDeviceId.substring(index));
        }


        if (!isUsePort) {
            connectStatus.setText("Disconnected");
            return ;
        }
        connectStatus.setText("Connected");
        try{

            CommonUtils.commUtil = null;
            PublicParameter.currentPort = currentPort;
            String sendMsg =  "start:" + ToolUtils.getFormatMsg(JsonRead.getInstance().getJsonTarget("cxsbmc","order")); //查询设备序列号
            serialPortSend(sendMsg);
            Thread.sleep(400);
            String cxsbmcOrderName = ToolUtils.getOrderName("cxsbmc");
            if (resultDeviceName.indexOf(cxsbmcOrderName)>=0 && resultDeviceName.indexOf("ERROR") < 0){
                deviceName.setText("Device Name " + resultDeviceName.substring((cxsbmcOrderName+"=").length() ));
            }else
            {
                deviceName.setText("Device Name " + "OXY-30000" );
            }
            //查询日期
            sendMsg =  "start:" + ToolUtils.getFormatMsg(JsonRead.getInstance().getJsonTarget("cxrq","order")); //查询 设备使用记录
            serialPortSend(sendMsg);
            Thread.sleep(400);
            String cxrqOrderName = ToolUtils.getOrderName("cxrq");
            if (resultDeviceDate.indexOf(cxrqOrderName)>=0 && resultDeviceDate.indexOf("ERROR") < 0){
                deviceDate.setText("Device Date  " + resultDeviceDate.substring((cxrqOrderName+"=").length() ));
            }else
            {
                deviceDate.setText("Device Date  " );
            }

            sendMsg =  "start:" + ToolUtils.getFormatMsg(JsonRead.getInstance().getJsonTarget("cxgjbb","order")); //查询 设备使用记录
            serialPortSend(sendMsg);
            Thread.sleep(400);
            String cxgjbbOrderName = ToolUtils.getOrderName("cxgjbb");
            if (resultDeviceVersion.indexOf(cxgjbbOrderName)>=0 && resultDeviceVersion.indexOf("ERROR") < 0){

                deviceVersionValue.setText(resultDeviceVersion.substring((cxgjbbOrderName + "=").length() ));
                PublicValue.FIRMWARE =deviceVersionValue.getText().trim();
            }else
            {
                deviceVersionValue.setText("1.0.1");
                PublicValue.FIRMWARE = "1.0.1";
            }
            Thread.sleep(400);
            //CommonUtils.commUtil = null;
            //PublicParameter.commonUtils = CommonUtils.getInstance(currentPort);
            sendMsg =  "start:" + ToolUtils.getFormatMsg(JsonRead.getInstance().getJsonTarget("cxsbsyjl","order")); //查询 设备使用记录
            serialPortSend(sendMsg);

            new Thread(new Runnable() {
                public void run() {
                    try{Thread.sleep(4000);}catch(Exception eg){}
                    //万一没取到数据，5秒钟后可进行其它操作
                    if (dataModel != null && dataModel.getRowCount() == 0 )  PublicParameter.isReadRecordOver = true;
                }
            }).start();


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
       if (dataModel != null) dataModel.setRowCount( 0 );
    }

    public List<DataEntity> getTableDataList(DefaultTableModel selectDataModel,JTable table){
        List<DataEntity> list = new ArrayList<DataEntity>();
        int rowSelect[] = table.getSelectedRows();
        for (int row = 0 ; row < rowSelect.length; row++){
            DataEntity value = new DataEntity();
            for (int col = 0 ; col <selectDataModel.getColumnCount() ; col ++ )
            {
                String val = selectDataModel.getValueAt(rowSelect[row],col) == null?"":selectDataModel.getValueAt(rowSelect[row],col).toString();
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
                        String s_str = String.format("%04d", Integer.parseInt(val));
                        value.setsVolume(s_str);
//                        value.setsVolume(val);
                        break;
                    case DataColumnsUtils.COL_DURATION:

                        String s = val;
                        int m_index = s.indexOf("m");
                        String str_m_str = s.substring(0,m_index);
                        int i_m = Integer.parseInt(str_m_str) * 60;
                        int s_index =  m_index + 1;
                        String  str_s_str = s.substring(s_index,s.length()-1);
                        int i_s = Integer.parseInt(str_s_str) ;
                        String s_result = String.format("%04d", (i_m + i_s));
                        value.setsDuration(s_result);
                        break;
                    case DataColumnsUtils.COL_DEL:
                        value.setsDel(val);
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

    public List<DataEntity> getTableDataList(DefaultTableModel selectDataModel){
        List<DataEntity> list = new ArrayList<DataEntity>();
        for (int row = 0 ; row< selectDataModel.getRowCount(); row++){
            DataEntity value = new DataEntity();
            for (int col = 0 ; col <selectDataModel.getColumnCount() ; col ++ )
            {
                String val = selectDataModel.getValueAt(row,col) == null?"":selectDataModel.getValueAt(row,col).toString();
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
                        String s_str = String.format("%04d", Integer.parseInt(val));
                        value.setsVolume(s_str);
                        break;
                    case DataColumnsUtils.COL_DURATION:
                        String s = val;
                        int m_index = s.indexOf("m");
                        String str_m_str = s.substring(0,m_index);
                        int i_m = Integer.parseInt(str_m_str) * 60;
                        int s_index =  m_index + 1;
                        String  str_s_str = s.substring(s_index,s.length()-1);
                        int i_s = Integer.parseInt(str_s_str) ;
                        String s_result = String.format("%04d", (i_m + i_s));
                        value.setsDuration(s_result);

                        break;
                    case DataColumnsUtils.COL_DEL:
                        value.setsDel(val);
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
            String _del = "" ;
            if (receive.length() > 25  )
            {
                _del = receive.substring(25,27); //删除标记,1.3.12版本时增加了del删除功能和标记
            }
            DataEntity data = new DataEntity();
            data.setsTreatent(String.valueOf(dataModel.getRowCount()+1));
            data.setsDate(_day + "/" + _month + "/" + _year);  //20201124日期为日月年
            data.setsTime(_hour + ":" + _sec);
//            data.setsVolume(_use1 + _use2 + "-----");
            data.setsVolume(String.valueOf(Integer.parseInt(_use1 + _use2)));
            int i_time = Integer.parseInt(_time1 + _time2);
            int i_m = i_time/60;
            int i_s = i_time%60;
//            data.setsDuration(_time1 + _time2);
            data.setsDuration(i_m + "m" +  i_s + "s");
            data.setsDel(_del);
            data.setsRoom("");
            data.setsContent("");
            dataModel.addRow(DataColumnsUtils.getListContent(data));
            dataModel.fireTableDataChanged();
            int maxHeight = scrollPanelData.getVerticalScrollBar().getMaximum();
            scrollPanelData.getViewport().setViewPosition(new Point(0,maxHeight));
        }catch(Exception eg){

        }



    }

    private void  showConsole()
    {

        if (ToolUtils.isPower().equalsIgnoreCase("HIGH"))
        {
            //正常可以操作
        }else
        {
            JOptionPane.showMessageDialog(null,
                    " No operation permission"
                    ,"Information", 1);
            return ;
        }
        if (!PublicParameter.isReadRecordOver  ){
            if (PublicParameter.currentPort.equalsIgnoreCase("NONE"))
            {
                JOptionPane.showMessageDialog(this, "串口信息连接异常!", "提示信息", 1);
                return ;
            }
            JOptionPane.showMessageDialog(this, "数据正在采集或无数据时，不可导出数据", "提示信息", 1);
            return ;
        }

        if (Console.getInstance() !=null)
        {
            Console.console = null;
        }
        Console console = Console.getInstance();
        if (!console.isVisible()){
            console.setVisible(true);
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
        String sendMsg =  "start:" + ToolUtils.getFormatMsg(JsonRead.getInstance().getJsonTarget("cxsbmc","order")); //查询设备序列号
        serialPortSend(sendMsg);
        try{
            Thread.sleep(400);
        }catch(Exception eg){}
        String deviceName = "OXY-30000";
        String cxsbmcOrderName = ToolUtils.getOrderName("cxsbmc");
        if (resultDeviceName.indexOf(cxsbmcOrderName)>=0 && resultDeviceName.indexOf("ERROR") < 0){
            deviceName = resultDeviceName.substring((cxsbmcOrderName+"=").length() ).trim();
        }
        //这里给name值
        setting.txtName.setText(deviceName);

        setting.setVisible(true);

        if (settingField.getAnswer()){
            //deviceDate.setText(settingField.getDeviceDate());
            //返回日期的取值
        }
        
    }
    private void showUsers()
    {
        if (ToolUtils.isPower().equalsIgnoreCase("HIGH"))
        {
            //正常可以操作
        }else
        {
            JOptionPane.showMessageDialog(null,
                    " No operation permission"
                    ,"Information", 1);
            return ;
        }
        if (User.getInstance() !=null)
        {
            User.user = null;
        }
        User user = User.getInstance();
        if (!user.isVisible()){
            user.setVisible(true);
        }


    }
    private void searchHistory()
    {
        String selectDeviceIdFile = comboBoxDeviceId.getSelectedItem().toString();

        String fileFullPath=ToolUtils.getUserDir() + "\\resources\\txt\\history"  ;
        File dirs = new File( fileFullPath);
        File files[] = dirs.listFiles();
        //按查询条件生成需要的文件 ,查询规则待定义
//        String searchFileName = "REC-222110228318-20200527095321.json";
        String searchFileName  = selectDeviceIdFile ;//+ "#"; //一定要加#，否则可能日期与设备id重复
        List<String> fileList = new ArrayList<>();
        for(File file:files){
            if (file.isFile()){
                if (file.getName().indexOf(searchFileName)>=0)
                {
                    fileList.add(file.toString());
                }
            }
        }
        if (fileList.size() <= 0) return ;
        dataModelHistory.setRowCount( 0 );
        for(String file : fileList)
        {
            List<DataEntity>  list =JsonRead.getJsonRecordFileToEntity(file);
            for(DataEntity data : list)
            {
                data.setsVolume(String.valueOf(Integer.parseInt(data.getsVolume())));
                int i_time = Integer.parseInt(data.getsDuration().trim());
                int i_m = i_time/60;
                int i_s = i_time%60;
                data.setsDuration(i_m + "m" +  i_s + "s");

                data.setsTreatent(String.valueOf(dataModelHistory.getRowCount() + 1));
                dataModelHistory.addRow(DataColumnsUtils.getListContent(data));
                dataModelHistory.fireTableDataChanged();
            }
        }
        this.tabbedPane1.setSelectedIndex(1);
        JOptionPane.showMessageDialog(this, "Data load over", "Information", 1);

    }

    /**
     *  去掉对话框

    private void searchHistory()
    {
        History history = History.getInstance();
        history.setIconImage(new ImageIcon("./resources/img/start.png").getImage());//设置图
        history.setTitle(JsonRead.getInstance().getJsonTarget("title"));
        history.setSize(300,130);
        history.setLocationRelativeTo(null);//窗体居中显示
        history.setUndecorated(true);// 不绘制边框
        history.initComponentValue();
        history.setVisible(true);
        if (!history.answer) {
            return ;
        }
        String fileFullPath=ToolUtils.getUserDir() + "\\resources\\txt\\history"  ;
        File dirs = new File( fileFullPath);
        File files[] = dirs.listFiles();
        //按查询条件生成需要的文件 ,查询规则待定义
//        String searchFileName = "REC-222110228318-20200527095321.json";
        String searchFileName  = history.searchFileName;
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
        String jsonFileContent = JsonRead.getInstance().readJsonFile(fileName);
        JSONObject object = JSONObject.parseObject(jsonFileContent);
        String sDevicename =  object.get("devicename").toString();
        String sDevicedate = object.get("devicedate").toString();
        String sDeviceversion = object.get("deviceversion").toString();
        deviceName.setText("Device Name " + sDevicename);
        deviceDate.setText("Device Date " + sDevicedate);
//        deviceVersion.setText("Device Version " + sDeviceversion);
        deviceVersionValue.setText(sDeviceversion);

        List<DataEntity>  list =JsonRead.getJsonRecordFileToEntity(fileName);
        removeRowForDetailTable();
        for(DataEntity data : list)
        {
            dataModel.addRow(DataColumnsUtils.getListContent(data));
            dataModel.fireTableDataChanged();
        }
        //保存保存在哪里呢，要告诉我



    }
     */
    private void exportPDF()
    {

        if (!PublicParameter.isReadRecordOver  &&  this.tabbedPane1.getSelectedIndex() == 0){
            if (PublicParameter.currentPort.equalsIgnoreCase("NONE"))
            {
                JOptionPane.showMessageDialog(this, "串口信息连接异常!", "提示信息", 1);
                return ;
            }
            JOptionPane.showMessageDialog(this, "数据正在采集或无数据时，不可导出数据", "提示信息", 1);
            return ;
        }
//        JFileChooser jfc=new JFileChooser();
//        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  //仅能选择目录
//        int jfcOper = jfc.showDialog(new JLabel(), "请选择文件导出路径");
//        if (jfcOper != JFileChooser.APPROVE_OPTION) return ;  //当点击取消按钮时
//
//        File file=jfc.getSelectedFile();
//        if (!file.exists()) {
//            JOptionPane.showMessageDialog(this, "导出文件的路径不存在", "提示信息", 1);
//            return;
//        }
//        if(file.isDirectory()){
//            System.out.println("文件夹:"+file.getAbsolutePath());
//        }else if(file.isFile()){
//            System.out.println("java-文件:"+file.getAbsolutePath());
//        }



        int dialogButton = JOptionPane.showConfirmDialog (null, "Are you sure export file?","Information",JOptionPane.YES_NO_OPTION);

        if(dialogButton != JOptionPane.YES_OPTION) {
            return ;
//            System.exit(0);}else {remove(dialogButton);
        }


        File file  = new File(ToolUtils.getUserDir());
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        String fileFullName = file.getAbsolutePath() + "\\resources\\export\\" + sdf.format(currentDate) + ".pdf";
        fileFullName = fileFullName.replace("\\\\","\\"); //，当选择根目录的时候，可能会有异常,不替换windows下适配
        try{
            if (this.tabbedPane1.getSelectedIndex() == 0)
            {

                int rowSelect[] = tableData.getSelectedRows();
                if (rowSelect.length > 0 )
                {
                    PdfUtils.createHardwarePDF(fileFullName,getTableDataList(this.dataModel,tableData));
                }else
                {
                    PdfUtils.createHardwarePDF(fileFullName,getTableDataList(this.dataModel));
                }
            }else
            {
                int rowSelect[] = tableHistory.getSelectedRows();
                if (rowSelect.length > 0 )
                {
                    PdfUtils.createHardwarePDF(fileFullName,getTableDataList(this.dataModelHistory,tableHistory));
                }else
                {
                    PdfUtils.createHardwarePDF(fileFullName,getTableDataList(this.dataModelHistory));

                }

            }
            JOptionPane.showMessageDialog(this, "Export success(file name:" + fileFullName + "):", "提示信息", 1);
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
