package bomber.games.gameobject;


import bomber.games.gamesession.GameSession;
import bomber.games.geometry.Point;
import bomber.games.model.Positionable;
import org.slf4j.LoggerFactory;

public final class Box implements Positionable {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Box.class);
    private final Point position;
    private boolean exist;
    private final long id;

    public Box(final long id, final Point position, boolean exist) {
        this.id = id;
        this.position = position;
        this.exist = exist;
        log.info("New Box: id={},  id={}, position({}, {})\n", position.getX(), position.getY());
    }


    @Override
    public Point getPosition() {
        return position;
    }

    public boolean isExist() {
        return exist;
    }

    @Override
    public long getId() {
        return id;
    }
}
