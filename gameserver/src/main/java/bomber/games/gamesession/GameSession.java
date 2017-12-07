package bomber.games.gamesession;


import bomber.games.model.GameObject;
import bomber.games.model.Tickable;
import bomber.games.util.GeneratorIdSession;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class GameSession implements Tickable {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GameSession.class);
    private List<GameObject> gameObjects = new ArrayList<>();
    private final long id = GeneratorIdSession.getAndIncrementId();
    private final AtomicLong idGenerator = new AtomicLong(0); // У каждой сессии свой набор id

    public long getAndIncrementIdIntoSession() {
        return idGenerator.getAndIncrement();
    }

    public long getId() {
        return id;
    }

    public long getIdGenerator() {
        return idGenerator.get();
    }

    public List<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    @Override
    public void tick(long elapsed) {
        log.info("tick");
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Tickable) {
                ((Tickable) gameObject).tick(elapsed);
            }
        }
    }

}
