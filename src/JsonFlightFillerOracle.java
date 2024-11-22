import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;


public class JsonFlightFillerOracle{
    public ArrayList<Flight> getList() {
        return list;
    }

    private ArrayList<Flight> list = new ArrayList<Flight>();
    public JsonFlightFillerOracle(String jsonString,World w){
        try {

                InputStream is = new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8));
                JsonReader rdr = Json.createReader(is);
                JsonObject obj = rdr.readObject();
                JsonArray results = obj.getJsonArray("data");
                for (JsonObject result : results.getValuesAs(JsonObject.class)) {
                    try {
                        String date = result.getJsonString("flight_date").getString();
                        String airlineName = result.getJsonObject("airline").getString("name");
                        String airlineCode = result.getJsonObject("airline").getString("iata");
                        Integer number = Integer.parseInt(result.getJsonObject("flight").getString("number"));
                        String iataCodeDeparture = result.getJsonObject("departure").getString("iata");
                        Aeroport depart = w.findByCode(iataCodeDeparture);
                        String iataCodeArrival = result.getJsonObject("arrival").getString("iata");
                        Aeroport arrival = w.findByCode(iataCodeArrival);
                        String heure = result.getJsonObject("departure").getString("scheduled");
                        LocalDateTime departTime = null;
                        if (heure != null) {
                            departTime = LocalDateTime.parse(heure, ISO_OFFSET_DATE_TIME);
                        }
                        heure = result.getJsonObject("arrival").getString("estimated");
                        LocalDateTime arrivalTime = null;
                        if (heure != null) {
                            arrivalTime = LocalDateTime.parse(heure, ISO_OFFSET_DATE_TIME);
                        }
                        list.add(new Flight(depart, arrival, airlineName, airlineCode, number, departTime, arrivalTime));
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                    }
           } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayFlight(){
        for (Flight f : list) System.out.println(f);

    }

    public static void main (String[] args){
        try {
            World w = new World ("./data/airport-codes_no_comma.csv");
            BufferedReader br = new BufferedReader(new FileReader("data/JsonOrly.txt"));
            String test = br.readLine();
            JsonFlightFillerOracle jSonFlightFiller = new JsonFlightFillerOracle(test,w);
            br.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
