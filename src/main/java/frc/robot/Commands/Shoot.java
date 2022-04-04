package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Subsystems.Shooter;

public class Shoot extends SequentialCommandGroup {
    public Shoot(Shooter shooter) {
        addCommands(
            // feed the ball to shoot it
            // new InstantCommand(() -> { shooter.Feed(); })

        );
    }
}
