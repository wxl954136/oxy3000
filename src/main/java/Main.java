import bean.DataEntity;
import common.CommonUtils;
import form.Help;
import pdf.PdfUtils;
import sun.swing.table.DefaultTableCellHeaderRenderer;
import utils.CommonMultipleSelect;
import utils.DataColumnsUtils;
import utils.JsonRead;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

public class Main extends JFrame {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton btnReadCom;
    private JButton btnExportPDF;
    private JTable tableDetail ;
    private JEditorPane editPaneHelp;
    private JScrollPane scrollPaneDetail;
    private JButton btnHelp;
    private  DefaultTableModel dataModel;


    public Main() {

        setContentPane(contentPane);
        //setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        initComponent();
        initDetailTable();

        btnReadCom.addActionListener(e -> readCom());

        btnExportPDF.addActionListener(e -> {
            if (editPaneHelp.getText().indexOf("at+record=end") >=0){
                exportPDF();
            }else
            {
                JOptionPane.showMessageDialog(null,"未正确采集数据或数据正在采集中，无法导出" ,"提示信息", 1);
            }

        });
        buttonOK.addActionListener(e -> onOK());
        buttonOK.setVisible(false); //隐藏起来暂时不用
        buttonCancel.addActionListener(e -> onCancel());
        btnHelp.addActionListener(e -> onHelp());


        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);




    }

    public static void main(String[] args) {

        try {
            String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel";
            UIManager.setLookAndFeel(lookAndFeel);
            initSystemFontStyle();

            } catch (Exception e) {
            e.printStackTrace();
        }
        initSystemForm();
    }
    private  void createUIComponents() {

        /*
        // TODO: 手动添加元素在此做
        JMenuBar menuBar ;
        menuBar = new JMenuBar();
        contentPane.add(menuBar,BorderLayout.NORTH);
        JMenu menuHelp = new JMenu("帮助");
        JMenuItem menuItemAbout  = new JMenuItem("关于我们");
        menuHelp.add(menuItemAbout);
        menuBar.add(menuHelp);
*/



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


    private static void initSystemForm()
    {
        Main frame = new Main();
        frame.setIconImage(new ImageIcon("./resources/img/main.jpg").getImage());//设置图
        frame.setTitle(JsonRead.getInstance().getJsonTarget("title"));
        frame.setSize(1080,600);
        frame.setLocationRelativeTo(null);//窗体居中显示
        frame.setVisible(true);
      //  System.exit(0);
    }
    private void initComponent(){
        editPaneHelp.setEditable(false);
        editPaneHelp.setText("串口读取信息");
        DefaultCaret caret = (DefaultCaret)editPaneHelp.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

    }

    public void initDetailTable() {

        Vector<Vector<Object>> rowDatas = new Vector<Vector<Object>>();
        dataModel = new DefaultTableModel(rowDatas, getTableColumnName()){
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        tableDetail.setModel(dataModel);
        //设置表内容居中显示
        DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
        cr.setHorizontalAlignment(JLabel.CENTER);
        tableDetail.setDefaultRenderer(Object.class, cr);
        //设置表头居中显示
        DefaultTableCellHeaderRenderer hr = new DefaultTableCellHeaderRenderer();
        hr.setHorizontalAlignment(JLabel.CENTER);
        tableDetail.getTableHeader().setDefaultRenderer(hr);
        initDetailTableStyle(tableDetail);


        //this.scrollPaneDetail


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

/*
    //获取Table内的数据
    private Vector<Vector<Object>> getDetailTableContent(){
        Vector<Vector<Object>> tableDatas = new Vector<Vector<Object>>();
        Vector<Object> detailData = new Vector<Object>();
        for (int row = 0 ; row< dataModel.getRowCount(); row++){
            for (int col = 0 ; col <dataModel.getColumnCount() ; col ++ )
            {
                detailData.add(dataModel.getValueAt(row,col));
            }
            tableDatas.addElement(detailData);
        }
        return tableDatas;

    }

 */
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
    private void initDetailTableStyle(JTable table)
    {
        table.setRowHeight(30);
        table.setForeground(Color.BLACK);
        table.setFont(new Font(null, Font.PLAIN, 14));
        table.setSelectionForeground(Color.DARK_GRAY);
        table.setSelectionBackground(Color.LIGHT_GRAY);
        table.setGridColor(Color.GRAY);
        //table.getColumnModel().getColumn(0).setPreferredWidth(40); 设置列宽
    }
/*
    private void addRowForDetailTable(){
        for (int i = 0 ; i <= 800 ; i++) {
            DataEntity data = new DataEntity();
            data.setsTreatent("222");
            data.setsDate("05/04/01");
            data.setsTime("07:02");
            data.setsVolume("10m3");
            data.setsDuration("NONE");
            data.setsRoom("1k");
            data.setsContent("fill");
            dataModel.addRow(DataColumnsUtils.getListContent(data));
        }
    }

 */
    private void removeRowForDetailTable()
    {
        dataModel.setRowCount( 0 );
    }
    private void setCommonInfo(String msg,boolean isClear)
    {
        if (isClear)  editPaneHelp.setText("");
        String text = editPaneHelp.getText();
        text = text + msg + "\n";
        editPaneHelp.setText(text);

    }

    //事件执行动作------------------------------>start
    private void readCom()
    {
        removeRowForDetailTable();
        this.setCommonInfo("",true);
        ArrayList<Object> list= CommonUtils.getLocalHostPortNames();
        if (list.size() <= 0)
        {
            this.setCommonInfo("串口信息: 本机未检测到可用串口",true);
            JOptionPane.showMessageDialog(this,"本机未检测到可用串口" ,"提示信息", 1);
            return ;
        }
        //如果有多个窗口，弹框选择，如果只有一个只选择默认
        Object currentPort = CommonMultipleSelect.getSelectCommonPort(list,this);

        if (null == currentPort || currentPort.toString().equalsIgnoreCase("NONE"))
        {
            JOptionPane.showMessageDialog(this,"请选择串口进行通讯" ,"提示信息", 1);
            return ;
        }
        this.setCommonInfo("当前通讯串口:" + currentPort.toString(),true);

        Thread thread = new Thread(new Runnable(){
            public void run(){
                try{
                    CommonUtils.commUtil = null;
                    String portName = currentPort.toString();
                    CommonUtils commonUtils = CommonUtils.getInstance(portName);
                    commonUtils.editPaneHelp = editPaneHelp;
                    commonUtils.dataModel = dataModel;
                    setCommonInfo("打开串口：" +  currentPort.toString() , false);
                    String _sendContent = "at+record=?";
                    String _endChar = "\r\n";
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
        });
        thread.start();



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

    private void onOK() {
        // PdfUtils.testPdf();
        // dispose();
       // getDetailTableContent();
    }

    private void onCancel() {
        if (CommonUtils.commUtil != null) {
            CommonUtils.commUtil.close();
            CommonUtils.commUtil = null;
        }

        dispose();
        System.exit(0);
    }
    private void onHelp() {

         Help help = new Help();
         help.setVisible(true);
    }
    //事件执行动作------------------------------>end

}
