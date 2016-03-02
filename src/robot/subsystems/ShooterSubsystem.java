package robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.R_Subsystem;
import robot.RobotMap;
import robot.commands.JoystickShootCommand;
import robot.util.R_VictorSP;

/**
 *
 */
public class ShooterSubsystem extends R_Subsystem {
	R_VictorSP shooterMotor = new R_VictorSP(RobotMap.MotorMap.INTAKE_MOTOR);
	DigitalInput shooterLimitSwitch = new DigitalInput(RobotMap.SensorMap.SHOOTER_LIMIT_SWITCH.port);

	public void init() {
	}

	public void initDefaultCommand() {
		setDefaultCommand(new JoystickShootCommand());	
	}
	
	/**
	 * Set the speed of the ball intake system.
	 * 
	 * @param speed the intake should spin at.
	 */
	public void setSpeed(double speed) {
		shooterMotor.set(speed);
		SmartDashboard.putNumber("ShooterMotor", speed);
	}
	
	/**
	 * Check if a ball is inside of the ball shooter
	 * 
	 * @return whether or not a ball is present.
	 */
	public boolean isBallIn(){
		return !shooterLimitSwitch.get();
	}

	@Override
	public void periodic() {
	}
	
	@Override
	public void updateDashboard() {
	}
}
