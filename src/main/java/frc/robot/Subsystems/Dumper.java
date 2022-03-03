package frc.robot.Subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Dumper extends SubsystemBase {
    private static CANSparkMax m_armMotorCanSparkMax = 
        new CANSparkMax(Constants.ArmMotorNumber, MotorType.kBrushless);
    private static RelativeEncoder m_encoder = m_armMotorCanSparkMax.getEncoder();
    private static int m_motorRevsPerOutputRevs = 5 * 7 * 9;
    private static double m_upStop = 0.30 * m_motorRevsPerOutputRevs;
    private static double m_speed = 0.2;

    private static TravelingDirection m_armTraveling = TravelingDirection.Stopped;

    public static Dumper instance;
    public static synchronized Dumper getInstance() {
        if (instance == null) {
            instance = new Dumper();
        }

        return instance;
    }

    private enum TravelingDirection {
        GoingUp,
        GoingDown,
        Stopped
    }

    public Dumper() {
        m_armMotorCanSparkMax.setInverted(true);
        m_encoder.setPosition(0);
        // m_armMotorCanSparkMax.setOpenLoopRampRate(2); // set how fast the motor is allowed to speed up
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

    public void stop(){
        m_armMotorCanSparkMax.set(0);
        m_armTraveling = TravelingDirection.Stopped;
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
