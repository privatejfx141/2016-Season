
package robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import robot.Robot;
import robot.util.R_GameController.Button;

/**
 *
 */
public class JoystickChassisCommand extends Command {

    public JoystickChassisCommand() {
        requires(Robot.chassisSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("Init the Joystick");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	if (Robot.getInstance().isAutonomous()) { return; }
    	
    	double speed = Robot.oi.getSpeed();
    	double turn = Robot.oi.getTurn();
    	
    	double leftSpeed;
    	double rightSpeed;
    	
    	if (Robot.oi.getButton(Button.BACK)) {
    		Robot.chassisSubsystem.resetGyro();
    	}
    	
    	if (Robot.oi.getButton(Button.START)) {
    		Robot.chassisSubsystem.calibrateGyro();
    	}
    	
    	if (Robot.oi.getPOVAngle() != -1) {
    		Scheduler.getInstance().add(new RotateToAngle(Robot.oi.getPOVAngle(), 3.0));
    		return;
    	}
    	
    	// If the driver is not turning, then follow the gyro using the GoStraight command.
    	if (Math.abs(turn) < 0.03) {
    		Scheduler.getInstance().add(new GoStraightCommand(Robot.chassisSubsystem.getCurrentAngle()));
    		return;
    	}
    	
    	// If the driver is not driving forward, pivot the robot.
    	if (Math.abs(speed) < 0.03) {
    		leftSpeed = turn * 0.25;
    		rightSpeed = -turn * 0.25;
    	} else {
    		if (speed < 0) {
    			leftSpeed = (turn > 0) ? speed * (1 + turn) : speed;
    			rightSpeed = (turn < 0) ? speed * (1 - turn) : speed;
    		} else {
    			leftSpeed = (turn < 0) ? speed * (1 + turn) : speed;
        		rightSpeed = (turn > 0) ? speed * (1 - turn) : speed;
    		}
    	}
    	
    	Robot.chassisSubsystem.setSpeed(leftSpeed, rightSpeed);
	}

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
