package frc.robot.Commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.Bucket;

public class BucketEject extends CommandBase {
    private Bucket m_bucket;
    private Timer m_timer = new Timer();
    private double m_ejectionTime = 1;

    public BucketEject(Bucket bucket) {
        m_bucket = bucket;
        addRequirements(m_bucket);
    }
    
    @Override
    public void initialize() {
        m_bucket.forward();
        m_timer.start();
    }

    @Override
    public void end(boolean interrupted) {
        m_timer.stop();
        m_timer.reset();
    }

    @Override
    public boolean isFinished()
    {
        return m_timer.hasElapsed(m_ejectionTime);
    }
}
