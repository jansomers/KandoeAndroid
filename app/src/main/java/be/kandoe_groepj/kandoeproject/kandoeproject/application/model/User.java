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

    public String getPictureSmall() {
        return pictureSmall;
    }

    public void setPictureSmall(String pictureSmall) {
        this.pictureSmall = pictureSmall;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String[] getMemberOfGroupIds() {
        return memberOfGroupIds;
    }

    public void setMemberOfGroupIds(String[] memberOfGroupIds) {
        this.memberOfGroupIds = memberOfGroupIds;
    }

    public String[] getMemberOf() {
        return memberOf;
    }

    public void setMemberOf(String[] memberOf) {
        this.memberOf = memberOf;
    }

    public String[] getOrganisatorOf() {
        return organisatorOf;
    }

    public void setOrganisatorOf(String[] organisatorOf) {
        this.organisatorOf = organisatorOf;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegistrar() {
        return registrar;
    }

    public void setRegistrar(String registrar) {
        this.registrar = registrar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureLarge() {
        return pictureLarge;
    }

    public void setPictureLarge(String pictureLarge) {
        this.pictureLarge = pictureLarge;
    }

    private String facebookId;
    private String pictureSmall;
    private String pictureLarge;
}