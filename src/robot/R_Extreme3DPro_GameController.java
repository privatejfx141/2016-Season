package robot;

import edu.wpi.first.wpilibj.Joystick;

public class R_Extreme3DPro_GameController {
	private final Joystick joystick;

	public enum Axis {
		X, Y;
	}

	public enum Button {
		b1,
		b2,
		b3,
		b4,
		b5,
		b6,
		b7,
		b8,
		b9,
		b10,
		b11,
		b12;
	}
	public R_Extreme3DPro_GameController(int port) {
		this.joystick = new Joystick(port);
	}

	public R_Extreme3DPro_GameController(Joystick j) {
		this.joystick = j;
	}

	public double getAxis(Axis axis) {
		double axisValue = 0.0;

		switch (axis) {
		case X:
			axisValue = joystick.getRawAxis(0);
			break;
		case Y:
			axisValue = joystick.getRawAxis(1);
			break;
		}

		// Round the axis value to 2 decimal places
		return Math.round(axisValue * 100) / 100.0;
	}

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

	public int getPOVAngle() {
		return joystick.getPOV();
	}

	public Joystick getRawJoystick() {
		// TODO Auto-generated method stub
		return joystick;
	}
}
