package frc.robot.Subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
    private static CANSparkMax m_intakeMotor = new CANSparkMax(
        Constants.IntakeMotorNumber, MotorType.kBrushed);
    private double m_speed = 0; 

    public void enable() {
        m_speed = -1;
    }

    public void disable() {
        m_speed = 0;
    }

    @Override
    public void periodic() {
        m_intakeMotor.set(m_speed);
    }
}
