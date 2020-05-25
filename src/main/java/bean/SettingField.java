package bean;

public class SettingField {
    private String currentPort; //当前端口
    private String deviceName;
    private String deviceDate;
    private String deviceHour;
    private String deviceMinute;
    private String deviceSecond = "30";
    private String atTime;
    private Boolean answer;

    public String getCurrentPort() {
        return currentPort;
    }

    public void setCurrentPort(String currentPort) {
        this.currentPort = currentPort;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceDate() {
        return deviceDate;
    }

    public void setDeviceDate(String deviceDate) {
        this.deviceDate = deviceDate;
    }

    public String getDeviceHour() {
        return deviceHour;
    }

    public void setDeviceHour(String deviceHour) {
        this.deviceHour = deviceHour;
    }

    public String getDeviceMinute() {
        return deviceMinute;
    }

    public void setDeviceMinute(String deviceMinute) {
        this.deviceMinute = deviceMinute;
    }

    public Boolean getAnswer() {
        return answer;
    }

    public void setAnswer(Boolean answer) {
        this.answer = answer;
    }

    public String getAtTime() {
        return this.getDeviceHour()+this.getDeviceMinute()+deviceSecond;
    }

    public void setAtTime(String atTime) {

        this.atTime = atTime;
    }
}
