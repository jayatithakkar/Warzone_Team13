package com.APP.Project.UserCoreLogic.containers;

import com.APP.Project.UserCoreLogic.GameEntities.Continent;
import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;

import java.util.*;
import java.util.stream.Collectors;

/*
 * This class file is responsible for searching continents from map engine.
 * @author Bhoomiben Bhatt
 */
public class ContinentContainer {
    /*
     * search continent by it's name
     * p_continentName is name of continent
     * returns value of matched continents.
     */
    public List<Continent> searchByContinentName(String p_continentName) {
        return MapFeatureEngine.getInstance().getContinentList().stream()
                .filter(p_continent -> p_continent.getContinentName().equals(p_continentName))
                .collect(Collectors.toList());
    }

    /*
     * search only one continent by it's name.
     */
    public Continent searchFirstByContinentName(String p_continentName) throws EntityNotFoundException {
        /*
         * p_continent name ia value of continent
         * returns value of very first match continent
         * if searching entity not found then it will throw an exception that
         * EntityNotFound.
         */
        List<Continent> l_continentList = this.searchByContinentName(p_continentName);
        if (l_continentList.size() > 0)
            return l_continentList.get(0);
        throw new EntityNotFoundException(String.format("'%s' continent not found", p_continentName));
    }

    /*
     * search continent by it's id.
     */
    public Continent searchByContinentId(Integer p_continentId) throws EntityNotFoundException {
        /*
         * p_continentId is value of continentId
         * returns continent value
         * if searching entity not found then it will throw an exception that
         * EntityNotFound.
         */
        List<Continent> l_continentList = MapFeatureEngine.getInstance().getContinentList().stream()
                .filter(p_continent -> p_continent.getUniqueContinentId().equals(p_continentId))
                .collect(Collectors.toList());
        if (!l_continentList.isEmpty()) {
            return l_continentList.get(0);
        }
        throw new EntityNotFoundException(String.format("Continent with %s id not found!", p_continentId));
    }
}