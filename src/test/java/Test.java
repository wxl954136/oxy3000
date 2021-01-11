
import utils.ToolUtils;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;


public class Test extends JFrame {


    /**
     * 程序入口 main(String args[])
     */
    public static void main(String args[]) {
        boolean x = true;
        System.out.println(x);
        String pwd = ToolUtils.getCurrentDate("hhmmss");
        System.out.println("0===" + pwd);
        Long enpwd = Long.parseLong(pwd) * 2;
        System.out.println("1====" + enpwd);
        String senpwd = String.valueOf(enpwd).substring(0);

//        String str = String.format("%04d", 100);
//        System.out.println(str);
//
//
//        int i_time = Integer.parseInt("0121");
//        int i_m = i_time/60;
//        int i_s = i_time%60;
//        System.out.println(i_m);
//        System.out.println(i_s);
//        String s = "2m4s";
//        int m_index = s.indexOf("m");
//        String str_m_str = s.substring(0,m_index);
//        int i_m = Integer.parseInt(str_m_str) * 60;
//        int s_index =  m_index + 1;
//        String  str_s_str = s.substring(s_index,s.length()-1);
//        int i_s = Integer.parseInt(str_s_str) ;
//        String s_result = String.format("%04d", (i_m + i_s));
//        System.out.println(s_result);

    }
}
