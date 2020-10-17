package utils;


import bean.DataEntity;
import bean.PublicValue;
import com.sun.deploy.util.StringUtils;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class ToolUtils {
    public static final String txtHelpFilePath = "./resources/txt/readme.txt";
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

    /**
     *
     * @param message 传入的字符可能有\r\n,替换掉
     * @return
     */
    public static String getPurStr(String message){
        if (isEmpty(message)) message = "";
        message = message.replaceAll("\r","");
        message = message.replaceAll("\n","");
        return message;
    }
    public static String getSendOrderKey(String source ){
//        source = "at+time=#time";
        String key = "NONE";
        int start =  source.indexOf("#");
        if (start >= 0)
        {
            key = source.substring(start);
        }
        return key;
    }
    public static String getCurrentDate(String format)
    {
        if (isEmpty(format)) format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    public static boolean isEmpty(String obj){
        if (obj == null) return true;
        if ("" == obj) return true;
        return false;
    }
    public static String nullFormat(String obj)
    {
        String result = "";
        if (obj == null)  result = "";
        result = obj ;
        return result;
    }

    public static Map<String,String> mapOrderName = new HashMap<>();
    public static String getOrderName(String target)
    {
        if (mapOrderName.containsKey(target)){
            return mapOrderName.get(target);
        }
        String orderName = nullFormat(JsonRead.getInstance().getJsonTarget(target,"order"));
        String result = "****";
        int loc =  orderName.indexOf("=");
        if (loc >= 0) {
            result = orderName.substring(0,loc);
            mapOrderName.put(target,result);
        }
        return result;
    }

    public static DataEntity array2Entity(String value)
    {
        if (ToolUtils.isEmpty(value)) return null;
        String[] array = value.split("\\|");
        DataEntity dataEntity = new DataEntity();
        for (int index = 0 ; index < array.length ; index++){
            switch(index)
            {
                case DataColumnsUtils.COL_TREATENT:
                    dataEntity.setsTreatent(array[index]);
                    break;
                case DataColumnsUtils.COL_DATE:
                    dataEntity.setsDate(array[index]);
                    break;
                case DataColumnsUtils.COL_TIME:
                    dataEntity.setsTime(array[index]);
                    break;
                case DataColumnsUtils.COL_VOLUME:
                    dataEntity.setsVolume(array[index]);
                    break;
                case DataColumnsUtils.COL_DURATION:
                    dataEntity.setsDuration(array[index]);
                    break;
                case DataColumnsUtils.COL_OPERATORNAME:
                    dataEntity.setsOperatorName(array[index]);
                    break;
                case DataColumnsUtils.COL_ROOM:
                    dataEntity.setsRoom(array[index]);
                    break;
                case DataColumnsUtils.COL_CONTENT:
                    dataEntity.setsContent(array[index]);
                    break;
            }
        }

        return dataEntity;
    }
    //获取一个排序集合
    public static Map getSortMap()
    {
        Map<String, Object> map = new TreeMap<String, Object>(
                new Comparator<String>() {
                    @Override
                    public int compare(String obj1, String obj2) {
                        // 降序排序key
                        return obj2.compareTo(obj1);
                    }
                });
        return map;
    }
    /**
     * 1.3.12版本增加新功能，低版本不可使用
     *     at+firmware=1.3.12
     *     1.3.12以前的版本不支持
     * @param version
     * @return
     */
    public static boolean izVersionSupport(String version)
    {
        if (version.indexOf("1.0.") >=0 ||
            version.indexOf("1.1.") >=0    ||
            version.indexOf("1.2.") >=0){
            return false;
        }
        if (version.indexOf("1.3.") >=0)
        {
            String ver = version.substring("1.3.".length());
            try{
                if (Integer.parseInt(ver) <12)
                {
                    return  false;
                }
            }catch(Exception eg) {
                return true;
            }
        }
        return true;
    }
    public static String isPower()
    {
        if ( PublicValue.CURRENT_USER == null ) return "";
        return PublicValue.CURRENT_USER.getLevel().trim().toUpperCase();
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
