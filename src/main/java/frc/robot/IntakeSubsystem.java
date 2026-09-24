package frc.robot;

import com.ctre.phoenix6.hardware.CANrange;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {
private final TalonFX intakeMotor = new TalonFX(8);
 private final TalonFX flyWheel = new TalonFX(9);
    private final CANrange distanceThing = new CANrange(6);

     public Command checkAndGrab() {
return run(() -> {
    if (distanceThing.getDistance().getValueAsDouble() < 0.2) {
        intakeMotor.set(0.5);
    } else {
        intakeMotor.set(0.0);
    }
}).finallyDo(() -> intakeMotor.set(0.0));
}
public Command outTake() { // i was bored in class so i made like 4 intake/outtake commands
  return run(() -> intakeMotor.set(-0.5)).finallyDo(() -> intakeMotor.set(0.0));
}
public Command fastOutTake() {
  return run(() -> intakeMotor.set(-0.75)).finallyDo(() -> intakeMotor.set(0.0));
}
public Command stopIntake() {
  return runOnce(() -> intakeMotor.set(0.0));
}
public Command shooter() {
  return run(() -> flyWheel.set(1.0)).finallyDo(() -> flyWheel.set(0.0));
}
public Command fullOuttakeJam() {
  return run(() -> {
    flyWheel.set(-0.5); intakeMotor.set(-0.5); 
  }).finallyDo(() -> {
    flyWheel.set(0.0); intakeMotor.set(0.0);
  });
}
}
