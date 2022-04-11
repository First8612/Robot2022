package frc.robot.Subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class Climber extends SubsystemBase {
    private static CANSparkMax m_retractionMotor = new CANSparkMax(Constants.ClimberRetractionMotor, MotorType.kBrushless);
    private static CANSparkMax m_extensionMotor = new CANSparkMax(Constants.ClimberExtensionMotor, MotorType.kBrushless);
    private RelativeEncoder m_retractionMotorEncoder = m_retractionMotor.getEncoder();
    private RelativeEncoder m_extensionMotorEncoder = m_extensionMotor.getEncoder();

    // using Double because it's a nullable object (so we can differentiate between 0 and null)
    private Double m_retractMotorSpeed = 0.0;
    private Double m_extensionMotorSpeed = 0.0;

    public Climber() {
        m_retractionMotor.setInverted(true);
        reset();
    }

    public void reset() {
        m_retractionMotor.setIdleMode(IdleMode.kCoast);
        m_extensionMotor.setIdleMode(IdleMode.kCoast);
        m_retractionMotorEncoder.setPosition(0);
        m_extensionMotorEncoder.setPosition(0);
    }
    
    public void extend() {
        m_retractionMotor.setIdleMode(IdleMode.kCoast);
        m_retractMotorSpeed = null;
        m_extensionMotorSpeed = 0.2;
    }

    public void retract() {
        m_extensionMotor.setIdleMode(IdleMode.kCoast);
        m_extensionMotorSpeed = -0.2;
        m_retractMotorSpeed = 0.3;
    }

    public void stop() {
        m_retractMotorSpeed = 0.0;
        m_retractionMotor.setIdleMode(IdleMode.kBrake);
        m_extensionMotorSpeed = 0.0;
        m_extensionMotor.setIdleMode(IdleMode.kBrake);
    }

    @Override
    public void periodic() {
        // encoder limits: retraction: 0-52, extension 0-94
        var retractionDesc = onlyDriveMotorIfNotNullAndNotHeadingOutsideLimit(m_retractionMotor, m_retractMotorSpeed, 0, 54);
        var extensionDesc = onlyDriveMotorIfNotNullAndNotHeadingOutsideLimit(m_extensionMotor, m_extensionMotorSpeed, 0, 94);
        SmartDashboard.putNumber("Climber: Retraction Motor Position", m_retractionMotorEncoder.getPosition());
        SmartDashboard.putNumber("Climber: Extension Motor Position", m_extensionMotorEncoder.getPosition());
        SmartDashboard.putString("Climber: Retraction Motor Speed (defined)", getDoubleString(m_retractMotorSpeed));
        SmartDashboard.putString("Climber: Extension Motor Speed (defined)", getDoubleString(m_extensionMotorSpeed));
        SmartDashboard.putString("Climber: Retraction Motor Idle Mode", m_retractionMotor.getIdleMode().toString());
        SmartDashboard.putString("Climber: Extension Motor Idle Mode", m_extensionMotor.getIdleMode().toString());
        SmartDashboard.putNumber("Climber: Retraction Motor Speed (actual)", m_retractionMotor.get());
        SmartDashboard.putNumber("Climber: Extension Motor Speed (actual)", m_extensionMotor.get());
        SmartDashboard.putString("Climber: Retraction Motor Speed (desc)", retractionDesc);
        SmartDashboard.putString("Climber: Extension Motor Speed (desc)", extensionDesc);
    }

    private String getDoubleString(Double value)
    {
        if (value != null) return value.toString();

        return "null";
    }

    private String onlyDriveMotorIfNotNullAndNotHeadingOutsideLimit(
        CANSparkMax motor,
        Double speed,
        double limitMin,
        double limitMax
    ) {
        motor.set(0);
        var encoderPosition = motor.getEncoder().getPosition();

        if (speed == null) return "Speed = null";

        if (speed > 0.0 && encoderPosition > limitMax) return "Over max";

        if (speed < 0.0 && encoderPosition < limitMin) return "Under min";


        motor.set(speed);
        return "Normal";
    }
}
