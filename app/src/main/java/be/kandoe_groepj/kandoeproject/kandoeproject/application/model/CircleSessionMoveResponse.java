package be.kandoe_groepj.kandoeproject.kandoeproject.application.model;

import java.io.Serializable;

public class CircleSessionMoveResponse implements Serializable {

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
}