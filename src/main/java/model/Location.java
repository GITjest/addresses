package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
    private long locationId;
    private double lat;
    private double lon;
    private Address address;

    public Location() {
    }

    public Location(double lat, double lon) {
        this(lat, lon, null);
    }

    public Location(double lat, double lon, Address address) {
        this.lat = lat;
        this.lon = lon;
        this.address = address;
    }

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + locationId +
                ", lat=" + lat +
                ", lon=" + lon +
                ", address=" + address +
                '}';
    }
}
