/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.defaults;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Peripherals;

public class PeripheralsDefault extends CommandBase {
    /** Creates a new PeripheralsDefault. */
    private Peripherals peripherals;

    public PeripheralsDefault(Peripherals peripherals) {
        this.peripherals = peripherals;
        addRequirements(this.peripherals);
        // Use addRequirements() here to declare subsystem dependencies.
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        // System.out.println(
        //         "Distance " + peripherals.getCamDistance() + " Angle " +
        // peripherals.getCamAngle());
        peripherals.getCamDistance();

        SmartDashboard.putNumber("Vision Distance", peripherals.getCamDistance());
        SmartDashboard.putNumber("Vision Angle", peripherals.getCamAngle());
        SmartDashboard.putNumber("Lidar Distance", peripherals.getLidarDistance());
        SmartDashboard.putNumber("Navx Angle", peripherals.getNavxAngle());
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(final boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
