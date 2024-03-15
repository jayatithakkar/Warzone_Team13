package com.APP.Project.UserCoreLogic.common.services;

import com.APP.Project.UserCoreLogic.game_entities.cards.*;
import com.APP.Project.UserCoreLogic.constants.enums.CardType;
import com.APP.Project.UserCoreLogic.constants.interfaces.Card;

import java.util.List;
import java.util.Random;

/**
 * This class provides functionality to assign a random card to the player.
 *
 * @author Rikin Dipakkumar Chauhan
 * @version 1.0
 */
public class AssignRandomCardService {

    /**
     * List of available card types.
     */
    public static List<CardType> d_CardList = CardType.usableCardList();

    /**
     * Generates a random card from the available card types.
     *
     * @return A randomly generated card.
     */
    public static Card randomCard() {
        Random rand = new Random();
        CardType l_cardType = d_CardList.get(rand.nextInt(d_CardList.size()));
        if (l_cardType == CardType.AIRLIFT) {
            return new AirliftCard();
        }
        if (l_cardType == CardType.BOMB) {
            return new BombCard();
        }
        if (l_cardType == CardType.BLOCKADE) {
            return new BlockadeCard();
        }
        if (l_cardType == CardType.DIPLOMACY) {
            return new DiplomacyCard();
        }
        return new EmptyCard();
    }
}
