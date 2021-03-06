package robot.oi;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoChooser {
	SendableChooser laneChooser = new SendableChooser();
	SendableChooser defenceChooser = new SendableChooser();
	SendableChooser distanceChooser = new SendableChooser();
	SendableChooser goalChooser = new SendableChooser();
	SendableChooser autoChooser = new SendableChooser();
	SendableChooser whenToFinish = new SendableChooser();
	
	public AutoChooser() {
		laneChooser.addDefault("1", new Integer(1));
		laneChooser.addObject("2", new Integer(2));
		laneChooser.addObject("3", new Integer(3));
		laneChooser.addObject("4", new Integer(4));
		laneChooser.addObject("5", new Integer(5));

		defenceChooser.addDefault("Low Bar", "Low Bar");
		defenceChooser.addObject("Ramparts", "Ramparts");
		defenceChooser.addObject("Moat", "Moat");
		defenceChooser.addObject("Rock Wall", "Rock Wall");
		defenceChooser.addObject("Rough Terrain", "Rough Terrain");
		defenceChooser.addObject("Portcullis", "Portcullis");
		defenceChooser.addObject("Cheval de Frise", "Cheval de Frise");

		distanceChooser.addDefault("Close", "Close");
		distanceChooser.addObject("Far", "Far");

		goalChooser.addDefault("Left", "Left");
		goalChooser.addObject("Center", "Center");
		goalChooser.addObject("Right", "Right");
		
		autoChooser.addDefault("Standard Auto", "Standard Auto");
		autoChooser.addObject("Do Nothing", "Do Nothing");
		
		whenToFinish.addDefault("After Shot", "After Shot"); 
		whenToFinish.addObject("After Crossing", "After Crossing");
		
		SmartDashboard.putData("Slot position", laneChooser);
		SmartDashboard.putData("Defences", defenceChooser);
		SmartDashboard.putData("Distance", distanceChooser);
		SmartDashboard.putData("Goal", goalChooser);
		SmartDashboard.putData("Enable Auto?", autoChooser);
		SmartDashboard.putData("When to finish", whenToFinish);
	}
	
	/**
	 * 
	 * @return What to do in auto
	 */
	public String whatDoWeDo() {
		return (String) autoChooser.getSelected();
	}
	
	public String whenToStop() {
		return (String) whenToFinish.getSelected();
	}
	
	/**
	 * 
	 * @return The selected lane, as an integer
	 */
	public int getSelectedSlot() {
		return (int) laneChooser.getSelected();
	}

	/**
	 * 
	 * @return The selected defense, as a string
	 */
	public String getSelectedDefence() {
		return (String) defenceChooser.getSelected();
	}

	/**
	 * 
	 * @return The selected distance, as a string
	 */
	public String getSelectedDistance() {
		return (String) distanceChooser.getSelected();
	}

	/**
	 * 
	 * @return The selected goal, as a String
	 */
	public String getSelectedGoal() {
		return (String) goalChooser.getSelected();
	}
}