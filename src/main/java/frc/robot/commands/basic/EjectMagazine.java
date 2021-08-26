// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import frc.robot.OI;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.MagIntake;

public class EjectMagazine extends CommandBase {

    private MagIntake magIntake;
    private Drive drive;
    private final double LOW_MAG_PERCENT = 0.4;
    private final double HIGH_MAG_PERCENT = 1;
    private final double HIGH_INTAKE_PERCENT = 0.8;
    WaitCommand waitCommand;
    private int counter = 0;

    public EjectMagazine(MagIntake magIntake, Drive drive) {
        this.magIntake = magIntake;
        this.drive = drive;
        addRequirements(magIntake, drive);
    }

    @Override
    public void initialize() {
        counter = 0;
    }

    @Override
    public void execute() {
        counter++;
        if (OI.driverController.getBumper(Hand.kRight)) {
            drive.setRightPercent(-0.1);
            drive.setLeftPercent(0.1);
        } else if (OI.driverController.getBumper(Hand.kLeft)) {
            drive.setRightPercent(0.1);
            drive.setLeftPercent(-0.1);
        } else {
            drive.setRightPercent(0);
            drive.setLeftPercent(0);
        }
        magIntake.setMagPercent(LOW_MAG_PERCENT, HIGH_MAG_PERCENT);
        magIntake.setIntakePercent(0, HIGH_INTAKE_PERCENT);
    }

    @Override
    public void end(boolean interrupted) {
        magIntake.setMagPercent(0.0, 0.0);
    }

    @Override
    public boolean isFinished() {
        if (counter >= 100) {
            return true;
        }
        return false;
    }
}
