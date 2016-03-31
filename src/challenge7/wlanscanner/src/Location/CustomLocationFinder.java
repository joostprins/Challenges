package challenge7.wlanscanner.src.Location;

import challenge7.wlanscanner.src.Utils.MacRssiPair;
import challenge7.wlanscanner.src.Utils.Position;
import challenge7.wlanscanner.src.Utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomLocationFinder implements LocationFinder {

    private static final double EPSILON = 0.0000001;

    private HashMap<String, Position> knownLocations; //Contains the known locations of APs. The long is a MAC address.

    public CustomLocationFinder(){
        knownLocations = Utils.getKnownLocations(); //Put the known locations in our hashMap
    }

    @Override
    public Position locate(MacRssiPair[] data) {
        List<MacRssiPair> accessPoints = new ArrayList<>();
        for (MacRssiPair pair : data) {
            if(knownLocations.containsKey(pair.getMacAsString())) {
                accessPoints.add(pair);
            }
        }

        if (accessPoints.size() == 3) {
            double x1 = knownLocations.get(accessPoints.get(0).getMacAsString()).getX();
            double y1 = knownLocations.get(accessPoints.get(0).getMacAsString()).getY();
            double r1 = getDistance(accessPoints.get(0).getRssi());
            System.out.println("X1: " + x1 + " - Y1: " + y1 + " - R1" + r1);
            double x2 = knownLocations.get(accessPoints.get(1).getMacAsString()).getX();
            double y2 = knownLocations.get(accessPoints.get(1).getMacAsString()).getY();
            double r2 = getDistance(accessPoints.get(1).getRssi());
            System.out.println("X2: " + x2 + " - Y2: " + y2 + " - R2" + r2);
            double x3 = knownLocations.get(accessPoints.get(2).getMacAsString()).getX();
            double y3 = knownLocations.get(accessPoints.get(2).getMacAsString()).getY();
            double r3 = getDistance(accessPoints.get(2).getRssi());
            System.out.println("X3: " + x3 + " - Y3: " + y3 + " - R3" + r3);
            calculateThreeCircleIntersection(x1, y1, r1, x2, y2, r2, x3, y3, r3);
        } else {
            System.out.println("Less / more than 3 APs.");
        }

        //printMacs(data); //print all the received data
        return getFirstKnownFromList(data); //return the first known APs location
    }

    /**
     * Returns the position of the first known AP found in the list of MacRssi pairs
     * @param data
     * @return
     */
    private Position getFirstKnownFromList(MacRssiPair[] data){
        Position ret = new Position(0,0);
        for(int i=0; i<data.length; i++){
            if(knownLocations.containsKey(data[i].getMacAsString())){
                ret = knownLocations.get(data[i].getMacAsString());
            }
        }
        return ret;
    }

    /**
     * Outputs all the received MAC RSSI pairs to the standard out
     * This method is provided so you can see the data you are getting
     * @param data
     */
    private void printMacs(MacRssiPair[] data) {
        for (MacRssiPair pair : data) {
            System.out.print(pair.getRssi() + " - ");
            System.out.println(getDistance(pair.getRssi()));
        }
    }

    public int getAverageRssi(MacRssiPair[] data) {
        int total = 0;
        int count = 0;
        for (MacRssiPair pair : data) {
            total += pair.getRssi();
            count++;
        }
        if (count != 0) {
            return total / count;
        }
        return 0;
    }

    public double getDistance(int rssi) {
        return Math.pow(2.71828182845905, (1.0-rssi)/20);
    }

    private boolean calculateThreeCircleIntersection(double x0, double y0, double r0, double x1, double y1, double r1, double x2, double y2, double r2) {
        double a, dx, dy, d, h, rx, ry;
        double point2_x, point2_y;

    /* dx and dy are the vertical and horizontal distances between
    * the circle centers.
    */
        dx = x1 - x0;
        dy = y1 - y0;

    /* Determine the straight-line distance between the centers. */
        d = Math.sqrt((dy*dy) + (dx*dx));

    /* Check for solvability. */
        if (d > (r0 + r1))
        {
        /* no solution. circles do not intersect. */
            return false;
        }
        if (d < Math.abs(r0 - r1))
        {
        /* no solution. one circle is contained in the other */
            return false;
        }

    /* 'point 2' is the point where the line through the circle
    * intersection points crosses the line between the circle
    * centers.
    */

    /* Determine the distance from point 0 to point 2. */
        a = ((r0*r0) - (r1*r1) + (d*d)) / (2.0 * d) ;

    /* Determine the coordinates of point 2. */
        point2_x = x0 + (dx * a/d);
        point2_y = y0 + (dy * a/d);

    /* Determine the distance from point 2 to either of the
    * intersection points.
    */
        h = Math.sqrt((r0*r0) - (a*a));

    /* Now determine the offsets of the intersection points from
    * point 2.
    */
        rx = -dy * (h/d);
        ry = dx * (h/d);

    /* Determine the absolute intersection points. */
        double intersectionPoint1_x = point2_x + rx;
        double intersectionPoint2_x = point2_x - rx;
        double intersectionPoint1_y = point2_y + ry;
        double intersectionPoint2_y = point2_y - ry;

        System.out.println("INTERSECTION Circle1 AND Circle2: (" + intersectionPoint1_x + "," + intersectionPoint1_y + ")" + " AND (" + intersectionPoint2_x + "," + intersectionPoint2_y + ")");

    /* Lets determine if circle 3 intersects at either of the above intersection points. */
        dx = intersectionPoint1_x - x2;
        dy = intersectionPoint1_y - y2;
        double d1 = Math.sqrt((dy*dy) + (dx*dx));

        dx = intersectionPoint2_x - x2;
        dy = intersectionPoint2_y - y2;
        double d2 = Math.sqrt((dy*dy) + (dx*dx));

        if(Math.abs(d1 - r2) < EPSILON) {
            System.out.println("INTERSECTION Circle1 AND Circle2 AND Circle3: (" + intersectionPoint1_x + "," + intersectionPoint1_y + ")");
        }
        else if(Math.abs(d2 - r2) < EPSILON) {
            System.out.println("INTERSECTION Circle1 AND Circle2 AND Circle3: (" + intersectionPoint2_x + "," + intersectionPoint2_y + ")"); //here was an error
        }
        else {
            System.out.println("INTERSECTION Circle1 AND Circle2 AND Circle3: NONE");
        }
        return true;
    }

}
