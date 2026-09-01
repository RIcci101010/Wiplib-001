package frc.robot;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.CANdi;
import com.ctre.phoenix6.hardware.CANrange;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.PositionVoltage;

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
    TalonFXConfiguration configs = new TalonFXConfiguration();

    public ElevatorSubsystem() {
        configs.slot0.kP = 1.1;
        configs.slot0.kI = 0.5;
        configs.slot0.kD = 2.0;
        configs.slot0.kF = 0.3;
        elevatorMotor.getConfigurator().apply(configs);
    }
private final PositionVoltage m_request = new PositionVoltage(0).withSlot(0);

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
    return run(() -> elevatorMotor.setControl(
      m_request.setControl(totalHeight * rotationsPerInch)));
      
    
    // .until(getHeightInches() >= totalHeight)   i dont think i need these anymore, shouldnt pid just keep then at set point.
       // .finallyDo(() -> elevatorMotor.set(0.0));
  }

}
