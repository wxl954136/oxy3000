package form;

import bean.PublicValue;
import bean.UserEntity;
import sun.swing.table.DefaultTableCellHeaderRenderer;
import utils.FileUtil;
import utils.JsonRead;
import utils.UserColumnsUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class User  extends JDialog{
    private JPanel panel1;
    private JTable tableUser;
    private JButton btnOK;
    private JTextPane txtNotes;
    public final static Color colorBackGround = new Color(47,63,80);
    public final static Color fontColor = new Color(173,206,47);
    public DefaultTableModel dataModelUser;
    public JPopupMenu tablePopupMenu;
    public static User user;
    public static User getInstance(){
        if(user == null){
            user = new User();
        }
        return user;
    }
    public User()
    {
        setModal(true);
        setContentPane(panel1);
        this.setSize(500,500);
        this.setMinimumSize(new Dimension(500,500));
        this.setLocationRelativeTo(null);//窗体居中显示
        this.panel1.setBorder(new LineBorder(Color.DARK_GRAY));
        this.setIconImage(new ImageIcon("./resources/img/start.png").getImage());//设置图
        this.setTitle(JsonRead.getInstance().getJsonTarget("title"));
        initTableDataModel();
        initDetailTable(this.tableUser);
        initDetailTableStyle();
        readUserInfo();
        setNotesContent();
        txtNotes.setFont(new Font(null, Font.PLAIN, 13));
        txtNotes.setOpaque(false);
        btnOK.setBorder(null);
        btnOK.setBackground(colorBackGround);
        btnOK.setForeground(fontColor);
        this.btnOK.addActionListener(e -> onOK());
        createTablePopupMenu();  //表格增加行菜单


    }
    public void createTablePopupMenu()
    {
        tablePopupMenu = new JPopupMenu();
        JMenuItem addMenItem = new JMenuItem();
        addMenItem.setText("新增行");
        addMenItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRow();
            }
        });
        tablePopupMenu.add(addMenItem);
        JMenuItem delMenItem = new JMenuItem();
        delMenItem.setText("删除行");
        delMenItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //该操作需要做的事
                deleteRow();
            }
        });
        tablePopupMenu.add(delMenItem);

    }
    public void setNotesContent()
    {
        String contents = "Notes:\n";
        contents += "1.admin have the root privilege and can't be deleted\n";
        contents += "2. only user admin can  add.delete.change  user's information \n";
        contents += "3. Privilege have three level :high normal low  \n";
        contents += "High: can check data , resotore data ,set device information ,and see all user's informaiton include password, change own password ;  but  can't see admin informaiton.  \n";
        contents += "Normal: can check data ,set device information,and see uesrlist,but not include password,change own password. \n";
        contents += "Low: check data ,seel userlist,but not include password,change own password \n";
        this.txtNotes.setText(contents);
    }
    public void initTableDataModel()
    {
        Vector<Vector<Object>> rowDatas = new Vector<Vector<Object>>();
        dataModelUser = new DefaultTableModel(rowDatas, getTableColumnName()) {
            /*
            @Override
            public void setValueAt(Object cell, int row, int column) {
                if (column == UserColumnsUtils.COL_PWD)
                {
                    Vector<Object> data = rowDatas.get(row);
//                    System.out.println(cell.toString());
                    super.setValueAt(cell,row,column);
                    return ;
                }
                super.setValueAt(cell,row,column);
            }

             */

            public boolean isCellEditable(int row, int column) {
                boolean result = false;
                String rowUser = this.getValueAt(row,UserColumnsUtils.COL_USER).toString();
                String currentUser  =  PublicValue.CURRENT_USER.getUser();
                switch (column) {
                    case UserColumnsUtils.COL_USER:
                        if (!currentUser.equalsIgnoreCase("admin"))
                        {
                            result =  false;
                        }else
                        {
                            result =  true;
                        }
                        if (rowUser.equalsIgnoreCase("admin")) {
                            result = false;
                        }

                        break;
                    case UserColumnsUtils.COL_PWD:
                        if (rowUser.equalsIgnoreCase("admin") && !currentUser.equalsIgnoreCase("admin"))
                        {
                            result =  false;
                        }else
                        {
                            result =  true;
                        }
                        if (!currentUser.equalsIgnoreCase("admin")) {
                            result = false;
                        }
                        break;
                    case UserColumnsUtils.COL_LEVEL:
                        if (rowUser.equalsIgnoreCase("admin") && !currentUser.equalsIgnoreCase("admin"))
                        {
                            result =  false;
                        }else
                        {
                            result =  true;
                        }
                        if (!currentUser.equalsIgnoreCase("admin")) {
                            result = false;
                        }
                        break;
                }
                return result;
            }
        };
        tableUser.setModel(dataModelUser);
    }
    private Vector<String> getTableColumnName(){
        Vector<String> columnNames = new Vector<String>();
        columnNames.add("Id");
        columnNames.add("Name");
        columnNames.add("Password");
        columnNames.add("Privilege");
        return columnNames;
    }
    public void initDetailTable(JTable table) {
        DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
        cr.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, cr);
        DefaultTableCellHeaderRenderer hr = new DefaultTableCellHeaderRenderer();
        hr.setHorizontalAlignment(JLabel.CENTER);
        table.getTableHeader().setDefaultRenderer(hr);
    }
    private void initDetailTableStyle()
    {
        tableUser.getTableHeader().setReorderingAllowed(false);
        tableUser.setRowHeight(30);
        tableUser.setForeground(Color.BLACK);
        tableUser.setFont(new Font(null, Font.PLAIN, 14));
        tableUser.setSelectionForeground(Color.DARK_GRAY);
        tableUser.setSelectionBackground(Color.LIGHT_GRAY);
        tableUser.setGridColor(Color.GRAY);
        tableUser.getTableHeader().setBackground(fontColor);
        setPasswordShowMethod();
//        JPasswordField c = new JPasswordField();
//        tableUser.getColumnModel().getColumn(UserColumnsUtils.COL_PWD).setCellEditor(new DefaultCellEditor(c));
//        tableUser.getColumnModel().getColumn(UserColumnsUtils.COL_PWD).setCellRenderer();
        tableUser.getColumnModel().getColumn(UserColumnsUtils.COL_ID).setPreferredWidth(30); //设置列宽
        tableUser.getColumnModel().getColumn(UserColumnsUtils.COL_USER).setPreferredWidth(150); //设置列宽
        tableUser.getColumnModel().getColumn(UserColumnsUtils.COL_PWD).setPreferredWidth(150); //设置列宽
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("High");
        comboBox.addItem("Normal");
        comboBox.addItem("Low");
        tableUser.getColumnModel().getColumn(UserColumnsUtils.COL_LEVEL).setCellEditor(new DefaultCellEditor(comboBox));
        tableUser.getColumnModel().getColumn(UserColumnsUtils.COL_LEVEL).setPreferredWidth(150); //设置列宽
        tableUser.setRowSelectionAllowed(true);
        tableUser.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

//隐藏一列
//        tableUser.getColumnModel().getColumn(UserColumnsUtils.COL_LEVEL).setMinWidth(0);
//        tableUser.getColumnModel().getColumn(UserColumnsUtils.COL_LEVEL).setMaxWidth(0);
        //超用户才可以新增修改用户信息
        if (PublicValue.CURRENT_USER.getUser().equalsIgnoreCase("admin"))
        {
            tableUser.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    jTable1MouseClicked(evt);
                }
            });
        }
        this.setBackground(colorBackGround);
        this.setResizable(false);
    }
    public void setPasswordShowMethod()
    {
        //设置密码格编辑器-------------------//
        JPasswordField passwordEdit = new JPasswordField();
        passwordEdit.setBorder(null);
        tableUser.getColumnModel().getColumn(UserColumnsUtils.COL_PWD).setCellEditor(new DefaultCellEditor(passwordEdit));

        //设置密码格表现器-------------------//
        tableUser.getColumnModel().getColumn(UserColumnsUtils.COL_PWD).setCellRenderer(new DefaultTableCellRenderer() {

            //重写 setValue 方法
            public void setValue(Object value) {
                String password = "";
                int wordLong = value.toString().length();
                for(int i = 0; i < wordLong; i++)
                    password += "*";
                super.setValue(password);
            }
        });
    }
    public void readUserInfo()
    {
        List<UserEntity> listUsers = JsonRead.getJsonRecordFileToUserEntity(JsonRead.jsonUserFile);
        dataModelUser.setRowCount(0);
        for (UserEntity data : listUsers) {
            data.setId(String.valueOf(dataModelUser.getRowCount() + 1));
            dataModelUser.addRow(UserColumnsUtils.getListContent(data));
            dataModelUser.fireTableDataChanged();
        }
    }
    public void onOK()
    {
        if (this.dataModelUser.getRowCount() <=0 ) return ;
        List<UserEntity> listUser = getTableDataList(this.dataModelUser);
        for(UserEntity data:listUser)
        {
            if (data.getUser().trim().length() == 0 || data.getPwd().trim().length() == 0  ||
            data.getLevel().trim().length() == 0 )
            {
                Object []option= {"ok"};
                JOptionPane.showOptionDialog(this,
                        "Invalid User name or Password or Privilege!",
                        "Warning",
                        JOptionPane.YES_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,option,//按钮的标题
                        option[0]);
                return ;
            }
        }
        FileUtil.setJsonFileUserData(listUser);
        Object []option= {"ok"};
        JOptionPane.showOptionDialog(this,
                "Save success!",
                "Information",
                JOptionPane.YES_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,option,//按钮的标题
                option[0]);
    }

    public List<UserEntity> getTableDataList(DefaultTableModel selectDataModel){
        List<UserEntity> list = new ArrayList<UserEntity>();
        for (int row = 0 ; row< selectDataModel.getRowCount(); row++){
            UserEntity value = new UserEntity();
            for (int col = 0 ; col <selectDataModel.getColumnCount() ; col ++ )
            {
                String val = selectDataModel.getValueAt(row,col) == null?"":selectDataModel.getValueAt(row,col).toString();
                switch(col)
                {
                    case UserColumnsUtils.COL_ID :
                        value.setId(val);
                        break;
                    case UserColumnsUtils.COL_USER :
                        value.setUser(val);
                        break;
                    case UserColumnsUtils.COL_PWD :
                        value.setPwd(val);
                        break;
                    case UserColumnsUtils.COL_LEVEL:
                        value.setLevel(val);
                        break;
                }
            }
            list.add(value);

        }
        return list;
    }
    public void addRow()
    {
        UserEntity data = new UserEntity();
        data.setLevel("");
        data.setPwd("");
        data.setUser("");
        data.setId(String.valueOf(dataModelUser.getRowCount() + 1));
        dataModelUser.addRow(UserColumnsUtils.getListContent(data));
        dataModelUser.fireTableDataChanged();
    }
    public void deleteRow()
    {
        int selectRow = this.tableUser.getSelectedRow();
        String _user = dataModelUser.getValueAt(selectRow,UserColumnsUtils.COL_USER).toString();
        if (_user.equalsIgnoreCase("admin")) return ;
        this.dataModelUser.removeRow(selectRow);
        this.dataModelUser.fireTableDataChanged();
    }
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {
        mouseRightButtonClick(evt);
    }
    private void mouseRightButtonClick(java.awt.event.MouseEvent evt) {
        //判断是否为鼠标的BUTTON3按钮，BUTTON3为鼠标右键
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            //通过点击位置找到点击为表格中的行
            int focusedRowIndex = tableUser.rowAtPoint(evt.getPoint());
            if (focusedRowIndex == -1) {
                return;
            }
            //将表格所选项设为当前右键点击的行
            tableUser.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
            //弹出菜单
            tablePopupMenu.show(tableUser, evt.getX(), evt.getY());
        }
    }
}
