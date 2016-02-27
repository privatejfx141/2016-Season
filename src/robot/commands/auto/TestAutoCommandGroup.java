package robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import robot.Robot;
import robot.commands.auto.base.Drive;

/**
 *
 */
public class TestAutoCommandGroup extends CommandGroup {
    
    public  TestAutoCommandGroup() {
    	requires(Robot.chassisSubsystem);
    	System.out.println("Building test auto");
    	addSequential(new Drive());
    
    }
    
    public void initialize() {
    	System.out.println("Auto command group scheduled");
    }
}