package com.APP.Project.UserCoreLogic.Container;

import com.APP.Project.UserCoreLogic.game_entities.Continent;
import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class file is responsible for searching continents from map engine.
 *
 * @author Bhoomiben Bhatt
 * @version 1.0
 */
public class ContinentContainer {
    /**
     * search continent by its name
     *
     * @param p_continentName is name of continent
     * @return value of matched continents.
     */
    public List<Continent> findByContinentName(String p_continentName) {
        return MapFeatureEngine.getInstance().getContinentList().stream()
                .filter(p_continent -> p_continent.getContinentName().equals(p_continentName))
                .collect(Collectors.toList());
    }

    /**
     * search only one continent by it's name.
     *
     * @param p_continentName Value of the name of continent.
     * @return Value of the first matched continents.
     * @throws EntityNotFoundException Throws if the being searched entity has been
     *                                 not found.
     */
    public Continent findFirstByContinentName(String p_continentName) throws EntityNotFoundException {
        List<Continent> l_continentList = this.findByContinentName(p_continentName);
        if (l_continentList.size() > 0)
            return l_continentList.get(0);
        throw new EntityNotFoundException(String.format("'%s' continent not found", p_continentName));
    }

    /**
     * search continent by it's id.
     *
     * @param p_continentId Value of the continent Id.
     * @return Value of the first matched continents.
     * @throws EntityNotFoundException Throws if the being searched entity has been
     *                                 not found.
     */
    public Continent findByContinentId(Integer p_continentId) throws EntityNotFoundException {
        List<Continent> l_continentList = MapFeatureEngine.getInstance().getContinentList().stream()
                .filter(p_continent -> p_continent.getContinentId().equals(p_continentId))
                .collect(Collectors.toList());
        if (!l_continentList.isEmpty()) {
            return l_continentList.get(0);
        }
        throw new EntityNotFoundException(String.format("Continent with %s id not found!", p_continentId));
    }
}