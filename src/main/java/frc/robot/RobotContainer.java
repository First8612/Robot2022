package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Commands.*;
import frc.robot.Subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private final int axis_forwardBack = PS4Controller.Axis.kLeftY.value;
  private final int axis_rotate = PS4Controller.Axis.kRightX.value;
  private final GenericHID m_controller = new XboxController(0);
  // private final JoystickButton m_boostButton = new JoystickButton(m_controller,
  // PS4Controller.Button.kL1.value);
  private final JoystickButton m_pneumButton = new JoystickButton(m_controller, PS4Controller.Button.kR1.value);
  private final JoystickButton m_NOButton = new JoystickButton(m_controller, PS4Controller.Button.kTouchpad.value);

  // The robot's subsystems
  private final Drivetrain m_robotDrive = new Drivetrain();
  private final Pneumatics m_pneumatics = new Pneumatics();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */

  public void init() {
    m_pneumatics.start();
  }

  public void autonomousInit() {
    CommandScheduler.getInstance().schedule(new AutoCommand(m_robotDrive));
  }

  public void teleopInit() {

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
            m_robotDrive));

    // m_intake.enable();
    // m_bucket.intake();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its
   * subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link PS4Controller}), and then passing
   * it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // Drive at full speed when trigger button is held
    // m_boostButton
    // .whenPressed(() -> m_robotDrive.setMaxOutput(1))
    // .whenReleased(() -> m_robotDrive.setMaxOutput(0.7));

    m_NOButton
        .whenPressed(() -> {
          m_pneumatics.stop();
          CommandScheduler.getInstance().cancelAll();
        });

    m_pneumButton
        .whenPressed(() -> {
          m_pneumatics.toggleGear();
        });
  }
}
