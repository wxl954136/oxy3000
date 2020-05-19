package install;

import utils.JsonRead;
import utils.ToolUtils;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;

public class Install extends JFrame {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    public Install() {
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        //dispose();
        /*
        System.out.println("x=1==" + ToolUtils.getJavaHome() );
        //System.out.println("y===" + ToolUtils.getJavaJdkHome());
        System.out.println("y==2=" + ToolUtils.getJavaJreHome());
        System.out.println("y=3==" + ToolUtils.getUserDir());
        ToolUtils.getJavaPathOfDir();


*/


    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
        System.exit(0);
    }

    public static void main(String[] args) {
        try {
            String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel";
            UIManager.setLookAndFeel(lookAndFeel);
            initSystemFontStyle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Install frame = new Install();
        frame.setIconImage(new ImageIcon("./resources/img/start.png").getImage());//设置图
        frame.setTitle(JsonRead.getInstance().getJsonTarget("title"));
        frame.setSize(1220,705);
        frame.setMinimumSize(new Dimension(800,650));
        frame.setLocationRelativeTo(null);//窗体居中显示
        frame.setVisible(true);

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
}
