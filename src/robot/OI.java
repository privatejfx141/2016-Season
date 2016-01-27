package robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
		return -(joystickValue * joystickValue * joystickValue);
	}

	public double getTurn() {
		double joystickValue = driverStick.getAxis(Stick.RIGHT, Axis.X);
		return -(joystickValue * joystickValue * joystickValue);
	}

	public boolean getDriverButtonPress(Button button) {
		return driverStick.getButton(button);
	}
	
	public boolean getOperaterButtonPress(Button button) {
		return operatorStick.getButton(button);
	}

	public int getPOVAngle() {
		return driverStick.getPOVAngle();
	}

	public void updateDashboard() {
		SmartDashboard.putString("Driver Controller", driverStick.toString());
	}
}
