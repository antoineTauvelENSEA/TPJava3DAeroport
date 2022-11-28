import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import static java.lang.Double.parseDouble;

public class World {
    public ArrayList<Aeroport> getList() {
        return list;
    }

    ArrayList<Aeroport> list = new ArrayList<Aeroport>();

    public World (String fileName){
        try{
            BufferedReader buf = new BufferedReader(new FileReader(fileName));
            String s = buf.readLine();
            while(s!=null){
                s=s.replaceAll("\"","");
                String fields[]=s.split(",");
                if (fields[1].equals("large_airport")){
                    list.add(new Aeroport(fields[2],fields[9],parseDouble(fields[12]),parseDouble(fields[11]),fields[5]));
                }
            s = buf.readLine();
            }
        }
        catch (Exception e){
            System.out.println("Maybe the file isn't there ?");
            System.out.println(list.get(list.size()-1));
            e.printStackTrace();
        }

    }

    private double distance (double x1, double y1, double x2, double y2){
        double deltaY = y2-y1;
        double deltaX = (x2-x1)*Math.cos((y2+y1)/2);
        return (deltaX*deltaX) + (deltaY*deltaY);

    }
    public Aeroport findNearestAirport(double lon, double lat){
        Aeroport aeroport = list.get(0);
        double d=distance(lon,lat,aeroport.getLongitude(),aeroport.getLatitude());

        for (Aeroport a : list){
            if (distance(lon,lat,a.getLongitude(),a.getLatitude())<d)
            {
                aeroport=a;
                d=distance(lon,lat,a.getLongitude(),a.getLatitude());
            }
        }

        return aeroport;


    }

    public Aeroport findByCode(String iata){
        for (Aeroport a : list){
            if (a.getIATA().equals(iata)) return a;
        }
        return null;
    }

    public static void main(String[] args){
        World w = new World ("./data/airport-codes_no_comma.csv");
        System.out.println("Found "+w.getList().size()+" airports.");
        Aeroport paris = w.findNearestAirport(2.316,48.866);
        Aeroport cdg = w.findByCode("CDG");
        double distance = w.distance(2.316,48.866,paris.getLongitude(),paris.getLatitude());
        System.out.println(paris);
        System.out.println(distance);
        double distanceCDG = w.distance(2.316,48.866,cdg.getLongitude(),cdg.getLatitude());
        System.out.println(cdg);
        System.out.println(distanceCDG);

    }
}
