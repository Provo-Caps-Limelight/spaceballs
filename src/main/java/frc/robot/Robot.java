// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ComplexWidget;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;

  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  //private final Joystick joystick = new Joystick(0);
  private final XboxController xboxController = new XboxController(0);
  private DriveTrain driveTrain = DriveTrain.getInstance();
  private FeederWheel feederWheel = FeederWheel.getInstance();
  private Flywheel flywheel = Flywheel.getInstance();

 
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

    double drive_speed = 0.0;
    // double prev_speed = 0.0;
    @Override
    public void teleopPeriodic() {

        // if (!(prev_speed - 0.025 < drive_speed && prev_speed + 0.025 > drive_speed)) {
        //     if (drive_speed < prev_speed && prev_speed > 0) {
        //         drive_speed = prev_speed - 0.025;
        //     }
        //     else if (drive_speed > prev_speed && prev_speed < 0) {
        //         drive_speed = prev_speed + 0.025;
        //     }
        // }

        if (xboxController.getAButton()) {
          //TODO: I may have totally flipped one of these axes, add or get rid of any negatives as needed
          drive_speed = -xboxController.getLeftY();
          driveTrain.arcadeDrive(drive_speed, xboxController.getRightX() * 0.6);
        } 
        else if (xboxController.getBButton()) {
          //TODO: just trying an arbitrary value for now, change this if needed
          driveTrain.drivetrainAngleLineup(1);
          
          //TODO: possible auto shooting thing you could add
          /*if (Math.abs(LimelightVisionTracking.getInstance().getHorizontalAngle()) < 2) {
            flywheel.setFlywheelSpeed(.6);
            feederWheel.setFeederWheelSpeed(.4);
          }*/
        }

        flywheel.setFlywheelSpeed(xboxController.getLeftTriggerAxis());
        
        feederWheel.setFeederWheelSpeed(xboxController.getRightTriggerAxis());

        



        /*  if (joystick.getRawButton(8)) {
        // intake.on();
        // }
        // else if (joystick.getRawButton(7)) {
        // intake.reverse();
        // }
        // else {
        // intake.off();
        // }

        // // if (xboxController.getRightBumper()) {
        // //   rightIntake.set(1);
        // // }
        // // else {
        // //   rightIntake.set(0);
        // // }

        // if (xboxController.getRightTriggerAxis() > .1) {
        // // flywheelMotors.set(flywheelSpeed);
        // leftFlywheel.set(TalonFXControlMode.PercentOutput, .68);
        // // leftFlywheel.set(TalonFXControlMode.Velocity, 12500); 
        // }
        // else if (xboxController.getLeftTriggerAxis() > .1) {
        // leftFlywheel.set(TalonFXControlMode.PercentOutput, .60);
        // }
        // else {
        // leftFlywheel.set(TalonFXControlMode.PercentOutput, 0);
        // // leftFlywheel.set(TalonFXControlMode.Velocity, 0);
        // }

        // if (joystick.getRawButton(9) || xboxController.getPOV() == 180) {//climber down
        // leftClimber.set(TalonFXControlMode.PercentOutput, -.85);
        // }
        // else if (joystick.getRawButton(10) || xboxController.getPOV() == 0) {
        // leftClimber.set(TalonFXControlMode.PercentOutput, .85);
        // }
        // else {
        // leftClimber.set(TalonFXControlMode.PercentOutput, 0);
        // };

        // if (xboxController.getStartButtonPressed()) {
        // leftClimber.overrideLimitSwitchesEnable(true);
        // rightClimber.overrideLimitSwitchesEnable(true);
        // }

        // if (joystick.getRawButton(6)) {
        // // intake.out();     
        // intake.getActuator().set(-1);
        // }
        // else if (joystick.getRawButton(4)) {
        // // intake.in();
        // intake.getActuator().set(1);
        // }
        // else {
        //     intake.getActuator().set(0);
        // }

        */

  }

  @Override
  public void disabledInit() {
    
  }

  @Override
  public void disabledPeriodic() {
    
  }

  @Override
  public void testInit() {
    
  }
}
