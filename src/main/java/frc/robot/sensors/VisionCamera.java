// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.sensors;

import edu.wpi.first.wpilibj.SerialPort;

import frc.robot.tools.math.Point;

import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/* JSON From Camera :
{"Distance": -11.0, "Angle": -100.0}
*/

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
    private AtomicBoolean shouldStop = new AtomicBoolean(false);
    private ConcurrentLinkedQueue<JSONObject> jsonResults = new ConcurrentLinkedQueue<JSONObject>();

    public VisionCamera(SerialPort jevois) {
        port = jevois;
        Runnable task =
                () -> {
                    String buffer = "";
                    while (!shouldStop.get()) {
                        if (port.getBytesReceived() > 0) {
                            buffer += port.readString();
                        }
                        if (buffer.length() > 0) {
                            int index = buffer.indexOf('{', 0);
                            if (index != -1 && index != 0) {
                                buffer = buffer.substring(index);
                            } else if (index == -1) {
                                buffer = "";
                            }
                        }
                        if (buffer.length() > 0) {
                            int index = buffer.indexOf('}', 0);
                            if (index != -1) {
                                String section = buffer.substring(0, index + 1);
                                try {
                                    JSONObject json = (JSONObject) parser.parse(section);
                                    jsonResults.add(json);
                                } catch (ParseException e) {
                                    System.err.println(e);
                                }
                                buffer = buffer.substring(index + 1);
                            }
                        }
                    }
                };
        Thread thread = new Thread(task);
        thread.start();
    }

    public void updateVision() {
        JSONObject json = jsonResults.poll();
        if (json != null) {
            double tempDistance = (double) json.get("Distance");
            double tempAngle = (double) json.get("Angle");

            if (tempDistance != badDistance) {
                distance = tempDistance;
            }
            if (tempAngle != badAngle) {
                angle = tempAngle;
            }
        }
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
        return a * Math.pow(getDistance(), 3)
                + b * Math.pow(getDistance(), 2)
                + c * getDistance()
                + d;
    }
}
