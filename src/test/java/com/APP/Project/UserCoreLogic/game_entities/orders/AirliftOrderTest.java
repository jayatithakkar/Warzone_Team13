package com.APP.Project.UserCoreLogic.game_entities.orders;

import com.APP.Project.Main;
import com.APP.Project.UserCoreLogic.UserCoreLogic;
import com.APP.Project.UserCoreLogic.gamePlay.services.CountryDistributionService;
import com.APP.Project.UserCoreLogic.game_entities.Country;
import com.APP.Project.UserCoreLogic.game_entities.Player;
import com.APP.Project.UserCoreLogic.game_entities.cards.AirliftCard;
import com.APP.Project.UserCoreLogic.game_entities.cards.BombCard;
import com.APP.Project.UserCoreLogic.exceptions.*;
import com.APP.Project.UserCoreLogic.gamePlay.GamePlayEngine;
import com.APP.Project.UserCoreLogic.map_features.adapters.EditMapAdapter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * This class represents the test cases for the AirliftOrder class, which is responsible for executing
 * an airlift order in the game.
 *
 * @author Rikin Dipakkumar Chauhan
 * @version 1.0
 */
public class AirliftOrderTest {
    private static Main d_Application;
    private static URL d_TestFilePath;
    private static GamePlayEngine d_GamePlayEngine;
    private CountryDistributionService d_distributeCountriesService;
    private List<Player> d_playerList;

    /**
     * Initializes necessary components for the test class.
     */
    @BeforeClass
    public static void createPlayersList() {
        d_Application = new Main();
        d_Application.handleApplicationStartup();
        d_GamePlayEngine = GamePlayEngine.getInstance();
        d_TestFilePath = BombOrderTest.class.getClassLoader().getResource("map_files/solar.map");
    }

    /**
     * Sets up the environment for each test case.
     *
     * @throws AbsentTagException       If a tag is absent.
     * @throws InvalidMapException      If the map is invalid.
     * @throws ResourceNotFoundException If a resource is not found.
     * @throws InvalidInputException    If the input is invalid.
     * @throws EntityNotFoundException If an entity is not found.
     * @throws URISyntaxException       If there is a syntax error in the URI.
     */
    @Before
    public void setup() throws AbsentTagException, InvalidMapException, ResourceNotFoundException, InvalidInputException, EntityNotFoundException, URISyntaxException {
        UserCoreLogic.getInstance().initialise();

        EditMapAdapter l_editMapService = new EditMapAdapter();
        assertNotNull(d_TestFilePath);
        String l_url = new URI(d_TestFilePath.getPath()).getPath();
        l_editMapService.handleLoadMap(l_url);

        Player l_player1 = new Player();
        Player l_player2 = new Player();

        l_player1.setName("User_1");
        l_player2.setName("User_2");

        d_GamePlayEngine.addPlayer(l_player1);
        d_GamePlayEngine.addPlayer(l_player2);
        d_playerList = d_GamePlayEngine.getPlayerList();
        d_distributeCountriesService = new CountryDistributionService();
        d_distributeCountriesService.distributeCountries();
    }

    /**
     * Tests the execution of the AirliftOrder.
     *
     * @throws EntityNotFoundException If an entity is not found.
     * @throws InvalidArgumentException If an argument is invalid.
     * @throws InvalidOrderException   If the order is invalid.
     * @throws CardNotFoundException   If a card is not found.
     */
    @Test
    public void testExecute()
            throws EntityNotFoundException, InvalidArgumentException, InvalidOrderException, CardNotFoundException{
        Player l_player = d_playerList.get(0);
        List<Country> l_playerAssignCountries = l_player.getAssignedCountries();
        l_playerAssignCountries.get(0).setNumberOfArmies(7);
        l_playerAssignCountries.get(1).setNumberOfArmies(5);
        int l_expected = l_playerAssignCountries.get(1).getNumberOfArmies();
        l_player.addCard(new AirliftCard());

        AirliftOrder l_airliftOrder = new AirliftOrder(l_playerAssignCountries.get(0).getCountryName(), l_playerAssignCountries.get(1).getCountryName(), "2", l_player);
        l_airliftOrder.execute();
        assertEquals(l_playerAssignCountries.get(1).getNumberOfArmies(), l_expected + 2);
    }

    /**
     * Tests the case when the player does not have the required card.
     *
     * @throws EntityNotFoundException If an entity is not found.
     * @throws InvalidArgumentException If an argument is invalid.
     * @throws InvalidOrderException   If the order is invalid.
     * @throws CardNotFoundException   If a card is not found.
     */
    @Test(expected = CardNotFoundException.class)
    public void testPlayerHasCard()
            throws EntityNotFoundException, InvalidArgumentException, InvalidOrderException, CardNotFoundException{
        Player l_player = d_playerList.get(0);
        List<Country> l_playerAssignCountries = l_player.getAssignedCountries();
        l_playerAssignCountries.get(0).setNumberOfArmies(7);
        l_playerAssignCountries.get(1).setNumberOfArmies(5);
        int l_expected = l_playerAssignCountries.get(1).getNumberOfArmies();
        l_player.addCard(new BombCard());

        AirliftOrder l_airliftOrder = new AirliftOrder(l_playerAssignCountries.get(0).getCountryName(), l_playerAssignCountries.get(1).getCountryName(), "2", l_player);
        l_airliftOrder.execute();
    }

    /**
     * Tests the case when the player tries to airlift to another player's country.
     *
     * @throws EntityNotFoundException If an entity is not found.
     * @throws InvalidArgumentException If an argument is invalid.
     * @throws InvalidOrderException   If the order is invalid.
     * @throws CardNotFoundException   If a card is not found.
     */
    @Test(expected = InvalidOrderException.class)
    public void testPlayerNotAirliftInOthersCountry()
            throws EntityNotFoundException, InvalidArgumentException, InvalidOrderException, CardNotFoundException{
        Player l_player = d_playerList.get(0);
        Player l_player2 = d_playerList.get(1);
        List<Country> l_playerAssignCountries = l_player.getAssignedCountries();
        List<Country> l_playerAssignCountries1 = l_player2.getAssignedCountries();
        l_player.addCard(new AirliftCard());

        AirliftOrder l_airliftOrder = new AirliftOrder(l_playerAssignCountries.get(0).getCountryName(), l_playerAssignCountries1.get(0).getCountryName(), "2", l_player);
        l_airliftOrder.execute();
    }

    /**
     * Tests the case when the player enters invalid number of armies for airlift.
     *
     * @throws EntityNotFoundException If an entity is not found.
     * @throws InvalidArgumentException If an argument is invalid.
     * @throws InvalidOrderException   If the order is invalid.
     * @throws CardNotFoundException   If a card is not found.
     */
    @Test(expected = InvalidOrderException.class)
    public void testPlayerHasEnteredArmies()
            throws EntityNotFoundException, InvalidArgumentException, InvalidOrderException, CardNotFoundException{
        Player l_player = d_playerList.get(0);
        List<Country> l_playerAssignCountries = l_player.getAssignedCountries();
        l_playerAssignCountries.get(0).setNumberOfArmies(7);
        l_playerAssignCountries.get(1).setNumberOfArmies(5);
        int l_expected = l_playerAssignCountries.get(1).getNumberOfArmies();
        l_player.addCard(new AirliftCard());

        AirliftOrder l_airliftOrder = new AirliftOrder(l_playerAssignCountries.get(0).getCountryName(), l_playerAssignCountries.get(1).getCountryName(), "8", l_player);
        l_airliftOrder.execute();
    }
}