import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class ThreadScrapOnlineFlight extends Thread{
    ArrayList<Flight> listOfFlight;
    Earth earth;
    Aeroport a;
    World w;

    public ThreadScrapOnlineFlight(Earth earth, Aeroport a, World w, ArrayList<Flight> listOfFlight) {
        this.earth = earth;
        this.a = a;
        this.w = w;
        this.listOfFlight=listOfFlight;
    }

    @Override
    public void run() {
        super.run();
        try{
            listOfFlight.clear();
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://api.aviationstack.com/v1/flights?access_key=cfaf27d3b7c76c08bafee49ddb0df72c&arr_iata=" + a.getIATA()))
                    .build();
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonFlightFillerOracle json = new JsonFlightFillerOracle(response.body().toString(),w);

            listOfFlight = json.getList();
            json.displayFlight();
            earth.getFlightList().addAll(listOfFlight);
            earth.setNeedUpdate(true);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }


}
