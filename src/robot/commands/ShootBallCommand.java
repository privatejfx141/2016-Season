package robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;

/**
 *
 */
public class ShootBallCommand extends Command {

    public ShootBallCommand() {
        requires(Robot.shooterSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	this.setTimeout(1.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.shooterSubsystem.setSpeed(1.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return this.isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooterSubsystem.setSpeed(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
