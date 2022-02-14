package frc.robot.Subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
    public static CANSparkMax m_leftMotor = new CANSparkMax(1, MotorType.kBrushed);
    public static CANSparkMax m_leftFollower = new CANSparkMax(2, MotorType.kBrushed);
    public static CANSparkMax m_rightMotor = new CANSparkMax(3, MotorType.kBrushless);
    public static CANSparkMax m_rightFollower = new CANSparkMax(4, MotorType.kBrushed);  
    public static CANSparkMax m_armMotorCanSparkMax = new CANSparkMax(5, MotorType.kBrushed);  
    private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_leftMotor, m_rightMotor);

    public void arcadeDrive(double speed, double rotation)
    {
        m_robotDrive.arcadeDrive(speed, rotation);
        m_robotDrive.setMaxOutput(0.5);
        m_leftFollower.follow(m_leftMotor);
        m_rightFollower.follow(m_rightMotor);
        m_leftMotor.setInverted(true);
        m_armMotorCanSparkMax.setIdleMode(IdleMode.kCoast);
    }
    /**
     * Sets the max output of the drive. Useful for scaling the drive to drive more slowly.
     *
    * @param maxOutput the maximum output to which the drive will be constrained
    */
    public void setMaxOutput(double maxOutput) {
        m_robotDrive.setMaxOutput(maxOutput);
    }
}
