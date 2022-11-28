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
            }
        };
        animationTimer.start();
    }

    public Sphere createSphere(Aeroport a,Color color){
        PhongMaterial col = new PhongMaterial();
        col.setSpecularColor(color);
        col.setDiffuseColor(color);

        double latitude=Math.toRadians(a.getLatitude()-13);
        double longitude=Math.toRadians(a.getLongitude());

        Sphere coloredSphere = new Sphere(2);
        coloredSphere.setMaterial(col);
     //   Translate tz= new Translate(300.*Math.cos(latitude)*Math.sin(longitude),-300*Math.sin(latitude),-300*Math.cos(latitude)*Math.cos(longitude));
        Translate tz = new Translate(0,0,-300);
        coloredSphere.getTransforms().add(tz);
        Rotate rTheta = new Rotate (40,0,0,300,Rotate.X_AXIS);
        Rotate rPhi = new Rotate (73,0,0,300,Rotate.Y_AXIS);
        coloredSphere.getTransforms().add(rTheta);
        coloredSphere.getTransforms().add(rPhi);

        return coloredSphere;
    }

    public void displayRedSphere(Aeroport a){
        this.getChildren().add(createSphere(a,Color.RED));
    }

    public void displayYellowSphere(ArrayList<Flight> list){
        PhongMaterial yellow = new PhongMaterial();
        yellow.setSpecularColor(Color.YELLOW);
        yellow.setDiffuseColor(Color.YELLOW);
        yellowSphere.clear();
        for (Flight f : list){
            if (f.getDeparture()!=null)
            {
                Sphere current = createSphere(f.getDeparture(),Color.YELLOW);
                this.getChildren().add(current);
            }
        }
    }
}
