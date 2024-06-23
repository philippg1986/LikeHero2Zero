package dev.guelink.likehero2zero;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Emission {

    private int id;
    private String country;
    private double co2;
    private int year;
    private int scientistId;
    private Scientist scientist;

    public Emission() {}

    public Emission(int id, String country, int year, String isoCode, double co2, int scientistId, Object scientist) {
    }

    public Emission(int id, String country, double co2, int year,int scientistId, Scientist scientist) {
        this.id = id;
        this.country = country;
        this.co2 = co2;
        this.year = year;
        this.scientistId = scientistId;
        this.scientist = scientist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getCo2() {
        return co2;
    }

    public void setCo2(double co2) {
        this.co2 = co2;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getScientistId() {
        return scientistId;
    }

    public void setScientistId(int scientistId) {
        this.scientistId = scientistId;
    }

    public Scientist getScientist() {
        return scientist;
    }

    public void setScientist(Scientist scientist) {
        this.scientist = scientist;
    }

    @Override
    public String toString() {
        return "Emission{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", co2=" + co2 +
                ", year=" + year +
                ", scientistId=" + scientistId +
                ", scientist=" + scientist +
                '}';
    }
}
