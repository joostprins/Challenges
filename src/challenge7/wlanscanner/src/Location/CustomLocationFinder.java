package challenge7.wlanscanner.src.Location;

import challenge7.wlanscanner.src.Utils.MacRssiPair;
import challenge7.wlanscanner.src.Utils.Position;
import challenge7.wlanscanner.src.Utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomLocationFinder implements LocationFinder {

    private double x;
    private double y;

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

        System.out.println(accessPoints.toString());

        if (accessPoints.size() == 3) {
            double x1 = knownLocations.get(accessPoints.get(0).getMacAsString()).getX();
            double y1 = knownLocations.get(accessPoints.get(0).getMacAsString()).getY();
            double r1 = getDistance(accessPoints.get(0).getRssi());
            System.out.println("X1: " + x1 + " - Y1: " + y1 + " - R1: " + r1 + " (RSSI: " + accessPoints.get(0).getRssi() + ")");
            double x2 = knownLocations.get(accessPoints.get(1).getMacAsString()).getX();
            double y2 = knownLocations.get(accessPoints.get(1).getMacAsString()).getY();
            double r2 = getDistance(accessPoints.get(1).getRssi());
            System.out.println("X2: " + x2 + " - Y2: " + y2 + " - R2: " + r2 + " (RSSI: " + accessPoints.get(1).getRssi() + ")");

            Position[] intersections = calculateIntersection(x1, y1, r1, x2, y2, r2);
            for (Position p : intersections) {
            	if (Double.isNaN(p.getX())) {
            		return new Position(x, y);
            	}
            }
            if (accessPoints.get(2).getRssi() < -50) {
            	this.x = intersections[0].getX();
            	this.y = intersections[0].getY();
                return intersections[0];
            } else {
            	this.x = intersections[0].getX();
            	this.y = intersections[0].getY();
                return intersections[1];
            }

        } else {
            System.out.println("Less / more than 3 APs.");
        }

        return new Position(x,y); //return the first known APs location
    }

    public double getDistance(int rssi) {
        double power = ((1d-rssi)/20d);
        return Math.pow(2.71828182845905d, power);
    }

    private Position[] calculateIntersection(double x0, double y0, double r0, double x1, double y1, double r1) {
        double a, dx, dy, d, h, rx, ry;
        double point2_x, point2_y;

    /* dx and dy are the vertical and horizontal distances between
    * the circle centers.
    */
        dx = x1 - x0;
        dy = y1 - y0;

    /* Determine the straight-line distance between the centers. */
        d = Math.sqrt((dy * dy) + (dx * dx));

    /* 'point 2' is the point where the line through the circle
    * intersection points crosses the line between the circle
    * centers.
    */

    /* Determine the distance from point 0 to point 2. */
        a = ((r0 * r0) - (r1 * r1) + (d * d)) / (2.0 * d);

    /* Determine the coordinates of point 2. */
        point2_x = x0 + (dx * a / d);
        point2_y = y0 + (dy * a / d);

    /* Determine the distance from point 2 to either of the
    * intersection points.
    */
        h = Math.sqrt((r0 * r0) - (a * a));

    /* Now determine the offsets of the intersection points from
    * point 2.
    */
        rx = -dy * (h / d);
        ry = dx * (h / d);

    /* Determine the absolute intersection points. */
        double intersectionPoint1_x = point2_x + rx;
        double intersectionPoint2_x = point2_x - rx;
        double intersectionPoint1_y = point2_y + ry;
        double intersectionPoint2_y = point2_y - ry;

        if (intersectionPoint1_y > intersectionPoint2_y) {
            return new Position[]{new Position(intersectionPoint1_x, intersectionPoint1_y), new Position(intersectionPoint2_x, intersectionPoint2_y)};
        } else {
            return new Position[]{new Position(intersectionPoint2_x, intersectionPoint2_y), new Position(intersectionPoint1_x, intersectionPoint1_y)};
        }
    }

}