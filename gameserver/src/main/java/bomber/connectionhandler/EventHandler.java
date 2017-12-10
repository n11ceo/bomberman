package bomber.connectionhandler;

import bomber.connectionhandler.json.*;
import bomber.games.model.GameObject;
import bomber.games.util.JsonHelper;
import bomber.gameservice.controller.GameController;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
    

    public static PlayerAction handleMoveAndPlanBombFromJson(@NotNull String json) { // из json делает объект
        HandleInputJson handleInputJson = JsonHelper.fromJson(json, HandleInputJson.class);
        PlayerAction playerAction = convertToPlayerAction(handleInputJson);
        if (playerAction == null) {
            throw new NullPointerException("Мы не смогли конверитировать json в playerAction");
        } else {
            return playerAction;
        }

    }

    @NotNull
    public static String handlePossessToJson(@NotNull final Integer data) { // возврщает json
        Possess possess = new Possess();
        possess.setData(data);
        return JsonHelper.toJson(possess);
    }


    @NotNull
    public static String handleReplica(@NotNull final Replica replica, @NotNull final List<? extends GameObject> list) {
        DataReplica dataReplica = replica.getData();
        dataReplica.setObjects(list);
        return JsonHelper.toJson(replica);
    }

    @Nullable
    private static PlayerAction convertToPlayerAction(@NotNull HandleInputJson handleInputJson) {
        PlayerAction playerAction = new PlayerAction();
        if (handleInputJson.getTopic() == Topic.MOVE) {
            playerAction.setType(handleInputJson.getData().getEventType());
            return playerAction;
        }
        if (handleInputJson.getTopic() == Topic.PLANT_BOMB) {
            playerAction.setType(PlayerAction.EventType.BOMB);
            return playerAction;
        }
        return null;
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