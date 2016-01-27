package robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MovePiston extends Command {
	
	DoubleSolenoid piston;
	Value moveValue;
	
    public MovePiston(DoubleSolenoid piston) {
        this.piston = piston;
        this.moveValue = (piston.get() == Value.kForward) ? Value.kReverse: Value.kForward;;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {    	
    	this.piston.set(this.moveValue);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
