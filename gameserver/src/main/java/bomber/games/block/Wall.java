package block;

import geometry.Point;
import model.GameSession;
import model.Positionable;
import org.slf4j.LoggerFactory;


public final class Wall implements Positionable {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Wall.class);

    private final int id;
    private final Point position;

    public Wall(final Point position) {
        this.position = position;
        this.id = GameSession.nextId();
        log.info("New Wall: id={},  id={}, position({}, {})\n", id, position.getX(), position.getY());
    }


    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public int getId() {
        return id;
    }
}
