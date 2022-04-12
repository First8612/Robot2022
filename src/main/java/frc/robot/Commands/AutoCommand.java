package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Subsystems.*;

public class AutoCommand extends SequentialCommandGroup {
    
    public AutoCommand(Drivetrain drivetrain, Shooter shooter) {
        addRequirements(drivetrain);
        addRequirements(shooter);
        addCommands(
            // start up the launcher wheel
            // new InstantCommand(() -> shooter.EnableFlywheel(), shooter),

            new DriveDistance(-0.1, 54, drivetrain),

            // shoot the ball
            new InstantCommand(() -> shooter.Feed(), shooter),

            // wait for the shoot cycle to finish
            new WaitCommand(1.5), 

            // disable the launcher wheel
            new InstantCommand(() -> shooter.DisableFlywheel(), shooter),

            // backup for two seconds
            new AutoDrive(drivetrain, -0.6, 2)
        );
    }
}

