package app.models;

import app.models.measurementComponents.Coordinates;
import app.models.measurementComponents.Date;



public class Measurement {

    private String location;
    private String parameter;
    private Date date;
    private double value;
    private String unit;
    private Coordinates coordinates;
    private String country;
    private String city;

    public enum Parameter{
        BC("bc"),
        CO("co"),
        NO2("no2"),
        O3("o3"),
        PM10("pm10"),
        PM25("pm25"),
        SO2("so2");

        String key;

        Parameter(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
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
