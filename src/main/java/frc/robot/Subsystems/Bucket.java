package frc.robot.Subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Bucket extends SubsystemBase {
    private static CANSparkMax m_bucketMotor = 
        new CANSparkMax(
            Constants.BucketMotorNumber, 
            MotorType.kBrushed
        );
    private double m_speed = 0;
    private double m_maxSpeed = 1; 
    
    public void eject() {
        m_speed = m_maxSpeed;
    }

    public void intake() {
        m_speed = -m_maxSpeed;
    }

    public void stop() {
        m_speed = 0;
    }

    @Override
    public void periodic()
    {
        m_bucketMotor.set(m_speed);
    }
}
