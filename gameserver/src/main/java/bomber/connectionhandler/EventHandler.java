package bomber.connectionhandler;

import bomber.gameservice.controller.GameController;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

@Component
public class EventHandler extends TextWebSocketHandler implements WebSocketHandler {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(EventHandler.class);
    private static final Map<WebSocketSession, Player> ConnectionPool = new HashMap<>();
    public static final String GAMEID_ARG = "gameId";
    public static final String NAME_ARG = "gameId";


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        //connected player count?
        ConnectionPool.put(session, uriToPlayer(session.getUri()));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        GameController.getGameSession(ConnectionPool.get(session).getGameid()).getInputQueue()
                .add(handleMoveAndPlanBombFromJson(message.getPayload()));

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        //connected player count?
        ConnectionPool.remove(session);
        super.afterConnectionClosed(session, closeStatus);
    }

    private static Player uriToPlayer(final URI uri) {
        Player player = new Player(); //is id needed?
        for (String iter : uri.getQuery().split("&")) {
            if (iter.contains(GAMEID_ARG) && !(iter.indexOf("=") == iter.length() - 1)) {
                player.setGameid(Integer.parseInt(iter.substring(iter.indexOf("=") + 1, iter.length() - 1)));
            }
            if (iter.contains(NAME_ARG) && !(iter.indexOf("=") == iter.length() - 1)) {
                player.setName(iter.substring(iter.indexOf("=") + 1, iter.length() - 1));
            }
        }
        return player;
    }


}