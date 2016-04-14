package robot.oi;

import java.util.Arrays;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.util.R_GameController;
import robot.util.R_GameController.Axis;
import robot.util.R_GameController.Button;
import robot.util.R_GameController.Stick;
import robot.util.R_GameController.Trigger;
import robot.util.R_GameControllerFactory;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private R_GameController driverStick = R_GameControllerFactory.getGameController(0);
	private double[] lastButtonPress = new double[Button.values().length];
	
	/**
	 * Get the speed off the driver joystick.
	 * 
	 * @return speed the robot should drive forward or backwards
	 */
	public double getSpeed() {
		double joystickValue = driverStick.getAxis(Stick.LEFT, Axis.Y);
		return -(Math.pow(joystickValue, 3) * 100) / 100.0;
	}

	/**
	 * Get the turn off the driver joystick
	 * 
	 * @return speed the robot should turn at
	 */
	public double getTurn() {
		double joystickValue = driverStick.getAxis(Stick.RIGHT, Axis.X);
		return (Math.pow(joystickValue, 3) * 100) / 100.0;
	}
	
	/**
	 * Get the trigger value off the driver joystick. 
	 * 
	 * @param trigger you would like to read
	 * @return value of the trigger
	 */
	public double getTrigger(Trigger trigger) {
		return driverStick.getTrigger(trigger);
	}
	
	/**
	 * Get the POV value off the driver joystick.
	 * 
	 * @return angle from the POV
	 */
	public int getPOVAngle() {
		return driverStick.getPOVAngle();
	}
	
	/**
	 * Get the button off the driver joystick
	 * 
	 * @param button you would like to read off the joystick
	 * @return whether or not the button is being pressed.
	 */
	public boolean getButton(Button button) {
		return debounce(button);
	}

	/**
	 * Debounce the given button. 
	 * 
	 * @param button The button of which you want to debounce.
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
	 * @param strength has to be between 0.0 and 1.0.
	 */
	public void setRumble(double strength) {
		driverStick.setRumble(strength);
	}

	/**
	 * Sets the joystick rumble strength on the left and right channels
	 * individually.
	 * 
	 * @param leftRumble rumble strength on the left channel.
	 * @param rightRumble rumble strength on the right channel.
	 */
	public void setRumble(double leftRumble, double rightRumble) {
		driverStick.setRumble(leftRumble, rightRumble);
	}
	
	public void init() {
		// Fill the debouncing array with 0 as no button has been pressed.
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
