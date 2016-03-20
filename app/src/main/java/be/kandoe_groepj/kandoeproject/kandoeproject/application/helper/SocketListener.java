package be.kandoe_groepj.kandoeproject.kandoeproject.application.helper;

public interface SocketListener {

    void notifyMoveCard(String cardId, String message, String cardPosition);
    void notifyInitCard(boolean roundEnded, String currentPlayerId, String[] cards);
}