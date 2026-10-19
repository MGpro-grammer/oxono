package io.github.mgprogrammer.oxono.event;

import io.github.mgprogrammer.oxono.model.Player;
import io.github.mgprogrammer.oxono.model.Pos;

/**
 * Represents an event for inserting a token into the game.
 * This event is triggered when a player performs a valid insert action during the game.
 * It notifies observers about the type and details of the action,
 * encapsulating the position of the insertion and the player who performed it.
 */
public class InsertEvent extends EventType{
    private final Pos position;
    private final Player player;

    /**
     * Constructs an InsertEvent with the specified position and player.
     *
     * @param position the position where the token is to be inserted
     * @param player   the player performing the insertion
     */
    public InsertEvent(Pos position, Player player) {
        this.position = position;
        this.player = player;
    }

    /**
     * Retrieves the position associated with this event.
     *
     * @return the position represented as a Pos object.
     */
    public Pos getPosition() {
        return position;
    }

    /**
     * Retrieves the player associated with this event.
     *
     * @return the player who performed the corresponding action in this event.
     */
    public Player getPlayer() {
        return player;
    }
}
