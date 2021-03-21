// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Drive;
import frc.robot.subsystems.LightRing;
import frc.robot.subsystems.MagIntake;
import frc.robot.subsystems.Peripherals;
import frc.robot.tools.controlloops.PID;

public class BallTracking extends CommandBase {

    private LightRing lightRing;
    private Drive drive;
    private Peripherals peripherals;

    private PID pid;
    private double kP = 0.12;
    private double kI = 0.0;
    private double kD = 0.3;
    private int counter = 0;
    private double angleOffset = 0;

    public BallTracking(
            LightRing lightRing,
            Drive drive,
            Peripherals peripherals,
            Double offset,
            MagIntake magIntake) {
        this.drive = drive;
        this.lightRing = lightRing;
        this.peripherals = peripherals;
        angleOffset = offset;

        addRequirements(this.drive, this.lightRing);
    }

    @Override
    public void initialize() {
        SmartDashboard.putBoolean("finished ball tracking", false);
        counter = 0;
        pid = new PID(kP, kI, kD);
        pid.setSetPoint(0);
        pid.setMinOutput(-0.4);
        pid.setMaxOutput(0.4);
    }

    @Override
    public void execute() {
        counter++;
        lightRing.ballTurnOn();
        SmartDashboard.putNumber("vision Angle", peripherals.getCamAngle());
        // System.out.println(peripherals.getCamAngle());
        pid.updatePID(peripherals.getCamAngle() + angleOffset);
        SmartDashboard.putNumber("Ball Tracking PID Output", pid.getResult());
        SmartDashboard.putNumber("Ball Tracking Counter", counter);
        drive.setRightPercent(-pid.getResult());
        drive.setLeftPercent(pid.getResult());
    }

    @Override
    public void end(boolean interrupted) {
        drive.setRightPercent(0);
        drive.setLeftPercent(0);
        SmartDashboard.putBoolean("finished ball tracking", true);
        // lightRing.turnOff();
        lightRing.ballTurnOff();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
