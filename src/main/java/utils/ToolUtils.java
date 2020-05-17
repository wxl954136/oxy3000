package utils;

import java.io.*;

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

}
