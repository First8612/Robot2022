package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TakeADump extends SequentialCommandGroup {

    public TakeADump() {
      addCommands(
          new ArmUp(),

          new ArmDown());  
    }
    
}
