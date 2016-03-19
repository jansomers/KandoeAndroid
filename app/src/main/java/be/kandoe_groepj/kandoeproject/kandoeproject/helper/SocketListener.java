package be.kandoe_groepj.kandoeproject.kandoeproject.helper;

public interface SocketListener {

    void notifyMoveCard(String cardId, String message, String cardPosition);
    void notifyInitCard(boolean roundEnded, String currentPlayerId, String[] cards);
}