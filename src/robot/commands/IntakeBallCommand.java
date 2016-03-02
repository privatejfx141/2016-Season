package robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;
import robot.util.R_GameController.Button;

/**
 *
 */
public class IntakeBallCommand extends Command {

    public IntakeBallCommand() {
        requires(Robot.shooterSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.shooterSubsystem.setSpeed(1.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.shooterSubsystem.isBallIn() || Robot.oi.getButton(Button.LEFT_BUMPER);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooterSubsystem.setSpeed(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}