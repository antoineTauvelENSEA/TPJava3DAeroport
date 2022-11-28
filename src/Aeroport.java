public class Aeroport {
    private String Name;
    private String IATA;
    private String country;
    private double latitude;
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public String getIATA() {
        return IATA;
    }

    @Override
    public String toString() {
        return "Aeroport{" +
                "Name='" + Name + '\'' +
                ", IATA='" + IATA + '\'' +
                ", country='" + country + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    public double getLongitude() {
        return longitude;
    }

    public Aeroport (String n, String code, double lat, double lon, String country){
        Name=n;
        IATA=code;
        latitude=lat;
        longitude=lon;
        country=country;
    }
}
