package bomber.games.model;

import bomber.games.geometry.Point;

/**
 * Any entity of game mechanics
 */
public interface GameObject {
    /**
     * Unique id
     */
    long getId();
    Point getPosition();
}
