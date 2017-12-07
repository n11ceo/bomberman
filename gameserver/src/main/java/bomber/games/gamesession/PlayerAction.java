package bomber.games.gamesession;

public class PlayerAction {

    private Integer id;
    private EventType type;

    public enum EventType {
        Up, Right, Left, Down, Bomb
    }

    public PlayerAction(Integer id, EventType type) {
        this.id = id;//Player id
        this.type = type;//U,R,L,D for MOVE, B for Bomb plant

    }

    public Integer getId() {
        return id;
    }

    public EventType getType() {
        return type;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}