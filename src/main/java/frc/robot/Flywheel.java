package frc.robot;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;

public class Flywheel {

    private static Flywheel instance;
    
    private final CANSparkMax rightMotor = new CANSparkMax(5, MotorType.kBrushless);
    private final CANSparkMax leftMotor = new CANSparkMax(6, MotorType.kBrushless);



    private Flywheel() {
        rightMotor.restoreFactoryDefaults();
        leftMotor.restoreFactoryDefaults();

        leftMotor.burnFlash();
        rightMotor.burnFlash();

        leftMotor.follow(rightMotor, true);

    }

    public static Flywheel getInstance() {
        if (instance == null) {
            instance = new Flywheel();
        }
        return instance;
    }


    public void setFlywheelSpeed(double speed) {
        rightMotor.set(speed);
    }

// feederWheel.setfeederWheelSpeed(xboxController.getRightTriggerAxis());

}

