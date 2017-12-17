package bomber.matchmaker.WebSocketClient;

import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

public class WebSocketClient {


    private static String name;
    private static String gameId;

    public WebSocketClient(String name,String gameId) {
        this.name = name;
        this.gameId = gameId;
    }


    public void startClient() {
        // connection url
        String uri = "ws://localhost:8090/game/connect?gameId=" + gameId + "&name=" + name;

        StandardWebSocketClient client = new StandardWebSocketClient();
        WebSocketSession session = null;
        try {
            EventHandler socket = new EventHandler();
            ListenableFuture<WebSocketSession> fut = client.doHandshake(socket, uri);
            session = fut.get();

        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }
}
