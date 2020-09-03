package utils;

import bean.DataEntity;
import bean.UserEntity;

import java.util.Vector;

public class UserColumnsUtils {
    public final static int COL_ID  = 0;
    public final static int COL_USER  = 1;
    public final static int COL_PWD = 2;
    public final static int COL_LEVEL  = 3;
    public static Vector<Object> getListContent(UserEntity data ){
        Vector<Object> vector = new Vector<Object>();
        vector.add(data.getId());
        vector.add(data.getUser());
        vector.add(data.getPwd());
        vector.add(data.getLevel());
        return vector;
    }




}
