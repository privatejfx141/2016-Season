package robot.oi;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.R_GameController;
import robot.R_GameController.Axis;
import robot.R_GameController.Button;
import robot.R_GameController.Stick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public R_GameController driverStick;
	public R_GameController operatorStick;
	
	public double getSpeed() {
		double joystickValue = driverStick.getAxis(Stick.LEFT, Axis.Y);
		return -Math.round(joystickValue * Math.abs(joystickValue) * 100) / 100.0;
	}

	public double getTurn() {
		double joystickValue = driverStick.getAxis(Stick.RIGHT, Axis.X);
		return Math.round(joystickValue * Math.abs(joystickValue) * 100) / 100.0;
	}

	public int getPOVAngle() {
		return driverStick.getPOVAngle();
	}
	
	public boolean getGyroReset() {
		return driverStick.getButton(Button.BACK);
	}

	/**
	 * Update the periodic running elements of the dashboard
	 * <p>
	 * i.e. all toggle buttons
	 */
	public void periodic() {}
	
	/**
	 * Put any items on the dashboard
	 */
	public void updateDashboard() {
		SmartDashboard.putString("Driver Controller", driverStick.toString());
	}
}
