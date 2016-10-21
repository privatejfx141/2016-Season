package robot.oi;

import java.util.Arrays;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.Field.Defense;
import robot.Field.Goal;
import robot.Field.Lane;
import robot.Field.Slot;
import robot.commands.auto.AutoCommandGroup;
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
	private R_GameController driverStick = R_GameControllerFactory.getGameController(0);;
	private AutoChooser autoChooser = new AutoChooser();
	private double[] lastButtonPress = new double[Button.values().length];
	
	private enum Action {
		GYRO_RESET(Button.BACK),
		GYRO_CALIBRATE(Button.START),
		SHOOTER(Button.RIGHT_BUMPER),
		CANCEL(Button.LEFT_BUMPER);
		
		private final Button button;
		
		Action(Button button) {
			this.button = button;
		}
		
		public Button getButton() {
			return button;
		}
	}	
	
	/**
	 * Get the speed off the driver joystick.
	 * 
	 * @return speed the robot should drive forward or backwards
	 */
	public double getSpeed() {
		double joystickValue = driverStick.getAxis(Stick.LEFT, Axis.Y);
		return -(Math.pow(joystickValue, 3) * 100) / 100.0;
	}
	
	public boolean getPistonButton(){
		return driverStick.getButton(Button.X);
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
	 * Check if the gyro reset button has been pressed.
	 * 
	 * @return if the reset button has been pressed.
	 */
	public boolean getGyroResetButton() {
		return getButton(Action.GYRO_RESET.getButton());
	}
	
	/**
	 * Check if the gyro calibrate button has been pressed.
	 * 
	 * @return if the calibrate button has been pressed.
	 */
	public boolean getGyroCalibrateButton() {
		return getButton(Action.GYRO_CALIBRATE.getButton());
	}
	
	
	/**
	 * Check if the button for intake has been pressed.
	 * 
	 * @return if the button has been pressed.
	 */
	public boolean getShooterButton() {
		return getButton(Action.SHOOTER.getButton());
	}
	
	
	/**
	 * Check if the cancel command button has been pressed.
	 * 
	 * @return if the button has been pressed.
	 */
	public boolean getCancelButton() {
		return getButton(Action.CANCEL.getButton());
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
	
	/**
	 * The defense that the robot is currently set in front of 
	 * as set in smart dashboard.
	 * 
	 * @return Defense the defense the robot is in front of
	 */
	public Defense getDefense() {
		return Defense.toEnum(autoChooser.getSelectedDefence());
	}

	/**
	 * The slot that the robot is currently at as set in smart dashboard.
	 * 
	 * @return Slot the slot that the robot is at
	 */
	public Slot getSlot() {
		return Slot.toEnum(autoChooser.getSelectedSlot());
	}

	/**
	 * The lane that the robot should make it's turn in. 
	 * Two "lanes" have been allocated on the field, short, and long,
	 * this is to help protect against robot collisions when doing autonomous.
	 * Must discuss with allience members which lane they will be occupying so 
	 * as to decide when to turn.
	 * 
	 * @return Lane the lane that the robot will turn in
	 */
	public Lane getLane() {
		return Lane.toEnum(autoChooser.getSelectedDistance());
	}

	/**
	 * The goal the robot will attempt to score in.
	 * 
	 * @return Goal the robot will score in
	 */
	public Goal getGoal() {
		return Goal.toEnum(autoChooser.getSelectedGoal());
	}

	/**
	 * Determine whether or not we should do an auto.
	 * 
	 * @return whether or not to do auto
	 */
	public boolean doAuto() {
		return autoChooser.whatDoWeDo() == "Standard Auto";
	}
	
	/**
	 * Determine whether or not we should do an auto.
	 * 
	 * @return whether or not to do auto
	 */
	public boolean stopAfterCrossing() {
		return autoChooser.whenToStop() == "After Crossing";
	}
	
	/**
	 * Method to get our autonomous command.
	 * 
	 * @return command
	 * 				Returns the autonomous command for autonomous.
	 */
	public Command getAutoCommand() {
		if (doAuto()) {
			return new AutoCommandGroup(getSlot(), getDefense(), getLane(), getGoal());
		} 
		return null;
	}
	
	public void init() {
		// Fill the debouncing array with 0 as no button has been pressed.
		Arrays.fill(lastButtonPress, 0.0);
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
