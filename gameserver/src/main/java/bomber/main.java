package bomber;

import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Timer;

public class main {
    public static void main(String args[]) throws URISyntaxException {
//        StandardWebSocketSession standardWebSocketSession = new StandardWebSocketSession();
        URI uri = new URI("ws://localhost:8090/game/connect?gameId=42&name=c");
        System.out.println(uri.getQuery());
        System.out.println(EventHandler.uriToPlayer(uri).getGameid());
        System.out.println(EventHandler.uriToPlayer(uri).getName());
    }
}
