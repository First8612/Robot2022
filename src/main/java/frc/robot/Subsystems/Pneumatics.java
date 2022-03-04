package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Pneumatics extends SubsystemBase {
    private Compressor m_compressor = new Compressor(PneumaticsModuleType.CTREPCM);

    // 7 is the side designated as "forward", 6 is "reverse".
    private DoubleSolenoid m_gearSwitcher = 
        new DoubleSolenoid(
            PneumaticsModuleType.CTREPCM, 
            Constants.PneumaticsLowGearSolenoidChannel, 
            Constants.PneumaticsHighGearSolenoidChannel
        );
    private DoubleSolenoid.Value lowGear = DoubleSolenoid.Value.kForward;
    private DoubleSolenoid.Value highGear = DoubleSolenoid.Value.kReverse;

    // constructor. it runs when "new" is called
    public Pneumatics()
    {
        m_gearSwitcher.set(DoubleSolenoid.Value.kReverse);
        m_compressor.disable();
    }

    public void start()
    {
        m_compressor.enableDigital();
    }

    public void stop()
    {
        m_compressor.disable();
    }
    
    public void setHighGear()
    {
        m_gearSwitcher.set(highGear);
    }

    public void setLowGear()
    {
        m_gearSwitcher.set(lowGear);
    }

    public void toggleGear() {
        m_gearSwitcher.toggle();
    }
}
