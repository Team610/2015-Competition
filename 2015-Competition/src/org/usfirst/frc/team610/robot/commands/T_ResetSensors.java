package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class T_ResetSensors extends Command {
	
	private DriveTrain driveTrain;

    public T_ResetSensors() {
    	
    	driveTrain = DriveTrain.getInstance();
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(1);
    	System.out.println("Resetting");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Reset encoders
    	driveTrain.resetEncoders();
    	//Reset the gryo
    	driveTrain.zeroYaw();
    	
    }

    protected boolean isFinished() {
        return isTimedOut();
    }


    protected void end() {
    }

    protected void interrupted() {
    }
}
