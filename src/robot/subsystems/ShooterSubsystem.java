package robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.R_Subsystem;
import robot.R_Talon;
import robot.RobotMap;
import robot.commands.JoystickCommand;

/**
 *
 */
public class ShooterSubsystem extends R_Subsystem {
	R_Talon shooterMotor = new R_Talon(RobotMap.MotorMap.LEFT_SHOOTER_MOTOR);
	DigitalInput shooterLimitSwitch = new DigitalInput(RobotMap.SensorMap.SHOOTER_LIMIT_SWITCH.port);

	public void init() {
	}

	//FIXME This will need to be CHANGED!
	public void initDefaultCommand() {
		setDefaultCommand(new JoystickCommand());
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
