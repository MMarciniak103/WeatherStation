package app.models;

import app.models.measurementComponents.Coordinates;
import app.models.measurementComponents.Date;


/**
 * Model for measurement object that represents API data as a POJO class.
 */
public class Measurement {

    private String location;
    private String parameter;
    private Date date;
    private double value;
    private String unit;
    private Coordinates coordinates;
    private String country;
    private String city;

    public enum Parameter {
        BC("bc", -1),
        CO("co", 5),
        NO2("no2", 3),
        O3("o3", 2),
        PM10("pm10", 0),
        PM25("pm25", 1),
        SO2("so2", 4);

        String key;
        int tableId;

        Parameter(String key, int tableId) {
            this.key = key;
            this.tableId = tableId;
        }

        /**
         * Finds parameter by a given key
         * @param key String value of parameter's key
         * @return Parameter
         */
        public static  Parameter findByKey(String key){
            if(key.equals("co"))
                return CO;
            else if(key.equals("no2"))
                return NO2;
            else if(key.equals("o3"))
                return O3;
            else if(key.equals("pm10"))
                return PM10;
            else if(key.equals("pm25"))
                return PM25;
            else if(key.equals("so2"))
                return SO2;
            else
                return null;
        }

        public String getKey() {
            return key;
        }

        public int getTableId() {
            return tableId;
        }
    }

    public Measurement() {
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "location='" + location + '\'' +
                ", parameter='" + parameter + '\'' +
                ", date=" + date +
                ", value=" + value +
                ", unit='" + unit + '\'' +
                ", coordinates=" + coordinates +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
