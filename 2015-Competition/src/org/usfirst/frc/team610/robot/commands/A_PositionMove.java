package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.constants.ElectricalConstants;
import org.usfirst.frc.team610.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class A_PositionMove extends Command {

	DriveTrain driveTrain;
	double angle;
	double lastGyroError = 0;
	double lastEncoderError = 0;
	double tInches;
	double cap;
	double tAngle;
	double gyroError;
	double encoderError;
	
	public A_PositionMove(double tInches, double cap) {

		driveTrain = DriveTrain.getInstance();
		requires(driveTrain);
		this.tInches = tInches;
		this.cap = cap;
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		driveTrain.resetEncoders();
		tAngle = driveTrain.getYaw();
		gyroError = driveTrain.getYaw();
		setTimeout(0);
		if (tInches == 0) {
			setTimeout(90);
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double p = ElectricalConstants.GYRO_P;
		double d = ElectricalConstants.GYRO_D;
		double leftSpeed, rightSpeed;
		double diffGyroError, diffEncoderError;
		
		encoderError = tInches - driveTrain.getAvgDistance();
		diffEncoderError = encoderError - lastEncoderError;
		
		System.out.println("Encoder" + driveTrain.getAvgDistance());
		SmartDashboard.putNumber("Encoder", driveTrain.getAvgDistance());
		

		rightSpeed = Math.min(cap, encoderError*ElectricalConstants.ENCODER_P - diffEncoderError * d);
		leftSpeed = Math.min(cap, encoderError*ElectricalConstants.ENCODER_P - diffEncoderError * d);


		gyroError = tAngle - driveTrain.getYaw();
		
		diffGyroError = gyroError - lastGyroError;
		
	    leftSpeed -= gyroError * p + diffGyroError * d;
	    rightSpeed += gyroError * p + diffGyroError * d;	
	    	
		
    	
    	

		driveTrain.setLeft(leftSpeed);
		driveTrain.setRight(rightSpeed);

		lastGyroError = gyroError;
		lastEncoderError = encoderError;

	}

	int tick = 0;

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
//		if (tInches == 0) {
//			return isTimedOut();
//		} else {
//			if (Math.abs(driveTrain.getAvgDistance() - tInches) < 5) {
//				tick++;
//			} else {
//				tick = 0;
//			}
//
//			if (tick > 20) {
//				driveTrain.setLeft(0);
//				driveTrain.setRight(0);
//				System.out.println("A_Position Finished");
//				return true;
//				
//			} else {
//				return false;
//			}
//		}
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
