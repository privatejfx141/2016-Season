package robot.commands.auto.base;

import robot.Robot;

public class DriveToProximity extends AutoGoStraightCommand {
	private double speedSetpoint;

	/**
	 * Drive (using the AutoGoStraightCommand) until the proximity 
	 * sensors at the front of the robot are triggered. 
	 * 
	 * @param speed
	 * 			Speed the robot should drive at
	 * @param angle
	 * 			Angle the robot should drive at
	 */
	public DriveToProximity(double speed, double angle) {
		super(angle);
		this.speedSetpoint = speed;
	}

	@Override
	protected void initialize() {
		super.initialize();
		setSpeed(speedSetpoint, Direction.FORWARD);
	}

	@Override
	protected boolean isFinished() {
		return Robot.chassisSubsystem.getProximity();
	}	
}
