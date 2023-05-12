package frc.robot;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;



import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ComplexWidget;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistribution;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.ADIS16470_IMU.CalibrationTime;
import edu.wpi.first.wpilibj.ADIS16470_IMU.IMUAxis;


public class DriveTrain {


    // Physical components
    private DifferentialDrive differentialDrive;
    private LimelightVisionTracking limelight = LimelightVisionTracking.getInstance();
    private final CANSparkMax leftMotor1 = new CANSparkMax(3, MotorType.kBrushless);
    private final CANSparkMax leftMotor2 = new CANSparkMax(2, MotorType.kBrushless);
    private final CANSparkMax rightMotor1 = new CANSparkMax(4, MotorType.kBrushless);
    private final CANSparkMax rightMotor2 = new CANSparkMax(1, MotorType.kBrushless);
    private final MotorControllerGroup leftMotors = new MotorControllerGroup(leftMotor1, leftMotor2);
    private final MotorControllerGroup rightMotors = new MotorControllerGroup(rightMotor1, rightMotor2);
    
    private static DriveTrain instance;

    private static ADIS16470_IMU IMU = new ADIS16470_IMU();
    
 
    private final double driveRampRate = 0.5;
    private DriveTrain() {
        leftMotor1.restoreFactoryDefaults();
        leftMotor2.restoreFactoryDefaults();
        rightMotor1.restoreFactoryDefaults();
        rightMotor2.restoreFactoryDefaults();

        leftMotor1.burnFlash();
        leftMotor2.burnFlash();
        rightMotor1.burnFlash();
        rightMotor2.burnFlash();

        differentialDrive = new DifferentialDrive(leftMotors, rightMotors);
    }
  
    public static DriveTrain getInstance2() {
        if (instance == null) {
            instance = new DriveTrain();
        }
        return instance;
    }

    public void arcadeDrive(double forwardSpeed, double turningRate) {
    
        differentialDrive.arcadeDrive(forwardSpeed * 0.6, turningRate * 0.6);

    }
    public void fullBrake() { // will be helpful with the seesaw auto
        leftMotor1.setIdleMode(IdleMode.kBrake);
        leftMotor2.setIdleMode(IdleMode.kBrake);
        rightMotor1.setIdleMode(IdleMode.kBrake);
        rightMotor2.setIdleMode(IdleMode.kBrake);
    }

    public void brake() {
        leftMotor1.setIdleMode(IdleMode.kBrake);
        leftMotor2.setIdleMode(IdleMode.kCoast);
        rightMotor1.setIdleMode(IdleMode.kBrake);
        rightMotor2.setIdleMode(IdleMode.kCoast);
    }
    
    public void coast() {
        leftMotor1.setIdleMode(IdleMode.kCoast);
        leftMotor2.setIdleMode(IdleMode.kCoast);
        rightMotor1.setIdleMode(IdleMode.kCoast);
        rightMotor2.setIdleMode(IdleMode.kCoast);
    }


 
    private double xP;
    private final double MIN_POWER = 0.04;
    private final double TURN_MIN_ANGLE_DEGREES = 1;
      


    private static double kP = 0.01;
    private static double turningkP = 0.009;

    public void safeArcade(double speed, double turn){arcadeDrive(speed, turn);}

    public void drivetrainAngleLineup(double xSpeed) {
        kP = SmartDashboard.getNumber("drivetrain_kP", 0);
        double outputTurn = 0.0;
        double outputSpeed = 0.0;
        double tx = limelight.getHorizontalAngle();
        double distance = limelight.getDistance();

        outputTurn = tx * kP;
        if (limelight.targetFound()) {
            outputSpeed = (60) * 0.01; // shooter calculator needs to be made into a integer
        }
        else {
            outputSpeed = xSpeed;
        }

       
        if (inRange(tx, TURN_MIN_ANGLE_DEGREES, TURN_MIN_ANGLE_DEGREES)) {
            outputTurn = 0;
        }

        arcadeDrive(outputSpeed, outputTurn);
        SmartDashboard.putNumber("Limelight HorizontalAngle",  limelight.getHorizontalAngle());
    }

    public double getCurrentAngle() {return IMU.getAngle();}

    public void drivetrainTurn(double xSpeed, double targetAngle){
        turningkP = SmartDashboard.getNumber("turn_kP", 0);

        double turnPower = -(getCurrentAngle() - targetAngle) * turningkP;        /////////////

         if (inRange(turnPower, -MIN_POWER, MIN_POWER)) {
            turnPower = MIN_POWER * Math.signum(turnPower);
        }

        if (inRange(getCurrentAngle() - targetAngle, -TURN_MIN_ANGLE_DEGREES, TURN_MIN_ANGLE_DEGREES)) {
            turnPower = 0;
        }

        if (turnPower > 0.5){
            turnPower = 0.5;
        } else if (turnPower < -0.5){
            turnPower = -0.5;
        }
        arcadeDrive(xSpeed, turnPower);
    }

        

        private boolean inRange(double input, double low, double high
        ){
            if((input >=low )&&(input <=high )){return true;}
            else {return false;}

        }
}





