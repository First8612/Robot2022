package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Subsystems.*;

public class AutoCommand extends SequentialCommandGroup {
    
    public AutoCommand(Drivetrain drivetrain) {
        addRequirements(drivetrain);
        addCommands(
            new AutoDrive(drivetrain, -0.7, 3)
        );
    }
}

