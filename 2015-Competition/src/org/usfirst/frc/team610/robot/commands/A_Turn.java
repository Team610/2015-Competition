package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.constants.PIDConstants;
import org.usfirst.frc.team610.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class A_Turn extends Command {
	// The drivetrain reference.
	private DriveTrain driveTrain;
	// The gyro error from the previous loop. Used for the D.
	private double lastGyroError = 0;
	// The encoder error from the previous loop. Used for the D.

	// The target value for PID in inches.
	private double tInches;
	// The motor power cap.
	private double cap;
	// The target angle for PID.
	private double tAngle;
	// The amount we need to move to arrive at tAngle.
	private double gyroError;
	// The amount we need to move to arrive at tInches.
	
	Timer time;

	int ticks;
	
	int targetAngle;




    public A_Turn(int targetAngle) {
    	driveTrain = DriveTrain.getInstance();
    	this.targetAngle = targetAngle;
    	setTimeout(3);
    	requires(driveTrain);
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("A_Turn");
		// Reset the drivetrain encoders.
		
		// Save the target angle as the current angle.
		tAngle = targetAngle;
		gyroError = 0;
		driveTrain.zeroYaw();
		ticks = 0;
		time = new Timer();
		time.start();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	// Get the gyro P and D from PIDConstants.
		double gyroP = PIDConstants.TURN_GYRO_P;
		double gyroD = PIDConstants.TURN_GYRO_D;
		double leftSpeed, rightSpeed;
		double diffGyroError, diffEncoderError;
		// Calculate the encoder error.

		// Calculate the speeds using P and D. Use Math.min to cap the value at
		// cap.
//		if (encoderError * PIDConstants.ENCODER_P - diffEncoderError * PIDConstants.ENCODER_D < 0) {
//			rightSpeed = Math.max(cap, encoderError * PIDConstants.ENCODER_P
//					- diffEncoderError * PIDConstants.ENCODER_D);
//			leftSpeed = Math.max(cap, encoderError * PIDConstants.ENCODER_P
//					- diffEncoderError * PIDConstants.ENCODER_D);
//		} else {
//			rightSpeed = Math.min(cap, encoderError * PIDConstants.ENCODER_P
//					- diffEncoderError * PIDConstants.ENCODER_D);
//			leftSpeed = Math.min(cap, encoderError * PIDConstants.ENCODER_P
//					- diffEncoderError * PIDConstants.ENCODER_D);
//		}


		// Calculate the gyro error.
		gyroError = tAngle - driveTrain.getYaw();
		// Find the difference between the current error and the error from the
		// last loop.
		diffGyroError = gyroError - lastGyroError;
		// Add the gyro PID to the left and right speeds.
		leftSpeed = 0;
		rightSpeed = 0;
		leftSpeed -= gyroError * gyroP + diffGyroError * gyroD;
		rightSpeed += gyroError * gyroP + diffGyroError * gyroD;
		//Cap the speeds to avoid losing comm.
		rightSpeed = Math.max(-0.6,Math.min(0.6, rightSpeed));
		leftSpeed = Math.max(-0.6,Math.min(0.6, leftSpeed));
		// Send the values to the drivetrain.
		driveTrain.setLeft(leftSpeed);
		driveTrain.setRight(rightSpeed);
		// Save the current errors for the next loop.
		lastGyroError = gyroError;

		SmartDashboard.putNumber("leftSpeed", leftSpeed);
		SmartDashboard.putNumber("rightSpeed", rightSpeed);
		SmartDashboard.putNumber("Time", time.get());

    	
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
    	
    	gyroError = tAngle - driveTrain.getYaw();
    	
    	if(Math.abs(gyroError) < 1){
    		ticks++;
    		
    	}
    	else{
    		ticks = 0;
    	}

    	if(ticks > 20){
    		
    		return true;
    		
    	}
    	else{
    		return isTimedOut();
    	}
    	
   
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
