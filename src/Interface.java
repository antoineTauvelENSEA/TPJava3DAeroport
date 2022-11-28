import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.io.StringReader;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class Interface extends Application {

    ArrayList<Flight> listOfFlight = new ArrayList<Flight>();
    Translate tz = new Translate();
    private double mousePosX;
    private double mousePosY;

    @Override
    public void start(Stage primaryStage) throws Exception {
        World w = new World("./data/airport-codes_no_comma.csv");

        primaryStage.setTitle("So world");

        Earth earth = new Earth();
        Scene ihm = new Scene(earth, 800, 600,true);

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-1000);
        camera.setNearClip(0.1);
        camera.setFarClip(3000.0);
        camera.setFieldOfView(35);
        ihm.setCamera(camera);

        ihm.addEventHandler(MouseEvent.ANY, event -> {
            if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                mousePosX = event.getSceneX();
                mousePosY = event.getSceneY();
            }
            if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                tz.setZ((event.getSceneY() - mousePosY)*0.1);
                camera.getTransforms().add(tz);
            }
        });

        ihm.addEventHandler(MouseEvent.ANY, event -> {
             if (event.getButton()== MouseButton.SECONDARY && event.getEventType()==MouseEvent.MOUSE_CLICKED) {
                PickResult pickResult = event.getPickResult();
                if (pickResult.getIntersectedNode() != null) {
                    Point2D click=pickResult.getIntersectedTexCoord();
                    double longitude=360*(click.getX()-0.5);
                    double latitude=2*Math.toDegrees(Math.atan(Math.exp((0.5-click.getY())/0.2678))-(Math.PI/4));
                    Aeroport a = w.findNearestAirport(longitude,latitude);
                    earth.displayRedSphere(a);
                    System.out.println("x="+longitude+" y ="+latitude);
                    System.out.println(a);

                   try {
                        HttpClient client = HttpClient.newHttpClient();

                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create("http://api.aviationstack.com/v1/flights?access_key=cfaf27d3b7c76c08bafee49ddb0df72c&arr_iata=" + a.getIATA()))
                                .build();
                        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
                        JsonFlightFillerOracle json = new JsonFlightFillerOracle(response.body().toString(),w);
                        listOfFlight = json.getList();
                        json.displayFlight();
                        earth.displayYellowSphere(listOfFlight);

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });



        primaryStage.setScene(ihm);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
