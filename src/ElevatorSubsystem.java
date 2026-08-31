package frc.robot;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.TalonFX;
import au.grapplerobotics.LaserCan;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
public class ElevatorSubsystem extends SubsystemBase {
    private final TalonFX elevatorMotor = new TalonFX(1);
    private final LaserCan distanceThing = new LaserCan(2);
    private final DigitalInput bottomLimit = new DigitalInput(3);
    private StatusSignal<Angle> motorPos = elevatorMotor.getPosition();
    private final double rotationsPerMM = 3;
    private final double totalHeightMM = 200;
    private final double scoringPosition = totalHeightMM * rotationsPerMM;

    public Command zeroElevator() {
        return run(() -> elevatorMotor.set(-0.2))
        .until(() -> bottomLimit.get())
        .finallyDo(() -> {
            elevatorMotor.set(0.0); 
            motorPos.refresh(); 
            elevatorMotor.setPosition(0);
            });
    }
    public boolean hasGamePiece() {
        return distanceThing.getMeasurement().distance_mm < 75;         
}
public double scoringPos() {
return scoringPosition;
}
public Command moveToScoreHeight() {
    return zeroElevator().andThen(
        run(() -> { motorPos.refresh(); elevatorMotor.set(0.2); })
   .until(()-> {motorPos.refresh(); motorPos.getValueAsDouble() >= scoringPosition;})
    .finallyDo(() -> {
       elevatorMotor.stopMotor(); 
        })
    );
}
}

