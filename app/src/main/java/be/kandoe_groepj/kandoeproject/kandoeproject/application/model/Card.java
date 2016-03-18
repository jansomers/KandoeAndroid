package be.kandoe_groepj.kandoeproject.kandoeproject.application.model;

import java.io.Serializable;

public class Card implements Serializable {

    private String id;
    private String name;
    private String themeId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }
}
