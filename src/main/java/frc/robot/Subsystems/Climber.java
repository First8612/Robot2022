package frc.robot.Subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

//Things to change after testing: upStop, motor speed, what to put for getPoition in getIsDown

public class Climber extends SubsystemBase {
    private static CANSparkMax m_climberMotor = new CANSparkMax(Constants.ClimberMotor, MotorType.kBrushless);
    private static RelativeEncoder m_encoder = m_climberMotor.getEncoder();
    private static double m_upStop = 5;

    private static upOrDown m_climbDirection = upOrDown.Stopped;

    private enum upOrDown {
        GoingUp,
        GoingDown,
        Stopped
    }

    public Climber() {
        m_climberMotor.setInverted(true);
        m_encoder.setPosition(0);
    }

    public void climbUp() {
        if (getIsUp())
            return;

        m_climberMotor.set(.5);
        m_climbDirection = upOrDown.GoingUp;
    }

    public void climbDown() {
        if (getIsDown())
            return;

        m_climberMotor.set(.5);
        m_climbDirection = upOrDown.GoingDown;
    }

    public void stop() {
        m_climberMotor.set(0);
        m_climbDirection = upOrDown.Stopped;
    }

    public boolean getIsUp() {
        return m_encoder.getPosition() >= m_upStop;
    }

    public boolean getIsDown() {
        return m_encoder.getPosition() <= 0;
    }

    @Override
    public void periodic() {
        if (m_climbDirection == upOrDown.GoingUp) {
            if (getIsUp()) {
                m_climberMotor.set(0);
                m_climbDirection = upOrDown.Stopped;
            }
        } else if (m_climbDirection == upOrDown.GoingDown) {
            if (getIsDown()) {
                m_climberMotor.set(0);
                m_climbDirection = upOrDown.Stopped;
            }
        }

    }

}
