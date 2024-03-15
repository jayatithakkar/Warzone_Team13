package com.APP.Project.UserCoreLogic.constants.enums;

/**
 * Enumerates the types of models used to represent different aspects of a map
 * in the data being read from a file.
 * This enumeration is used to distinguish between the different kinds of data
 * models that are applicable to map construction and manipulation,
 * specifically focusing on continents, countries, and their borders. It
 * provides a clear way to handle different sections or types of data
 * within a map file, facilitating parsing and organization of map-related
 * information.
 *
 * @author Bhoomiben Bhatt
 * @version 2.0
 */
public enum MapModelTypes {
    /**
     * Represents data pertaining to the continents in a map. This type is used to
     * identify and process
     * information related to the continents' definitions within the map data.
     */
    CONTINENT("continents"),
    /**
     * Represents data pertaining to the countries in a map. This type is used to
     * identify and process
     * information related to the countries' definitions, including their
     * associations with continents.
     */
    COUNTRY("countries"),
    /**
     * Represents data pertaining to the borders between countries in a map. This
     * type is used to identify
     * and process information related to how countries are interconnected through
     * their borders.
     */
    BORDER("borders");

    /**
     * The string representation of the enum value, designed for use with JSON
     * serialization or similar contexts.
     */
    public String d_jsonValue;

    /**
     * Initializes the enum with a specific string value intended for JSON
     * serialization or similar usage.
     *
     * @param p_jsonValue The string representation of the enum value.
     */
    private MapModelTypes(String p_jsonValue) {
        this.d_jsonValue = p_jsonValue;
    }

    /**
     * Retrieves the string representation of the enum value.
     * This method enables the retrieval of the enum's value in a format suitable
     * for JSON serialization,
     * aiding in the process of exporting or interpreting enum values within a
     * data-exchange context.
     *
     * @return The string representation of the enum, suitable for JSON
     *         serialization.
     */
    public String getJsonValue() {
        return d_jsonValue;
    }
}
