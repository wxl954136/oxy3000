package utils;


import bean.DataEntity;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *  调用方法: JsonRead.getInstance().getJsonTarget("pdf-font"));
 */
public class JsonRead {

    public static final String jsonFile = "./resources/json/link.json";
    private static JsonRead jsonRead;

    private JsonRead() {
    }

    public static JsonRead getInstance() {
        if (jsonRead == null) {
            jsonRead = new JsonRead();
        }
        return jsonRead;
    }

    /**
     * @return
     * @Param target 直接给出json标签，从./resources/json/link.json中取值
     * @Param alignMidde  是否垂直居中
     * @Param haveColor 是否有背景色(灰色)
     */
    public String getJsonTarget(String target) {
        String jsonSource = readJsonFile(jsonFile);
        JSONObject object = JSONObject.parseObject(jsonSource);
        return object.get(target).toString();
    }
    //当有两级时
    public String getJsonTarget(String target1,String target2) {
        String result = "";
        try {
            String jsonSource = ToolUtils.nullFormat(readJsonFile(jsonFile));
            JSONObject object = JSONObject.parseObject(jsonSource);
            String result1 = ToolUtils.nullFormat(object.get(target1).toString());
            JSONObject object2 = JSONObject.parseObject(result1);
            String result2 = object2.get(target2).toString();
            result = result2;
        }
        catch(Exception eg)
        {
             System.out.println("查询指令不存在");
        }
        return result;
    }


    public String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param tag : json标签
     * @param value ： json值
     *  @param end ："," 是否结尾给,号，就是，及空字符串
     * @return
     */
    public static String getGenJsonTag(String tag,String value, String end)
    {
        String result =  "\"" + tag + "\"" + ":" + "\"" + value + "\"" + end + "\n";
        return result;
    }
    public static String getGenJsonStart(){
        return "{\n";
    }
    public static String getGenJsonEnd(){
        return "}\n";
    }
    public static String getGenJsonArrayBegin(String tag)
    {
       String result = "\""  + tag +  "\"" + ":\n\t[\n";
       return result;
    }
    public static String getGenJsonArrayEnd()
    {
        return "\t]\n";
    }
    public static String getStr2JsonEntity(String end)
    {
        String result = "";
        result = result + "\t\t{" + "\n";
        result += "\t\t\t\"" + "treatment" + "\"" + ":" + "\"#treatment#\"" + ",";
        result += ("\"" + "date" + "\"" + ":" + "\"#date#\"" + ",");
        result += ("\"" + "time" + "\"" + ":" + "\"#time#\"" + ",");
        result += ("\"" + "volume" + "\"" + ":" + "\"#volume#\"" + ",");
        result += ("\"" + "duration" + "\"" + ":" + "\"#duration#\"" + ",");
        result += ("\"" + "operatorname" + "\"" + ":" + "\"#operatorname#\"" + ",");
        result += ("\"" + "room" + "\"" + ":" + "\"#room#\"" + ",");
        result += ("\"" + "content" + "\"" + ":" + "\"#content#\"" );
        result += "\n";
        result += "\t\t} " + end + "\n" ;
        return result;
    }
/*
解析json文件后放入table中，还没有做 lukeWang
prcessTableModelData 注意start.java中参照
 */
    public static List<DataEntity> getJsonRecordFileToEntity(String filename){
        filename = ToolUtils.getUserDir() + "\\resources\\txt\\history\\" + filename;
        String readJson = JsonRead.getInstance().readJsonFile(filename);
        JSONObject object = JSONObject.parseObject(readJson);
        JSONArray jarr=JSONArray.parseArray(object.get("data").toString());
        List<DataEntity> listData = new ArrayList<>();
        for (Iterator iterator = jarr.iterator(); iterator.hasNext();) {
            JSONObject value=(JSONObject)iterator.next();
            DataEntity data = new DataEntity();
            data.setsTreatent(value.get("treatment").toString());
            data.setsDate(value.get("date").toString());
            data.setsTime(value.get("time").toString());
            data.setsVolume(value.get("volume").toString());
            data.setsDuration(value.get("duration").toString());
            data.setsOperatorName(value.get("operatorname").toString());
            data.setsRoom(value.get("room").toString());
            data.setsContent(value.get("content").toString());
            listData.add(data);
        }
        return listData;
    }

}
