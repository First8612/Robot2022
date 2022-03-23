package frc.robot.Subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
    private CANSparkMax m_launcherWheelMotor = new CANSparkMax(Constants.ShooterLaunchMotor, MotorType.kBrushless);
    private int m_launcherMotorSpeed = 0;

    private DoubleSolenoid m_feedSolenoid = 
    new DoubleSolenoid(
        PneumaticsModuleType.CTREPCM, 
        Constants.PneumaticsShooterFeedExtendSolenoidChannel, 
        Constants.PneumaticsShooterFeedRetractSolenoidChannel
    );
    private DoubleSolenoid.Value m_feedExtended = DoubleSolenoid.Value.kForward;
    private DoubleSolenoid.Value m_feedRetracted = DoubleSolenoid.Value.kReverse;
    private Timer m_feedExtendTimer = new Timer();


    public void Enable() {
        m_launcherMotorSpeed = 1;
    }

    public void Disable() {
        m_launcherMotorSpeed = 0;
    }

    public void Feed() {
        m_feedSolenoid.set(m_feedExtended);
        m_feedExtendTimer.start();
    }

    @Override
    public void periodic() {
        m_launcherWheelMotor.set(m_launcherMotorSpeed);

        if (m_feedExtendTimer.hasElapsed(1)) {
            m_feedSolenoid.set(m_feedRetracted);
            m_feedExtendTimer.stop();
            m_feedExtendTimer.reset();
        }
    }
}
