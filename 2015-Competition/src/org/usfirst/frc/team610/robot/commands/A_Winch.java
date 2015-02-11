package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.subsystems.Bumper;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class A_Winch extends Command {

	Bumper bumper;
	boolean isFinished;
	double distance;
	
    public A_Winch(double distance) {
    	bumper = Bumper.getInstance();
    	this.distance = distance;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	bumper.resetWinchEncoder();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(bumper.getWinchEncoder() <= distance){
    		bumper.setWinch(distance);
    		isFinished = false;
    	} else {
    		isFinished = true;
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
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
