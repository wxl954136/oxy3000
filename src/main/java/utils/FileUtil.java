package utils;

import bean.DataEntity;
import bean.RememberEntity;
import bean.UserEntity;
import form.Start;

import java.io.*;
import java.util.*;

public class FileUtil {


    public static File getCreateJsonFile(String fileName)
    {
        File resultFile = null;
//        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//        fileName = "rec" + fileName + ".json";
        String fileFullPath=ToolUtils.getUserDir() + "\\resources\\txt\\history"  ;
        try{
            File dirs = new File( fileFullPath);
            if (!dirs.exists()) {
                dirs.mkdirs();// 能创建多级目录
            }
            String fileFullName =  fileFullPath + "\\"  + fileName;
            File file = new File(fileFullName);
            if (!file.exists()){
                file.createNewFile();
            }
            resultFile = file;
        }catch(Exception e){
            resultFile = null;
        }
        return resultFile;
    }
    public static File getCreateJsonResourceFile(String fileName)
    {
        File resultFile = null;
        String fileFullPath=ToolUtils.getUserDir() + "\\resources\\json"  ;
        try{
            File dirs = new File( fileFullPath);
            if (!dirs.exists()) {
                dirs.mkdirs();// 能创建多级目录
            }
            String fileFullName =  fileFullPath + "\\"  + fileName;
            File file = new File(fileFullName);
            if (!file.exists()){
                file.createNewFile();
            }
            resultFile = file;
        }catch(Exception e){
            resultFile = null;
        }
        return resultFile;
    }
    public static void setJsonFileData(List<DataEntity> listData){
        String content = "";
        if (listData.size() <=0 ) return ;
        String records = "";
        for (int index = 0 ; index<listData.size() ;index++ ){
            DataEntity data = listData.get(index);
            records = JsonRead.getStr2JsonEntity(  (index==listData.size()-1)?"":",");
            records = records.replaceAll("#treatment#",data.getsTreatent());
            records = records.replaceAll("#date#",data.getsDate());
            records = records.replaceAll("#time#",data.getsTime());
            records = records.replaceAll("#volume#",data.getsVolume());
            records = records.replaceAll("#duration#",data.getsDuration());
            records = records.replaceAll("#operatorname#",data.getsOperatorName());
            records = records.replaceAll("#room#",data.getsRoom());
            records = records.replaceAll("#content#",data.getsContent());
            content +=  records;
        }

        String result = "";
        result = result + JsonRead.getGenJsonStart();
        //增加单一元素----开始
        result = result + JsonRead.getGenJsonTag("filefulldate", ToolUtils.getCurrentDate("yyyyMMddHHmmss"),",");
        result = result + JsonRead.getGenJsonTag("filedate", ToolUtils.getCurrentDate("yyyyMMdd"),",");
        result = result + JsonRead.getGenJsonTag("filetime", ToolUtils.getCurrentDate("HHmmss"),",");
        String deviceid =  ToolUtils.getPurStr(Start.getInstance().resultDeviceId);
        deviceid = deviceid.indexOf("=") >=0?deviceid.substring(deviceid.indexOf("=") +1):"";
        result = result + JsonRead.getGenJsonTag("deviceid",deviceid,",");


        String fileName = "REC-" + deviceid + ".json";//+ "#" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".json";
        result = result + JsonRead.getGenJsonTag("filename",fileName,",");


        String devicename =  ToolUtils.getPurStr(Start.getInstance().resultDeviceName);
        devicename = devicename.indexOf("=") >=0?devicename.substring(devicename.indexOf("=") +1):"";
        result = result + JsonRead.getGenJsonTag("devicename", devicename,",");

        String devicedate =  ToolUtils.getPurStr(Start.getInstance().resultDeviceDate);
        devicedate = devicedate.indexOf("=") >=0?devicedate.substring(devicedate.indexOf("=") +1):"";
        result = result + JsonRead.getGenJsonTag("devicedate", devicedate,",");

        String deviceversion =  ToolUtils.getPurStr(Start.getInstance().resultDeviceVersion);
        deviceversion = devicedate.indexOf("=") >=0?deviceversion.substring(deviceversion.indexOf("=") +1):"";
        result = result + JsonRead.getGenJsonTag("deviceversion",deviceversion,",");
        //增加单一元素----结束
        result = result + JsonRead.getGenJsonArrayBegin("data");
        //增加明细--开始
        result = result + content;
        //增加明细--开始结束
        result = result + JsonRead.getGenJsonArrayEnd();
        result = result + JsonRead.getGenJsonEnd();

        try{
            File createFile = getCreateJsonFile(fileName);
            writeJsonFile(result,createFile);
            if(listData.size() >0)
            {
                //每天都保存，重复记录就直接显示，不管它
                deleteExportJsonFile(createFile.getName());
            }
        }catch(Exception eg){
            eg.printStackTrace();
        }
    }

    public static boolean writeJsonFile(String content,File  fileName)throws Exception {
        RandomAccessFile mm = null;
        boolean flag = false;
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(fileName);
            outputStream.write(content.getBytes("GBK"));
            outputStream.close();
//   mm=new RandomAccessFile(fileName,"rw");
//   mm.writeBytes(content);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mm != null) {
                mm.close();
            }
        }
        return flag;
    }

    public static void setJsonFileRememberData(RememberEntity data)
    {
        if (data.remember.trim().length() <=0 ) return ;
        String result = "";
        result = result + JsonRead.getGenJsonStart();
        //增加单一元素----开始
        result = result + JsonRead.getGenJsonTag("remember", data.remember.trim(),",");
        result = result + JsonRead.getGenJsonTag("times", data.times.trim(),"");
        String fileName = "login.json";
        //增加单一元素----结束
        result = result + JsonRead.getGenJsonEnd();
        try{
            File createFile = getCreateJsonResourceFile(fileName);
            writeJsonFile(result,createFile);
        }catch(Exception eg){
            eg.printStackTrace();
        }
    }
    public static void setJsonFileUserData(List<UserEntity> listData){
        String content = "";
        if (listData.size() <=0 ) return ;
        String records = "";
        for (int index = 0 ; index<listData.size() ;index++ ){
            UserEntity data = listData.get(index);
            records = JsonRead.getStr2JsonUserEntity((index==listData.size()-1)?"":",");
            records = records.replaceAll("#user#",data.getUser());
            records = records.replaceAll("#pwd#",data.getPwd());
            records = records.replaceAll("#level#",data.getLevel());
            content +=  records;
        }
        String result = "";
        result = result + JsonRead.getGenJsonStart();
        String fileName = "user.json";
        result = result + JsonRead.getGenJsonArrayBegin("users");
        result = result + content;
        result = result + JsonRead.getGenJsonArrayEnd();
        result = result + JsonRead.getGenJsonEnd();
        try{
            File createFile = getCreateJsonResourceFile(fileName);
            writeJsonFile(result,createFile);
            if(listData.size() >0)
            {
                //wxl modi
                //每天都保存，重复记录就直接显示，不管它
               // deleteExportJsonFile(createFile.getName());
            }
        }catch(Exception eg){
            eg.printStackTrace();
        }
    }

    public static List<String> getHistoryFiles()
    {
        List<String> list = new ArrayList<>();
        String fileFullPath=ToolUtils.getUserDir() + "\\resources\\txt\\history"  ;
        File dirs = new File( fileFullPath);
        File files[] = dirs.listFiles();
        for(File file:files){
            if (file.isFile()){
                list.add(file.getName());
            }
        }
        return list;
    }

    public static String getSearchFile(String deviceid,String date)
    {
        String fileTemp ="REC-" + deviceid.trim()+ "#" + date.replaceAll("-","");
        List<String> listFiles = getHistoryFiles();
        String result = "";
        for(String fileName : listFiles){
            if (fileName.indexOf(fileTemp) >= 0){
                result = fileName ;
                break;
            }
        }
        return result;
    }
    public static void deleteExportJsonFile(String retainFile )
    {
        //fileIndex.同一设备同一天一天只能存在一份,以最后一次的为准
        int loc = retainFile.indexOf("#");
        if(loc < 0 ) return ;
        String fileIndex = retainFile.substring(0,loc+1) +retainFile.substring(loc +1,loc+9);
        String fileFullPath=ToolUtils.getUserDir() + "\\resources\\txt\\history"  ;
        File dirs = new File( fileFullPath);
        File files[] = dirs.listFiles();
        for(File file:files){
            if (file.isFile()){
                if (file.getName().equalsIgnoreCase(retainFile)) continue;
                if (file.getName().lastIndexOf(fileIndex) >=0)
                {
                    file.delete();
                }

            }
        }
    }


    /**
     * 获取history目录下的所有文件，并解析成deviceid与日期
     * Map<String,List<String>> key:deviceid  list<String>每个deviceId有几个日期的
     * @return
     */
    public static Map<String,List<String>> getRecordHistory()
    {
        TreeMap map = new TreeMap(Comparator.naturalOrder());  //升序

        List<String> files = FileUtil.getHistoryFiles();
        for(String file: files){
            if (file.indexOf("REC") <0) continue;
            file = file.replaceAll("REC-","");
            String deviceid = file.replaceAll(".json","");
            /*
            int loc = file.indexOf("#");
            String deviceid = file.substring(0,loc);
            String date = file.substring(loc+1,loc + 9);
            String year = date.substring(0,4);
            String month = date.substring(4,6);
            String day = date.substring(6);
            //符合日期格式则在下拉框中给出日期格式
            if (date.length() == 8 ) date = year + "-" + month + "-" + day;
            */
            if (map.containsKey(deviceid))
            {
                ((List<String>)map.get(deviceid)).add(deviceid);
            }else
            {
                List<String>  listDate = new ArrayList<>();
                listDate.add(deviceid);
                map.put(deviceid,listDate);
            }
        }
        return map;
    }

}
