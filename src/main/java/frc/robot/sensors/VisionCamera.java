// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.sensors;

import edu.wpi.first.hal.util.UncleanStatusException;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;

import frc.robot.tools.math.Point;

import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class VisionCamera {

    JSONParser parser = new JSONParser();
    SerialPort port;
    public String sanitizedString = "nothing";
    public double lastParseTime;
    private double distance;
    private double angle;
    private double badAngle = -100.0;
    private double badDistance = -11.0;
    private Point targetPoint = new Point(0, 0);

    public VisionCamera(SerialPort port) {
        this.port = port;
    }

    public void updateVision() {
        try {
            String unsanitizedString = this.getString();
            String jsonString =
                    unsanitizedString.substring(
                            unsanitizedString.indexOf('{'), unsanitizedString.indexOf('}') + 1);
            double tryDistance = badDistance;
            double tryAngle = badAngle;

            if (jsonString != null) {

                tryDistance = parseDistance(jsonString);
                tryAngle = parseAngle(jsonString);
            }
            if (tryAngle != badAngle) {
                distance = tryDistance;
                angle = tryAngle;

                lastParseTime = Timer.getFPGATimestamp();
            }

        } catch (Exception e) {
        }
    }

    public double parseAngle(String jsonString) {

        try {
            Object object = parser.parse(jsonString);
            JSONObject jsonObject = (JSONObject) object;
            if (jsonObject != null) {
                double distString = (double) jsonObject.get("Angle");
                return Double.valueOf(distString);
            }
        } catch (ParseException e) {
        } catch (UncleanStatusException e) {
        } catch (ClassCastException e) {
        }

        return badAngle;
    }

    public double parseDistance(String jsonString) {

        try {
            Object object = parser.parse(jsonString);
            JSONObject jsonObject = (JSONObject) object;
            if (jsonObject != null) {
                double distString = (double) jsonObject.get("Distance");
                return (Double.valueOf(distString)) / 12;
            }
        } catch (ParseException e) {
        } catch (UncleanStatusException e) {
        } catch (ClassCastException e) {
        }

        return badDistance;
    }

    public double getDistance() {
        return distance;
    }

    public double getAngle() {
        return angle;
    }
    
    public String getString() {
        try {
            if (port.getBytesReceived() > 2) {
                String unsanitizedString = port.readString();
                if (unsanitizedString.length() > 5
                        && !unsanitizedString.isBlank()
                        && !unsanitizedString.isEmpty()) {
                    sanitizedString = unsanitizedString;
                }
            }
        } catch (Exception e) {
        }
        return sanitizedString;
    }

    public Point getTargetPoint() {
        double xAverage = 0;
        double yAverage = 0;
        updateVision();
        for (int i = 0; i < 20; i++) {
            xAverage = xAverage + (getDistance() * Math.cos(Math.toRadians(getAngle())));
            yAverage = yAverage + (getDistance() * Math.sin(Math.toRadians(getAngle())));
        }
        xAverage = xAverage / 20;
        yAverage = yAverage / 20;
        targetPoint.setLocation(xAverage, yAverage);
        return targetPoint;
    }

    public double getCorrectedDistance(double a, double b, double c, double d) {
        updateVision();
        return a * Math.pow(getDistance(), 3) + b * Math.pow(getDistance(), 2) + c * getDistance() + d;
    }
}
