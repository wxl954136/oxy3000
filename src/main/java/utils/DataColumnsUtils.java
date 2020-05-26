package utils;

import bean.DataEntity;

import java.util.Vector;

public class DataColumnsUtils {
    public final static int COL_TREATENT  = 0;
    public final static int COL_DATE = 1;
    public final static int COL_TIME  = 2;
    public final static int COL_VOLUME = 3;
    public final static int COL_DURATION  = 4;
    public final static int COL_OPERATORNAME = 5;
    public final static int COL_ROOM  = 6;
    public final static int COL_CONTENT  = 7;

    public static Vector<Object> getListContent(DataEntity data ){
        Vector<Object> vector = new Vector<Object>();
        vector.add(data.getsTreatent());
        vector.add(data.getsDate());
        vector.add(data.getsTime());
        vector.add(data.getsVolume());
        vector.add(data.getsDuration());
        vector.add(data.getsRoom());
        vector.add(data.getsContent());
        return vector;

    }




}
