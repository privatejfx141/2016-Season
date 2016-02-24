package robot.util;

import edu.wpi.first.wpilibj.AnalogInput;

public class R_Ultrasonic extends AnalogInput {
	R_LowPassFilter lowPass = new R_LowPassFilter(10, 0.02, 10);
	
	public R_Ultrasonic(int port) {
		super(port);
		reset();
	}
	
	/**
	 * Resets the lowpass filter.
	 * 
	 * Must be called before getDistance();
	 */
	public void reset() {
		lowPass.reset(getRawDistance());
	}

	/**
	 * Gets the distance from the ultrasonic sensor.
	 * 
	 * @return Distance in inches
	 */
	public double getRawDistance() {
		// y = mx + b v = y = 103.97x + 0.2898
		// R^2 = 0.9999
		// (y - b)/m = x
		return 103.97 * super.getVoltage() + 0.2898;
	}
	
	/**
	 * Gets the distance from the ultrasonic sensor and then filters it. 
	 * 
	 * @return Filtered distance in inches
	 */
	public double getDistance() {
		return lowPass.calculate(getRawDistance());
	}
}
