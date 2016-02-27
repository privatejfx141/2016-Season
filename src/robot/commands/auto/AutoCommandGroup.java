package robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import robot.Robot;
import robot.Field.Defense;
import robot.Field.Goal;
import robot.Field.Lane;
import robot.Field.Slot;
import robot.commands.RotateToAngle;
import robot.commands.auto.base.DriveToDistance;
import robot.commands.auto.base.DriveToProximity;
import robot.commands.auto.base.DriveToUltraDistance;
import robot.commands.auto.base.ShootBallCommand;
import robot.commands.auto.base.WaitUntilPathClear;

public class AutoCommandGroup extends CommandGroup {
	public AutoCommandGroup(Slot slot, Defense defense, Lane lane, Goal goal) {
		System.out.println("Constructing Auto Command");
		double normSpeed = 0.25;
		double highSpeed = normSpeed * 2;
		double waitTime = 4.0;
		
		// Make sure the robot has set it's forward facing direction as 0 deg
		Robot.chassisSubsystem.resetGyro();
		
		// Figure out what defense we're driving over
		switch (defense) {
			case LOW_BAR:
				addSequential(new DriveToDistance(normSpeed, 0, Defense.LOW_BAR.getDriveDistance()));
				break;
			case MOAT:
				addSequential(new DriveToDistance(highSpeed, 0, Defense.MOAT.getDriveDistance()));
				break;
			case RAMPARTS:
				addSequential(new DriveToDistance(highSpeed, 0, Defense.RAMPARTS.getDriveDistance()));
				break;
			case ROCK_WALL:
				addSequential(new DriveToDistance(highSpeed, 0, Defense.ROCK_WALL.getDriveDistance()));
				break;
			case ROUGH_TERRAIN:
				addSequential(new DriveToDistance(highSpeed, 0, Defense.ROUGH_TERRAIN.getDriveDistance()));
				break;
			case PORTCULLIS:
				break;
			case CHEVAL_DE_FRISE:
				break;
			default:
				addSequential(new DriveToDistance(normSpeed, 0, Defense.LOW_BAR.getDriveDistance()));
				break;
		}

		// Drive the distance to the lane. May not drive if lane is "close"
		addSequential(new DriveToDistance(normSpeed, 0, lane.getDriveDistance()));

		// Rotate to 90 degrees, because that's what we always do
		addSequential(new RotateToAngle(90, waitTime));

		// Wait until our path is clear, or 4 seconds. This is to avoid alliance members
		addSequential(new WaitUntilPathClear(waitTime, slot));

		// Figure out what x-position we need to drive to for our goal 
		System.out.println("Goal");
		switch (goal) {
			case LEFT:
				addSequential(new DriveToUltraDistance(normSpeed, 90, Slot.TWO.getDistanceToLeftWall()));
				break;
			case CENTER:
				addSequential(new DriveToUltraDistance(normSpeed, 90,
						(Slot.THREE.getDistanceToLeftWall() + Slot.FOUR.getDistanceToLeftWall()) / 2));
				break;
			case RIGHT:
				addSequential(new DriveToUltraDistance(normSpeed, 90, Slot.FIVE.getDistanceToLeftWall()));
				break;
			default:
				addSequential(new DriveToUltraDistance(normSpeed, 90, Slot.TWO.getDistanceToLeftWall()));
				break;
		}

		// Rotate to 0 degrees, because that's what we always do.
		addSequential(new RotateToAngle(0, waitTime));

		// Drive forward to get to the wall of the field
		if (lane == Lane.CLOSE) {
			addSequential(new DriveToDistance(normSpeed, 0, Lane.CLOSE.getDistanceToWall()));
		} else {
			addSequential(new DriveToDistance(normSpeed, 0, Lane.FAR.getDistanceToWall()));
		}

		// Drive back 15 inches to line up with the batter
		if (goal != Goal.CENTER) {
			addSequential(new DriveToDistance(normSpeed, 0, -15));
		}

		int rampAngle = 52;

		// Decide what angle we need to rotate to for our goal
		switch (goal) {
			case LEFT:
				addSequential(new RotateToAngle(rampAngle, waitTime));
				break;
			case CENTER:
				// do nothing.
				break;
			case RIGHT:
				rampAngle = 360 - 20 - rampAngle;
				addSequential(new RotateToAngle(rampAngle, waitTime));
				break;
			default:
				addSequential(new RotateToAngle(rampAngle, waitTime));
				break;
		}

		// Drive until we get to the batter
		addSequential(new DriveToProximity(normSpeed, rampAngle));
		
		// Shoot! Score!
		addSequential(new ShootBallCommand());
	}
}
