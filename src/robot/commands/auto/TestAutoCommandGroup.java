package robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import robot.commands.auto.base.Drive;

/**
 *
 */
public class TestAutoCommandGroup extends CommandGroup {
    
    public  TestAutoCommandGroup() {    	
    	System.out.println("Building test auto");
    	addSequential(new Drive());
    }
}