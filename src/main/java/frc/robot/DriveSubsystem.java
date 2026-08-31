package frc.robot;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.controls.DutyCycleOut;
public class DriveSubsystem extends SubsystemBase {
  
    private final TalonFX right1 = new TalonFX(1);
    private final TalonFX left1 = new TalonFX(2);
private final TalonFX right2 = new TalonFX(3);
private final TalonFX left2 = new TalonFX(4);

  public void drive(double y, double x) { 
    if (Math.abs(y) < 0.05) {
        y = 0.0;
        }
        if (Math.abs(x) < 0.05) {
            x = 0.0;
        }
        double rightspeed = y - x;
       double leftspeed = y + x;
        right1.setControl(new DutyCycleOut(rightspeed));
            left1.setControl(new DutyCycleOut(leftspeed));
           right2.setControl(new DutyCycleOut(rightspeed));
           left2.setControl(new DutyCycleOut(leftspeed));
    }
}
