package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
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
  private final JoystickButton m_pneumButton = new JoystickButton(m_controller, PS4Controller.Button.kR1.value);
  private final JoystickButton m_feedButton = new JoystickButton(m_controller, PS4Controller.Button.kL1.value);
  private final JoystickButton m_resetButton = new JoystickButton(m_controller, PS4Controller.Button.kOptions.value);
  private JoystickButton m_flywheelButton = new JoystickButton(m_controller, PS4Controller.Button.kTriangle.value);

  private final POVButton m_PovUp_ClimberUp = new POVButton(m_controller, 0);
  private final POVButton m_PovDown_ClimberDown = new POVButton(m_controller, 180);

  // The robot's subsystems
  private final Drivetrain m_robotDrive = new Drivetrain();
  private final Pneumatics m_pneumatics = new Pneumatics();
  private final Climber m_climber = new Climber();
  private final Shooter m_shooter = new Shooter();
  // private PowerDistribution m_pdp = new PowerDistribution();
  private PneumaticsControlModule m_pcm = new PneumaticsControlModule();

  // commands
  private Shoot m_shootCommand = new Shoot(m_shooter);

  public RobotContainer() {
    SmartDashboard.putData(m_robotDrive);
    SmartDashboard.putData(m_shootCommand);
  }

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */

  public void init() {
    m_pneumatics.start();

    if (m_pcm.getSolenoidVoltageFault()) System.out.println("Solenoid Voltage Fault");
    if (m_pcm.getSolenoidVoltageStickyFault()) System.out.println("Solenoid Voltage Sticky Fault");

    // m_pdp.clearStickyFaults();
    m_pcm.clearAllStickyFaults();
  }

  public void autonomousInit() {
    CommandScheduler.getInstance().schedule(new AutoCommand(m_robotDrive, m_shooter));
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
              m_robotDrive.arcadeDrive(-speed, rotation);
            },
            m_robotDrive));
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
    /**
     * Buton summary:
     * RB - gear toggle
     * LB - feed ball (not working I don't think)
     * POV Up - extend climbing arms
     * POV Down - retract climbing arms
     * Start - resets encoders, switches to low gear, and puts climbing motors into coast mode. (helpful for resetting robot after climb)
     */




    m_pneumButton
        .whenPressed(() -> {
          m_robotDrive.toggleGear();
        });

    m_PovUp_ClimberUp
        .whenPressed(() -> {
          m_climber.extend();
        })

        .whenReleased(() -> {
          m_climber.stop();
        });

    m_PovDown_ClimberDown
        .whenPressed(() -> {
          m_climber.retract();
        })

        .whenReleased(() -> {
          m_climber.stop();
        });

    m_feedButton
        .whenPressed(m_shootCommand);

    m_resetButton
        .whenPressed(() -> {
          m_robotDrive.reset();
          m_climber.reset();
        });

    m_flywheelButton
        .whenPressed(() -> {
          m_shooter.toggleFlywheel();
        }, m_shooter);
  }

  public void periodic()
  {
    SmartDashboard.putNumber("Controller: POV", m_controller.getPOV());

  }
}
