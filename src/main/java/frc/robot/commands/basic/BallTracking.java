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
    private MagIntake magIntake;

    private PID pid;
    private double kP = 0.015;
    private double kI = 0.0;
    private double kD = 0.3;
    private int counter = 0;
    private int tapCounter = 0;
    private int thirdCounter = 0;
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
        this.magIntake = magIntake;
        angleOffset = offset;

        addRequirements(this.drive, this.lightRing);
    }

    @Override
    public void initialize() {
        SmartDashboard.putBoolean("finsihed ball tracking", false);
        counter = 0;
        tapCounter = 0;
        thirdCounter = 0;
        pid = new PID(kP, kI, kD);
        pid.setSetPoint(0);
        pid.setMinOutput(-0.15);
        pid.setMaxOutput(0.15);
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
        drive.setRightPercent(-pid.getResult() - 0.1);
        drive.setLeftPercent(pid.getResult() - 0.1);
    }

    @Override
    public void end(boolean interrupted) {
        drive.setRightPercent(0);
        drive.setLeftPercent(0);
        SmartDashboard.putBoolean("finished ball tracking", true);
        // lightRing.turnOff();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
