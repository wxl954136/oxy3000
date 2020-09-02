import bean.DataEntity;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import form.Start;
import utils.FileUtil;
import utils.JsonRead;
import utils.ToolUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//对象模型
public class Test {


    public static void main(String[] args) {
        String x = "1.3.12";
        if(x.indexOf("1.3.") >=0)
        {
            System.out.println( x.substring("1.3.".length()));
        }


/*
        //创建json文件开始
        String result = "" ;
        result = result + JsonRead.getGenJsonStart();
        //增加单一元素----开始
         result = result + JsonRead.getGenJsonTag("time","0506",",");
        //增加单一元素----结束
        result = result + JsonRead.getGenJsonArrayBegin("data");
        //增加明细--开始
        result = result + JsonRead.getStr2JsonEntity(",");
        result = result + JsonRead.getStr2JsonEntity("");
        //增加明细--开始结束
        result = result + JsonRead.getGenJsonArrayEnd();
        result = result + JsonRead.getGenJsonEnd();
        //创建文件结束
        System.out.println(result);
*/
    }
}
