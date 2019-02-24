package com.example.googleplayssdkprj.dto;

public class KTLocation {

    private String formatted_address;
    private Double lat;
    private Double lng;

    public KTLocation(String formatted_address, Double lat, Double lng) {
        this.formatted_address = formatted_address;
        this.lat = lat;
        this.lng = lng;
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

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
