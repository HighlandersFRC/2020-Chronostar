// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.composite;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;

import frc.robot.commands.basic.DriveBackwards;
import frc.robot.commands.basic.SmartIntake;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.MagIntake;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Autonomous extends ParallelRaceGroup {
    private Drive drive;
    private MagIntake magIntake;
    /** Creates a new Autonomous. */
    public Autonomous() {
        // Add your commands in the addCommands() call, e.g.
        // addCommands(new FooCommand(), new BarCommand());
        addCommands(new DriveBackwards(drive), new SmartIntake(magIntake));
    }
}
