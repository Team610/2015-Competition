package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.subsystems.Bumper;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class A_Winch extends Command {
	// The singleton bumper.
	private Bumper bumper;
	double timeout;

	public A_Winch(double timeout) {
		// Get the singleton bumper.
		bumper = Bumper.getInstance();
		this.timeout = timeout;

	}

	//Unused
	protected void initialize() {
		System.out.println("A_Winch");
		setTimeout(timeout);
	}

	protected void execute() {
		bumper.setWinch(0.75);

	}

	//returns true if arms are moved to correct position
	protected boolean isFinished() {
		return isTimedOut();
	}

	//Unused
	protected void end() {
		bumper.setWinch(0);
	}
	
    //Unused
	protected void interrupted() {
		bumper.setWinch(0);
	}
}
