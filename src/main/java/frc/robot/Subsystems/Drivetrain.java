package frc.robot.Subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {
    public static CANSparkMax m_leftMotor = new CANSparkMax(3, MotorType.kBrushless);
    public static CANSparkMax m_leftFollower = new CANSparkMax(4, MotorType.kBrushless);
    public static CANSparkMax m_rightMotor = new CANSparkMax(1, MotorType.kBrushless);
    public static CANSparkMax m_rightFollower = new CANSparkMax(2, MotorType.kBrushless);
    public static CANSparkMax[] m_motors = {
        m_leftMotor,
        m_leftFollower,
        m_rightMotor,
        m_rightFollower
    };

    private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_leftMotor, m_rightMotor);

    private double m_defaultSpeed = 1;

    // gear changing section
    private DoubleSolenoid m_gearSwitcher = 
        new DoubleSolenoid(
            PneumaticsModuleType.CTREPCM, 
            Constants.PneumaticsLowGearSolenoidChannel, 
            Constants.PneumaticsHighGearSolenoidChannel
        );
    private DoubleSolenoid.Value lowGear = DoubleSolenoid.Value.kForward;
    private DoubleSolenoid.Value highGear = DoubleSolenoid.Value.kReverse;

    public Drivetrain() {
        m_rightMotor.setInverted(false);
        m_leftMotor.setInverted(true);
        m_leftFollower.follow(m_leftMotor);
        m_rightFollower.follow(m_rightMotor);
        m_leftMotor.setIdleMode(IdleMode.kBrake);
        m_rightMotor.setIdleMode(IdleMode.kBrake);
    }

    public void reset() {
        resetMaxSpeed();
        m_gearSwitcher.set(lowGear);
        
        for (var motor : m_motors) {
            motor.getEncoder().setPosition(0);
        }
    }

    public void arcadeDrive(double speed, double rotation)
    {
        m_robotDrive.arcadeDrive(-speed, rotation * 0.75);
        SmartDashboard.putNumber("Drivetrain: Speed", speed);
        SmartDashboard.putNumber("Drivetrain: Rotation", rotation);
    }
    /**
     * Sets the max output of the drive. Useful for scaling the drive to drive more slowly.
     *
    * @param maxOutput the maximum output to which the drive will be constrained
    */
     public void setMaxSpeed(double maxOutput) {
        m_robotDrive.setMaxOutput(maxOutput);
     }

    public void resetMaxSpeed() {
        m_robotDrive.setMaxOutput(m_defaultSpeed);
    }
        
    public void setHighGear()
    {
        m_gearSwitcher.set(highGear);
    }

    public void setLowGear()
    {
        m_gearSwitcher.set(lowGear);
    }

    public void toggleGear() {
        m_gearSwitcher.toggle();
    }

    
    @Override
    public void periodic() {
        SmartDashboard.putNumber("Drivetrain: Right Lead Position", m_rightMotor.getEncoder().getPosition());
        SmartDashboard.putNumber("Drivetrain: Right Follower Position", m_rightFollower.getEncoder().getPosition());
        SmartDashboard.putNumber("Drivetrain: Left Lead Position", m_leftMotor.getEncoder().getPosition());
        SmartDashboard.putNumber("Drivetrain: Left Follower Position", m_leftFollower.getEncoder().getPosition());
    }
}
