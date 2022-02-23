package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.Dumper;

public class ArmUp extends CommandBase {
    private static Dumper m_dumper = new Dumper();

    public ArmUp() {
        addRequirements(m_dumper);
    }
    
    @Override
    public void initialize() {
        m_dumper.armUp();
    }

    @Override
    public boolean isFinished()
    {
        return m_dumper.getIsUp();
    }
}
