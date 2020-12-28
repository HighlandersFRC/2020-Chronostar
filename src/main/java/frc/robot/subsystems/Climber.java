// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {

    /*
     * TODO Adi, give this one the works, same as I've done with all the others.
     * TODO You're the one who knows it the best.
     */

    private final DoubleSolenoid climberPiston = new DoubleSolenoid(2, 3);
    private final DoubleSolenoid winchRelease = new DoubleSolenoid(4, 5);

    public Climber() {}

    @Override
    public void periodic() {}
}
