package common;
/*
        //https://blog.csdn.net/songyulong8888/article/details/78234275/

 */
import gnu.io.*;
import utils.JsonRead;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TooManyListenersException;

public class CommonUtilsOther  implements SerialPortEventListener{
    Enumeration<CommPortIdentifier> portList;
    // 检测系统可用端口
    private CommPortIdentifier portIdentifier;
    // 端口
    private SerialPort port;
    // 输入/输出流
    private InputStream inputStream;
    private OutputStream outputStream;
    //lukeWang -- 获取本机串口 start
    public ArrayList<Object> getLocalHostPortNames(){
        ArrayList<Object> list = new ArrayList<>();
        portList = CommPortIdentifier.getPortIdentifiers();
        while(portList.hasMoreElements()) {
            portIdentifier = (CommPortIdentifier) portList.nextElement();
            if (portIdentifier.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                list.add(portIdentifier.getName());

            }
        }
        return list;
    }
    //lukeWang -- 获取本机串口 end



    //lukeWang:监控指定端口 start
    public void monitorPortList(String portName) {
        // 获得系统支持的所有端口（串口，并口）
        portList = CommPortIdentifier.getPortIdentifiers();
        while(portList.hasMoreElements()) {
            portIdentifier = (CommPortIdentifier)portList.nextElement();
            if (portIdentifier.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                //监控指定端口
                if (portIdentifier.getName().equals(portName)) {
                    try {
                        // open:打开串口，第一个参数应用程序名称 字符串可随意填写，第二个参数阻塞时等待多少毫秒
                        port = (SerialPort)portIdentifier.open("ReadMonitorPort", 2000);
                        // 串口设置监听
                        port.addEventListener(this);
                        // 设置可监听
                        port.notifyOnDataAvailable(true);
                        // 设置串口通信参数
                        // 波特率，数据位，停止位，校验方式

                        //波特率该设备仅支持 19200

                        int rate = 19200;
                        try {
                            String sRate = JsonRead.getInstance().getJsonTarget("rate");
                            rate = Integer.parseInt(sRate);
                        }catch(Exception e){
                            rate = 19200;
                        }
                        port.setSerialPortParams(rate,
                                SerialPort.DATABITS_8,
                                SerialPort.STOPBITS_1,
                                SerialPort.PARITY_NONE);
                        outputStream = port.getOutputStream();
                        inputStream = port.getInputStream();
                    } catch (PortInUseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (TooManyListenersException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (UnsupportedCommOperationException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }

// //lukeWang:监控指定端口 start

//lukeWang: 不用了注意关闭  start
    /*
    public void close(){
        try {
            if (inputStream != null) inputStream.close();
            if (outputStream != null) outputStream.close();
            if (port != null )
            {
                port.notifyOnDataAvailable(false);
                port.removeEventListener();
                port.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     */
//lukeWang: 不用了注意关闭  end




/*

    public void getPortList() {

        // 获得系统支持的所有端口（串口，并口）
        portList = CommPortIdentifier.getPortIdentifiers();
        while(portList.hasMoreElements()) {
            portIdentifier = (CommPortIdentifier)portList.nextElement();
            // CommPortIdentifier.PORT_SERIAL :串口
            // CommPortIdentifier.PORT_PARALLEL :并口
            // CommPortIdentifier.PORT_RS485 :RS485
            // CommPortIdentifier.PORT_I2C :I2C
            // CommPortIdentifier.PORT_RAW
            if (portIdentifier.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                System.out.println(portIdentifier.getName());
                if (portIdentifier.getName().equals("COM3")) {
                    try {
                        // open:打开串口，第一个参数应用程序名称 字符串可随意填写，第二个参数阻塞时等待多少毫秒
                        port = (SerialPort)portIdentifier.open("ReadCurrentCommon", 2000);
                        // 串口设置监听
                        port.addEventListener(this);
                        // 设置可监听
                        port.notifyOnDataAvailable(true);
                        // 设置串口通信参数
                        // 波特率，数据位，停止位，校验方式
                        port.setSerialPortParams(115200,
                                SerialPort.DATABITS_8,
                                SerialPort.STOPBITS_1,
                                SerialPort.PARITY_NONE);
                        outputStream = port.getOutputStream();
                        inputStream = port.getInputStream();
                    } catch (PortInUseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (TooManyListenersException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (UnsupportedCommOperationException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }

    }


 */
    @Override
    public void serialEvent(SerialPortEvent event) {
        // TODO Auto-generated method stub
        // TODO Auto-generated method stub
        /*
         *  SerialPortEvent.BI:/*Break interrupt,通讯中断
         *  SerialPortEvent.OE:/*Overrun error，溢位错误
         *  SerialPortEvent.FE:/*Framing error，传帧错误
         *  SerialPortEvent.PE:/*Parity error，校验错误
         *  SerialPortEvent.CD:/*Carrier detect，载波检测
         *  SerialPortEvent.CTS:/*Clear to send，清除发送
         *  SerialPortEvent.DSR:/*Data set ready，数据设备就绪
         *  SerialPortEvent.RI:/*Ring indicator，响铃指示
         *  SerialPortEvent.OUTPUT_BUFFER_EMPTY:/*Output buffer is empty，输出缓冲区清空
         *  SerialPortEvent.DATA_AVAILABLE:
         */
        switch (event.getEventType()) {
            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                break;
            case SerialPortEvent.DATA_AVAILABLE: //
                readData();
                break;
            default:
                break;
        }
    }


    public void readData() {
        byte[] rbuff = new byte[1024];
        int hasRead = 0;

        try {
            while((hasRead=inputStream.read(rbuff)) > 0) {
                System.out.println("read2************************");
                System.out.print(new String(rbuff, 0, hasRead));
                break;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void sendData(String data) {
        try {
            outputStream.write(data.getBytes());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @SuppressWarnings("")
    public void callHelp()
    {
        //new CommonUtils().getPortList();
    }


}
