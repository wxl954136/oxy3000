package bean;

public class RememberEntity {


    public String remember;
    public String times;
    public RememberEntity()
    {

    }

    public RememberEntity(String remember, String times) {
        this.remember = remember;
        this.times = times;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }
    public String getRemember() {
        return remember;
    }

    public void setRemember(String remember) {
        this.remember = remember;
    }



}
