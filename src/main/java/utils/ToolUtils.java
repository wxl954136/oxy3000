package utils;


import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
public class ToolUtils {
    public static final String txtHelpFilePath = "./resources/txt/install.txt";
    private static ToolUtils toolUtils;
    private ToolUtils() {
    }

    public static ToolUtils getInstance() {
        if (toolUtils == null) {
            toolUtils = new ToolUtils();
        }
        return toolUtils;
    }
    public String getHelpFileContent()
    {
        //File file = new File(txtHelpFilePath);
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(txtHelpFilePath),"UTF-8"));
           // BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }

    //jdk中的jre
    public static String getJavaHome(){
        return System.getProperty("java.home");
    }
    //这个是隐藏文件夹，不可用
    public static String getJavaJdkHome(){
        return System.getProperty("java.ext.dirs");
    }
    public static String getJavaJreHome(){
        File dir = new File(getJavaHome());
        String  upPath = dir.getParent();
        dir = new File(upPath);
        return dir.getParent();
    }
    //获取java文件下的所有文件目录
    public static List<String> getJavaPathOfDir()
    {
        List<String>  list = new ArrayList<>();
        File files[] = new File(getJavaJreHome()).listFiles();
        for (File file : files) {
            if (file.isDirectory()){
                list.add(file.toString());
            }
        }
        return list;
    }
    //当前用户运行文件所在的绝对路径
    public static String getUserDir(){
        return System.getProperty("user.dir");
    }

    public static String getSeriallDLLFileFullPath(){
        String bits = System.getProperty("os.arch"); //操作系统类型
        //32位显示的是x86,
        if (bits.indexOf("64") >=0){
            bits = "x64";
        }else if (bits.indexOf("32") >=0){
            bits = "x86";
        }

        String baseBin = "\\" + "bin"  ;
        String basePath = getUserDir() + "\\resources\\sys\\" + bits;
        String fullPath = basePath + baseBin;
        return fullPath;
    }
    //需要加载的动态文件
    public static List<String> getSerialDLLFiles()
    {
        List<String> list = new ArrayList<>();
        String file1 = "RXTXcomm.jar";
        String file2 = "rxtxParallel.dll";
        String file3 = "rxtxSerial.dll";
        String arch = System.getProperty("os.arch"); //操作系统类型
        //32位显示的是x86,
        if (arch.indexOf("64") >=0){
            arch = "x64";
        }else if (arch.indexOf("32") >=0){
            arch = "x86";
        }else{
            return list;
        }
        String result = "FAIL";
        String baseJar = "\\" + "lib"  + "\\";
        String baseBin = "\\" + "bin"  + "\\";
        String basePath = getUserDir() + "\\resources\\sys\\" + arch  ;
        String fileSourcePath1 = basePath + baseJar + file1;  //jar
        String fileSourcePath2 = basePath +  baseBin + file2;   //dll
        String fileSourcePath3 = basePath + baseBin + file3;   //dll
        list.add(fileSourcePath2);
        list.add(fileSourcePath3);
        return list;
    }
    //系统字体加载
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
    public static ImageIcon changeImage(ImageIcon image, double i) {//  i 为放缩的倍数
        float width = (float) (image.getIconWidth() * i);
        float height = (float) (image.getIconHeight() * i);
        Image img = image.getImage().getScaledInstance((int)width, (int)height, Image.SCALE_DEFAULT);//第三个值可以去查api是图片转化的方式
        ImageIcon image2 = new ImageIcon(img);
        return image2;
    }

    public static String getFormatMsg(String message){
        message += "\r\n";
        return message;
    }
    public static String getFormatSendOrder(String order,String value){
//        String order= "debug:at+speed=#{speed}";
        int key_start = order.indexOf("+") + 1;
        int key_end = order.indexOf("=#");
        String key = order.substring(key_start,key_end).trim();
        String replaceStr = "#\\{" + key + "}";
        String result = order.replaceAll(replaceStr,value);
        return result;
    }


}

/*
另外：System.getProperty()中的字符串参数如下：
System.getProperty()参数大全
# java.version                                Java Runtime Environment version
# java.vendor                                Java Runtime Environment vendor
# java.vendor.url                           Java vendor URL
# java.home                                Java installation directory
# java.vm.specification.version   Java Virtual Machine specification version
# java.vm.specification.vendor    Java Virtual Machine specification vendor
# java.vm.specification.name      Java Virtual Machine specification name
# java.vm.version                        Java Virtual Machine implementation version
# java.vm.vendor                        Java Virtual Machine implementation vendor
# java.vm.name                        Java Virtual Machine implementation name
# java.specification.version        Java Runtime Environment specification version
# java.specification.vendor         Java Runtime Environment specification vendor
# java.specification.name           Java Runtime Environment specification name
# java.class.version                    Java class format version number
# java.class.path                      Java class path
# java.library.path                 List of paths to search when loading libraries
# java.io.tmpdir                       Default temp file path
# java.compiler                       Name of JIT compiler to use
# java.ext.dirs                       Path of extension directory or directories
# os.name                              Operating system name
# os.arch                                  Operating system architecture
# os.version                       Operating system version
# file.separator                         File separator ("/" on UNIX)
# path.separator                  Path separator (":" on UNIX)
# line.separator                       Line separator ("/n" on UNIX)
# user.name                        User’s account name
# user.home                              User’s home directory
# user.dir                               User’s current working directory



 */
