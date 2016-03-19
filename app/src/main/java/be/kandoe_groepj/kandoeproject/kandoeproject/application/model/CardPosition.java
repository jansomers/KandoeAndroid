package be.kandoe_groepj.kandoeproject.kandoeproject.application.model;

import java.io.Serializable;

public class CardPosition implements Serializable {

    private String id;
    private String sessionId;
    private String cardId;
    private String userId;
    private String[] userHistory;
    private int position;
    private String lastChanged;
    private String error;
    private boolean roundEnded;
    private String currentPlayerId;
    private CardPosition updatedCardPosition;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isRoundEnded() {
        return roundEnded;
    }

    public void setRoundEnded(boolean roundEnded) {
        this.roundEnded = roundEnded;
    }

    public String getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setCurrentPlayerId(String currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }

    public CardPosition getUpdatedCardPosition() {
        return updatedCardPosition;
    }

    public void setUpdatedCardPosition(CardPosition updatedCardPosition) {
        this.updatedCardPosition = updatedCardPosition;
    }

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

    public String getLastChanged() {
        return lastChanged;
    }

    public void setLastChanged(String lastChanged) {
        this.lastChanged = lastChanged;
    }
}
