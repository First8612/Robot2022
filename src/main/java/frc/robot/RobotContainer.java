package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Commands.ArmDown;
import frc.robot.Commands.ArmUp;
import frc.robot.Commands.TakeADump;
import frc.robot.Subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    private final int axis_forwardBack = 1;
    private final int axis_rotate = 2;
    private final GenericHID m_controller = new Joystick(0);
    private final JoystickButton m_boostButton = new JoystickButton(m_controller, 2);
    private final JoystickButton m_dumpButton = new JoystickButton(m_controller, 1);
    private final JoystickButton m_armUpButton = new JoystickButton(m_controller, 6);
    private final JoystickButton m_armDownButton = new JoystickButton(m_controller, 4);
    private final JoystickButton m_highGearButton = new JoystickButton(m_controller, 5);
    private final JoystickButton m_lowGearButton = new JoystickButton(m_controller, 3);
    private final JoystickButton m_NOOOOOButton = new JoystickButton(m_controller, 7);
    private final JoystickButton m_testButton = new JoystickButton(m_controller, 12);

    // The robot's subsystems
    private final Drivetrain m_robotDrive = new Drivetrain();
    private final Pneumatics m_pneumatics = new Pneumatics();
    private final Intake m_intake = new Intake();
    private final Bucket m_bucket = new Bucket();
    private final Dumper m_dumper = Dumper.getInstance();

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
      CommandScheduler.getInstance().registerSubsystem(m_intake);
      CommandScheduler.getInstance().registerSubsystem(m_bucket);

      // Configure the button bindings
      configureButtonBindings();
  
      // Configure default commands
      // Set the default drive command to arcade drive
        m_robotDrive.setDefaultCommand(
          new RunCommand(
            () -> {
                double speed = m_controller.getRawAxis(axis_forwardBack);
                double rotation = m_controller.getRawAxis(axis_rotate);
                m_robotDrive.arcadeDrive(speed, rotation);
            },
            m_robotDrive)
        );
    }

    public void init(){
      m_pneumatics.start();
      m_intake.enable();
      m_bucket.reverse();
    }
  
    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link PS4Controller}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
      // Drive at full speed when trigger button is held
        m_boostButton
          .whenPressed(() -> m_robotDrive.setMaxOutput(1))
          .whenReleased(() -> m_robotDrive.setMaxOutput(0.5));

        m_armUpButton
            .whenPressed(new ArmUp());
            
        m_armDownButton
            .whenPressed(new ArmDown()); 
        
        m_dumpButton
            .whenPressed(new TakeADump(m_bucket));

        m_NOOOOOButton
          .whenPressed(() -> {
            m_pneumatics.stop();
            m_intake.disable();
            m_bucket.stop();
            m_dumper.stop();
            CommandScheduler.getInstance().cancelAll();
          });

        m_highGearButton
          .whenPressed(() -> m_pneumatics.setHighGear());
        
        m_lowGearButton
          .whenPressed(() -> m_pneumatics.setLowGear());

        m_testButton
          .whenPressed(() -> m_bucket.forward());
    }
  }
  