package bomber.games.gameobject;

import bomber.games.gamesession.GameSession;
import bomber.games.geometry.Point;
import bomber.games.model.Movable;
import org.slf4j.LoggerFactory;


public final class Player implements Movable {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Player.class);
    private final int id;
    private Point position;
    private long lifeTime;
    private int velocity;



    private int rangeExplosion;
    private int countBomb;
    private Type type;


    public enum Type {
        GIRL, BOY
    }


    public Player(final Point position, Type type) {
        this.position = position;
        this.id = GameSession.nextId();
        this.rangeExplosion = 1;
        this.velocity = 1;
        this.countBomb = 1;
        this.lifeTime = 0;
        this.type = type;
        log.info("New Player: id={}, position({}, {}), type={}\n", id, position.getX(), position.getY(), type);
    }

    @Override
    public Point move(Direction direction, long time) {
        if (time > 0) {
            switch (direction) {
                case UP:
                    position = new Point(position.getX(), (int) (position.getY() + (time * velocity)));
                    tick(1L);
                    break;
                case DOWN:
                    position = new Point(position.getX(), (int) (position.getY() - (time * velocity)));
                    tick(1L);
                    break;
                case RIGHT:
                    position = new Point((int) (position.getX() + (time * velocity)), position.getY());
                    tick(1L);
                    break;
                case LEFT:
                    position = new Point((int) (position.getX() - (time * velocity)), position.getY());
                    tick(1L);
                    break;
                case IDLE:
                    tick(1L);
                    break;
                default:
                    break;
            }
        } else
            throw new IllegalArgumentException("can't support negative time");
        return position;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void tick(long elapsed) {
        lifeTime += elapsed;
    }

    public int getRangeExplosion() {
        return rangeExplosion;
    }

}
