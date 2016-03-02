package robot.util;

import edu.wpi.first.wpilibj.Encoder;
import robot.RobotMap.EncoderMap;

public class R_Encoder extends Encoder {
	
	/**
	 * Creates an encoder based off the values of an EncoderMap enum.
	 * 
	 * @param encoder the encoder that you would like to have made.
	 */
	public R_Encoder(EncoderMap encoder) {
		super(encoder.ch1, encoder.ch2);
	}
}
