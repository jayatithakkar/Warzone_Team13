public class Country {

    int d_countryID;
    String d_countryName;
    int d_parentContinentID;

    public void setCountryID(int p_countryID) {
        d_countryID = p_countryID;
    }

    public int getCountryID() {
        return d_countryID;
    }

    public void setCountryName(String p_countryName) {
        d_countryName = p_countryName;
    }

    public String getCountryName() {
        return d_countryName;
    }

    public void setParentContinentID(int p_parentContinentID) {
        d_parentContinentID = p_parentContinentID;
    }

    public int getParentContinentID() {
        return d_parentContinentID;
    }
}
