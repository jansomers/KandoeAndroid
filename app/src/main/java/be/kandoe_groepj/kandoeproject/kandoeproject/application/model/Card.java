package be.kandoe_groepj.kandoeproject.kandoeproject.application.model;

public class Card {

    private String id;
    private String name;
    private String themeId;
    private String cardPosition = "0";

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

    public String getCardPosition() {
        return cardPosition;
    }

    public void setCardPosition(String cardPosition) {
        this.cardPosition = cardPosition;
    }
}
