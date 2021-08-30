package Greedy;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Path {
    private int id; // used to identify starting point (root)
    private double distance;
    private ArrayList<String> waypoints_name;

    public Path(int id) {
        this.id = id;
        this.distance = 0.0;
        waypoints_name = new ArrayList<String>();
    }

    public Path() {
        waypoints_name = new ArrayList<String>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public ArrayList<String> getWaypoints_name() {
        return waypoints_name;
    }

    public void setWaypoints_name(ArrayList<String> waypoints_name) {
        this.waypoints_name = waypoints_name;
    }

    public void addWaypointNameToPath(String name){
        waypoints_name.add(name);
    }

    public String toString(){
        String output = "";
        output += "Distance: " + new DecimalFormat("#.###").format(distance) + "km | ";
        output += "Path: ";
        output += waypoints_name.get(0);
        for(int i = 1; i < waypoints_name.size(); i++){
            output += " - " + waypoints_name.get(i);
        }

        return output;
    }


}
