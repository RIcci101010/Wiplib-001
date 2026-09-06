package frc.robot;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.CANdi;
import com.ctre.phoenix6.hardware.CANrange;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.controls.MotionMagicVoltage;

public class ElevatorSubsystem extends SubsystemBase {
    private final TalonFX elevatorMotor = new TalonFX(5);
    private final TalonFX intakeMotor = new TalonFX(8);
    private final CANrange  distanceThing = new CANrange(6);
    private final CANdi bottomLimit = new CANdi(7);
    private StatusSignal<Angle> motorPos = elevatorMotor.getPosition();
    private final double rotationsPerInch = 0.5; // rotations per inch
    private final double totalHeight = 30; // shooting heigh in inches
    private final TalonFXConfiguration configs = new TalonFXConfiguration();

    public ElevatorSubsystem() {
        configs.Slot0.kP = 1.1;
        configs.Slot0.kI = 0.5;
        configs.Slot0.kD = 2.0;
        configs.MotionMagic.MotionMagicCruiseVelocity = 80;
        configs.MotionMagic.MotionMagicAcceleration = 160;
        configs.MotionMagic.MotionMagicJerk = 1600;

        elevatorMotor.getConfigurator().apply(configs);
    }
    private final MotionMagicVoltage m_request = new MotionMagicVoltage(0).withSlot(0);

public double getHeightInches() { // calcuates bots height in inches i hope
  motorPos.refresh();
  return motorPos.getValueAsDouble() / rotationsPerInch;
}
    public Command zeroElevator() { // zeros motors 
        return run(() -> elevatorMotor.set(-0.2))
          .until(() -> bottomLimit.getS1Closed().getValue() == true)
          .finallyDo(() -> {
            elevatorMotor.set(0.0); 
            motorPos.refresh(); 
            elevatorMotor.setPosition(0);
          });
}
public Command MoveToScore() {
    return run(() -> elevatorMotor.setControl(
      m_request.withPosition(totalHeight * rotationsPerInch)));
  
  }

  public Command checkAndGrab() {
return run(() -> {
    if (distanceThing.getDistance().getValueAsDouble() < 0.2) {
        intakeMotor.set(0.5);
    } else {
        intakeMotor.set(0.0);
    }
}).finallyDo(() -> intakeMotor.set(0.0));
}
}