package common;

import form.Config;
import form.Setting;
import form.Start;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import utils.FileUtil;
import utils.JsonRead;
import utils.PublicParameter;
import utils.ToolUtils;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * @author: lukeWang
 * @create 2020-05-20
 */
public   class CommonUtils implements SerialPortEventListener {

    private static  String PORT_NAME = "";
    private static  int BIT_RATE = 222;
    public static final int DATA_BITS = SerialPort.DATABITS_8;
    public static final int STOP_BIT = SerialPort.STOPBITS_1;
    public static final int PARITY_BIT = SerialPort.PARITY_NONE;

    private static SerialPort serialPort;
    private static InputStream in;
    private static OutputStream out;
    public static CommonUtils commUtil;

//    public DefaultTableModel dataModel;
    public String sendMessag;
//    public String deviceId;
    private CommonUtils(){}

    public static CommonUtils getInstance(String portName){
        PORT_NAME = portName;
        if(commUtil==null){
            BIT_RATE = Integer.parseInt(JsonRead.getInstance().getJsonTarget("rate"));
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

    public synchronized   void init(){
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

    public synchronized void send(String message){
        try {
            sendMessag = message;
            //主采集区Start.jsava采集数据Form发送的串口指令
            message = message.replaceAll("start:","");
            //厂家Config.java串口时调试发送指令
            message = message.replaceAll("debug:","");
            //客户区域Setting.java串口设备参数修改
            message = message.replaceAll("setting:","");
            if (out == null || in == null ) return ;
            out.write(message.getBytes());
            //等待一下，以免串口数据接收混乱
            Thread.sleep(500);
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

    public   void serialEvent(SerialPortEvent event) {
        switch (event.getEventType()){
            case SerialPortEvent.DATA_AVAILABLE:
                //receive();
                try{
                    //"不能加线程等待，否则会丢失数据-------------"
                    String result = receive();
//                    System.out.println("lukeWang: 收取信息:" + result);
                    if (result.length() == 0 ) return ;

                    if(sendMessag.indexOf("debug:") >=0){
                        showCollectValue(sendMessag,result);
                        return ;
                    }
                    if(sendMessag.indexOf("setting:") >=0){
                        showSettingValue(sendMessag,result);
                        return ;
                    }
                    //处理数据采集at+record=?/r/n
                    if (sendMessag.indexOf("start:") >=0)
                    {

                        if (sendMessag.indexOf(ToolUtils.getOrderName("cxsbsyjl")) >=0){
                            processRecordsData(result);
                        }
                        String sbmcOrderName = ToolUtils.getOrderName("cxsbmc");
                        if (sendMessag.indexOf(sbmcOrderName) >= 0 && result.indexOf(sbmcOrderName) >= 0) {
                            Start.getInstance().resultDeviceName = result;
                            //表示连接正确，但是设备有问题，返回ERROR
                            if (result.indexOf("=error") >= 0) {
                                Start.getInstance().resultDeviceName = "ERROR";
                            }
                        }
                        String cxrqOrderName = ToolUtils.getOrderName("cxrq");
                        if (sendMessag.indexOf(cxrqOrderName) >= 0 && result.indexOf(cxrqOrderName) >= 0) {
                            Start.getInstance().resultDeviceDate = result;
                            //表示连接正确，但是设备有问题，返回ERROR
                            if (result.indexOf("=error") >= 0) {
                                Start.getInstance().resultDeviceDate = "ERROR";
                            }
                        }
                        String cxsbxlhOrderName = ToolUtils.getOrderName("cxsbxlh");
                        if (sendMessag.indexOf(cxsbxlhOrderName) >= 0 && result.indexOf(cxsbxlhOrderName) >= 0) {
                            Start.getInstance().resultDeviceId = result;
                            //表示连接正确，但是设备有问题，返回ERROR
                            if (result.indexOf("=error") >= 0) {
                                Start.getInstance().resultDeviceId = "ERROR";
                            }
                        }
                        String cxgjbbOrderName = ToolUtils.getOrderName("cxgjbb");
                        if (sendMessag.indexOf(cxgjbbOrderName) >= 0 && result.indexOf(cxgjbbOrderName) >= 0) {
                            Start.getInstance().resultDeviceVersion = result;
                            //表示连接正确，但是设备有问题，返回ERROR
                            if (result.indexOf("=error") >= 0) {
                                Start.getInstance().resultDeviceVersion = "ERROR";
                            }
                        }
                    }

                }catch(Exception e){
                    e.printStackTrace();
                    this.close();
                }
                break;
        }
    }
    //和Config.java窗体文件关联
    public void showCollectValue(String sendMessag,String result){
        sendMessag = sendMessag.replace("debug:","");
        sendMessag = sendMessag.replace("\r\n","");
        Config.showCollectValue(sendMessag,result);
    }
    //和Config.java窗体文件关联
    public void showSettingValue(String sendMessag,String result){
        sendMessag = sendMessag.replace("setting:","");
        sendMessag = sendMessag.replace("\r\n","");
        Setting.showSettingValue(sendMessag,result);
    }
    public void processRecordsData(String result){
        String cxsbsyjlOrderName = ToolUtils.getOrderName("cxsbsyjl");
        //at+record
        if (result.indexOf(cxsbsyjlOrderName + "=begin") >=0)
        {
            return ;
        }
        if (result.indexOf("error") >=0)
        {
            PublicParameter.isReadRecordOver = true; //如果返回有错误，也当做读取完毕
            return ;
        }
        //为什么这里会截断显示，设备有问题
        if (result.indexOf(cxsbsyjlOrderName + "=end") >=0  || result.indexOf("ord=end") >= 0){
            //Error 0x5 at ..\rxtx\src\termios.c(892)
            //|| result.indexOf("=end") >= 0
            //应该这个设备有问题点，就是最后一行数据读取时，会把at+record=end放在最后一行数据上
            PublicParameter.isReadRecordOver = true;
            //写txt文件
            FileUtil.setJsonFileData(Start.getInstance().getTableDataList());
            return ;
        }
        Start.getInstance().prcessTableModelData(result);
    }



    public  String receive(){
        String result = "";
        try{

            int count = 0;
            while (count == 0) {
                count = in.available();
            }
            byte[] buffer = new byte[count];  //还有四个字符/r/n

            in.read(buffer);

            result = new String(buffer);

        } catch(Exception eg)
        {
            this.close();
            eg.printStackTrace();
        }finally {

        }
        return result;

        /*
        byte[] buffer = null;
        String result = "";
        try {

            int bufflenth = in.available();

            if (in.available() > 0) {
                System.out.println("x======" + bufflenth);
                bufflenth = 128;
                buffer= new byte[bufflenth];
                in.read(buffer);
            }
            result = new String(buffer);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;

         */
    }
/*
    public String receive(){
        byte[] buffer = new byte[128];
        int data;
        String result = null;
        try{
            int len = 0;
            while ( ( data = in.read()) > -1 ){
                System.out.println("x=====" + len);
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
*/
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
                serialPort = null;
                in = null;
                out = null;
            }
        }catch (IOException e) {
            e.printStackTrace();
        }catch (Exception eg){
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

}








