package com.APP.Project.UserCoreLogic.game_entities.orders;

import com.APP.Project.Main;
import com.APP.Project.UserCoreLogic.UserCoreLogic;
import com.APP.Project.UserCoreLogic.game_entities.Country;
import com.APP.Project.UserCoreLogic.game_entities.Player;
import com.APP.Project.UserCoreLogic.game_entities.cards.BombCard;
import com.APP.Project.UserCoreLogic.exceptions.*;
import com.APP.Project.UserCoreLogic.gamePlay.GamePlayEngine;
import com.APP.Project.UserCoreLogic.gamePlay.services.CountryDistributionService;
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
 * This class contains unit tests for the AdvanceOrder class.
 *
 * @author Rikin Dipakkumar Chauhan
 * @version 1.0
 */
public class AdvanceOrderTest {
    private static Main d_Application;
    private static URL d_TestFilePath;
    private static GamePlayEngine d_GamePlayEngine;
    private CountryDistributionService d_distributeCountriesService;
    private List<Player> d_playerList;
    private Player d_player1;
    private Player d_player2;

    /**
     * Initializes necessary objects before running test cases.
     */
    @BeforeClass
    public static void beforeClass() {
        d_Application = new Main();
        d_Application.handleApplicationStartup();
        d_GamePlayEngine = GamePlayEngine.getInstance();
        d_TestFilePath = AdvanceOrderTest.class.getClassLoader().getResource("test_map_files/test_map.map");
    }

    /**
     * Sets up the environment before each test case execution.
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

        d_player1 = d_playerList.get(0);
        d_player2 = d_playerList.get(1);
    }

    /**
     * Tests the execution of AdvanceOrder with an invalid target country.
     *
     * @throws EntityNotFoundException if the entity is not found
     * @throws InvalidArgumentException if an invalid argument is provided
     * @throws InvalidOrderException if the order is invalid
     */
    @Test(expected = EntityNotFoundException.class)
    public void testInvalidCountry()
            throws EntityNotFoundException, InvalidArgumentException, InvalidOrderException {
        AdvanceOrder l_advanceOrder = new AdvanceOrder("INDIA", "CANADA", "10", d_player1);
        l_advanceOrder.execute();
    }

    /**
     * Tests the execution of AdvanceOrder with an invalid number of armies.
     *
     * @throws EntityNotFoundException if the entity is not found
     * @throws InvalidArgumentException if an invalid argument is provided
     */
    @Test(expected = InvalidArgumentException.class)
    public void testInvalidNoOfArmies() throws EntityNotFoundException, InvalidArgumentException {
        new AdvanceOrder("Mercury-South", "Mercury-East", "-10", d_player1);
    }

    /**
     * Tests the execution of AdvanceOrder with an invalid source country.
     *
     * @throws EntityNotFoundException if the entity is not found
     * @throws InvalidArgumentException if an invalid argument is provided
     * @throws InvalidOrderException if the order is invalid
     */
    @Test(expected = InvalidOrderException.class)
    public void testAdvanceOrderAsInvalidSourceCountry()
            throws EntityNotFoundException, InvalidArgumentException, InvalidOrderException {
        AdvanceOrder l_advanceOrder = new AdvanceOrder(d_player2.getAssignedCountries().get(0).getCountryName(), d_player2.getAssignedCountries().get(1).getCountryName(), "10", d_player1);
        l_advanceOrder.execute();
    }

    /**
     * Tests the execution of AdvanceOrder when moving armies to own country.
     *
     * @throws EntityNotFoundException if the entity is not found
     * @throws InvalidArgumentException if an invalid argument is provided
     * @throws InvalidOrderException if the order is invalid
     */
    @Test(expected = Test.None.class)
    public void testArmiesWhenMovedToOwnCountry()
            throws EntityNotFoundException, InvalidArgumentException, InvalidOrderException{
        Country l_country1 = d_player1.getAssignedCountries().get(0);
        Country l_country2 = d_player1.getAssignedCountries().get(1);

        l_country1.setNumberOfArmies(10);
        l_country2.setNumberOfArmies(20);

        AdvanceOrder l_advanceOrder = new AdvanceOrder(l_country1.getCountryName(), l_country2.getCountryName(), "20", d_player1);
        l_advanceOrder.execute();
        assertEquals(30, l_country2.getNumberOfArmies());
    }

    /**
     * Tests the execution of AdvanceOrder in a successful battle scenario.
     *
     * @throws EntityNotFoundException if the entity is not found
     * @throws InvalidArgumentException if an invalid argument is provided
     * @throws InvalidOrderException if the order is invalid
     */
    @Test(expected = Test.None.class)
    public void testSuccessfulBattle()
            throws EntityNotFoundException, InvalidArgumentException, InvalidOrderException{
        Country l_country1 = d_player1.getAssignedCountries().get(4);
        Country l_country2 = d_player2.getAssignedCountries().get(0);

        l_country1.setNumberOfArmies(20);
        l_country2.setNumberOfArmies(5);

        d_player1.addCard(new BombCard());
        AdvanceOrder l_advanceOrder = new AdvanceOrder(l_country1.getCountryName(), l_country2.getCountryName(), "10", d_player1);
        l_advanceOrder.execute();
        assertEquals(10, l_country1.getNumberOfArmies());
        assertEquals(6, l_country2.getNumberOfArmies());
        assertEquals(d_player1, l_country2.getOwnedBy());
        assertEquals(2, d_player1.getCards().size());
    }

    /**
     * Tests the execution of AdvanceOrder in an unsuccessful battle scenario.
     *
     * @throws EntityNotFoundException if the entity is not found
     * @throws InvalidArgumentException if an invalid argument is provided
     * @throws InvalidOrderException if the order is invalid
     */
    @Test(expected = Test.None.class)
    public void testUnsuccessfulBattle()
            throws EntityNotFoundException, InvalidArgumentException, InvalidOrderException{
        Country l_country1 = d_player1.getAssignedCountries().get(4);
        Country l_country2 = d_player2.getAssignedCountries().get(0);

        l_country1.setNumberOfArmies(10);
        l_country2.setNumberOfArmies(5);

        d_player1.addCard(new BombCard());
        AdvanceOrder l_advanceOrder = new AdvanceOrder(l_country1.getCountryName(), l_country2.getCountryName(), "6", d_player1);
        l_advanceOrder.execute();
        assertEquals(6, l_country1.getNumberOfArmies());
        assertEquals(1, l_country2.getNumberOfArmies());
        assertEquals(d_player2, l_country2.getOwnedBy());
    }

    /**
     * Tests the execution of AdvanceOrder with negotiation between players.
     *
     * @throws EntityNotFoundException if the entity is not found
     * @throws InvalidArgumentException if an invalid argument is provided
     * @throws InvalidOrderException if the order is invalid
     */
    @Test(expected = Test.None.class)
    public void testNegotiation()
            throws EntityNotFoundException, InvalidArgumentException, InvalidOrderException {
        Country l_country1 = d_player1.getAssignedCountries().get(4);
        Country l_country2 = d_player2.getAssignedCountries().get(0);

        l_country1.setNumberOfArmies(10);
        l_country2.setNumberOfArmies(5);

        d_player1.addNegotiatePlayer(d_player2);

        AdvanceOrder l_advanceOrder = new AdvanceOrder(l_country1.getCountryName(), l_country2.getCountryName(), "6", d_player1);
        l_advanceOrder.execute();
        assertEquals(10, l_country1.getNumberOfArmies());
        assertEquals(5, l_country2.getNumberOfArmies());
    }
}