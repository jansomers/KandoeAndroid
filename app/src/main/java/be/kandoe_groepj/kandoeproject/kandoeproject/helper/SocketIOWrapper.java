package be.kandoe_groepj.kandoeproject.kandoeproject.helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketIOWrapper {

    private Socket socket;
    private SocketListener listener;

    public SocketIOWrapper(SocketListener listener, String circleSessionId) {
        this.listener = listener;
        initSocket(circleSessionId);
    }

    private void initSocket(String circleSessionId) {
        try {
            socket = IO.socket("http://10.0.3.2:8080");
            joinRoom(circleSessionId);
            initEvents();
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void initEvents() {
        socket.on("send move", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    System.out.println(args.length);
                    JSONObject obj = new JSONObject((String) args[0]);
                    String cardId = obj.getString("_cardId");
                    String currentPlayerId = obj.getString("_currentPlayerId");
                    String cardPosition = obj.getString("_cardPosition");
                    listener.notify(cardId, currentPlayerId, cardPosition);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void joinRoom(String circleSessionId) {
        try {
            JSONObject joinSessionObject = new JSONObject();
            joinSessionObject.put("sessionId", circleSessionId);
            send("join session", joinSessionObject, true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void send(String event, JSONObject data, boolean isString) {
        socket.emit(event, isString ? data.toString() : data);
    }

    public void sendMoveCard(String cardId, String currentPlayerId, int cardPosition) {
        try {
            JSONObject object = new JSONObject();
            object.put("_cardId", cardId);
            object.put("_currentPlayerId", currentPlayerId);
            object.put("_cardPosition", cardPosition);
            send("send move", object, false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}