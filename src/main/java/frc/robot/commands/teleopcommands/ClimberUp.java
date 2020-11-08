    /*----------------------------------------------------------------------------*/
    /* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
    /* Open Source Software - may be modified and shared by FRC teams. The code   */
    /* must be accompanied by the FIRST BSD license file in the root directory of */
    /* the project.                                                               */
    /*----------------------------------------------------------------------------*/

    package frc.robot.commands.teleopcommands;

    import edu.wpi.first.wpilibj.Timer;
    import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
    import edu.wpi.first.wpilibj2.command.CommandBase;
    import frc.robot.RobotMap;

    public class ClimberUp extends CommandBase {

    public static double startTime;

    public ClimberUp() {}

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        startTime = Timer.getFPGATimestamp();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        RobotMap.climberRelease.set(Value.kForward);
        if(Timer.getFPGATimestamp() - startTime > 1 && Timer.getFPGATimestamp() - startTime < 4) {
            RobotMap.climberMotor.set(-0.3);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        RobotMap.climberMotor.set(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return Timer.getFPGATimestamp() - startTime > 4;
    }
}