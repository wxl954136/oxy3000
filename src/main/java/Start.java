import bean.DataEntity;
import common.CommonUtils;
import pdf.PdfUtils;
import sun.swing.table.DefaultTableCellHeaderRenderer;
import utils.DataColumnsUtils;
import utils.JsonRead;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;
import java.util.List;

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
    //http://www.icosky.com/icon-search/图标合集
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
            String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel";
            UIManager.setLookAndFeel(lookAndFeel);
            initSystemFontStyle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Start frame = new Start();
        frame.setIconImage(new ImageIcon("./resources/img/start.png").getImage());//设置图
        frame.setTitle(JsonRead.getInstance().getJsonTarget("title"));
        frame.setSize(1220,705);
        frame.setMinimumSize(new Dimension(800,650));
        frame.setLocationRelativeTo(null);//窗体居中显示
        frame.setVisible(true);
        frame.readCom();
    }
    public static void initSystemFontStyle()
    {
        FontUIResource fontRes = new FontUIResource(new Font("微软雅黑",Font.PLAIN,16));
        for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontRes);
            }
        }

    }
    private  void initSystemStyle()
    {
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

        logoImage.setIcon( changeImage(image,0.5));
       // panelTopCenter.setimage(new ImageIcon("./resources/img/main.jpg").getImage());//设置图
        Dimension dim = new Dimension(this.getWidth(),60);
        panelTop.setPreferredSize(dim);
        panelTop.updateUI();

        panelBottom.setPreferredSize(dim);
        panelBottom.updateUI();

        btnExport.setIcon( changeImage(new ImageIcon("./resources/img/export.png"),0.5));
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
        btnSetting.setIcon( changeImage(new ImageIcon("./resources/img/settings.png"),0.5));
        btnSetting.setText("");
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


    public ImageIcon changeImage(ImageIcon image, double i) {//  i 为放缩的倍数
        int width = (int) (image.getIconWidth() * i);
        int height = (int) (image.getIconHeight() * i);
        Image img = image.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);//第三个值可以去查api是图片转化的方式
        ImageIcon image2 = new ImageIcon(img);
        return image2;
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
    private void readCom()
    {
        removeRowForDetailTable();
        ArrayList<Object> list= CommonUtils.getLocalHostPortNames();
        /*
        ArrayList<Object> list= new ArrayList<>();
        list.add("COM1");
        list.add("COM8");
        list.add("COM9");
*/

        if (list.size() <= 0)
        {
            connectStatus.setText("Disconnected");
            return ;
        }
        currentPort = "NONE";
        boolean isUsePort = false;
        for (Object port:list){
            if (CommonUtils.portIsUsed(port.toString())){
                currentPort = port.toString();
                isUsePort = true;
                break;
            }
        }
        if (!isUsePort) {
            connectStatus.setText("Disconnected");
            return ;
        }
/*
        Object currentPort = CommonMultipleSelect.getSelectCommonPort(list,this);
        if (null == currentPort || currentPort.toString().equalsIgnoreCase("NONE"))
        {
            JOptionPane.showMessageDialog(this,"请选择串口进行通讯" ,"提示信息", 1);
            return ;
        }
 */
        //获取DeviceId
        CommonUtils.commUtil = null;
        commonUtils = CommonUtils.getInstance(currentPort);
        String _sendContent = "at+deviceid=?";
        String _endChar = "\r\n";
        commonUtils.send(_sendContent + _endChar);
        deviceName.setText("Device Name " + commonUtils.deviceId.substring("at+deviceid=".length() ));
        try{
            // CommonUtils.commUtil = null;
            commonUtils = CommonUtils.getInstance(currentPort);
            commonUtils.dataModel = dataModel;
            _sendContent = "at+record=?";
            _endChar = "\r\n";
            //setCommonInfo("正在读取数据中，请稍等..."  , false);
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
    private void exportPDF()
    {
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
            System.out.println("Main.java-文件夹:"+file.getAbsolutePath());
        }else if(file.isFile()){
            System.out.println("Main.java-文件:"+file.getAbsolutePath());
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
