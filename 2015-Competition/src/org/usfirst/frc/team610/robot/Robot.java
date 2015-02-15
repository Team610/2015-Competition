package org.usfirst.frc.team610.robot;

import org.usfirst.frc.team610.robot.commands.G_Step;
import org.usfirst.frc.team610.robot.commands.G_StepBinCentre;
import org.usfirst.frc.team610.robot.commands.A_NoAuto;
import org.usfirst.frc.team610.robot.commands.G_BinTwoLeft;
import org.usfirst.frc.team610.robot.commands.G_BinTwoRight;
import org.usfirst.frc.team610.robot.commands.D_SensorReadings;
import org.usfirst.frc.team610.robot.commands.G_StepBinLeft;
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

	// Command to run during auto.
	// 0: None
	// 1: Two Step
	// 2: Two Bins Right
	// 3: Two Bins Left
	// 4: Two Step Bin Centre
	// 5: Two Step Bin Left
	private int autoMode = 5;

	public void robotInit() {

		// instantiate the command used for the autonomous and teleop period
		readings = new D_SensorReadings();
		// Start pushing values to the SD.
		readings.start();

		auto = new A_NoAuto();
		teleop = new T_TeleopGroup();

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();

	}

	public void autonomousInit() {
		// Start autonomous and cancel teleop.
		switch (autoMode) {
		case 0:
			auto = new A_NoAuto();
			break;
		case 1:
			auto = new G_Step();
			break;
		case 2:
			auto = new G_BinTwoRight();
			break;
		case 3:
			auto = new G_BinTwoLeft();
			break;
		case 4:
			auto = new G_StepBinCentre();
			break;
		case 5:
			auto = new G_StepBinLeft();
			break;
		}

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
