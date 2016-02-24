package robot.util;

import edu.wpi.first.wpilibj.AnalogGyro;

/**
 * Improving the AnalogGyro class from WPILib.
 * 
 * @author Chase
 */
public class R_Gyro extends AnalogGyro {
	public R_Gyro(int port) {
		super(port);
	}

	@Override
	/**
	 * Takes the angle given by the gyro and modifies it to be (0, 360]
	 * 
	 * @return angle
	 * 				Returns the angle that the robot is facing.
	 */
	public double getAngle() {
		double angle = -super.getAngle() % 360;
		return (angle < 0) ? angle + 360 : angle;
	}
	
	/**
	 * Gives the number of degrees and the fastest direction of travel to get 
	 * to that angle, given the robot's current heading.
	 * 
	 * @param targetAngle 
	 * 					Angle that you want to find the direction and 
	 * 					degrees towards
	 * @return angle difference
	 * 					Returns the difference between the given angle
	 * 					and the angle that the robot is currently facing 
	 */
	public double getAngleDifference(double targetAngle) {
		double currentAngle = getAngle();
		double difference = targetAngle - currentAngle;
		
		if (difference > 180) {
			difference -= 360;
		} else if (difference < -180) {
			difference += 360;
		}
		
		return difference;
	}
}
