package bean;

public class UserEntity {


    private String id;
    private String user;
    private String pwd;

    private String level;



    private String pwd2;

    public UserEntity()
    {

    }
    public UserEntity (String _user,String _pwd,String _level)
    {
        this.setUser(_user);
        this.setPwd(_pwd);
        this.setLevel(_level);
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }


    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd2 = pwd;
        this.pwd = pwd;
    }

    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }

    public String getPwd2() {
        return pwd2;
    }

    public void setPwd2(String pwd2) {
        this.pwd2 = pwd2;
    }
}
