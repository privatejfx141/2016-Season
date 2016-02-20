package robot.oi;

import java.util.Arrays;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.Field.Defense;
import robot.Field.Goal;
import robot.Field.Lane;
import robot.Field.Slot;
import robot.R_GameController;
import robot.R_GameController.Axis;
import robot.R_GameController.Button;
import robot.R_GameController.Stick;
import robot.R_GameController.Trigger;
import robot.R_GameControllerFactory;
import robot.commands.auto.AutoCommandGroup;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	private R_GameController driverStick = R_GameControllerFactory.getGameController(0);;
	private AutoChooser autoChooser = new AutoChooser();
	private double[] lastButtonPress = new double[Button.values().length];
	
	public double getSpeed() {
		double joystickValue = driverStick.getAxis(Stick.LEFT, Axis.Y);
		return -(Math.pow(joystickValue, 3) * 100) / 100.0;
	}

	public double getTurn() {
		double joystickValue = driverStick.getAxis(Stick.RIGHT, Axis.X);
		return (Math.pow(joystickValue, 3) * 100) / 100.0;
	}
	
	public double getTrigger(Trigger trigger) {
		return driverStick.getTrigger(trigger);
	}
	
	public boolean getButton(Button button) {
		if (driverStick.getButton(button)) {
			return debounce(button);
		} 
		return false;
	}

	/**
	 * Debounce the given button. 
	 * 
	 * @param button
	 * 				The button of which you want to debounce.
	 * @return
	 */
	private boolean debounce(Button button) {
		if (System.nanoTime() - lastButtonPress[button.ordinal()] > 300000000) {
			lastButtonPress[button.ordinal()] = System.nanoTime();
			return driverStick.getButton(button);
		}
		return false;
	}
	
	/**
	 * Sets the joystick rumble strength.
	 * 
	 * @param strength
	 *            Has to be between 0.0 and 1.0.
	 */
	public void setRumble(double strength) {
		driverStick.setRumble(strength);
	}

	/**
	 * Sets the joystick rumble strength on the left and right channels
	 * individually.
	 * 
	 * @param leftRumble
	 *            Rumble strength on the left channel.
	 * @param rightRumble
	 *            Rumble strength on the right channel.
	 */
	public void setRumble(double leftRumble, double rightRumble) {
		driverStick.setRumble(leftRumble, rightRumble);
	}

	public int getPOVAngle() {
		return driverStick.getPOVAngle();
	}
	
	public Defense getDefense() {
		return Defense.toEnum(autoChooser.getSelectedDefence());
	}

	public Slot getSlot() {
		return Slot.toEnum(autoChooser.getSelectedSlot());
	}

	public Lane getLane() {
		return Lane.toEnum(autoChooser.getSelectedDistance());
	}

	public Goal getGoal() {
		return Goal.toEnum(autoChooser.getSelectedGoal());
	}

	/**
	 * Method to get our autonomous command.
	 * 
	 * @return command
	 * 				Returns the autonomous command for autonomous.
	 */
	public Command getAutoCommand() {
		return new AutoCommandGroup(getSlot(), getDefense(), getLane(), getGoal());
	}
	
	public void init() {
		Arrays.fill(lastButtonPress, 0);
	}

	/**
	 * Update the periodic running elements of the dashboard
	 * <p>
	 * i.e. all toggle buttons
	 */
	public void periodic() {
	}

	/**
	 * Put any items on the dashboard
	 */
	public void updateDashboard() {
		SmartDashboard.putString("Driver Controller", driverStick.toString());
	}
}
