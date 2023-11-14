
package com.example.nanoformula.modelo.countryDetails;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryDetail {

    @SerializedName("tld")
    @Expose
    private List<String> tld;
    @SerializedName("cca2")
    @Expose
    private String cca2;
    @SerializedName("ccn3")
    @Expose
    private String ccn3;
    @SerializedName("cca3")
    @Expose
    private String cca3;
    @SerializedName("cioc")
    @Expose
    private String cioc;
    @SerializedName("independent")
    @Expose
    private Boolean independent;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("unMember")
    @Expose
    private Boolean unMember;
    @SerializedName("capital")
    @Expose
    private List<String> capital;
    @SerializedName("altSpellings")
    @Expose
    private List<String> altSpellings;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("subregion")
    @Expose
    private String subregion;
    @SerializedName("latlng")
    @Expose
    private List<Double> latlng;
    @SerializedName("landlocked")
    @Expose
    private Boolean landlocked;
    @SerializedName("borders")
    @Expose
    private List<String> borders;
    @SerializedName("area")
    @Expose
    private Double area;
    @SerializedName("flag")
    @Expose
    private String flag;
    @SerializedName("population")
    @Expose
    private Integer population;
    @SerializedName("fifa")
    @Expose
    private String fifa;
    @SerializedName("timezones")
    @Expose
    private List<String> timezones;
    @SerializedName("continents")
    @Expose
    private List<String> continents;
    @SerializedName("flags")
    @Expose
    private Flags flags;
    @SerializedName("startOfWeek")
    @Expose
    private String startOfWeek;


    public List<String> getTld() {
        return tld;
    }

    public void setTld(List<String> tld) {
        this.tld = tld;
    }

    public String getCca2() {
        return cca2;
    }

    public void setCca2(String cca2) {
        this.cca2 = cca2;
    }

    public String getCcn3() {
        return ccn3;
    }

    public void setCcn3(String ccn3) {
        this.ccn3 = ccn3;
    }

    public String getCca3() {
        return cca3;
    }

    public void setCca3(String cca3) {
        this.cca3 = cca3;
    }

    public String getCioc() {
        return cioc;
    }

    public void setCioc(String cioc) {
        this.cioc = cioc;
    }

    public Boolean getIndependent() {
        return independent;
    }

    public void setIndependent(Boolean independent) {
        this.independent = independent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getUnMember() {
        return unMember;
    }

    public void setUnMember(Boolean unMember) {
        this.unMember = unMember;
    }

    public List<String> getCapital() {
        return capital;
    }

    public void setCapital(List<String> capital) {
        this.capital = capital;
    }

    public List<String> getAltSpellings() {
        return altSpellings;
    }

    public void setAltSpellings(List<String> altSpellings) {
        this.altSpellings = altSpellings;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public List<Double> getLatlng() {
        return latlng;
    }

    public void setLatlng(List<Double> latlng) {
        this.latlng = latlng;
    }

    public Boolean getLandlocked() {
        return landlocked;
    }

    public void setLandlocked(Boolean landlocked) {
        this.landlocked = landlocked;
    }

    public List<String> getBorders() {
        return borders;
    }

    public void setBorders(List<String> borders) {
        this.borders = borders;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public String getFifa() {
        return fifa;
    }

    public void setFifa(String fifa) {
        this.fifa = fifa;
    }
    public List<String> getTimezones() {
        return timezones;
    }

    public void setTimezones(List<String> timezones) {
        this.timezones = timezones;
    }

    public List<String> getContinents() {
        return continents;
    }

    public void setContinents(List<String> continents) {
        this.continents = continents;
    }

    public Flags getFlags() {
        return flags;
    }

    public void setFlags(Flags flags) {
        this.flags = flags;
    }

    public String getStartOfWeek() {
        return startOfWeek;
    }

    public void setStartOfWeek(String startOfWeek) {
        this.startOfWeek = startOfWeek;
    }

}
