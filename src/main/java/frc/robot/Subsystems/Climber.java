package frc.robot.Subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class Climber extends SubsystemBase {
    private static CANSparkMax m_climberMotor = new CANSparkMax(Constants.ClimberMotor, MotorType.kBrushless);
    private double m_climberMotorSpeed = 0;

    // private DoubleSolenoid m_climbSolenoid = 
    // new DoubleSolenoid(
    //         PneumaticsModuleType.CTREPCM, 
    //         Constants.ClimberDownSolenoidChannel, 
    //         Constants.ClimberUpSolenoidChannel
    // );
    // private DoubleSolenoid.Value m_climberDown = DoubleSolenoid.Value.kForward;
    // private DoubleSolenoid.Value m_climberUp = DoubleSolenoid.Value.kReverse;

    public Climber() {
        m_climberMotor.setInverted(true);
        m_climberMotor.setIdleMode(IdleMode.kBrake);
    }

    // public void climbUp() {
    //     m_climbSolenoid.set(m_climberUp);
    // }

    // public void climbDown() {
    //     m_climbSolenoid.set(m_climberDown);
    // }
    

    public void set() {
        m_climberMotorSpeed = 0.5;
    }

    public void stop() {
        m_climberMotorSpeed = 0;
    }



    @Override
    public void periodic() {
       m_climberMotor.set(m_climberMotorSpeed);
    }
}
