package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.subsystems.Bumper;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class A_Winch extends Command {

	//The singleton bumper
	Bumper bumper;
	//Checks if command is Finished
	boolean isFinished;
	//Distance to move to
	double distance;
	
	
    public A_Winch(double distance) {
    	//Gets bumper
    	bumper = Bumper.getInstance();
    	this.distance = distance;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		System.out.println("A_Winch");

    	//Reset the Encoder on the Winch
    	bumper.resetWinchEncoder();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//if the current encoder read is less than how far we need to go, keep pulling the winch
    	//in
    	if(bumper.getWinchEncoder() <= distance){
    		//Sets the winch motor
    		bumper.setWinchEncoder(distance);
    		//Keeps the command running
    		isFinished = false;
    	} else {
    		//If we are at the correct distance, finish the command
    		isFinished = true;
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//Returns true if the command is finished
        return isFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
