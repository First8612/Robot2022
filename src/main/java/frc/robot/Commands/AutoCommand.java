package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Subsystems.*;

public class AutoCommand extends SequentialCommandGroup {
    
    public AutoCommand(Drivetrain drivetrain, Shooter shooter) {
        addRequirements(drivetrain);
        addRequirements(shooter);
        addCommands(
            // start up the launcher wheel
            new InstantCommand(() -> shooter.EnableFlywheel(), shooter),

            new DriveDistance(-0.6, 50, drivetrain),

            // wait to stop moving
            new WaitCommand(0.5), 

            // shoot the ball
            new InstantCommand(() -> shooter.Feed(), shooter),

            // wait for the shoot cycle to finish
            new WaitCommand(.75), 

            // disable the launcher wheel
            new InstantCommand(() -> shooter.DisableFlywheel(), shooter),

            new DriveDistance(-0.6, 50, drivetrain)
        );
    }
}

