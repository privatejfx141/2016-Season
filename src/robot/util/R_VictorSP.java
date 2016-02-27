package robot.util;

import edu.wpi.first.wpilibj.VictorSP;
import robot.RobotMap.MotorMap;

public class R_VictorSP extends VictorSP {
	public R_VictorSP(MotorMap motor) {
		super(motor.port);
		this.setInverted(motor.inverted);
	}
}