/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.OI;
import frc.robot.subsystems.LightRing;

public class LightRingOn extends CommandBase {
    private LightRing lightRing;

    public LightRingOn(LightRing lightRing) {
        this.lightRing = lightRing;
        addRequirements(lightRing);
    }

    @Override
    public void initialize() {
        lightRing.turnOn();
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        if (!OI.operatorRB.get()) {
            return true;
        }
        return false;
    }
}
