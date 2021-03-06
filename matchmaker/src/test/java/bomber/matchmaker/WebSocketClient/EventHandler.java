package bomber.matchmaker.WebSocketClient;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

@Component
public class EventHandler extends TextWebSocketHandler implements WebSocketHandler {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(EventHandler.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        log.info("WebSocket connection established - " + session);
        GameController.setConnectedPlayerCount(GameController.getConnectedPlayerCount() + 1);
        String str = session.getUri().toString();
        String str2 = str.substring(str.lastIndexOf("name=", str.length()));
        log.info("Prolonging WS connection for 60 SEC for player," + "asd");
        sleep(TimeUnit.SECONDS.toMillis(30));
        log.info("Closing connection for player #" + "asd");
        session.close();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        GameController.setConnectedPlayerCount(GameController.getConnectedPlayerCount() - 1);
        log.info("Socket Closed: [" + closeStatus.getCode() + "] " + closeStatus.getReason());
        super.afterConnectionClosed(session, closeStatus);
    }

}