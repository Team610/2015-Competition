package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.constants.ElectricalConstants;
import org.usfirst.frc.team610.robot.constants.PIDConstants;
import org.usfirst.frc.team610.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class A_PositionMove extends Command {
	// The drivetrain reference.
	private DriveTrain driveTrain;
	// The gyro error from the previous loop. Used for the D.
	private double lastGyroError = 0;
	// The encoder error from the previous loop. Used for the D.
	private double lastEncoderError = 0;
	// The target value for PID in inches.
	private double tInches;
	// The motor power cap.
	private double cap;
	// The target angle for PID.
	private double tAngle;
	// The amount we need to move to arrive at tAngle.
	private double gyroError;
	// The amount we need to move to arrive at tInches.
	private double encoderError;
	//Count how long we've been in our target zone.
	private int targetCounter = 0;

	/**
	 * Moves forward a certain number of inches with a power cap.
	 * @param tInches the distance we need to move
	 * @param cap the motor power cap.
	 */
	public A_PositionMove(double tInches, double cap) {
		//Get the singleton drivetrain.
		driveTrain = DriveTrain.getInstance();
		//Make sure this is the only command using the drivetrain.
		requires(driveTrain);
		//Save the tInches and cap locally.
		this.tInches = tInches;
		this.cap = cap;
		
	}

	protected void initialize() {
		//Reset the drivetrain encoders.
		driveTrain.resetEncoders();
		//Save the target angle as the current angle.
		tAngle = driveTrain.getYaw();
		gyroError = 0;
		//If we are holding the current position, hold it for 90 seconds.
		if (tInches == 0) {
			setTimeout(90);
		}
	}

	protected void execute() {
		//Get the gyro P and D from PIDConstants.
		double gyroP = PIDConstants.GYRO_P;
		double gyroD = PIDConstants.GYRO_D;
		double leftSpeed, rightSpeed;
		double diffGyroError, diffEncoderError;
		//Calculate the encoder error.
		encoderError = tInches - driveTrain.getAvgDistance();
		//Find the difference between the current error and the error from the last loop.
		diffEncoderError = encoderError - lastEncoderError;
		
		//Calculate the speeds using P and D. Use Math.min to cap the value at cap.
		rightSpeed = Math.min(cap, encoderError * PIDConstants.ENCODER_P
				- diffEncoderError * gyroD);
		leftSpeed = Math.min(cap, encoderError * PIDConstants.ENCODER_P
				- diffEncoderError * gyroD);
		//Calculate the gyro error.
		gyroError = tAngle - driveTrain.getYaw();
		//Find the difference between the current error and the error from the last loop.
		diffGyroError = gyroError - lastGyroError;
		//Add the gyro PID to the left and right speeds.
		leftSpeed -= gyroError * gyroP + diffGyroError * gyroD;
		rightSpeed += gyroError * gyroP + diffGyroError * gyroD;
		//Send the values to the drivetrain.
		driveTrain.setLeft(leftSpeed);
		driveTrain.setRight(rightSpeed);
		//Save the current errors for the next loop.
		lastGyroError = gyroError;
		lastEncoderError = encoderError;

	}


	protected boolean isFinished() {
		return false;
		// if (tInches == 0) {
		// return isTimedOut();
		// } else {
		// if (Math.abs(driveTrain.getAvgDistance() - tInches) < 5) {
		// tick++;
		// } else {
		// tick = 0;
		// }
		//
		// if (tick > 20) {
		// driveTrain.setLeft(0);
		// driveTrain.setRight(0);
		// System.out.println("A_Position Finished");
		// return true;
		//
		// } else {
		// return false;
		// }
		// }
	}

	protected void end() {
	}

	protected void interrupted() {
	}
}
