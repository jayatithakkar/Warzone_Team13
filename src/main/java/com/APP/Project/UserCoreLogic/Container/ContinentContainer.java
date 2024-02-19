package com.APP.Project.UserCoreLogic.Container;

import com.APP.Project.UserCoreLogic.game_entities.Continent;

import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;

import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class file is responsible for searching continents from map engine.
 * @author Bhoomiben Bhatt
 */
public class ContinentContainer {
    /**
     * search continent by it's name
     * 
     * @param p_continentName value of name of continent
     * @return value of matched continents.
     */
    public List<Continent> searchByContinentName(String p_continentName) {
        return MapFeatureEngine.getInstance().getContinentList().stream()
                .filter(p_continent -> p_continent.getContinentName().equals(p_continentName))
                .collect(Collectors.toList());
    }

    /**
     * search only one continent by it's name.
     * 
     * @param p_continentName name is value of continent
     * @return value of very first match continent
     * @throws EntityNotFoundException if searching entity not found
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

    /**
     * search continent by it's id.
     * 
     * @param p_continentId is value of continentId
     * @return value of first matched continent
     * @throws EntityNotFoundException if searching entity not found.
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