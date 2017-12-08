package bomber.connectionhandler;

import bomber.connectionhandler.json.HandlerInputJson;
import bomber.connectionhandler.json.Possess;
import bomber.games.util.JsonHelper;
import bomber.gameservice.controller.GameController;
import org.jetbrains.annotations.NotNull;
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
    private static HandlerInputJson handlerInputJson;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        log.info("WebSocket connection established - " + session);
        GameController.setConnectedPlayerCount(GameController.getConnectedPlayerCount() + 1);
        log.info("Prolonging WS connection for 60 SEC for player #" + GameController.getConnectedPlayerCount());
        sleep(TimeUnit.SECONDS.toMillis(300));
        log.info("Closing connection for player #" + "asd");
        session.close();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        GameController.setConnectedPlayerCount(GameController.getConnectedPlayerCount() - 1);
        log.info("Socket Closed: [" + closeStatus.getCode() + "] " + closeStatus.getReason());
        super.afterConnectionClosed(session, closeStatus);
    }

    public static HandlerInputJson handleMoveAndPlanBomb(@NotNull String json) {
        handlerInputJson = JsonHelper.fromJson(json, HandlerInputJson.class);
        return handlerInputJson;
    }

    public static String hadnlePossess(@NotNull Possess possess, @NotNull String topic, @NotNull Integer data) {
        possess.setTopic(topic);
        possess.setData(data);
        String json = JsonHelper.toJson(possess);
        return json;
    }



}