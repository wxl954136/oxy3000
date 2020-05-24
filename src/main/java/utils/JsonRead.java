package utils;


import com.alibaba.fastjson.JSONObject;

import java.io.*;

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
        String jsonSource = readJsonFile(jsonFile);
        JSONObject object = JSONObject.parseObject(jsonSource);
        String result1 = object.get(target1).toString();
        JSONObject object2 = JSONObject.parseObject(result1);
        String result2 = object2.get(target2).toString();
        return result2;
    }
    //从字符中取
    public String getStrJson(String jsonSource) {
        JSONObject object = JSONObject.parseObject(jsonSource);
        return object.get(jsonSource).toString();
    }

    public JSONObject getCommonJSONObject() {
        String jsonSource = readJsonFile(jsonFile);
        return JSONObject.parseObject(jsonSource);
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

}
