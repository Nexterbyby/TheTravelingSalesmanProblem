/**
 * Author: Jens Eichenberger
 * Created: 25.11.2020
 * Version: 25.11.2020
 * Description:
 *      The single waypoint is created here.
 */
public class Waypoint {
    private int id;
    private String name;
    private double x;
    private double y;

    public Waypoint(int id, String name, double x,double y){
        this.x = x;
        this.y = y;
        this.name = name;
        this.id = id;
    }

    public Waypoint(){
        //
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
