package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Subsystems.Bucket;

public class TakeADump extends SequentialCommandGroup {

    public TakeADump(Bucket bucket) {
        addRequirements(bucket);
        addCommands(
          new InstantCommand(() -> bucket.stop()),
          new ArmUp(),
          new BucketEject(bucket),
          new InstantCommand(() -> bucket.stop(), bucket),
          new ArmDown(),
          new InstantCommand(() -> bucket.intake(), bucket)
        );
    }

}
