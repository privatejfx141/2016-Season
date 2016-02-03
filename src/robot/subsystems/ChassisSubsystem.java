
package robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.R_Encoder;
import robot.R_Gyro;
import robot.R_PIDController;
import robot.R_PIDInput;
import robot.R_Subsystem;
import robot.R_Talon;
import robot.R_Ultrasonic;
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
	Encoder leftEncoder = new R_Encoder(RobotMap.EncoderMap.LEFT);
	Encoder rightEncoder = new R_Encoder(RobotMap.EncoderMap.RIGHT);
	DigitalInput leftLimitSwitch = new DigitalInput(RobotMap.SensorMap.LEFT_LIMIT_SWITCH.port);
	DigitalInput rightLimitSwitch = new DigitalInput(RobotMap.SensorMap.RIGHT_LIMIT_SWITCH.port);
	R_Ultrasonic ultrasonic = new R_Ultrasonic(RobotMap.SensorMap.ULTRASONIC.port);

	/*
	 * Motor PID Controllers
	 */
	R_PIDInput leftPIDInput = new R_PIDInput() {
		@Override
		public double pidGet() {
			return leftEncoder.getRate() / RobotMap.EncoderMap.LEFT.maxRate;
		}
	};

	R_PIDInput rightPIDInput = new R_PIDInput() {
		@Override
		public double pidGet() {
			return rightEncoder.getRate() / RobotMap.EncoderMap.RIGHT.maxRate;
		}
	};

	R_PIDController leftMotor1PID = new R_PIDController(0.5, 0.0, 0.0, 1.0, leftPIDInput, leftMotor1);
	R_PIDController leftMotor2PID = new R_PIDController(0.5, 0.0, 0.0, 1.0, leftPIDInput, leftMotor2);
	R_PIDController rightMotor1PID = new R_PIDController(0.5, 0.0, 0.0, 1.0, rightPIDInput, rightMotor1);
	R_PIDController rightMotor2PID = new R_PIDController(0.5, 0.0, 0.0, 1.0, rightPIDInput, rightMotor2);

	List<R_PIDController> pidControllers = new ArrayList<R_PIDController>();
	PIDController p;
	// Gyro
	R_Gyro gyro = new R_Gyro(RobotMap.SensorMap.GYRO.port);

	public void init() {
		pidControllers.add(leftMotor1PID);
		pidControllers.add(leftMotor2PID);
		pidControllers.add(rightMotor1PID);
		pidControllers.add(rightMotor2PID);

		gyro.initGyro();
		gyro.setSensitivity(0.00165 * (360.0 / 365.0));
		gyro.calibrate();
	}

	public void initDefaultCommand() {
		setDefaultCommand(new JoystickCommand());
	}

	public void setSpeed(double leftSpeed, double rightSpeed) {
		leftMotor1PID.setSetpoint(leftSpeed);
		leftMotor2PID.setSetpoint(leftSpeed);
		rightMotor1PID.setSetpoint(rightSpeed);
		rightMotor2PID.setSetpoint(rightSpeed);

		if (!leftMotor1PID.isEnabled()) {
			leftMotor1PID.enable();
		}
		if (!leftMotor2PID.isEnabled()) {
			leftMotor2PID.enable();
		}
		if (!rightMotor1PID.isEnabled()) {
			rightMotor1PID.enable();
		}
		if (!rightMotor2PID.isEnabled()) {
			rightMotor2PID.enable();
		}
	}

	public double getCurrentAngle() {
		return gyro.getAngle();
	}

	public double getAngleDifference(double targetAngle) {
		return gyro.getAngleDifference(targetAngle);
	}

	public boolean getFrontLimit() {
		boolean frontLimit = !rightLimitSwitch.get() || !leftLimitSwitch.get();
		SmartDashboard.putBoolean("Front Limit", frontLimit);
		return frontLimit;
	}

	public double getUltraSonicDistance() {
		return this.ultrasonic.getDistance();
	}

	/**
	 * Gets the approximate distance using encoder counts by averaging the two
	 * encoder distances.
	 * 
	 * @return the approximate distance.
	 */
	public double getEncoderDistance() {
		return (this.leftEncoder.getDistance() - this.rightEncoder.getDistance()) / 2.0
				/ RobotMap.EncoderMap.LEFT.countsPerInch;
	}

	/**
	 * Resets the encoders.
	 */
	public void resetEncoders() {
		this.leftEncoder.reset();
		this.rightEncoder.reset();
	}

	public void resetGyro() {
		gyro.reset();
	}

	@Override
	public void periodic() {
		// Update all of the PIDs every loop
		for (R_PIDController pid : pidControllers) {
			pid.calculate();
		}
	}

	@Override
	public void updateDashboard() {
		SmartDashboard.putData("Left Motor 1", leftMotor1);
		SmartDashboard.putData("Right Motor 2", rightMotor1);
		SmartDashboard.putData("Left Motor 1", leftMotor1);
		SmartDashboard.putData("Right Motor 2", rightMotor1);
		SmartDashboard.putData("Left Encoder", leftEncoder);
		SmartDashboard.putData("Right Encoder", rightEncoder);
		SmartDashboard.putData("Left Motor PID", leftMotor1PID);
		SmartDashboard.putData("Left Motor PID", leftMotor2PID);
		SmartDashboard.putData("Right Motor PID", rightMotor1PID);
		SmartDashboard.putData("Right Motor PID", rightMotor2PID);
		SmartDashboard.putData("Gyro", gyro);
		SmartDashboard.putNumber("Gyro Angle", gyro.getAngle());
	}
}
