package com.APP.Project.UserCoreLogic.constants.enums;

import com.APP.Project.UserCoreLogic.constants.interfaces.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Enumerates different types of cards used in the game, each representing a
 * specific action or effect.
 * This enumeration defines the types of cards that can be used within the game,
 * providing a way to differentiate
 * between various game actions such as airlift, blockade, bomb, and diplomacy.
 * It also includes an EMPTY type
 * for scenarios where no card action is applicable. The enum facilitates
 * managing game logic related to cards
 * by providing a typed list of all possible card actions, excluding the EMPTY
 * type, through the {@link #usableCardList()} method.
 *
 * @see Card An interface that these card types might implement or interact
 *      with.
 * @version 2.0
 * @author Bhoomiben Bhatt
 */
public enum CardType {
    /**
     * If card type is empty.
     */
    EMPTY("empty"),
    /**
     * If card type is airlift.
     */
    AIRLIFT("airlift"),
    /**
     * If card type is blockade.
     */
    BLOCKADE("blockade"),
    /**
     * If card type is bomb.
     */
    BOMB("bomb"),
    /**
     * If card type is diplomacy.
     */
    DIPLOMACY("diplomacy");

    /**
     * The JSON-compatible string representation of the enum value.
     */
    public String d_jsonValue;

    /**
     * Initializes the enum value with a string representation suitable for JSON
     * serialization.
     *
     * @param p_jsonValue The JSON-compatible string representation of the enum.
     */
    private CardType(String p_jsonValue) {
        this.d_jsonValue = p_jsonValue;
    }

    /**
     * Provides a list of card types that are usable in the game, excluding the
     * EMPTY type.
     * <p>
     * This method returns a list of all card types that represent actionable cards
     * within the game,
     * allowing for easy identification and filtering of cards that can be used
     * during gameplay.
     * </p>
     *
     * @return A list of {@link CardType} excluding {@link CardType#EMPTY}.
     */
    public static List<CardType> usableCardList() {
        List<CardType> l_cardTypeList = new ArrayList<>();
        l_cardTypeList.add(CardType.AIRLIFT);
        l_cardTypeList.add(CardType.BLOCKADE);
        l_cardTypeList.add(CardType.BOMB);
        l_cardTypeList.add(CardType.DIPLOMACY);
        return l_cardTypeList;
    }

    /**
     * Retrieves the string representation of the enum value, suitable for JSON
     * serialization.
     *
     * @return The JSON-compatible string representation of the enum.
     */
    public String getJsonValue() {
        return d_jsonValue;
    }
}
