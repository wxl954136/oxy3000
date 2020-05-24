package utils;

import javax.swing.*;
import java.awt.*;
import java.util.List;
/*
*调用方法
*       ArrayList<Object> list=new ArrayList<Object>();
        list.add("COM1");
        list.add("COM2");
        list.add("COM3");
        Object result = CommonMutilSelect.getSelectCommonPort(list,this);
        System.out.println("x----" + result);

 */
public class CommonMultipleSelect {

    public static Object getSelectCommonPort(List<Object> listPorts, Component component){
        if (listPorts.size()  == 1 )  {
            return listPorts.get(0);
        }
        if (listPorts.size() > 1)
        {
            Object[] selectionValues = new Object[listPorts.size()];
            listPorts.toArray(selectionValues);
            Object inputContent = JOptionPane.showInputDialog(
                    component,
                    "请选择串口: ",
                    "串口选择",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    selectionValues,
                    selectionValues[0]
            );
            return inputContent;
        }
        //封装

        return "NONE";
    }

}
