package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.constants.PIDConstants;
import org.usfirst.frc.team610.robot.subsystems.Bumper;
import org.usfirst.frc.team610.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class A_TimedDrive extends Command {
	// The drivetrain reference.
	private DriveTrain driveTrain;
	private double power;

	/**
	 * Moves forward a certain number of inches with a power cap.
	 * 
	 * @param tInches
	 *            the distance we need to move
	 * @param cap
	 *            the motor power cap.
	 */
	public A_TimedDrive(double power) {
		// Get the singleton drivetrain.
		driveTrain = DriveTrain.getInstance();
		this.power = power;
		setTimeout(0.5);
		// Make sure this is the only command using the drivetrain.
		requires(driveTrain);

	}

	protected void initialize() {

	}

	protected void execute() {
		driveTrain.setLeft(power);
		driveTrain.setRight(power);

	}

	protected boolean isFinished() {
		if (isTimedOut()) {
			driveTrain.setLeft(0);
			driveTrain.setRight(0);
		}
		return isTimedOut();

	}

	protected void end() {
	}

	protected void interrupted() {
	}
}
