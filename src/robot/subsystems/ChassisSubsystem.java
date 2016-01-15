
package robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.R_Encoder;
import robot.R_Subsystem;
import robot.R_Talon;
import robot.RobotMap;
import robot.commands.JoystickCommand;

/**
 *
 */
public class ChassisSubsystem extends R_Subsystem {
	
	Talon leftMotor1 = new R_Talon(RobotMap.MotorMap.LEFT_MOTOR1);
	Talon rightMotor1 = new R_Talon(RobotMap.MotorMap.RIGHT_MOTOR1);
	Talon leftMotor2 = new R_Talon(RobotMap.MotorMap.LEFT_MOTOR2);
	Talon rightMotor2 = new R_Talon(RobotMap.MotorMap.RIGHT_MOTOR2);
	DigitalInput limitSwitch = new DigitalInput(RobotMap.SensorMap.LIMIT_SWITCH.port);
	Encoder leftEncoder = new R_Encoder(RobotMap.EncoderMap.LEFT);
	Encoder rightEncoder = new R_Encoder(RobotMap.EncoderMap.RIGHT);
	
	/*
	 * Motor PID Controllers
	 */
	/*R_PIDInput leftPIDInput = new R_PIDInput() {
		@Override
		public double pidGet() { return leftEncoder.getRate()/RobotMap.EncoderMap.LEFT.maxRate; }
		};
	
	R_PIDInput rightPIDInput = new R_PIDInput() {
		@Override
		public double pidGet() { return rightEncoder.getRate()/RobotMap.EncoderMap.RIGHT.maxRate;	}	
		};
		
	R_PIDController leftMotorPID = new R_PIDController(0.5, 0.0, 0.0, 1.0, leftPIDInput, leftMotor);
	
	R_PIDController rightMotorPID = new R_PIDController(0.5, 0.0, 0.0, 1.0, rightPIDInput, rightMotor); 

	List<R_PIDController> pidControllers = new ArrayList<R_PIDController>();
	PIDController p;
	// Gyro
	R_Gyro gyro = new R_Gyro(RobotMap.SensorMap.GYRO.port);

	public ChassisSubsystem() {
		pidControllers.add(leftMotorPID);
		pidControllers.add(rightMotorPID);
	}*/
	
	public void init() {
		/*gyro.initGyro();
		gyro.setSensitivity(0.00165);*/
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new JoystickCommand());
	}

	public void setSpeed(double leftSpeed, double rightSpeed) {
		if (!limitSwitch.get()) {
			leftSpeed = 0;
			rightSpeed = 0;
		}
		
		rightMotor1.set(rightSpeed);
		rightMotor2.set(rightSpeed);
		leftMotor1.set(leftSpeed);
		leftMotor2.set(leftSpeed);
		
		/*leftMotorPID.setSetpoint(leftSpeed);
		rightMotorPID.setSetpoint(rightSpeed);
		if (!leftMotorPID.isEnabled()) {
			leftMotorPID.enable();
		}
		if (!rightMotorPID.isEnabled()) {
			rightMotorPID.enable();
		}*/
	}

	/*public double getCurrentAngle() {
		return gyro.getAngle();
	}
	
	public double getAngleDifference(double targetAngle) {
		return gyro.getAngleDifference(targetAngle);
	}*/
	
	@Override
	public void periodic() {
		// Update all of the PIDs every loop
		/*for (R_PIDController pid : pidControllers) {
			pid.calculate();
		}*/
	}
	
	@Override
	public void updateDashboard() {
		SmartDashboard.putData("Left Motor 1", leftMotor1);
		SmartDashboard.putData("Right Motor 2", rightMotor1);
		SmartDashboard.putData("Left Motor 1", leftMotor1);
		SmartDashboard.putData("Right Motor 2", rightMotor1);
		SmartDashboard.putData("Limit Switch", limitSwitch);
		SmartDashboard.putData("Left Encoder", leftEncoder);
		SmartDashboard.putData("Right Encoder", rightEncoder);
		/*SmartDashboard.putData("Left Motor PID", leftMotorPID);
		SmartDashboard.putData("Right Motor PID", rightMotorPID);
		SmartDashboard.putData("Gyro", gyro);
		SmartDashboard.putNumber("Gyro Angle", gyro.getAngle());*/
	}
}
