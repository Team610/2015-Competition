package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.subsystems.Bumper;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class A_SetArmUp extends Command {
	// The singleton bumper.
	private Bumper bumper;
	// The value to return in isFinished.
	private boolean isFinished;
	// The position that we want to set the arm to.
	private boolean position;

	public A_SetArmUp(boolean position) {
		// Get the singleton bumper.
		bumper = Bumper.getInstance();
		// Save the position to the local variable.
		this.position = position;

	}

	//Unused
	protected void initialize() {
		System.out.println("A_SetArmUp");

	}

	protected void execute() {
		// Set the position of the arms.
		bumper.setArmsUp(position);
		// Finish the command.
		isFinished = true;

	}

	//returns true if arms are moved to correct position
	protected boolean isFinished() {
		System.out.println("A_SetArmUp is finished");
		return isFinished;
	}

	//Unused
	protected void end() {
	}
	
    //Unused
	protected void interrupted() {
	}
}
