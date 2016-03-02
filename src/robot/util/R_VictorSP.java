package robot.util;

import edu.wpi.first.wpilibj.VictorSP;
import robot.RobotMap.MotorMap;

public class R_VictorSP extends VictorSP {
	/**
	 * Creates a VictorSP from the given MotorMap enum.
	 * 
	 * @param motor you would like to create
	 */
	public R_VictorSP(MotorMap motor) {
		super(motor.port);
		this.setInverted(motor.inverted);
	}
}