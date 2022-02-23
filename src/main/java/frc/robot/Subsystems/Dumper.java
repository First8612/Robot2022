package frc.robot.Subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Dumper extends SubsystemBase {
    private static CANSparkMax m_armMotorCanSparkMax = new CANSparkMax(5, MotorType.kBrushless);
    private static RelativeEncoder m_encoder = m_armMotorCanSparkMax.getEncoder();
    private static double m_upStop = 10;
    private static double m_speed = 0.1;

    private static TravelingDirection m_armTraveling = TravelingDirection.Stopped;

    private enum TravelingDirection {
        GoingUp,
        GoingDown,
        Stopped
    }

    public Dumper() {
        m_encoder.setPosition(0);
    }

    public void armUp() {
        if (getIsUp()) return;

        m_armMotorCanSparkMax.set(m_speed);
        m_armTraveling = TravelingDirection.GoingUp;
    }

    public void armDown() {
        if (getIsDown()) return;

        m_armMotorCanSparkMax.set(-m_speed);
        m_armTraveling = TravelingDirection.GoingDown;
    }

    public boolean getIsUp() {
        return m_encoder.getPosition() >= m_upStop;
    }

    public boolean getIsDown() {
        return m_encoder.getPosition() <= 0;
    }

    @Override
    public void periodic() {
        if (m_armTraveling == TravelingDirection.GoingUp) {
            if (getIsUp()) {
                m_armMotorCanSparkMax.set(0);
                m_armTraveling = TravelingDirection.Stopped;
            }
        } else if (m_armTraveling == TravelingDirection.GoingDown) {
            if (getIsDown()) {
                m_armMotorCanSparkMax.set(0);
                m_armTraveling = TravelingDirection.Stopped;
            }
        }
    }




}
