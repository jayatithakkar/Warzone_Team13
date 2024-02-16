package com.APP.Project.UserCoreLogic.constants.enums;

public enum MapModelTypes {
    
    CONTINENT("continents"),
    
    COUNTRY("countries"),
    
    BORDER("borders");

    public String d_jsonValue;

    private MapModelTypes(String p_jsonValue) {
        this.d_jsonValue = p_jsonValue;
    }

    public String getJsonValue() {
        return d_jsonValue;
    }
}

