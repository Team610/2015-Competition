package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.subsystems.Bumper;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class A_SetWingsOpen extends Command {
	// The singleton bumper.
	private Bumper bumper;
	// The position we need to move the wings to.
	private boolean position;
	// The boolean returned in isFinished.
	private boolean isFinished;

	public A_SetWingsOpen(boolean position) {
		// Get the singleton bumper.
		bumper = Bumper.getInstance();
		// Save the position locally.
		this.position = position;
	}

	protected void initialize() {
		System.out.println("A_SetWingsOpen");

	}

	protected void execute() {
		// Set the position of the wings.
		bumper.setWingsOpen(true);
		// End the command.
		isFinished = true;
	}

	protected boolean isFinished() {
		return isFinished;
	}

	protected void end() {
	}

	protected void interrupted() {
	}
}
