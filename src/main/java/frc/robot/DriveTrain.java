package frc.robot;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;

public class DriveTrain {
    private static DriveTrain instance;

    // Physical components
    private DifferentialDrive differentialDrive;
    //private LimelightVisionTracking limelight = LimelightVisionTracking.getInstance();
    private final CANSparkMax leftMotor1 = new CANSparkMax(3, MotorType.kBrushless);
    private final CANSparkMax leftMotor2 = new CANSparkMax(2, MotorType.kBrushless);
    private final CANSparkMax rightMotor1 = new CANSparkMax(4, MotorType.kBrushless);
    private final CANSparkMax rightMotor2 = new CANSparkMax(1, MotorType.kBrushless);
    private final MotorControllerGroup leftMotors = new MotorControllerGroup(leftMotor1, leftMotor2);
    private final MotorControllerGroup rightMotors = new MotorControllerGroup(rightMotor1, rightMotor2);
    
    // constant: limits how fast the motors can accelerate? (William fact check this plz)
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
    public static DriveTrain getInstance() {
        if (instance == null) {
            instance = new DriveTrain();
        }
        return instance;
    }

    public void arcadeDrive(double forwardSpeed, double turningRate) {
        //prev_speed = drive_speed;
        //drive_speed = forwardSpeed;

        // drive speed limiting stuff goes here if needed

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
    
}


