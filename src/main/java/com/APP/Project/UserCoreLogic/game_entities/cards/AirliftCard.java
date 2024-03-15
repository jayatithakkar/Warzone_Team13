package com.APP.Project.UserCoreLogic.game_entities.cards;

import com.APP.Project.UserCoreLogic.constants.enums.CardType;
import com.APP.Project.UserCoreLogic.constants.interfaces.Card;

/**
 * The Airlift Card allows player to transfer your armies long distances. Each time player play one, player can do a
 * single transfer from any of their territories to any other territory of their or one of their teammates. Similar to a
 * normal transfer, those armies can't do any other action that turn. They must wait until the next turn to attack, for
 * example.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class AirliftCard extends Card {
    /**
     * Constructor to assign its data members.
     */
    public AirliftCard() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CardType getType() {
        return CardType.AIRLIFT;
    }
}
