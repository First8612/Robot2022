package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.Drivetrain;

public class DriveDistance extends CommandBase {
    private Drivetrain m_driveTrain;
    private double m_startPosition;
    private double m_speed;
    private double m_distance;

    public DriveDistance(double speed, double distance, Drivetrain driveTrain) {
        this.m_speed = speed;
        this.m_distance = distance;
        this.m_driveTrain = driveTrain;

    }

    @Override
    public void initialize() {
        m_startPosition = m_driveTrain.getEncoderPosition();
        System.out.println("autodrive: init");
    }
    
    @Override
    public void execute() {
        m_driveTrain.arcadeDrive(m_speed, 0);
        System.out.println(("autodrive: execute"));
    }
    
    @Override 
    public boolean isFinished() {
        var distanceTraveled = Math.abs(m_driveTrain.getEncoderPosition() - m_startPosition);

        return distanceTraveled > m_distance;
    }
}
