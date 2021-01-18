// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.tools.pathing;

import frc.robot.Constants;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Peripherals;

public class Odometry {

    private double startingLeft;
    private double currentLeft;
    private double startingRight;
    private double currentRight;
    private double startingTheta;
    private double currentTheta;
    private double startingCentre;
    private double currentCentre;
    private double startingX;
    private double currentX;
    private double startingY;
    private double currentY;

    private Drive drive;
    private Peripherals peripherals;

    public Odometry(Drive drive, Peripherals peripherals) {
        startingLeft = Constants.driveUnitsToFeet(drive.getLeftPosition());
        startingRight = Constants.driveUnitsToFeet(drive.getRightPosition());
        startingTheta = peripherals.getNavxAngle();
        this.drive = drive;
        this.peripherals = peripherals;
        startingCentre = (startingLeft + startingRight) / 2;
    }

    public double getLeft() {
        update();
        return currentLeft;
    }

    public double getRight() {
        update();
        return currentRight;
    }

    public double getX() {
        update();
        return currentX;
    }

    public double getY() {
        update();
        return currentY;
    }

    public double getTheta() {
        update();
        return currentTheta;
    }

    public double getCentre() {
        update();
        return currentCentre;
    }

    public void update() {
        currentLeft = startingLeft + Constants.driveUnitsToFeet(drive.getLeftPosition());
        currentRight = startingRight + Constants.driveUnitsToFeet(drive.getRightPosition());
        currentTheta = startingTheta + peripherals.getNavxAngle();
        currentCentre = startingCentre + (currentLeft + currentRight) / 2;
        currentX = startingX + currentCentre * Math.cos(Math.toRadians(currentTheta));
        currentY = startingY + currentCentre * Math.sin(Math.toRadians(currentTheta));
    }
}
