package bean;

public class DataEntity {



    private String sId;
    private String sTreatent;
    private String sDate;
    private String sTime;
    private String sVolume;
    private String sDuration;
    private String sOperatorName;
    private String sRoom;
    private String sContent;

    public DataEntity(){}
    public DataEntity(String sTreatent, String sDate , String sTime, String sVolume, String sDuration, String sOperatorName,
                      String sRoom, String sContent){
        this.sTreatent = sTreatent;
        this.sDate = sDate;
        this.sTime = sTime;
        this.sVolume = sVolume;
        this.sDuration = sDuration;
        this.sOperatorName = sOperatorName;
        this.sRoom = sRoom;
        this.sContent = sContent;
    }
    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }
    public String getsTreatent() {
        return sTreatent;
    }

    public void setsTreatent(String sTreatent) {
        this.sTreatent = sTreatent;
    }

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }

    public String getsVolume() {
        return sVolume;
    }

    public void setsVolume(String sVolume) {
        this.sVolume = sVolume;
    }

    public String getsDuration() {
        return sDuration;
    }

    public void setsDuration(String sDuration) {
        this.sDuration = sDuration;
    }

    public String getsOperatorName() {
        return sOperatorName;
    }

    public void setsOperatorName(String sOperatorName) {
        this.sOperatorName = sOperatorName;
    }

    public String getsRoom() {
        return sRoom;
    }

    public void setsRoom(String sRoom) {
        this.sRoom = sRoom;
    }

    public String getsContent() {
        return sContent;
    }

    public void setsContent(String sContent) {
        this.sContent = sContent;
    }
}
