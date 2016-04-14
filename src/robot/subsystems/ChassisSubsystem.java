package robot.subsystems;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.R_PIDController;
import robot.R_PIDInput;
import robot.R_Subsystem;
import robot.RobotMap;
import robot.commands.JoystickChassisCommand;
import robot.util.R_Encoder;
import robot.util.R_VictorSP;

public class ChassisSubsystem extends R_Subsystem {
	R_VictorSP leftMotor = new R_VictorSP(RobotMap.MotorMap.LEFT_MOTOR);
	R_VictorSP rightMotor = new R_VictorSP(RobotMap.MotorMap.RIGHT_MOTOR);
	R_Encoder leftEncoder = new R_Encoder(RobotMap.EncoderMap.LEFT);
	R_Encoder rightEncoder = new R_Encoder(RobotMap.EncoderMap.RIGHT);

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

	@Override
	public void updateDashboard() {
		SmartDashboard.putData("Left Motor", leftMotor);
		SmartDashboard.putData("Right Motor", rightMotor);
		SmartDashboard.putData("Left Encoder", leftEncoder);
		SmartDashboard.putData("Right Encoder", rightEncoder);
		SmartDashboard.putData("Left Motor PID", leftMotorPID);
		SmartDashboard.putData("Right Motor PID", rightMotorPID);
	}
}
