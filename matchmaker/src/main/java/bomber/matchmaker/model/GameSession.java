package bomber.matchmaker.model;




import bomber.matchmaker.connection.Connection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Queue;


@Entity
@Table(name = "gs", schema = "bomber")
public class GameSession {

    @Id
    @Column(name = "gameId", unique = true)
    private Integer gameId;

    @Column(name = "user1", length = 30)
    private String user1;

    @Column(name = "user2", length = 30)
    private String user2;

    @Column(name = "user3", length = 30)
    private String user3;

    @Column(name = "user4", length = 30)
    private String user4;

    @Column(name = "time", nullable = false)
    private Date time = new Date();

    public GameSession(Queue<? extends Connection> queue) {
        this.user1 = queue.poll().getName();
        this.user2 = queue.poll().getName();
        this.user3 = queue.poll().getName();
        this.user4 = queue.poll().getName();
    }

    public GameSession() {
    }

    public String getUser1() {
        return user1;
    }

    public GameSession setUser1(String user1) {
        this.user1 = user1;
        return this;
    }

    public String getUser2() {
        return user2;
    }

    public GameSession setUser2(String user2) {
        this.user2 = user2;
        return this;
    }

    public String getUser3() {
        return user3;
    }

    public GameSession setUser3(String user3) {
        this.user3 = user3;
        return this;
    }

    public String getUser4() {
        return user4;
    }

    public GameSession setUser4(String user4) {
        this.user4 = user4;
        return this;
    }

    public Integer getGameId() {
        return gameId;
    }

    public GameSession setGameId(Integer gameId) {
        this.gameId = gameId;
        return this;
    }

    public Date getTime() {
        return time;
    }

    public GameSession setTime(Date time) {
        this.time = time;
        return this;
    }

    @Override
    public String toString() {
        return "GameSession{" +
                ",gameId=" + gameId +
                ",user1=" + user1 +
                "user2=" + user2 +
                "user3=" + user3 +
                "user4=" + user4 +
                "time=" + time;
    }
}


