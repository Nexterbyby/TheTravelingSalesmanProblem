import Greedy.Path;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


/**
 * Author: Jens Eichenberger
 * Created: 25.11.2020
 * Version: 16.01.2021
 * Description:
 *      The Main Class for the traveling salesman.
 *      In here there will the magic happen.
 */
public class Main {
    public static void main(String[] args) throws IOException, FileNotFoundException, InterruptedException{
        long processtimeStart = 0;
        long processtimeEnd = 0;

        Functions func = new Functions();
        WaypointList waypoints;
        Path [] paths = new Path[1];
        Scanner s = new Scanner(System.in);

        String input = "";

        // just preloads the example Waypoints
        waypoints = func.importFile();

        // Here comes the Menu
        while(!input.equals("c")){
            System.out.println("Welcome to the Traveling Salesperson Problemsolver.");
            System.out.println(waypoints.size() + " Waypoints preloaded:");
            waypoints.printWaypoints();
            System.out.println("\nDo you want to change them? (y/n)");
            input = s.nextLine();
            if(input.equals("y")){
                System.out.println("type in the filepath src/output/");
                input = s.nextLine();
                func.setFile(input);
                waypoints = func.importFile();
            }else if(input.equals("n")){
                break;
            }
            System.out.println("\nContiniue? (c) If not (anything)");
            input = s.nextLine();
        }

        System.out.println("\nSet a city as root: [name]");
        System.out.println("Available:\n");
        for(Waypoint w : waypoints.getWaypoints()){
            System.out.print(w.getName() + ", ");
        }
        System.out.println("");
        try{ // wrong input does not matter
            input = s.nextLine();
            processtimeStart = System.currentTimeMillis();
            paths = func.greedyAlgorithm(waypoints);
            paths = func.sortPaths(paths);
            paths = func.convertDistances(paths); // displays in km. If this is not used, then the toString of Path will be wrong
            paths = func.setRootOfPaths(paths, input);
            paths = func.keepUniques(paths);
            processtimeEnd = System.currentTimeMillis();
        }catch (Exception e){
            System.out.println("Something happened, maybe misspelled the name?");
            e.printStackTrace();
        }


        System.out.println("\nResult:");
        for(Path p : paths){
            System.out.println(p.toString());
        }
        System.out.println("Do you want to save the output? (y/n)");
        input = s.nextLine();
        switch (input){
            case "y":
                func.exportResult(paths);
                System.out.println("Saved in " + func.getOutput());
                break;
            case "n":
                System.out.println("Ok.");
                break;
        }

        System.out.println("\nProcessingTime: " + (processtimeEnd-processtimeStart) + "ms");
    }
}
