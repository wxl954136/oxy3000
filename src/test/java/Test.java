import bean.DataEntity;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import form.Start;
import utils.FileUtil;
import utils.JsonRead;
import utils.ToolUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//对象模型
public class Test {

    public static void main(String[] args) {
        String date = "20190102";
        System.out.println(date.length());
        String year = date.substring(0,4);
        String month = date.substring(4,6);
        String day = date.substring(6);
        System.out.println(year);
        System.out.println(month);
        System.out.println(day);
        /*
        Map<String,List<String>> map = new HashMap();
        List<String> files = FileUtil.getHistoryFiles();
        for(String file: files){
            if (file.indexOf("REC") <0) continue;
            file = file.replaceAll("REC-","");
            int loc = file.indexOf("#");
            String deviceid = file.substring(0,loc);
            String date = file.substring(loc+1,loc + 9);

            if (map.containsKey(deviceid))
            {
                ((List<String>)map.get(deviceid)).add(date);
            }else
            {
                List<String>  listDate = new ArrayList<>();
                listDate.add(date);
                map.put(deviceid,listDate);
            }
        }

        for(String key:map.keySet()){
            System.out.println("key="+key+"  value= " +map.get(key).toString());
        }

         */
        /*
        String fileKey = "REC-222110228318#20200527144139.json";

        int loc = fileKey.indexOf("#");
        String fileIndex = fileKey.substring(0,loc+1) +fileKey.substring(loc +1,loc+9);
        System.out.println(fileIndex);
*/
        /*
        Map map = ToolUtils.getSortMap();
        map.put("1111","aaaaa");

        map.put("3333","ccccc");
        map.put("44444","bbbbb");
        map.put("2222","2222");
        Iterator<String> iterator = map.keySet().iterator();
        while(iterator.hasNext()){
            String key = iterator.next();
            System.out.println(key + "===" + map.get(key));


        }


         */
        /*
        List<DataEntity>  list =JsonRead.getJsonRecordFileToEntity("ttt.json");
        for(DataEntity data:list)
        {
            System.out.println("x===="  + data.getsDate() + "=**==" + data.getsTime() + "=::==" + data.getsVolume());
        }

         */
/*
        String filename = ToolUtils.getUserDir() + "\\resources\\txt\\history\\" + "ttt.json";
        String readJson = JsonRead.getInstance().readJsonFile(filename);
        JSONObject object = JSONObject.parseObject(readJson);
        JSONArray jarr=JSONArray.parseArray(object.get("data").toString());
        List<DataEntity> listData = new ArrayList<>();
        for (Iterator iterator = jarr.iterator(); iterator.hasNext();) {
            JSONObject value=(JSONObject)iterator.next();
            DataEntity data = new DataEntity();
            data.setsTreatent(value.get("treatment").toString());
            data.setsDate(value.get("date").toString());
            data.setsTime(value.get("treatment").toString());
            data.setsVolume(value.get("time").toString());
            data.setsDuration(value.get("duration").toString());
            data.setsOperatorName(value.get("operatorname").toString());
            data.setsRoom(value.get("room").toString());
            data.setsContent(value.get("content").toString());
            listData.add(data);
        }
        System.out.println(object.get("data"));

 */
/*
        String deviceid = "at+deviceid=222110228316";
        System.out.println("x====" + ToolUtils.getOrderName("sdsbxlh"));
        int loc = deviceid.indexOf( ToolUtils.getOrderName("sdsbxlh") + "=");
        System.out.println("g===");
        System.out.println("c-=====" + ToolUtils.getOrderName("cxsbxlh"));
        System.out.println("c1-=====" + deviceid);
       // String k  = deviceid.replaceAll(ToolUtils.getOrderName("cxsbxlh"),"");
        String k = deviceid.replaceAll("at+deviceid","");
        System.out.println("g====" + k);

 */
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
