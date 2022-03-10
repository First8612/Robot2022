package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Subsystems.*;

public class AutoCommand extends SequentialCommandGroup {
    
    public AutoCommand(Drivetrain drivetrain, Bucket bucket, Intake intake, Dumper dumper) {
        addRequirements(drivetrain);
        addCommands(
            //new ArmDown(),
            //new TakeADump(bucket),
            new AutoDrive(drivetrain, 0.7, 2)
        );
    }
}

