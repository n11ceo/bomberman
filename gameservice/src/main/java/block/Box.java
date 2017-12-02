package block;


import geometry.Point;
import model.GameSession;
import model.Positionable;
import org.slf4j.LoggerFactory;

public final class Box implements Positionable {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Box.class);
    private final int id;
    private final Point position;

    public Box(final Point position) {
        this.id = GameSession.nextId();
        this.position = position;
        log.info("New Box: id={},  id={}, position({}, {})\n", id, position.getX(), position.getY());
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
