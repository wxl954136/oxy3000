package utils;

import bean.DataEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FileUtil {
    public static void test()
    {
        System.out.println(getCreateFile().toString());
    }
    public static File getCreateFile()
    {
        File resultFile = null;
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        fileName = "rec" + fileName + ".txt";
        String fileFullPath=ToolUtils.getUserDir() + "\\resources\\txt\\history"  ;
        try{
            File dirs = new File( fileFullPath);
            if (!dirs.exists()) {
                dirs.mkdirs();// 能创建多级目录
            }
            String fileFullName =  fileFullPath + "\\" +  fileName;
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

    public static void setTxtFileData(List<DataEntity> listData){
        String content = "";
        content += ("Treatent" + "|");
        content += ("Date" + "|");
        content += ("Time" + "|");
        content += ("Volume" + "|");
        content += ("Duration" + "|");
        content += ("OperatorName" + "|");
        content += ("Room" + "|");
        content += ("Content" );
        content += "\n" ;

        for(DataEntity data : listData){
            content += data.getsTreatent() + "|";
            content += data.getsDate() + "|";
            content += data.getsTime() + "|";
            content += data.getsVolume() + "|";
            content += data.getsDuration() + "|";
            content += data.getsOperatorName() + "|";
            content += data.getsRoom() + "|";
            content += data.getsContent() ;
            content += "\n";
        }
        try{
            File createFile = getCreateFile();
            writeTxtFile(content,createFile);
            if(listData.size() >0)
            {
                deleteExportTxtFile(createFile.getName());
            }
        }catch(Exception eg){
            eg.printStackTrace();
        }
    }

    public static boolean writeTxtFile(String content,File  fileName)throws Exception {
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
    public static void deleteExportTxtFile(String retainFile )
    {
        String fileFullPath=ToolUtils.getUserDir() + "\\resources\\txt\\history"  ;
        File dirs = new File( fileFullPath);
        File files[] = dirs.listFiles();
        for(File file:files){
            if (file.isFile()){
                if (file.getName().equalsIgnoreCase(retainFile)) continue;
                file.delete();
            }
        }

    }
}
