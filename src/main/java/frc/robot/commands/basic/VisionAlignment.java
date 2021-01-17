// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.sensors.VisionCamera;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.LightRing;
import frc.robot.subsystems.Peripherals;
import frc.robot.tools.controlloops.PID;

public class VisionAlignment extends CommandBase {

    private LightRing lightRing;
    private Drive drive;
    private Peripherals peripherals;
    private VisionCamera visionCam;

    private PID pid;
    private double kP = 0.01;
    private double kI = 0;
    private double kD = 0;

    public VisionAlignment(LightRing lightRing, Drive drive, Peripherals peripherals) {
        this.drive = drive;
        this.lightRing = lightRing;
        this.peripherals = peripherals;

        addRequirements(this.drive, this.lightRing);
    }

    @Override
    public void initialize() {
        pid = new PID(kP, kI, kD);
        pid.setSetPoint(0);
        pid.setMinOutput(-0.5);
        pid.setMaxOutput(0.5);
    }

    @Override
    public void execute() {
        lightRing.turnOn();
        pid.updatePID(peripherals.getCamAngle());
        SmartDashboard.putNumber("VisionPID Angle", peripherals.getCamAngle());
        SmartDashboard.putNumber("Result", pid.getResult());
        SmartDashboard.putBoolean("Finished", false);
        drive.setRightPercent(pid.getResult());
        drive.setLeftPercent(-pid.getResult());
    }

    @Override
    public void end(boolean interrupted) {
        drive.setRightPercent(0);
        drive.setLeftPercent(0);
        lightRing.turnOff();
    }

    @Override
    public boolean isFinished() {
        return Math.abs(peripherals.getCamAngle()) <= 3;
    }
}
