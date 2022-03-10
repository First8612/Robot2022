package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.Drivetrain;
import edu.wpi.first.wpilibj.Timer;


public class AutoDrive extends CommandBase {
    public static Drivetrain m_drivetrain;
    private Timer m_timer = new Timer();
    private double speed;
    private double seconds;

    public AutoDrive(Drivetrain drivetrain, double speed, double seconds) {
        m_drivetrain = drivetrain;
        this.speed = speed;
        this.seconds = seconds;
        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        m_timer.reset();
        m_timer.start();
        System.out.print("AutoDrive initialize");
    }

    @Override
    public void execute() {
        System.out.print(speed);
        System.out.print("AutoDrive execute");
        m_drivetrain.arcadeDrive(speed, 0);
    }

    @Override 
    public boolean isFinished() {
        return m_timer.hasElapsed(seconds);
    }

    
}
