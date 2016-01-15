package robot;

import edu.wpi.first.wpilibj.Encoder;
import robot.RobotMap.EncoderMap;

public class R_Encoder extends Encoder {
	public R_Encoder(EncoderMap encoder) {
		super(encoder.ch1, encoder.ch2);
	}
}
