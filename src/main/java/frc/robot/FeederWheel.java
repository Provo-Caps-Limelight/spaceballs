
package frc.robot;


import edu.wpi.first.math.controller.DifferentialDriveAccelerationLimiter;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Joystick;



public class FeederWheel {
    private static FeederWheel instance;
    
    private TalonSRX talon = new TalonSRX(2); 
    
    private FeederWheel() {
        
    }

    public static FeederWheel getInstance() {
        if (instance == null) {
            instance = new FeederWheel();
        }
        return instance;
    }
   
    public void setFeederWheelSpeed(double speed) {
        //double stick = _Joystick.getRawAxis(1);
        talon.set(ControlMode.PercentOutput, -speed);

    }

    

    /*private static feederWheel instance;
        
        private final WPI_VictorSPX feederMotor = new WPI_VictorSPX(7);
        private final WPi_VictorSPX Belt = new WPI_VIctorSPX(8);
            
        private feederMotor
        feederMotor; burnFlash();
        // private feederWheel() {
        //feederMotor.restoreFactoryDefaults();
    
        
    
        public static feederWheel getInstance() {
            if (instance == null) {
                instance = new feederWheel();
            }
        }

        //public static void setfeederWheelSpeed(double rightTriggerAxis) {
        }
*/
}
