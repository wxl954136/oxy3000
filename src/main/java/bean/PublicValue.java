package bean;

import java.util.Map;

public class PublicValue {
    //固件版本号，软件启动后，需要获取Start.java中
    public static String FIRMWARE = "";
    public static Map<String,UserEntity> USERS = null;
    public static UserEntity CURRENT_USER = null;
    public static boolean isExistUserName(String userName)
    {
        return USERS.containsKey(userName.trim())?true:false;
    }

    public static UserEntity getUsersForName(String userName) {
        if (USERS == null || !USERS.containsKey(userName)) return null;
        return USERS.get(userName);
    }

}
