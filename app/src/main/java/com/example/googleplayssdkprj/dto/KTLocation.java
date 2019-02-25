package com.example.googleplayssdkprj.dto;

public class KTLocation {

    private String formatted_address;
    private Double lat;
    private Double lon;


    public KTLocation() {
    }

    public KTLocation(String formatted_address, Double lat, Double lon) {
        this.formatted_address = formatted_address;
        this.lat = lat;
        this.lon = lon;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
