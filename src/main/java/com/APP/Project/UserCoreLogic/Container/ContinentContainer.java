package com.APP.Project.UserCoreLogic.containers;

import com.APP.Project.UserCoreLogic.GameEntities.Continent;
import com.APP.Project.UserCoreLogic.exceptions.EntityNotFoundException;
import com.APP.Project.UserCoreLogic.map_features.MapFeatureEngine;

import java.util.*;
import java.util.stream.Collectors;

public class ContinentContainer {
    public List<Continent> searchByContinentName(String p_continentName) {
        return MapFeatureEngine.getInstance().getContinentList().stream()
                .filter(p_continent -> p_continent.getContinentName().equals(p_continentName))
                .collect(Collectors.toList());
    }

    public Continent searchFirstByContinentName(String p_continentName) throws EntityNotFoundException {
        List<Continent> l_continentList = this.searchByContinentName(p_continentName);
        if (l_continentList.size() > 0)
            return l_continentList.get(0);
        throw new EntityNotFoundException(String.format("'%s' continent not found", p_continentName));
    }

    public Continent searchByContinentId(Integer p_continentId) throws EntityNotFoundException {
        List<Continent> l_continentList = MapFeatureEngine.getInstance().getContinentList().stream()
                .filter(p_continent -> p_continent.getUniqueContinentId().equals(p_continentId))
                .collect(Collectors.toList());
        if (!l_continentList.isEmpty()) {
            return l_continentList.get(0);
        }
        throw new EntityNotFoundException(String.format("Continent with %s id not found!", p_continentId));
    }
}