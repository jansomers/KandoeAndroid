package be.kandoe_groepj.kandoeproject.kandoeproject.application.model;

public class User extends SimpleResult {

    public User() {

    }

    public User(String email, String password) {
        this.name = "hello world";
        this.email = email;
        this.password = password;
        this.id = "wtf";
    }

    private String name;
    private String email;
    private String password;
    private String registrar;
    private String id;
    private String[] organisatorOf;
    private String[] memberOf;
    private String[] memberOfGroupIds;
    private String facebookId;
    private String pictureSmall;
    private String pictureLarge;
}