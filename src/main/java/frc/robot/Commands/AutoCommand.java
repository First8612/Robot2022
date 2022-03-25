package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Subsystems.*;

public class AutoCommand extends SequentialCommandGroup {
    
    public AutoCommand(Drivetrain drivetrain, Shooter shooter) {
        addRequirements(drivetrain);
        addRequirements(shooter);
        addCommands(
            // start up the launcher wheel
            new InstantCommand(() -> shooter.EnableLauncher(), shooter),

            // shoot the ball
            // new Shoot(shooter),

            // wait for the shoot cycle to finish
            new WaitCommand(2), 

            // disable the launcher wheel
            new InstantCommand(() -> shooter.DisableLauncher(), shooter),

            // backup for two seconds
            new AutoDrive(drivetrain, -1, 2)
        );
    }
}

