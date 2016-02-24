package robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.R_Subsystem;
import robot.RobotMap;
import robot.commands.JoystickShootCommand;
import robot.util.R_Victor;

/**
 *
 */
public class ShooterSubsystem extends R_Subsystem {
	R_Victor shooterMotor = new R_Victor(RobotMap.MotorMap.INTAKE_MOTOR);
	DigitalInput shooterLimitSwitch = new DigitalInput(RobotMap.SensorMap.SHOOTER_LIMIT_SWITCH.port);

	public void init() {
	}

	public void initDefaultCommand() {
		setDefaultCommand(new JoystickShootCommand());	
	}

	public void shooterWheelSpeed(double leftSpeed, double rightSpeed) {
		
	}
	
	public void setSpeed(double speed) {
		shooterMotor.set(speed);
		SmartDashboard.putNumber("ShooterMotor", speed);
	}
	
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
