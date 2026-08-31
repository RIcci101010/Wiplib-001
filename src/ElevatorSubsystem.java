package frc.robot;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.CANdi;
import com.ctre.phoenix6.hardware.CANrange;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
public class ElevatorSubsystem extends SubsystemBase {
    private final TalonFX elevatorMotor = new TalonFX(5);
    private final CANrange distanceThing = new CANrange(6);
    private final CANdi bottomLimit = new CANdi(7);
    private StatusSignal<Angle> motorPos = elevatorMotor.getPosition();
    private final double rotationsPerInch = 0.5; // rotations per inch
    private final double totalHeight = 30; // shooting heigh in inches

public double getHeightInches() { // calcuates bots height in inches i hope
  motorPos.refresh();
  return motorPos.getValueAsDouble() / rotationsPerInch;
}
    public Command zeroElevator() { // zeros motors 
        return run(() -> elevatorMotor.set(-0.2))
        .until(() -> bottomLimit.getS1Closed.getValue())
        .finallyDo(() -> {
            elevatorMotor.set(0.0); 
            motorPos.refresh(); 
            elevatorMotor.setPosition(0);
            });
    }
public Command MoveToScore() {
return run(() -> elevatorMotor.set(0.2)).until(getHeightInches() >= totalHeight).finallyDo(() -> elevatorMotor.set(0.0));
}

}
