package robot;

import edu.wpi.first.wpilibj.Joystick;

public class R_Extreme3DPro_GameController extends R_GameController {
	private final Joystick joystick;

	public R_Extreme3DPro_GameController(int port) {
		this.joystick = new Joystick(port);
	}

	public R_Extreme3DPro_GameController(Joystick j) {
		this.joystick = j;
	}

	@Override
	public double getAxis(Stick stick, Axis axis) {
		double axisValue = 0.0;

		switch (stick) {
		case LEFT:
			switch (axis) {
			case X:
				axisValue = joystick.getRawAxis(0);
				break;
			case Y:
				axisValue = joystick.getRawAxis(1);
				break;
			}
			break;
		case RIGHT:
			switch (axis) {
			case X:
				axisValue = joystick.getRawAxis(3);
				break;
			case Y:
				axisValue = joystick.getRawAxis(2);
				break;
			}
			break;
		}

		// Round the axis value to 2 decimal places
		return Math.round(axisValue * 100) / 100.0;
	}

	@Override
	public double getTrigger(Trigger trigger) {
		return 0;
	}

	@Override
	public boolean getButton(Button button) {
		switch (button) {
		case b1:
			return joystick.getRawButton(1);
		case b2:
			return joystick.getRawButton(2);
		case b3:
			return joystick.getRawButton(3);
		case b4:
			return joystick.getRawButton(4);
		case b5:
			return joystick.getRawButton(5);
		case b6:
			return joystick.getRawButton(6);
		case b7:
			return joystick.getRawButton(7);
		case b8:
			return joystick.getRawButton(8);
		case b9:
			return joystick.getRawButton(9);
		case b10:
			return joystick.getRawButton(10);
		case b11:
			return joystick.getRawButton(11);
		default:
			return false;
		}
	}

	@Override
	public int getPOVAngle() {
		return joystick.getPOV();
	}

	@Override
	public Joystick getRawJoystick() {
		// TODO Auto-generated method stub
		return joystick;
	}
}
