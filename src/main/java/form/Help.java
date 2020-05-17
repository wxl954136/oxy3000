package form;

import utils.ToolUtils;

import javax.swing.*;
import java.awt.event.*;

public class Help extends JDialog {
    private JPanel contentPane;

    private JButton buttonCancel;
    private JTextPane helpPane;

    public Help() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonCancel);

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
        initHelp();
    }

    public void initHelp()
    {
        this.setIconImage(new ImageIcon("./resources/img/main.jpg").getImage());//设置图
        this.setTitle("帮助信息...");
        this.setSize(800,600);
        this.setLocationRelativeTo(null);//窗体居中显示
        helpPane.setEditable(false);
        helpPane.setText(ToolUtils.getInstance().getHelpFileContent());
    }
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
