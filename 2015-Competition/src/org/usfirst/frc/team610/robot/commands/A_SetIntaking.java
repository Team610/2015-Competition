package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class A_SetIntaking extends Command {
	// The singleton bumper.
	private Intake intake;
	// The value to return in isFinished.
	private boolean isFinished;
	// The position that we want to set the arm to.

	private double intakeSpeed;

	public A_SetIntaking(double speed, double timeout) {
		// Get the singleton bumper.
		intake = Intake.getInstance();
		setTimeout(timeout);
		// Save the position to the local variable.
		this.intakeSpeed = speed;

	}

	// Unused
	protected void initialize() {
		System.out.println("A_SetIntaking");

	}

	protected void execute() {
		// Set the position of the arms.
		intake.setIntakeSpeed(intakeSpeed);

	}

	// returns true if arms are moved to correct position
	protected boolean isFinished() {
		if (isTimedOut()) {
			intake.setIntakeSpeed(0);

		}
		return isTimedOut();
	}

	// Unused
	protected void end() {
	}

	// Unused
	protected void interrupted() {
		intake.setIntakeSpeed(0);

	}
}
