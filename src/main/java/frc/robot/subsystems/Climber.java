// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Climber extends SubsystemBaseEnhanced {

    private final DoubleSolenoid climberPiston = new DoubleSolenoid(2, 3);
    private final DoubleSolenoid winchRelease = new DoubleSolenoid(4, 5);

    public Climber() {}

    @Override
    public void init() {}

    @Override
    public void autoInit() {}

    @Override
    public void teleopInit() {}

    @Override
    public void periodic() {}
}
