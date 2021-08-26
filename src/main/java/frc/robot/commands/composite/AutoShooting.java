// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.composite;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.commands.basic.DriveAgain;
import frc.robot.commands.basic.DriveBackwards;
import frc.robot.commands.basic.DriveForwards;
import frc.robot.commands.basic.EncoderReset;
import frc.robot.commands.basic.NavxTurn;
import frc.robot.commands.basic.SmartIntake;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.LightRing;
import frc.robot.subsystems.MagIntake;
import frc.robot.subsystems.Peripherals;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoShooting extends SequentialCommandGroup {

    /** Creates a new AutoShooting. */
    public AutoShooting(
            Drive drive,
            Shooter shooter,
            Hood hood,
            MagIntake magIntake,
            LightRing lightRing,
            Peripherals peripherals) {
        addRequirements(drive, shooter, magIntake, lightRing, peripherals);
        DriveBackwards driveBackwards = new DriveBackwards(drive);
        SmartIntake smartIntake = new SmartIntake(magIntake);

        // Add your commands in the addCommands() call, e.g.
        // addCommands(new FooCommand(), new BarCommand());

        addCommands(
                // new Fire(shooter, hood, magIntake, drive, lightRing, peripherals, 1.1, 3500, 10),
                new DriveBackwards(drive),
                new NavxTurn(drive, peripherals, 20),
                new ParallelRaceGroup(new DriveForwards(drive), new SmartIntake(magIntake)),
                new EncoderReset(drive),
                new DriveAgain(drive));
        //  new EncoderReset(drive),
        // new DriveForwards(drive),
        // new Fire(shooter, hood, magIntake, drive, lightRing, peripherals, 1.3, 4900, 10));
    }
}
