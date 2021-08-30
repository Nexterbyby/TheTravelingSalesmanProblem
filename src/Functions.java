import Greedy.Path;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: Jens Eichenberger
 * Created: 25.11.2020
 * Version: 30.08.2021
 * Description:
 *      Use full functions will fill this class
 */
public class Functions {
    private String file = "presetWaypoints.txt";
    private String filePath = "src/input/" + file;
    private String output = "output.txt";
    private String outputFilePath = "src/output/" + output;
    //
    // calculates the Distance between two coordinates (Class: Waypoint)
    //
    public double getDistance(Waypoint pa, Waypoint pb) {

        double sa = pb.getX() - pa.getX();
        double sb = pb.getY() - pa.getY();

        return Math.sqrt(Math.pow(sa, 2) + Math.pow(sb, 2));
    }

    /**
     * It returns an array of every possibility of the starting point,
     * containing the starting point, the distance & a list of every visited city in order.
     */
    public Path[] greedyAlgorithm(WaypointList waypoints) {
        Path[] allGreedyPaths = new Path[waypoints.size()]; // Saves all greedy paths from every root point.
        Waypoint current = new Waypoint();
        Waypoint closest = new Waypoint();
        WaypointList visited = new WaypointList();
        double tempDistance = 0.0; // is the current between waypoint A & (B | C | D)
        double currentDistance = Double.MAX_VALUE; // is the shortest distance from the round
        // s is the starting point of the path.
        for (int i = 0; i < waypoints.size(); i++) { // waypoints.getWaypoint(i) is the current root waypoint.
            allGreedyPaths[i] = new Path(waypoints.getWaypoint(i).getId()); // Saves the ID of the root
            allGreedyPaths[i].addWaypointNameToPath(waypoints.getWaypoint(i).getName()); // Saves the first Waypointname in the chain.
            current = waypoints.getWaypoint(i);
            for (int a = 0; a < waypoints.size() - 1; a++) {
                // searches the closest waypoint
                visited.addWaypoint(current);
                for (Waypoint w : waypoints.getWaypoints()) {
                    if (visited.contains(w)) {
                        //nothing
                    } else {
                        tempDistance = getDistance(current, w);
                        if (tempDistance < currentDistance) {
                            currentDistance = tempDistance;
                            closest = w;
                        }
                    }
                }
                allGreedyPaths[i].setDistance(allGreedyPaths[i].getDistance() + currentDistance); // adds with every progressed waypoint the distance
                allGreedyPaths[i].addWaypointNameToPath(closest.getName());
                current = closest; // moves to the next Waypoint
                currentDistance = Double.MAX_VALUE; // Somewhat needed here
            }
            allGreedyPaths[i].setDistance(allGreedyPaths[i].getDistance() + getDistance(waypoints.getWaypoint(i), current));
            visited.clear();
            tempDistance = 0.0;
            // allGreedyPaths[i].addWaypointNameToPath(waypoints.getWaypoint(i).getName());
        }
        return allGreedyPaths;
    }

    /**
     * Just sorts all the paths from above,
     * from the shortest to highest distance
     */
    public Path[] sortPaths(Path[] paths) {
        Path temp;
        for (int j = 0; j < paths.length; j++) {
            for (int i = 0; i < paths.length - 1; i++) {
                if (paths[i].getDistance() > paths[i + 1].getDistance()) {
                    temp = paths[i + 1];
                    paths[i + 1] = paths[i];
                    paths[i] = temp;
                }
            }
        }
        return paths;
    }

    /**
     * Function for converting the distance from the decimal points to the real distance Km for ex.
     */
    public double decimalToKm(double decimalDistance) {
        return decimalDistance * 111;
    }

    /**
     * Converts the decimal distances in the whole list
     */
    public Path[] convertDistances(Path[] paths) {
        for (Path p : paths) {
            p.setDistance(decimalToKm(p.getDistance()));
        }
        return paths;
    }

    /**
     * Shifts the path so that the user's decided root is first
     */
    public Path[] setRootOfPaths(Path[] paths, String rootName) {
        String[] temp = new String[paths.length];
        boolean isAfterRoot;
        int indexCounter;
        for(Path p : paths){
            isAfterRoot = false;
            indexCounter = 0;
            for(String s : p.getWaypoints_name()){
                if(s.equals(rootName)){
                    isAfterRoot = true;
                }
                if(isAfterRoot){
                    temp[indexCounter] = s;
                    indexCounter++;

                }
            }
            for(int i = indexCounter; i < paths.length; i++){
                temp[i] = p.getWaypoints_name().get(i-indexCounter);
            }
            for(int i = 0; i < paths.length; i++){
                p.getWaypoints_name().set(i, temp[i]);
            }
            p.getWaypoints_name().add(p.getWaypoints_name().get(0));
            // path id ändern
        }
        return paths;
    }

    /**
     * it imports the waypoints from a file
     */
    public WaypointList importFile() throws IOException, FileNotFoundException {
        WaypointList temp = new WaypointList();
        BufferedReader br = new BufferedReader(new FileReader(filePath));

        String line;
        String [] wA;
        while((line = br.readLine()) != null){
            if(line.equals("")){
                break;
            }else if(line.charAt(0) != '#'){
                wA = line.split(";");
                temp.addWaypoint(new Waypoint(Integer.parseInt(wA[0]), wA[1], Double.parseDouble(wA[2]), Double.parseDouble(wA[3])));
            }
        }
        br.close();
        return temp;
    }

    public void exportResult(Path[] paths) throws IOException, FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(outputFilePath));
        // retrieve the contents
        String line;
        String [] wA;
        String contents = "";
        while((line = br.readLine()) != null){
            contents += line + "\n";
        }
        br.close();

        // append result
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date());
        for(int i = 0; i < 3; i++){
            contents += (i+1)  + ". " + paths[i].toString() + "\n";
        }
        contents += "\n" + timeStamp + "\n\n";

        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath));
        bw.write(contents);
        bw.close();
    }

    // This function is used for cleanup, which will be in the output file
    public Path[] keepUniques(Path[] paths){
        int counter = 0;
        Path[] temp = new Path[paths.length];
        for(int i = 0; i < temp.length;i++){
            temp[i] = new Path();
        }
        for(int i = 0; i < paths.length; i++){
            if(!customContains(paths[i], temp)){
                temp[counter] = paths[i];
                counter++;
            }
        }
        Path[] newArray = new Path[counter];
        for(int i = 0; i < newArray.length; i++){
            newArray[i] = temp[i];
        }
        return newArray;
    }

    // used for the function above
    public boolean customContains(Path path, Path[] paths){
        boolean exists = false;

        for(Path p : paths){
            if(p.getWaypoints_name().equals(path.getWaypoints_name())){
                exists = true;
                break;
            }
        }

        return exists;
    }

    // Getter / Setter


    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public void setOutputFilePath(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }
}