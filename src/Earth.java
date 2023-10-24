import javafx.animation.AnimationTimer;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.util.ArrayList;


public class Earth extends Group {
    private ArrayList<Sphere> yellowSphere;
    private Rotate ry = new Rotate();

    public Sphere getEarth() {
        return sph;
    }

    private Sphere sph;
    private Sphere redSphere;
    private Boolean needUpdate=false;
    private ArrayList<Flight> flightList = new ArrayList<>();

    public void setNeedUpdate(Boolean needUpdate) {
        this.needUpdate = needUpdate;
    }

    public ArrayList<Flight> getFlightList() {
        return flightList;
    }

    public Earth() {
        yellowSphere = new ArrayList<Sphere>();
        sph = new Sphere(300);

        PhongMaterial skin = new PhongMaterial();
        skin.setDiffuseMap(new Image("file:./data/earth_lights_4800.png"));
        skin.setSelfIlluminationMap(new Image("file:./data/earth_lights_4800.png"));
        sph.setMaterial(skin);
        this.getChildren().add(sph);
        this.getTransforms().add(ry);
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                ry.setAxis(new Point3D(0,1,0));
                ry.setAngle(l/50000000);
                if(needUpdate){
                    needUpdate=false;
                    displayYellowSphere(flightList);
                }
            }
        };
        animationTimer.start();
    }

    public Sphere createSphere(Aeroport a,Color color){
        return createSphere(a.getLatitude(),a.getLongitude(),color);
    }

    public Sphere createSphere(double latitude, double longitude,Color color){
        PhongMaterial col = new PhongMaterial();
        col.setSpecularColor(color);
        col.setDiffuseColor(color);

        Sphere coloredSphere = new Sphere(5);
        coloredSphere.setMaterial(col);

        coloredSphere.setTranslateZ(-sph.getRadius());

        Rotate rPhi = new Rotate (-longitude,
                -coloredSphere.getTranslateX(),-coloredSphere.getTranslateY(),
                -coloredSphere.getTranslateZ(),Rotate.Y_AXIS);

        coloredSphere.getTransforms().add(rPhi);
        Rotate rTheta = new Rotate (-latitude*60.0/90.0,
                -coloredSphere.getTranslateX(),-coloredSphere.getTranslateY(),
                -coloredSphere.getTranslateZ(),Rotate.X_AXIS);
        coloredSphere.getTransforms().add(rTheta);

        return coloredSphere;
    }

    public void displayRedSphere(Aeroport a){
        redSphere=createSphere(a,Color.RED);
      this.getChildren().add(redSphere);
    }

    public void displayYellowSphere(ArrayList<Flight> list){
        yellowSphere.clear();

        for (Flight f : list){
            if (f.getDeparture()!=null)
            {
                Sphere current = createSphere(f.getDeparture(),Color.YELLOW);
                this.getChildren().add(current);
            }
        }
    }

    public void displayBlueSphere(){
        yellowSphere.clear();

        for (int latitude=-90;latitude<90; latitude+=20){
            for (int longitude=-180;longitude<180;longitude+=20) {
                Sphere current = createSphere(latitude, longitude, Color.BLUE);
                this.getChildren().add(current);
            }
        }
    }
}
