package robot.commands.auto;

import robot.Robot;

public class DriveToUltraDistance extends AutoGoStraightCommand {


	/**
	 * The distance to drive to.
	 */
	private double distanceSetpoint;

	/**
	 * The constructor for a new DriveToDistance command.
	 * 
	 * @param speed
	 *            The speed at which to drive.
	 * @param angle
	 *            The angle to drive at (in degrees).
	 * @param distance
	 *            The distance to drive to.
	 */
	public DriveToUltraDistance(double speed, double angle, double distance) {
		super(speed, angle);
		this.distanceSetpoint = distance;
	}

	/**
	 * Gets the distance set point.
	 * 
	 * @return the distance set point.
	 */
	public double getDistance() {
		return distanceSetpoint;
	}

	// Called once after isFinished returns true
	protected boolean isFinished() {
		
		// Stop 4in early because it takes the robot 4 inches to stop.
		return (distanceSetpoint-4.0 <= Robot.chassisSubsystem.getUltraSonicDistance());
	}

}