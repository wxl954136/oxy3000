package bean;

public class SettingField {
    private String currentPort; //当前端口
    private String deviceName;
    private String deviceDate;
    private String deviceHourStart;
    private String deviceHourEnd;
    private Boolean answer;

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

    public String getDeviceHourStart() {
        return deviceHourStart;
    }

    public void setDeviceHourStart(String deviceHourStart) {
        this.deviceHourStart = deviceHourStart;
    }

    public String getDeviceHourEnd() {
        return deviceHourEnd;
    }

    public void setDeviceHourEnd(String deviceHourEnd) {
        this.deviceHourEnd = deviceHourEnd;
    }


    public Boolean getAnswer() {
        return answer;
    }

    public void setAnswer(Boolean answer) {
        this.answer = answer;
    }

    public String getCurrentPort() {
        return currentPort;
    }

    public void setCurrentPort(String currentPort) {
        this.currentPort = currentPort;
    }
}
