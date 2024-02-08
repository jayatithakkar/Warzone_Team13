import java.util.ArrayList;

public class Country {

    public int d_countryID;
    public String d_countryName;
    public int d_parentContinentID;
    public ArrayList<Integer> d_neighborCountries;

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

    public void setNeighborCountries(ArrayList<Integer> p_neighborCountries) {
        d_neighborCountries = p_neighborCountries;
    }

    public ArrayList<Integer> getNeighborCountries() {
        return d_neighborCountries;
    }
}
