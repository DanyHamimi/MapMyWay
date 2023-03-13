package fr.uparis.backapp.model;

public class Cordonnee {

    private double latitude;
    private double longitude;

    public Cordonnee(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cordonnee cordonnee = (Cordonnee) o;
        return Double.compare(cordonnee.latitude, latitude) == 0 && Double.compare(cordonnee.longitude, longitude) == 0;
    }

}
