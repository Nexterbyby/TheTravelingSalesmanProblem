import java.util.ArrayList;
/**
 * Author: Jens Eichenberger
 * Created: 25.11.2020
 * Version: 13.01.2020
 * Description:
 *      Contains all the waypoints.
 */
public class WaypointList {
    private ArrayList<Waypoint> waypoints;

    public WaypointList(){
        waypoints = new ArrayList<Waypoint>();
    }

    public ArrayList<Waypoint> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(ArrayList<Waypoint> waypoints) {
        this.waypoints = waypoints;
    }

    public void addWaypoint(Waypoint waypoint){
        waypoints.add(waypoint);
    }

    public Waypoint getWaypoint(int index){
        return waypoints.get(index);
    }

    public void printWaypoints(){
        System.out.println("Id\t\tName\t\tLongitude\t\tLatitude");
        for(Waypoint w : waypoints){
            System.out.println(w.getId() + "\t\t" + w.getName() + "\t\t" + w.getX() + "\t\t" + w.getY());
        }
    }

    public int size(){
        return this.waypoints.size();
    }

    public boolean contains(Waypoint test){
        boolean temp = false;

        for(Waypoint w : waypoints){
            if(w.equals(test)){
                temp = true;
            }
        }

        return temp;
    }

    public void clear(){
        waypoints.clear();
    }
}
