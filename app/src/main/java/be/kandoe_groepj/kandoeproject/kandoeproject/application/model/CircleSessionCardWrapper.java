package be.kandoe_groepj.kandoeproject.kandoeproject.application.model;

public class CircleSessionCardWrapper {

    private Card card;
    private String cardId;
    private boolean inPlay;

    public boolean isInPlay() {
        return inPlay;
    }

    public void setInPlay(boolean inPlay) {
        this.inPlay = inPlay;
    }

    public Card getCard() {

        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
