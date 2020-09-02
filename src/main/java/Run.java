import form.Login;
import form.Start;
import utils.ClassLoaderUtils;
import utils.JsonRead;
import utils.ToolUtils;

import javax.swing.*;
import java.awt.*;

public class Run {
    public static void main(String[] args) {
        try {
            sysLoadLibraryForDLL();
            String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel";
            UIManager.setLookAndFeel(lookAndFeel);
            ToolUtils.initSystemFontStyle();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Start frame = Start.getInstance();
        frame.setIconImage(new ImageIcon("./resources/img/start.png").getImage());//设置图
        frame.setTitle(JsonRead.getInstance().getJsonTarget("title"));
        frame.setSize(860,600);
        frame.setMinimumSize(new Dimension(680,480));
        frame.setLocationRelativeTo(null);//窗体居中显示
        frame.setVisible(true);
        if (Run.loginFormShow())
        {
            frame.readCom();
        }
    }
    public static boolean loginFormShow()
    {
        boolean result = false;
        Login login = Login.getInstance();
        if (login.getAnswer() != null && login.getAnswer()  )
        {
            result = true ;
        }
        return result;
    }
    public static void sysLoadLibraryForDLL(){
        try{
            ClassLoaderUtils.loadSerialDynamically();
        }catch(Exception eg){
            eg.printStackTrace();
        }
    }
}
