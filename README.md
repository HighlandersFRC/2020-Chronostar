# 2020-Chronostar

Team 4499's robot code for Chronostar. Chronostar's code is written in Java, using mainly the CTRE, REV, Kauai Labs, and WPILib 2020 libs, based off WPILib's Java control system.

## Setup

### General

1. Clone this repo
2. Run `<repo directory>`/gradlew to install Gradle and necessary libs

#### VS Code

1. Get the WPILib extension from Marketplace, as well as Java 11+.
2. In `<repo directory>/.vscode/settings.json`, set `java.home` to the directory pointing to your JDK.

## Functionality

- Drive train path following with a custom pure pursuit controller and motion profiling (NOT WRITTEN YET)
- Velocity PID controlling the flywheel
- Custom PID controlling the hood motor
- JeVois-based vision system for target detection
- Hook-based climbing affair based on a state machine (NOT WRITTEN YET)

## Packages

- `frc.robot`
  
  Contains the robot's central functions, holds all numerical constants used throughout the code.

- `frc.robot.subsystems`
  
  Contains code for the subsystems, all of which extend the `Subsystem` class. Each subsystem sets its own default command, and there is only one instance of each. Subsystems contain an `init()` method, which initializes certain hardware and other properties regarding the subsystem.

- `frc.robot.commands.auto`

  NOT WRITTEN YET - Contains plans for autonomous commands and sequences. Each auto command is a `SequentialCommandGroup`, and each declares its own subsystem requirements.

- `frc.robot.commands.basic`

  Contains basic commands that are used to control one aspect of a subsystem, e.g. moving a piston or setting a motor to a certain speed.

- `frc.robot.commands.composite`

  Contains slightly more complex commands that take control over two or more aspects of a subsystem, e.g. a firing sequence or climbing.

- `frc.robot.commands.default`
  
  Contains the default procedures of a command. WPILib is kind enough to build a default command into each subsystem. That default command is also intelligent enough to know when to stop, due to the subsystem requirements put forth by every command.

- `frc.robot.tools`
  
  Contains tools necessary to do cool stuff, like path generation, path following, control loops, and some trajectory math.

- `frc.robot.sensors`

  Contains classes that are hardware representations of sensors found on the robot, e.g. the LiDAR, JeVois computer vision cameras, and the NavX.

## Read More
- [2020-Chronostar Wiki](https://github.com/HighlandersFRC/2020-Chronostar/wiki)
- [Official WPILib Docs](https://docs.wpilib.org/en/stable/)
- [CTRE Phoenix FRC Documetation](https://phoenix-documentation.readthedocs.io/en/latest/)
- [REV Robotics FRC Documentation](https://docs.revrobotics.com/docs/first-robotics-competition)
- [Kauai Labs FRC Documentation](https://pdocs.kauailabs.com/navx-mxp/software/roborio-libraries/)