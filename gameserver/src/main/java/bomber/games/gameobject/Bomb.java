package bomber.games.gameobject;

import bomber.games.geometry.Point;
import bomber.games.model.Positionable;
import org.slf4j.LoggerFactory;
import bomber.games.model.Tickable;


public final class Bomb implements Tickable, Positionable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Bomb.class);
    private static final long LIFE_TIME = 2500; //after pass death

    private final long id;
    private long lifeTime = 0;
    private int rangeExplosion = 1;
    private final Point position;

    public Bomb(long id, final Point position) {
        this.id = id;
        this.position = position;
        this.lifeTime = 0;

        log.info("New Bomb: id={}, position({}, {})\n", id, position.getX(), position.getY());
    }

    @Override
    public void tick(final long elapsed) {
        lifeTime += elapsed;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public long getId() {
        return id;
    }
}
