package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Subsystems.*;

public class AutoCommand extends SequentialCommandGroup {
    
    public AutoCommand(Drivetrain drivetrain, Shooter shooter) {
        addRequirements(drivetrain);
        addRequirements(shooter);
        addCommands(
            // start up the launcher wheel
            new InstantCommand(() -> shooter.EnableLauncher()),

            // shoot the ball
            new Shoot(shooter),

            // wait for the shoot cycle to finish
            new WaitCommand(1),

            // disable the launcher wheel
            new InstantCommand(() -> shooter.DisableLauncher()),

            // backup for two seconds
            new AutoDrive(drivetrain, 0.7, -2)
        );
    }
}

