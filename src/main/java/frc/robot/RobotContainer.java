package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
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
    private final JoystickButton m_boostButton = new JoystickButton(m_controller, 1);

    // The robot's subsystems
    private final Drivetrain m_robotDrive = new Drivetrain();

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
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
    }
  }
  