package frc.robot;

import java.sql.PseudoColumnUsage;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.XboxController;
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
    private final int axis_forwardBack = PS4Controller.Axis.kLeftY.value;
    private final int axis_rotate = PS4Controller.Axis.kRightX.value;
    private final GenericHID m_controller = new XboxController(0);
    private final JoystickButton m_boostButton = new JoystickButton(m_controller, PS4Controller.Button.kL1.value);
    private final JoystickButton m_dumpButton = new JoystickButton(m_controller, PS4Controller.Button.kCross.value);
    private final JoystickButton m_armUpButton = new JoystickButton(m_controller, PS4Controller.Button.kL2.value);
    private final JoystickButton m_armDownButton = new JoystickButton(m_controller, PS4Controller.Button.kR2.value);
    private final JoystickButton m_highGearButton = new JoystickButton(m_controller, PS4Controller.Button.kTriangle.value);
    private final JoystickButton m_lowGearButton = new JoystickButton(m_controller, PS4Controller.Button.kCircle.value);
    private final JoystickButton m_NOButton = new JoystickButton(m_controller, PS4Controller.Button.kTouchpad.value);

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

        m_NOButton
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
    }
  }
  