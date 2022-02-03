// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

// all of these are the "packages" or "libraries" that this code is using
// we do this to avoid naming problems
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  private final int axis_forwardBack = 1;
  private final int axis_rotate = 2;
  private final int axis_throttle = 3;
  public static CANSparkMax m_leftMotor = new CANSparkMax(1, MotorType.kBrushed);
  public static CANSparkMax m_leftFollower = new CANSparkMax(2, MotorType.kBrushed);
  public static CANSparkMax m_rightMotor = new CANSparkMax(3, MotorType.kBrushed);
  public static CANSparkMax m_rightFollower = new CANSparkMax(4, MotorType.kBrushed);  
  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_leftMotor, m_rightMotor);
  private final GenericHID m_controller = new Joystick(0);
  private final Timer m_timer = new Timer();
  private final JoystickButton m_boostButton = new JoystickButton(m_controller, 1);
  private final JoystickButton m_spinLeftButton = new JoystickButton(m_controller, 3);
  private final JoystickButton m_spinRightButton = new JoystickButton(m_controller, 4);

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.

    m_leftFollower.follow(m_leftMotor);
    m_rightFollower.follow(m_rightMotor);
    m_leftMotor.setInverted(true);

  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    m_timer.reset();
    m_timer.start();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
    if (m_timer.get() < 2.0) {
      m_robotDrive.arcadeDrive(0.5, 0.0); // drive forwards half speed
    } else {
      m_robotDrive.stopMotor(); // stop robot
    }
  }

  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {
  }

  /** This function is called periodically (about every 20 milliseconds) during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    // driveFactor is a 0 to 1 value from the throttle axis on the joystick.
    // I did this partially because I wanted to see if I could, and partially because
    // it was too touchy.
    double driveFactor = 
      (m_controller.getRawAxis(axis_throttle) // will be -1 to 1
      * 1 // reverse (forward is faster). When I first checked this, forward was -1, and back was 1, which is the opposite of what we want
      + -1) // 0 to 2. shift it to the positive
      / 2; // 0 to 1. divide it in half so that it will be 0 to 1

      System.out.print(driveFactor);

    if (m_spinRightButton.get()) {
      // if the spin right button is being pressed, whip around to the right
      m_robotDrive.arcadeDrive(0, 0.5);
    } else if (m_spinLeftButton.get()) {
      // if the spin left button is being pressed, whip around to the left
      m_robotDrive.arcadeDrive(0, -0.5);
    } else {
      // if no spin button is pressed

      if (m_boostButton.get()) {
        // if the boost button (trigger) is pressed, then override that drive factor all the way up to 1 for full speed.
        driveFactor = 1;
      }

      double speed = m_controller.getRawAxis(axis_forwardBack) * driveFactor;
      double rotation = m_controller.getRawAxis(axis_rotate) * driveFactor * 0.5; // multiplying by 0.5 because otherwise it's too touchy.
      m_robotDrive.arcadeDrive(speed, rotation);
    }

  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
