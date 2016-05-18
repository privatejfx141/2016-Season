package robot.subsystems;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.R_PIDController;
import robot.R_PIDInput;
import robot.R_Subsystem;
import robot.RobotMap;
import robot.commands.JoystickChassisCommand;
import robot.util.R_Encoder;
import robot.util.R_Gyro;
import robot.util.R_Ultrasonic;
import robot.util.R_VictorSP;

public class ChassisSubsystem extends R_Subsystem {
	R_VictorSP leftMotor = new R_VictorSP(RobotMap.MotorMap.LEFT_MOTOR);
	R_VictorSP rightMotor = new R_VictorSP(RobotMap.MotorMap.RIGHT_MOTOR);
	DigitalInput leftProximitySensor = new DigitalInput(RobotMap.SensorMap.LEFT_PROXIMITY_SENSOR.port);
	DigitalInput rightProximitySensor = new DigitalInput(RobotMap.SensorMap.RIGHT_PROXIMITY_SENSOR.port);
	R_Encoder leftEncoder = new R_Encoder(RobotMap.EncoderMap.LEFT);
	R_Encoder rightEncoder = new R_Encoder(RobotMap.EncoderMap.RIGHT);
	R_Ultrasonic ultrasonicSensor = new R_Ultrasonic(RobotMap.SensorMap.ULTRASONIC.port);
	R_Gyro gyro = new R_Gyro(RobotMap.SensorMap.GYRO.port);

	R_PIDInput leftPIDInput = new R_PIDInput() {
		@Override
		public double pidGet() {
			return leftEncoder.getRate() / RobotMap.EncoderMap.LEFT.maxRate;
		}
	};

	R_PIDInput rightPIDInput = new R_PIDInput() {
		@Override
		public double pidGet() {
			return -rightEncoder.getRate() / RobotMap.EncoderMap.RIGHT.maxRate;
		}
	};

	R_PIDController leftMotorPID = new R_PIDController(1.7, 0.0, 0.0, 1.0, leftPIDInput, leftMotor);
	R_PIDController rightMotorPID = new R_PIDController(1.7, 0.0, 0.0, 1.0, rightPIDInput, rightMotor);

	ArrayList<R_PIDController> pidControllers = new ArrayList<>();

	public void init() {
		pidControllers.add(leftMotorPID);
		pidControllers.add(rightMotorPID);

		gyro.initGyro();
		gyro.setSensitivity(0.00165);
		gyro.calibrate();
	}

	public void initDefaultCommand() {
		setDefaultCommand(new JoystickChassisCommand());
	}

	/**
	 * Set the speed for the robot to drive at.
	 * 
	 * @param leftSpeed speed of the right side of the drive train
	 * @param rightSpeed speed of the left side of the drive train
	 */
	public void setSpeed(double leftSpeed, double rightSpeed) {
		SmartDashboard.putNumber("LeftMotorSpeed", leftSpeed);
		SmartDashboard.putNumber("RightMotorSpeed", rightSpeed);

		leftMotorPID.setSetpoint(leftSpeed);
		rightMotorPID.setSetpoint(rightSpeed);

		if (!leftMotorPID.isEnabled()) {
			leftMotorPID.enable();
		}
		if (!rightMotorPID.isEnabled()) {
			rightMotorPID.enable();
		}
	}

	/**
	 * Get the current angle from the gyro, in respect to it's starting 
	 * position.
	 * 
	 * @return the current heading of the robot.
	 */
	public double getCurrentAngle() {
		return gyro.getAngle();
	}

	/**
	 * Get the difference, in degrees, of the current heading to the target angle.
	 * Also gives the fastest direction of rotation to the angle, with a negative
	 * return meaning counter-clockwise rotation is faster, and a positive meaning 
	 * clockwise rotation is faster.
	 * 
	 * @param targetAngle angle you want to get the difference to
	 * @return degrees to, and direction of, fastest rotation to the given angle.
	 */
	public double getAngleDifference(double targetAngle) {
		return gyro.getAngleDifference(targetAngle);
	}

	/**
	 * Check if either of the robot's proximity sensors are activated.
	 * 
	 * @return whether either proximity sensor is active.
	 */
	public boolean getProximity() {
		boolean proximity = !leftProximitySensor.get() || !rightProximitySensor.get();
		SmartDashboard.putBoolean("Proximity Sensor(s) active", proximity);
		return proximity;
	}

	/**
	 * Retrieve the distance off of the ultrasonic sensor.
	 * 
	 * @return the distance from the back of the robot to an object behind it
	 */
	public double getUltrasonicDistance() {
		return this.ultrasonicSensor.getDistance();
	}

	@Override
	public void periodic() {
		// Update all of the PIDs every loop
		for (R_PIDController pid : pidControllers) {
			pid.calculate();
		}
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
	 * Resets the encoder distance.
	 */
	public void resetEncoders() {
		this.leftEncoder.reset();
		this.rightEncoder.reset();
	}

	/**
	 * Reset the ultrasonic sensor's filter.
	 */
	public void resetUltrasonicSensorFilter() {
		ultrasonicSensor.reset();
	}

	/**
	 * Reset the gyro heading.
	 */
	public void resetGyro() {
		gyro.reset();
	}
	
	/**
	 * Calibrate the gyro. This also resets the heading.
	 */
	public void calibrateGyro() {
		gyro.calibrate();
	}

	@Override
	public void updateDashboard() {
		SmartDashboard.putData("Left Motor", leftMotor);
		SmartDashboard.putData("Right Motor", rightMotor);
		SmartDashboard.putBoolean("Left Limit Switch", !leftProximitySensor.get());
		SmartDashboard.putBoolean("Right Limit Switch", !rightProximitySensor.get());
		SmartDashboard.putData("Left Encoder", leftEncoder);
		SmartDashboard.putData("Right Encoder", rightEncoder);
		SmartDashboard.putData("Left Motor PID", leftMotorPID);
		SmartDashboard.putData("Right Motor PID", rightMotorPID);
		SmartDashboard.putData("Gyro", gyro);
		SmartDashboard.putNumber("Gyro Angle", gyro.getAngle());
		SmartDashboard.putNumber("Ultrasonic Sensor Distance", ultrasonicSensor.getDistance());
		SmartDashboard.putNumber("Raw ultrasonic sensor voltage", ultrasonicSensor.getVoltage());
	}
}
