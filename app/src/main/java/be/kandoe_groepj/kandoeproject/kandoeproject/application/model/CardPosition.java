package be.kandoe_groepj.kandoeproject.kandoeproject.application.model;

import java.io.Serializable;
import java.util.Date;

public class CardPosition implements Serializable {

    private String id;
    private String sessionId;
    private String cardId;
    private String userId;
    private String[] userHistory;
    private int position;
    private Date lastChanged;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String[] getUserHistory() {
        return userHistory;
    }

    public void setUserHistory(String[] userHistory) {
        this.userHistory = userHistory;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Date getLastChanged() {
        return lastChanged;
    }

    public void setLastChanged(Date lastChanged) {
        this.lastChanged = lastChanged;
    }
}
