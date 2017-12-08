package bomber.connectionhandler;

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
        return "id = " + id + "type = " + type;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            if (obj instanceof PlayerAction) {
                PlayerAction playerAction = (PlayerAction) obj;
                return this.id == playerAction.id && this.type == playerAction.type;
            }
            return false;
        }
    }
}