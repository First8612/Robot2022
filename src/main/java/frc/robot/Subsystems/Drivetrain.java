package frc.robot.Subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
    public static CANSparkMax m_leftMotor = new CANSparkMax(1, MotorType.kBrushed);
    public static CANSparkMax m_leftFollower = new CANSparkMax(2, MotorType.kBrushed);
    public static CANSparkMax m_rightMotor = new CANSparkMax(3, MotorType.kBrushed);
    public static CANSparkMax m_rightFollower = new CANSparkMax(4, MotorType.kBrushed);    
    private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_leftMotor, m_rightMotor);

    private double m_defaultSpeed = 0.5;

    public Drivetrain() {
        m_rightMotor.setInverted(true);
        m_leftMotor.setInverted(false);
        m_leftFollower.follow(m_leftMotor);
        m_rightFollower.follow(m_rightMotor);
        m_leftMotor.setIdleMode(IdleMode.kBrake);
        m_rightMotor.setIdleMode(IdleMode.kBrake);

        resetMaxOutput();
    }

    public void arcadeDrive(double speed, double rotation)
    {
        m_robotDrive.arcadeDrive(-speed, rotation * 0.75);
    }
    /**
     * Sets the max output of the drive. Useful for scaling the drive to drive more slowly.
     *
    * @param maxOutput the maximum output to which the drive will be constrained
    */
     public void setMaxOutput(double maxOutput) {
        m_robotDrive.setMaxOutput(maxOutput);
     }

    public void resetMaxOutput() {
        m_robotDrive.setMaxOutput(m_defaultSpeed);
    }
}
