package bomber.games.gameobject;


import bomber.games.geometry.Point;
import bomber.games.model.Positionable;
import org.slf4j.LoggerFactory;
/*
* The game card is not implemented !!!!!
*/
public final class GameGround implements Positionable {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Box.class);
    private final long id;
    private final Point position;

    public GameGround(final long id, final Point position) {
        this.id = id;
        this.position = position;
        log.info("New Box: id={},  id={}, position({}, {})", id, position.getX(), position.getY());
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
