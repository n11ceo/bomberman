package bomber.games.gamesession;


import bomber.connectionhandler.PlayerAction;
import bomber.games.model.GameObject;
import bomber.games.model.Tickable;
import bomber.games.util.GeneratorIdSession;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

public class GameSession implements Tickable {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GameSession.class);
    private List<GameObject> gameObjects = new ArrayList<>();
    private final long id;
    private final AtomicLong idGenerator = new AtomicLong(0); // У каждой сессии свой набор id
    private  ConcurrentLinkedQueue<PlayerAction> inputQueue = new ConcurrentLinkedQueue<>();

    public ConcurrentLinkedQueue<PlayerAction> getInputQueue() {
        return inputQueue;
    }

    public GameSession(long id) {
        this.id = id;
    }

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        } else {
            if (obj instanceof GameSession) {
               GameSession gameSession = (GameSession) obj;
               return this.id == gameSession.id;
            }
            return false;
        }
    }


}
