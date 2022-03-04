package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Subsystems.*;

public class AutoCommand extends SequentialCommandGroup {
    
    public AutoCommand(Drivetrain drivetrain, Bucket bucket) {
        addRequirements(drivetrain, bucket);
        addCommands(
            new TakeADump(bucket),
            new AutoDrive(drivetrain, 0.5, 0)
        );
    }
}

