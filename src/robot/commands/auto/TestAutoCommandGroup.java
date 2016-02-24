package robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import robot.commands.RotateToAngle;
import robot.commands.auto.base.DriveToDistance;
import robot.commands.auto.base.DriveToProximity;
import robot.commands.auto.base.DriveToUltraDistance;

/**
 *
 */
public class TestAutoCommandGroup extends CommandGroup {
    
    public  TestAutoCommandGroup() {    	
    	addSequential(new DriveToDistance(0.5, 0.0, 192));
    	addSequential(new RotateToAngle(90.0,4.0));
    	addSequential(new DriveToUltraDistance(0.5, 90.0, 154.0));
    	addSequential(new RotateToAngle(0.0,4.0));
    	addSequential(new DriveToProximity(0.5, 0.0));
    	
    	
    }
}
