package fr.uparis.backapp.model;

/**
 * Représente une coordonnée gps
 */
public class Coordonnee{
    private double latitude; //latitude en degré
    private double longitude; //longitude en degré

    /**
     * Constructeur de la classe Coordonnee
     * @param latitude latitude de la coordonnée en degré
     * @param longitude longitude de la coordonnée en degré
     */
    public Coordonnee(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * @return la latitude en degré
     */
    public double getLatitude(){
        return latitude;
    }

    /**
     * Transforme la latitude de la coordonnée de degré à radian.
     * @return la latitude en radian
     */
    public double getLatitudeRadian(){
        return Math.toRadians(latitude);
    }

    /**
     * @return la longitude en degré
     */
    public double getLongitude(){
        return longitude;
    }

    /**
     * Transforme la longitude de la coordonnée de degré à radian.
     * @return la longitude en radian
     */
    public double getLongitudeRadian(){
        return Math.toRadians(longitude);
    }

    /**
     * Setter de la latitude en degré.
     * @param latitude latitude en degré
     */
    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    /**
     * Setter de la longitude en degré.
     * @param longitude longitude en degré
     */
    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    /**
     * Comparaison de deux coordonnées.
     * @param o objet avec lequel comparer
     * @return true si o et this ont les mêmes latitude et longitude, false sinon
     */
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Coordonnee coordonnee = (Coordonnee) o;
        return Double.compare(coordonnee.latitude, latitude) == 0
            && Double.compare(coordonnee.longitude, longitude) == 0;
    }
}