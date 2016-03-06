package robot;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import robot.commands.GoStraightPID;
import robot.oi.OI;
import robot.subsystems.ChassisSubsystem;
import robot.subsystems.ShooterSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	// Declare all subsystems and add them to the list of subsystems
	public static final ChassisSubsystem chassisSubsystem = new ChassisSubsystem();
	public static final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
	public static OI oi;
	public static CameraServer cameraServer;
	
	public static List<R_Subsystem> subsystemList = new ArrayList<R_Subsystem>();

	Command autonomousCommand;

	private static Robot instance = null;
	
	public Robot() {
		Robot.instance = this;
	}
	
	public static Robot getInstance() { 
		return instance; 
	}
	
	@Override
	public void autonomousInit() {
		autonomousCommand = oi.getAutoCommand();
		
		// schedule the autonomous command
		if (autonomousCommand != null) autonomousCommand.start();

		updateDashboard();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		subsystemPeriodic();
		updateDashboard();
	}

	/**
	 * This function is called when the disabled button is hit. You can use it
	 * to reset subsystems before shutting down.
	 */
	@Override
	public void disabledInit() {
		updateDashboard();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		updateDashboard();
	}

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		
		cameraServer = CameraServer.getInstance();
        cameraServer.setQuality(30);
        cameraServer.setSize(1);
        cameraServer.startAutomaticCapture("cam0");
        
		// Add all the subsystems to the subsystem list.
		subsystemList.add(chassisSubsystem);
		subsystemList.add(shooterSubsystem);

		for (R_Subsystem s : subsystemList) {
			s.init();
		}

		oi.init();
		updateDashboard();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		updateDashboard();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		subsystemPeriodic();
		updateDashboard();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}

	private void subsystemPeriodic() {
		// update all subsystem runtime data.
		for (R_Subsystem r : subsystemList) {
			r.periodic();
		}
		oi.periodic();

		GoStraightPID.periodic();
	}

	private void updateDashboard() {
		// update all subsystems and the OI dashboard items.
		for (R_Subsystem r : subsystemList) {
			r.updateDashboard();
		}
		
		oi.updateDashboard();

		GoStraightPID.updateDashboard();
		
		// Put the currently scheduled commands on the dashboard
		// SmartDashboard.putData("SchedulerCommands", Scheduler.getInstance());
	}
}
