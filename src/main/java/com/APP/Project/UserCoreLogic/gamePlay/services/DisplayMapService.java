package com.APP.Project.UserCoreLogic.gamePlay.services;

import java.util.LinkedList;
import java.util.List;

public class DisplayMapService {
    List<Player> d_allPlayerList;
    GamePlayEngine d_engine;
    com.warzone.team08.VM.map_editor.services.ShowMapService d_displayMapService;

    /**
     * Default constructor that initializes the different objects.
     *
     * @throws EntityNotFoundException Is thrown if the required entity is not found.
     */
    public DisplayMapService() throws EntityNotFoundException {
        d_engine = GamePlayEngine.getInstance();
        d_allPlayerList = d_engine.getPlayerList();
        d_displayMapService = new com.warzone.team08.VM.map_editor.services.ShowMapService();
    }

    /**
     * Method is used to initiate all methods of ShowMapService file.
     *
     * @param p_allCommandValues list of all the parameters entered by the user
     * @return The value of string of continent with associated neighbouring country information.
     * @throws EntityNotFoundException is thrown in case no player is available.
     */
    @Override
    public String executeCommand(List<String> p_allCommandValues) throws EntityNotFoundException {
        StringBuilder l_playerInformation = new StringBuilder();
        // l_count used to denote the number of players in the list
        int l_count = 0;
        if (!this.d_allPlayerList.isEmpty()) {
            for (Player l_player : d_allPlayerList) {
                l_playerInformation.append("Player Number = " + (++l_count) + "\n");
                l_playerInformation.append("Deployed Armies by Player = " + (l_player.getReinforcementCount() - l_player.getRemainingReinforcementCount()) + "\n");
                l_playerInformation.append("Total Reinforcement Count = " + l_player.getReinforcementCount() + "\n");
                l_playerInformation.append(this.displayContentForPlayer(l_player));
            }
            return l_playerInformation.toString() + "\n" + "CONNECTIVITY" + "\n" + d_displayMapService.showNeighbourCountries();
        } else {
            throw new EntityNotFoundException("Kindly add the players to display game status!");
        }
    }

    /**
     * This method displays information of countries owned by
     * game player along with army count for each country.
     *
     * @param p_gamePlayer denotes the player whose information is to be displayed.
     * @return A String of related information for the required player
     */
    public String displayContentForPlayer(Player p_gamePlayer) {
        List<Country> l_allCountriesList = p_gamePlayer.getAssignedCountries();
        LinkedList<String> l_allCountriesNames = new LinkedList<>();

        for (Country l_country : l_allCountriesList) {
            l_allCountriesNames.add(l_country.getCountryName());
        }

        String[] l_header = new String[l_allCountriesList.size() + 1];
        l_header[0] = p_gamePlayer.getName().toUpperCase();
        for (int l_row = 1; l_row < l_header.length; l_row++) {
            l_header[l_row] = l_allCountriesNames.pollFirst();
        }
        String[] l_gamePlayerMap = new String[l_header.length];
        l_gamePlayerMap[0] = "Army Count";
        LinkedList<Country> l_allCountriesNames2 = new LinkedList<>();
        l_allCountriesNames2.addAll(l_allCountriesList);

        //the following loop displays army count per country
        for (int l_row = 1; l_row < l_gamePlayerMap.length; l_row++) {
            Country l_country = l_allCountriesNames2.pollFirst();
            l_gamePlayerMap[l_row] = String.valueOf(l_country.getNumberOfArmies());
        }
        return FlipTable.of(l_header, new String[][]{l_gamePlayerMap});

    }

}
