package frc.robot.Commands;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class TakeADump extends CommandBase {
    public static CANSparkMax m_armMotorCanSparkMax = new CANSparkMax(5, MotorType.kBrushless);
    private static RelativeEncoder m_encoder;

    public TakeADump() {
        // initialize encoder objects
        m_encoder = m_armMotorCanSparkMax.getEncoder();
    }
    
    @Override
    public void initialize() {
        m_encoder.setPosition(0);
    }

    @Override
    public void execute() {
        m_armMotorCanSparkMax.set(0.3);
    }

    @Override
    public boolean isFinished()
    {
        System.out.print(m_encoder.getPosition());
        return m_encoder.getPosition() >= 10;
    }

    @Override
    public void end(boolean interrupted) {
        m_armMotorCanSparkMax.set(0);
    }
}
