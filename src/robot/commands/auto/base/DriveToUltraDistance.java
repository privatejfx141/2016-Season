package robot.commands.auto.base;

import robot.Robot;

public class DriveToUltraDistance extends AutoGoStraightCommand {

	/**
	 * The distance to drive to.
	 */
	private double distanceSetpoint;

	private double speedSetpoint;

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
		super(angle);
		this.speedSetpoint = speed;
		this.distanceSetpoint = distance;
	}

	protected void initialize() {
		super.initialize();
		Robot.chassisSubsystem.resetUltrasonicSensorFilter();
		if (distanceSetpoint - Robot.chassisSubsystem.getUltrasonicDistance() < 0) {
			setSpeed(-speedSetpoint, Direction.BACKWARD);
		} else {
			setSpeed(speedSetpoint, Direction.FORWARD);
		}
	}

	/**
	 * Gets the distance set point.
	 * 
	 * @return the distance set point.
	 */
	public double getDistanceSetpoint() {
		return distanceSetpoint;
	}

	// Called once after isFinished returns true
	protected boolean isFinished() {
		// Stop 4in early because it takes the robot 4 inches to stop.
		return (Math.abs(distanceSetpoint - Robot.chassisSubsystem.getUltrasonicDistance()) <= 4);

	}
}