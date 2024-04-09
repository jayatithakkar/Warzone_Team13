package com.APP.Project.UserCoreLogic;

import com.APP.Project.UserCoreLogic.gamePlay.GamePlayEngine;
import com.APP.Project.UserCoreLogic.map_features.MapEditorEngine;
import com.APP.Project.UserCoreLogic.map_features.adapters.EditMapService;
import com.jakewharton.fliptables.FlipTable;
import com.APP.Project.UserCoreLogic.game_entities.GameResult;
import com.APP.Project.UserCoreLogic.game_entities.Player;
import com.APP.Project.UserCoreLogic.exceptions.UserCoreLogicException;
import com.APP.Project.UserCoreLogic.gamePlay.GameLoop;
import com.APP.Project.UserCoreLogic.GameEngine;
import com.APP.Project.UserCoreLogic.gamePlay.services.CountryDistributionService;
import com.APP.Project.UserCoreLogic.logger.LogEntryBuffer;
import com.APP.Project.UserCoreLogic.phases.PlaySetup;
import com.APP.Project.UserCoreLogic.Utility.FindFilePathUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the engine for running a tournament with multiple games.
 *
 * @author Rikin Dipakkumar Chauhan
 * @version 3.0
 */
public class TournamentEngine {
    
    private static TournamentEngine d_Instance;

    private Map<Integer, List<com.APP.Project.UserCoreLogic.GameEngine>> d_playedGameEngineMappings;

    private List<Player> d_players;

    private List<String> d_mapFileList;

    private int d_numberOfGames;

    private int d_maxNumberOfTurns;

    private LogEntryBuffer d_logEntryBuffer = LogEntryBuffer.getLogger();

    private GameLoop d_gameLoop;

    /**
     * Retrieves the instance of the tournament engine.
     *
     * @return The instance of the tournament engine.
     * @throws NullPointerException If the instance is not initialized.
     */
    public static TournamentEngine getInstance() throws NullPointerException {
        if (d_Instance == null) {
            d_Instance = new TournamentEngine();
            d_Instance.initialise();
        }
        return d_Instance;
    }

    /**
     * Initializes the tournament engine.
     */
    public void initialise() {
        d_mapFileList = new ArrayList<>();
        d_players = new ArrayList<>();
        d_playedGameEngineMappings = new HashMap<>();
    }

    /**
     * Shuts down the tournament engine.
     */
    public void shutdown() {
    }

    /**
     * Adds a player to the tournament.
     *
     * @param p_player The player to be added.
     */
    public void addPlayer(Player p_player) {
        this.d_players.add(p_player);
    }

    /**
     * Sets the number of games for the tournament.
     *
     * @param p_numberOfGames The number of games to be set.
     */
    public void setNumberOfGames(int p_numberOfGames) {
        d_numberOfGames = p_numberOfGames;
    }

    /**
     * Retrieves the number of games in the tournament.
     *
     * @return The number of games in the tournament.
     */
    public int getNumberOfGames() {
        return d_numberOfGames;
    }

    /**
     * Retrieves the maximum number of turns for the tournament.
     *
     * @return The maximum number of turns for the tournament.
     */
    public int getMaxNumberOfTurns() {
        return d_maxNumberOfTurns;
    }

    /**
     * Sets the maximum number of turns for the tournament.
     *
     * @param p_maxNumberOfTurns The maximum number of turns to be set.
     */
    public void setMaxNumberOfTurns(int p_maxNumberOfTurns) {
        d_maxNumberOfTurns = p_maxNumberOfTurns;
    }

    /**
     * Sets the list of players for the tournament.
     *
     * @param p_playersList The list of players to be set.
     */
    public void setPlayers(List<Player> p_playersList) {
        d_players = p_playersList;
    }

    /**
     * Retrieves the list of map files used in the tournament.
     *
     * @return The list of map files used in the tournament.
     */
    public List<String> getMapFileList() {
        return d_mapFileList;
    }

    /**
     * Sets the list of map files for the tournament.
     *
     * @param p_mapFileList The list of map files to be set.
     */
    public void setMapFileList(List<String> p_mapFileList) {
        d_mapFileList = p_mapFileList;
    }

    /**
     * Retrieves the cloned list of players participating in the tournament.
     *
     * @return The cloned list of players participating in the tournament.
     */
    public List<Player> getPlayers() {
        List<Player> l_clonedPlayers = new ArrayList<>();
        for (Player l_player : d_players) {
            l_clonedPlayers.add(new Player(l_player.getName(), l_player.getPlayerStrategyType()));
        }
        return l_clonedPlayers;
    }

    /**
     * Initiates the tournament.
     *
     * @throws UserCoreLogicException If an error occurs during tournament setup.
     */
    public void onStart() throws UserCoreLogicException {
        for (int d_currentGameIndex = 0; d_currentGameIndex < d_numberOfGames; d_currentGameIndex++) {
            for (String l_mapFilePath : d_mapFileList) {
                GamePlayEngine l_gamePlayEngine = new GamePlayEngine();
                com.APP.Project.UserCoreLogic.GameEngine l_gameEngine = new com.APP.Project.UserCoreLogic.GameEngine(new MapEditorEngine(), l_gamePlayEngine);
                
                UserCoreLogic.setGameEngine(l_gameEngine);

                EditMapService l_editMapService = new EditMapService();

                
                String resolvedPathToFile = FindFilePathUtil.resolveFilePath(l_mapFilePath);

                l_editMapService.handleLoadMap(resolvedPathToFile, false);

                l_gamePlayEngine.setPlayerList(this.getPlayers());

                CountryDistributionService l_distributeCountriesService = new CountryDistributionService();
                l_distributeCountriesService.execute(new ArrayList<>());

                
                l_gameEngine.setGamePhase(new PlaySetup(l_gameEngine));


                if (d_playedGameEngineMappings.containsKey(d_currentGameIndex)) {
                    d_playedGameEngineMappings.get(d_currentGameIndex).add(l_gameEngine);
                } else {
                    List<com.APP.Project.UserCoreLogic.GameEngine> l_gameEngineList = new ArrayList<>();
                    l_gameEngineList.add(l_gameEngine);
                    d_playedGameEngineMappings.put(d_currentGameIndex, l_gameEngineList);
                }
                d_gameLoop = new GameLoop(l_gamePlayEngine);
                d_gameLoop.run();
            }
        }
        this.onComplete();
    }

    /**
     * Finalizes the tournament and generates the results.
     */
    public void onComplete() {
        
        String[][] l_gameResultMatrix = new String[this.getNumberOfGames()][d_mapFileList.size()];
        List<String> l_playerNames = new ArrayList<>();
        StringBuilder l_builder = new StringBuilder();

        for (Player l_player : d_players) {
            l_playerNames.add(l_player.getName());
        }

        l_builder.append("\n----Result of Tournament after Execution----\n");
        l_builder.append("M: " + d_mapFileList.toString() + "\n");
        l_builder.append("P: " + l_playerNames.toString() + "\n");
        l_builder.append("G: " + this.getNumberOfGames() + "\n");
        l_builder.append("D: " + this.getMaxNumberOfTurns() + "\n");

        int l_count = 1;
        for (int l_row = 0; l_row < d_mapFileList.size(); l_row++) {
            l_gameResultMatrix[l_row][0] = "Map" + l_count;
            l_count++;
        }

        int l_rowIndex = 0;
        int l_columnIndex;
        for (Map.Entry<Integer, List<com.APP.Project.UserCoreLogic.GameEngine>> entry : d_playedGameEngineMappings.entrySet()) {
            l_columnIndex = 0;
            for (com.APP.Project.UserCoreLogic.GameEngine l_singleGameEngine : entry.getValue()) {
                GamePlayEngine l_gamePlayEngine = l_singleGameEngine.getGamePlayEngine();
                GameResult l_gameResult = l_gamePlayEngine.getGameResult();

                
                if (l_gameResult.isDeclaredDraw()) {
                    l_gameResultMatrix[l_rowIndex][l_columnIndex] = "Draw";
                } else if (l_gameResult.getWinnerPlayer() != null) {
                    l_gameResultMatrix[l_rowIndex][l_columnIndex] = l_gameResult.getWinnerPlayer().getName();
                } else {
                    l_gameResultMatrix[l_rowIndex][l_columnIndex] = "Interrupted";
                }
                l_columnIndex++;
            }
            l_rowIndex++;
        }

        String[] l_gameHeader = new String[d_mapFileList.size()];
        l_gameHeader[0] = "Result";
        for (int i = 1; i < l_gameHeader.length; i++) {
            l_gameHeader[i] = "Game" + i;
        }
        String l_tournamentData=l_builder + FlipTable.of(l_gameHeader, l_gameResultMatrix);
        d_logEntryBuffer.dataChanged("tournament",l_tournamentData);
        UserCoreLogic.getInstance().stdout(l_tournamentData);
    }
}