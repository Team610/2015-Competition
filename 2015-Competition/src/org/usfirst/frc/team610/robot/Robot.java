package org.usfirst.frc.team610.robot;

import org.usfirst.frc.team610.robot.commands.A_ForwardBack;
import org.usfirst.frc.team610.robot.commands.A_PositionMove;
import org.usfirst.frc.team610.robot.commands.D_SensorReadings;
import org.usfirst.frc.team610.robot.commands.T_TeleopGroup;
import org.usfirst.frc.team610.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Robot extends IterativeRobot {

	// Readings pushes values to the SD.
	private Command readings;
	// Commands to run at the beginning of teleop and auto.
	private CommandGroup auto;
	private CommandGroup teleop;

	public void robotInit() {

		// instantiate the command used for the autonomous and teleop period
		readings = new D_SensorReadings();
		// Start pushing values to the SD.
		readings.start();
		auto = new A_ForwardBack();
		teleop = new T_TeleopGroup();

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();

	}

	public void autonomousInit() {
		// Start autonomous and cancel teleop.
		auto = new A_ForwardBack();

		auto.start();
		teleop.cancel();
		// Start pushing sensor readings to the SD.
		readings.start();

	}

	public void autonomousPeriodic() {
		Scheduler.getInstance().run();

	}

	public void teleopInit() {
		// Cancel the autonomous in case it didn't end in time.
		auto.cancel();
		// Create the teleop command and start it.
		teleop.start();
		// Reset the drivetrain encoders
		DriveTrain.getInstance().resetEncoders();
		// Start pushing sensor readings to the SD.
		readings.start();
	}

	public void disabledInit() {
		// Start pushing sensor readings to the SD.
		readings.start();
	}

	public void teleopPeriodic() {
		Scheduler.getInstance().run();

	}

	public void testPeriodic() {
		LiveWindow.run();
	}
}
