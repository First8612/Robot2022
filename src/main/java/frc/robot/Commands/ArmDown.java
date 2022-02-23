package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.Dumper;

public class ArmDown extends CommandBase {
    private static Dumper m_dumper = new Dumper();

    public ArmDown() {
        addRequirements(m_dumper);
    }
    
    @Override
    public void initialize() {
        m_dumper.armDown();
    }

    @Override
    public boolean isFinished()
    {
        return m_dumper.getIsDown();
    }
}
