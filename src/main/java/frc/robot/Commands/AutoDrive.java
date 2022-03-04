package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.Drivetrain;
import edu.wpi.first.wpilibj.Timer;


public class AutoDrive extends CommandBase {
    public static Drivetrain m_drivetrain;
    private Timer m_timer = new Timer();

    public AutoDrive(Drivetrain drivetrain, double speed, double seconds) {
        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        m_timer.reset();
        m_timer.start();
    }

    @Override
    public void execute() {
        new AutoDrive(m_drivetrain, 0.5, 0);
    }

    @Override 
    public boolean isFinished() {
        return m_timer.hasElapsed(3);
    }

    
}
