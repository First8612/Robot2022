package frc.robot.Subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
    private CANSparkMax m_launcherWheelMotor = new CANSparkMax(Constants.ShooterLaunchMotor, MotorType.kBrushed);
    private boolean m_flywheelEnabled;

    private DoubleSolenoid m_feedSolenoid = 
    new DoubleSolenoid(
        PneumaticsModuleType.CTREPCM, 
        Constants.PneumaticsShooterFeedExtendSolenoidChannel, 
        Constants.PneumaticsShooterFeedRetractSolenoidChannel
    );
    private DoubleSolenoid.Value m_feedExtended = DoubleSolenoid.Value.kForward;
    private DoubleSolenoid.Value m_feedRetracted = DoubleSolenoid.Value.kReverse;
    private Timer m_feedExtendTimer = new Timer(); 


    public void EnableFlywheel() {
        m_flywheelEnabled = true;
    }

    public void DisableFlywheel() {
        m_flywheelEnabled = false;
    }

    public void toggleFlywheel() {
        m_flywheelEnabled = !m_flywheelEnabled;
    }

    public void Feed() {
        m_feedSolenoid.set(m_feedExtended);
        m_feedExtendTimer.start();
    }

    @Override
    public void periodic() {
        var motorSpeed = 0.0;

        if (m_flywheelEnabled) motorSpeed = 1;
        m_launcherWheelMotor.set(motorSpeed);

        if (m_feedExtendTimer.hasElapsed(1)) {
            m_feedSolenoid.set(m_feedRetracted);
            m_feedExtendTimer.stop();
            m_feedExtendTimer.reset();
        }
    }
}
