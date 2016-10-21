package robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    private final static boolean inverted = true;

    public enum MotorMap {
        LEFT_MOTOR (0, RobotMap.inverted),
        RIGHT_MOTOR(1, !RobotMap.inverted),
        INTAKE_MOTOR(2, !RobotMap.inverted);

        public final int port;
        public final boolean inverted;

        MotorMap(int port, boolean invertedState) {
            this.port = port;
            this.inverted = invertedState;
        }
    }
    
    public enum SensorMap {
    	// Analog Ports
    	GYRO         (0), 
    	ULTRASONIC   (1),
    	
    	// Digital Ports
    	SHOOTER_LIMIT_SWITCH(8),
    	LEFT_PROXIMITY_SENSOR(9),
    	RIGHT_PROXIMITY_SENSOR(2);
    	
        public final int port;

        SensorMap(int port) {
            this.port = port;
        }
    }
    
    public enum EncoderMap {
    	LEFT (3, 4, 2000.0, 10.189),
    	RIGHT(1, 0, 2000.0, 10.160);
    	
    	public final int ch1;
    	public final int ch2;
    	public final double maxRate;
    	public final double countsPerInch;
    	
    	EncoderMap(int ch1, int ch2, double maxRate, double countsPerInch) {
    		this.ch1 = ch1;
    		this.ch2 = ch2;
    		this.maxRate = maxRate;
    		this.countsPerInch = countsPerInch;
    	}
    }    
}
