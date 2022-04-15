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

            //drive and wait
            new DriveDistance(-0.4, 15, drivetrain),


            // wait to stop moving
            new WaitCommand(1), 
            new WaitCommand(1), 

            // shoot the ball
            new InstantCommand(() -> shooter.Feed(), shooter),

            // wait for the shoot cycle to finish
            new WaitCommand(.75), 

            // disable the launcher wheel
            new InstantCommand(() -> shooter.DisableFlywheel(), shooter),

            //drive again
            new DriveDistance(-0.6, 35, drivetrain)
        );
    }
}

