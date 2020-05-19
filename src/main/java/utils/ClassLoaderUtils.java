package utils;

import java.lang.reflect.Field;
import java.util.List;

public class ClassLoaderUtils {

    // //https://www.cnblogs.com/huiy/p/6293056.html
    public static void loadSerialDynamically() {
        try {

            String addPath = ToolUtils.getSeriallDLLFileFullPath();
            String newPath = System.getProperty("java.library.path").replaceAll(";.", ";")
                    + addPath;

            newPath = newPath.replaceAll(";.",";");
            newPath = newPath.replaceAll(";:",";");
            System.setProperty("java.library.path", newPath);

            Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
            fieldSysPath.setAccessible(true);
            fieldSysPath.set(null, null);
            //C++动态链接库
            //List<String> list = ToolUtils.getSerialDLLFiles();
            System.loadLibrary("rxtxSerial");
            System.loadLibrary("rxtxParallel");
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


}

