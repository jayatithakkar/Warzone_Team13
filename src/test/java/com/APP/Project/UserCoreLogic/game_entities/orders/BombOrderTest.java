package com.APP.Project.UserCoreLogic.game_entities.orders;

import com.APP.Project.Main;
import com.APP.Project.UserCoreLogic.UserCoreLogic;
import com.APP.Project.UserCoreLogic.gamePlay.services.CountryDistributionService;
import com.APP.Project.UserCoreLogic.game_entities.Country;
import com.APP.Project.UserCoreLogic.game_entities.Player;
import com.APP.Project.UserCoreLogic.game_entities.cards.BlockadeCard;
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
 * This class represents the test cases for the BombOrder class, which is responsible for executing bomb orders
 * in the game. It includes tests for various scenarios such as bombing an opponent's country, handling
 * the absence of bomb cards, ensuring correct removal of cards, and negotiating with other players.
 *
 * @author Rikin Dipakkumar Chauhan
 * @version 1.0
 */
public class BombOrderTest {
    private static Main d_Application;
    private static URL d_TestFilePath;
    private static GamePlayEngine d_GamePlayEngine;
    private CountryDistributionService d_distributeCountriesService;
    private List<Player> d_playerList;

    /**
     * Initializes necessary components before executing test cases.
     */
    @BeforeClass
    public static void beforeClass() {
        d_Application = new Main();
        d_Application.handleApplicationStartup();
        d_GamePlayEngine = GamePlayEngine.getInstance();
        d_TestFilePath = BombOrderTest.class.getClassLoader().getResource("test_map_files/test_map.map");
    }

    /**
     * Sets up the environment before each test case execution.
     *
     * @throws AbsentTagException      If required tag is absent.
     * @throws InvalidMapException     If the map is invalid.
     * @throws ResourceNotFoundException If a resource is not found.
     * @throws InvalidInputException   If the input is invalid.
     * @throws EntityNotFoundException If an entity is not found.
     * @throws URISyntaxException      If URI syntax is incorrect.
     */
    @Before
    public void before() throws AbsentTagException, InvalidMapException, ResourceNotFoundException, InvalidInputException, EntityNotFoundException, URISyntaxException {
        
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
     * Tests the bomb operation with a bomb card.
     *
     * @throws EntityNotFoundException If an entity is not found.
     * @throws InvalidOrderException   If the order is invalid.
     * @throws CardNotFoundException   If the card is not found.
     */
    @Test(expected = Test.None.class)
    public void testBombOperationWithBombCard()
            throws EntityNotFoundException, InvalidOrderException, CardNotFoundException{
        Player l_player1 = d_playerList.get(0);
        Player l_player2 = d_playerList.get(1);
        List<Country> l_player2AssignCountries = l_player2.getAssignedCountries();

        l_player1.addCard(new BombCard());
        BombOrder l_bombOrder = new BombOrder(l_player2AssignCountries.get(0).getCountryName(), l_player1);
        l_player2AssignCountries.get(0).setNumberOfArmies(10);
        int l_armies = l_player2AssignCountries.get(0).getNumberOfArmies();
        l_bombOrder.execute();
        assertEquals(l_player2AssignCountries.get(0).getNumberOfArmies(), l_armies / 2);
    }

    /**
     * Tests the bomb operation without a bomb card.
     *
     * @throws EntityNotFoundException If an entity is not found.
     * @throws InvalidOrderException   If the order is invalid.
     * @throws CardNotFoundException   If the card is not found.
     */
    @Test(expected = CardNotFoundException.class)
    public void testBombOperationWithOutBombCard()
            throws EntityNotFoundException, InvalidOrderException, CardNotFoundException{
        Player l_player1 = d_playerList.get(0);
        Player l_player2 = d_playerList.get(1);
        List<Country> l_player2AssignCountries = l_player2.getAssignedCountries();

        l_player1.addCard(new BlockadeCard());
        BombOrder l_bombOrder = new BombOrder(l_player2AssignCountries.get(0).getCountryName(), l_player1);
        l_player2AssignCountries.get(0).setNumberOfArmies(10);
        int l_armies = l_player2AssignCountries.get(0).getNumberOfArmies();
        l_bombOrder.execute();
        assertEquals(l_player2AssignCountries.get(0).getNumberOfArmies(), l_armies / 2);
    }

    /**
     * Tests the bomb operation on a player-owned country.
     *
     * @throws EntityNotFoundException If an entity is not found.
     * @throws InvalidOrderException   If the order is invalid.
     * @throws CardNotFoundException   If the card is not found.
     */
    @Test(expected = InvalidOrderException.class)
    public void testBombOperationOnPlayerOwnedCountry()
            throws EntityNotFoundException, InvalidOrderException, CardNotFoundException{
        Player l_player1 = d_playerList.get(0);
        List<Country> l_assignCountries = l_player1.getAssignedCountries();
        BombOrder l_bombOrder = new BombOrder(l_assignCountries.get(0).getCountryName(), l_player1);
        l_bombOrder.execute();
    }

    /**
     * Tests if the card is successfully removed after executing the bomb order.
     *
     * @throws EntityNotFoundException If an entity is not found.
     * @throws InvalidOrderException   If the order is invalid.
     * @throws CardNotFoundException   If the card is not found.
     */
    @Test(expected = CardNotFoundException.class)
    public void testCardSuccessfullyRemoved()
            throws EntityNotFoundException, InvalidOrderException, CardNotFoundException{
        Player l_player1 = d_playerList.get(0);
        Player l_player2 = d_playerList.get(1);
        List<Country> l_player2AssignCountries = l_player2.getAssignedCountries();

        l_player1.addCard(new BombCard());
        BombOrder l_bombOrder = new BombOrder(l_player2AssignCountries.get(0).getCountryName(), l_player1);
        l_bombOrder.execute();

        l_bombOrder.execute();
    }

    /**
     * Test method for negotiate.
     *
     * @throws InvalidOrderException   When order is invalid.
     * @throws CardNotFoundException   When card is not found.
     * @throws EntityNotFoundException When entity is not found.
     */
    @Test (expected = Test.None.class)
    public void testNegotiate() throws InvalidOrderException, CardNotFoundException, EntityNotFoundException {
        Player l_player1 = d_playerList.get(0);
        Player l_player2 = d_playerList.get(1);
        l_player1.addNegotiatePlayer(l_player2);

        List<Country> l_player2AssignCountries = l_player2.getAssignedCountries();
        int l_expectedArmies = l_player2AssignCountries.get(0).getNumberOfArmies();
        l_player1.addCard(new BombCard());
        BombOrder l_bombOrder = new BombOrder(l_player2AssignCountries.get(0).getCountryName(), l_player1);
        l_bombOrder.execute();
        assertEquals(l_player2AssignCountries.get(0).getNumberOfArmies(),l_expectedArmies);
    }
}