package common;

import bean.DataEntity;
import gnu.io.*;
import utils.DataColumnsUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * @author: lukeWang
 * @create 2020-05-20
 */
public class CommonUtils implements SerialPortEventListener {

    private static  String PORT_NAME = "";
    private static final int BIT_RATE = 19200;
    public static final int DATA_BITS = SerialPort.DATABITS_8;
    public static final int STOP_BIT = SerialPort.STOPBITS_1;
    public static final int PARITY_BIT = SerialPort.PARITY_NONE;

    private static SerialPort serialPort;
    private static InputStream in;
    private static OutputStream out;
    public static CommonUtils commUtil;


    public DefaultTableModel dataModel;
    public String sendMessag;
    public String deviceId;
    private CommonUtils(){}

    public static CommonUtils getInstance(String portName){
        PORT_NAME = portName;
        if(commUtil==null){

            commUtil = new CommonUtils();
            commUtil.init();
        }
        return commUtil;
    }

    public static boolean portIsUsed(String portName){
        boolean status = false;
        try {
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
            if (portIdentifier.isCurrentlyOwned()){
                status  = false;
            }else if(portIdentifier.getPortType()==1){
                SerialPort tSerialPort = (SerialPort) portIdentifier.open(portName,1000);
                tSerialPort.setSerialPortParams(BIT_RATE,DATA_BITS,STOP_BIT,PARITY_BIT);
                tSerialPort.close();

                portIdentifier=null;
                status = true;
                //serialPort.notifyOnDataAvailable(true);
            }else{
                status = false;
            }
        } catch (Exception e) {
            status = false;
            //e.printStackTrace();
        }
        return status;
    }

    public static  ArrayList<Object> getLocalHostPortNames(){
        ArrayList<Object> list = new ArrayList<>();
        CommPortIdentifier portIdentifier;
        Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
        while(portList.hasMoreElements()) {
            portIdentifier = (CommPortIdentifier) portList.nextElement();
            if (portIdentifier.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                list.add(portIdentifier.getName());
            }
        }
        return list;
    }

    public void init(){
        try {
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(PORT_NAME);
            if (portIdentifier.isCurrentlyOwned()){
                System.out.println("Error: Port is currently in use");
            }else if(portIdentifier.getPortType()==1){
                serialPort = (SerialPort) portIdentifier.open(PORT_NAME,1000);
                serialPort.setSerialPortParams(BIT_RATE,DATA_BITS,STOP_BIT,PARITY_BIT);
                in = serialPort.getInputStream();
                out = serialPort.getOutputStream();
                serialPort.addEventListener(this);
                serialPort.notifyOnDataAvailable(true);
            }else{
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        } catch (Exception e) {
            String className = e.getClass().toString();
            if (className.indexOf("gnu.io.PortInUseException") >=0){
                JOptionPane.showMessageDialog(null,"当前使用的串口被占用" ,"提示信息", 1);
            }else
            {
                e.printStackTrace();
            }

        }
    }

    public void send(String message){
        try {

            sendMessag = message;
            if (out == null ) return ;
            out.write(message.getBytes());
            Thread.sleep(1000);
            /*
            //不需要转换为16进制
            byte[] bytes = hexStrToByteArray(message);
            out.write(bytes);
            Thread.sleep(1000);
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  void serialEvent(SerialPortEvent event) {
        switch (event.getEventType()){
            case SerialPortEvent.DATA_AVAILABLE:
                //receive();
                try{
                    String result = receive();
                    //处理数据采集at+record=?/r/n
                    if (sendMessag.indexOf("at+record") >=0){
                        processRecordsData(result);
                    }
                    if (sendMessag.indexOf("at+deviceid") >=0){
                        if (result.indexOf("at+deviceid") >=0) {
                            deviceId = result;
                            //System.out.println("rrrr---" + deviceId);
                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    this.close();
                }
                break;
        }
    }
    /*
    public void processDeviceData(String result){
        deviceId = result
        System.out.println("查询 设备号: " + result);
    }

     */
    public void processRecordsData(String result){

        if (result.indexOf("at+record=begin") >=0)
        {
            //this.setCommonInfo("开始读取数据[at+record=begin]" ,false);
        }
        if (result.indexOf("at+record=end") >=0){
            //System.out.println("数据读取结束 ");
           // this.setCommonInfo("读取数据完毕[at+record=end]" ,false);
        }
        if ( result.indexOf("at+record=")<0) prcessTableModelData(result);
    }
    /*
    private void setCommonInfo(String msg,boolean isClear)
    {
        if (editPaneHelp == null) return ;
        if (isClear)  editPaneHelp.setText("");
        String text = editPaneHelp.getText();
        text = text + msg + "\n";
        editPaneHelp.setText(text);

    }

     */
    public void prcessTableModelData(String receive){
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
    }
    public String receive(){
        byte[] buffer = new byte[128];
        int data;
        String result = null;
        try{
            int len = 0;
            while ( ( data = in.read()) > -1 ){
                buffer[len++] = (byte) data;
            }
            byte[] copyValue = new byte[len];
            System.arraycopy(buffer,0,copyValue,0,len);
           // result = ByteArrayToString(copyValue);
            result =  new String(copyValue);

           // System.out.println(new String(copyValue));
        }catch ( IOException e ){
            e.printStackTrace();
        }
        return result;
    }

/*
    public String receive_source_bak(){
        byte[] buffer = new byte[128];
        int data;
        String result = null;
        try{
            int len = 0;
            while ( ( data = in.read()) > -1 ){
                buffer[len++] = (byte) data;
            }
            byte[] copyValue = new byte[len];
            System.arraycopy(buffer,0,copyValue,0,len);
            result = ByteArrayToString(copyValue);

        }catch ( IOException e ){
            e.printStackTrace();
        }
        return result;
    }


 */
    public  void close(){
        try {
            if (null != in )in.close();
            if (null != out ) out.close();
            if (serialPort != null){
                serialPort.notifyOnDataAvailable(false);
                serialPort.removeEventListener();
                serialPort.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //16进制转byte数组
    public byte[] hexStrToByteArray(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return new byte[0];
        }
        byte[] byteArray = new byte[str.length() / 2];
        for (int i = 0; i < byteArray.length; i++) {
            String subStr = str.substring(2 * i, 2 * i + 2);
            byteArray[i] = ((byte) Integer.parseInt(subStr, 16));
        }
        return byteArray;
    }
/*
    public String ByteArrayToString(byte[] by) {
        String str = "";
        for (int i = 0; i < by.length; i++) {
            String hex = Integer.toHexString(by[i] & 0xFF);
            if (hex.length() == 1) {
                hex = "0" + hex;
            }
            str += hex.toUpperCase();
        }
        return str;
    }
*/
/*
    public static void main ( String[] args ){
        CommonUtils1 commUtil = CommonUtils1.getInstance("COM8");
        String _sendContent = "at+record=?\r\n";
        commUtil.send(_sendContent);
        //commUtil.send("8101060108080301FF");
    }

 */
}








