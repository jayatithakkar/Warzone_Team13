package com.APP.Project.UserCoreLogic.constants.interfaces;

import com.APP.Project.UserCoreLogic.constants.enums.CardType;

import java.util.Date;

/**
 * Represents the base abstract class for cards used in gameplay. Cards offer
 * strategic benefits such as gaining additional
 * armies, conducting espionage, and facilitating army mobility among other
 * advantages.
 * Players earn card pieces upon successful attacks and territory captures
 * within a turn. Ownership of a territory at the end
 * of the turn is not required for a player to retain their earned card piece.
 * This mechanism encourages aggressive play and
 * strategic territory captures. Each card has a creation timestamp and a
 * specific type that determines its utility in the game.
 *
 * @author Bhoomiben Bhatt
 * @version 2.0
 */
public abstract class Card {
    private final Date d_createdTime;

    /**
     * Constructor to assign its data members.
     */
    public Card() {
        d_createdTime = new Date();
    }

    /**
     * Returns the type of this card.
     *
     * @return Value of type of card.
     */
    public abstract CardType getType();

    /**
     * Gets the created time for this class.
     *
     * @return Value of timestamp.
     */
    public long getCreatedTime() {
        return this.d_createdTime.getTime();
    }
}
