import java.time.LocalDateTime;

public class Flight {
    /*{"flight_date":"2021-11-27",
    "flight_status":"scheduled",
    "departure":
        {"airport":"Chongqing Jiangbei International",
        "timezone":"Asia\/Shanghai","iata":"CKG","icao":"ZUCK","terminal"
        :null,"gate":null,"delay":null,"scheduled":"2021-11-27T03:00:00+00:00",
        "estimated":"2021-11-27T03:00:00+00:00",
        "actual":null,"estimated_runway":null,"actual_runway":null},
    "arrival":
        {"airport":"Shanghai Pudong International",
        "timezone":"Asia\/Shanghai","iata":"PVG","icao":"ZSPD","terminal":null,
        "gate":null,"baggage":null,"delay":null,
        "scheduled":"2021-11-27T05:20:00+00:00",
        "estimated":"2021-11-27T05:20:00+00:00",
        "actual":null,"estimated_runway":null,"actual_runway":null},
    "airline":{"name":"China Southern Airlines","iata":"CZ","icao":"CSN"},
    "flight":
    {"number":"496","iata":"CZ496","icao":"CSN496","codeshared":null},"aircraft":null,"live":null},
    */
        private Aeroport departure;
        private Aeroport arrival;
        private String airlineName;
        private String airLineCode;
        private int number;
        private LocalDateTime arrivalTime;

    public Aeroport getDeparture() {
        return departure;
    }

    private LocalDateTime departureTime;

    public Aeroport getArrival() {
        return arrival;
    }

    public Flight(Aeroport departure, Aeroport arrival, String airlineName, String airLineCode, int number, LocalDateTime arrivalTime, LocalDateTime departureTime) {
        this.departure = departure;
        this.arrival = arrival;
        this.airlineName = airlineName;
        this.airLineCode = airLineCode;
        this.number = number;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "departure=" + departure +
                ", arrival=" + arrival +
                ", airline='" + airlineName + '\'' +
                ", number=" + number +
                ", arrivalTime=" + arrivalTime +
                ", departureTime=" + departureTime +
                '}';
    }
}
