package bomber.connectionhandler.json;

import bomber.connectionhandler.PlayerAction;
import bomber.games.model.GameObject;
import bomber.games.util.JsonHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Json {



    public static PlayerAction handleMoveAndPlanBombFromJson(@NotNull String json) { // Это для PLANT_BOMB и MOVE
        HandleInputJson handleInputJson = JsonHelper.fromJson(json, HandleInputJson.class);
        PlayerAction playerAction = convertToPlayerAction(handleInputJson);
        if (playerAction == null) {
            throw new NullPointerException("Мы не смогли конверитировать json в playerAction");
        } else {
            return playerAction;
        }

    }

    @NotNull
    public static String handlePossessToJson(@NotNull final Integer data) { // это для отправки json
        Possess possess = new Possess();
        possess.setData(data);
        return JsonHelper.toJson(possess);
    }


    @NotNull
    public static String handleReplica(@NotNull final Replica replica, @NotNull final Map<Integer,? extends GameObject> map) { //отправка Replic через JSON
        DataReplica dataReplica = replica.getData();
        List<GameObject> list = new ArrayList<>(map.values());
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
}
