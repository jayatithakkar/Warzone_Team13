package com.APP.Project.UserCoreLogic.game_entities.orders;

import com.APP.Project.Main;
import com.APP.Project.UserCoreLogic.UserCoreLogic;
import com.APP.Project.UserCoreLogic.game_entities.Country;
import com.APP.Project.UserCoreLogic.game_entities.Player;
import com.APP.Project.UserCoreLogic.game_entities.cards.BlockadeCard;
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
 * This class represents the test cases for the BlockadeOrder class,
 * which is responsible for executing blockade operations in the game.
 * It includes tests for various scenarios such as successful blockade operation
 * with a blockade card, blockade operation without a blockade card, invalid
 * blockade operations, and successful card removal after blockade operation.
 *
 * @author Rikin Dipakkumar Chauhan
 * @version 1.0
 */
public class BlockadeOrderTest {
    private static Main d_Application;
    private static URL d_TestFilePath;
    private static GamePlayEngine d_GamePlayEngine;
    private CountryDistributionService d_distributeCountriesService;
    private List<Player> d_playerList;

    /**
     * Initializes necessary objects before running test cases.
     */
    @BeforeClass
    public static void beforeClass() {
        d_Application = new Main();
        d_Application.handleApplicationStartup();
        d_GamePlayEngine = GamePlayEngine.getInstance();
        d_TestFilePath = BlockadeOrderTest.class.getClassLoader().getResource("test_map_files/test_map.map");
    }

    /**
     * Sets up the environment before each test case execution.
     *
     * @throws AbsentTagException        If required tags are not found.
     * @throws InvalidMapException       If the map is invalid.
     * @throws ResourceNotFoundException If a required resource is not found.
     * @throws InvalidInputException     If the input is invalid.
     * @throws EntityNotFoundException  If an entity is not found.
     * @throws URISyntaxException       If there is a syntax error in URI creation.
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
     * Tests the blockade operation with a blockade card.
     *
     * @throws EntityNotFoundException If an entity is not found.
     * @throws InvalidOrderException   If the order is invalid.
     * @throws CardNotFoundException   If the card is not found.
     */
    @Test(expected = Test.None.class)
    public void testBlockadeOperationWithBlockadeCard()
            throws EntityNotFoundException, InvalidOrderException, CardNotFoundException{
        Player l_player1 = d_playerList.get(0);
        List<Country> l_player1AssignCountries = l_player1.getAssignedCountries();
        l_player1.addCard(new BlockadeCard());
        BlockadeOrder l_blockadeOrder = new BlockadeOrder(l_player1AssignCountries.get(0).getCountryName(), l_player1);
        Country l_country = l_player1AssignCountries.get(0);
        l_country.setNumberOfArmies(10);
        int l_armies = l_country.getNumberOfArmies();
        l_blockadeOrder.execute();
        assertEquals(l_country.getNumberOfArmies(), l_armies * 3);
    }

    /**
     * Tests the blockade operation without a blockade card.
     *
     * @throws EntityNotFoundException If an entity is not found.
     * @throws InvalidOrderException   If the order is invalid.
     * @throws CardNotFoundException   If the card is not found.
     */
    @Test(expected = CardNotFoundException.class)
    public void testBlockadeOperationWithOutBlockadeCard()
            throws EntityNotFoundException, InvalidOrderException, CardNotFoundException{
        Player l_player1 = d_playerList.get(0);
        List<Country> l_player1AssignCountries = l_player1.getAssignedCountries();
        l_player1.addCard(new BombCard());
        BlockadeOrder l_blockadeOrder = new BlockadeOrder(l_player1AssignCountries.get(0).getCountryName(), l_player1);
        Country l_country = l_player1AssignCountries.get(0);
        l_country.setNumberOfArmies(10);
        int l_armies = l_country.getNumberOfArmies();
        l_blockadeOrder.execute();
        assertEquals(l_country.getNumberOfArmies(), l_armies * 3);
    }

    /**
     * Tests the blockade operation on a country owned by another player.
     *
     * @throws EntityNotFoundException If an entity is not found.
     * @throws InvalidOrderException   If the order is invalid.
     * @throws CardNotFoundException   If the card is not found.
     */
    @Test(expected = InvalidOrderException.class)
    public void testBlockadeOperationOnOtherPlayerOwnedCountry()
            throws EntityNotFoundException, InvalidOrderException, CardNotFoundException{
        Player l_player1 = d_playerList.get(0);
        Player l_player2 = d_playerList.get(1);
        List<Country> l_player2AssignCountries = l_player2.getAssignedCountries();
        l_player1.addCard(new BlockadeCard());
        BlockadeOrder l_blockadeOrder = new BlockadeOrder(l_player2AssignCountries.get(0).getCountryName(), l_player1);
        l_blockadeOrder.execute();
    }

    /**
     * Tests successful removal of card after blockade operation.
     *
     * @throws EntityNotFoundException If an entity is not found.
     * @throws InvalidOrderException   If the order is invalid.
     * @throws CardNotFoundException   If the card is not found.
     */
    @Test(expected = CardNotFoundException.class)
    public void testCardSuccessfullyRemoved() throws
            EntityNotFoundException, InvalidOrderException, CardNotFoundException{
        Player l_player1 = d_playerList.get(0);
        List<Country> l_player1AssignCountries = l_player1.getAssignedCountries();
        l_player1.addCard(new BlockadeCard());
        BlockadeOrder l_blockadeOrder = new BlockadeOrder(l_player1AssignCountries.get(0).getCountryName(), l_player1);
        l_blockadeOrder.execute();

        l_blockadeOrder.execute();
    }
}
