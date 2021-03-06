package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.constants.PIDConstants;
import org.usfirst.frc.team610.robot.subsystems.Bumper;
import org.usfirst.frc.team610.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
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
	//Count how long we've been within our target distance, is used to finish the command. 
	private int tick = 0;

	/**
	 * Moves forward a certain number of inches with a power cap.
	 * 
	 * @param tInches
	 *            the distance we need to move
	 * @param cap
	 *            the motor power cap.
	 */
	public A_PositionMove(double tInches, double cap) {
		// Get the singleton drivetrain.
		driveTrain = DriveTrain.getInstance();
		// Make sure this is the only command using the drivetrain.
		requires(driveTrain);
		// Save the tInches and cap locally.
		this.tInches = tInches;
		this.cap = cap;

	}

	protected void initialize() {
		System.out.println("A_PositionMove");
		// Reset the drivetrain encoders.
		driveTrain.resetEncoders();
		// Save the target angle as the current angle.
		tAngle = driveTrain.getYaw();
		gyroError = 0;
		// If we are holding the current position, hold it for 90 seconds.
		if (tInches == 0) {
			setTimeout(0.5);
		}
	}

	protected void execute() {
		// Get the gyro P and D from PIDConstants.
		double gyroP = PIDConstants.GYRO_P;
		double gyroD = PIDConstants.GYRO_D;
		double leftSpeed, rightSpeed;
		double diffGyroError, diffEncoderError;
		// Calculate the encoder error.
		encoderError = tInches - driveTrain.getAvgDistance();
		// Find the difference between the current error and the error from the
		// last loop.
		diffEncoderError = encoderError - lastEncoderError;
		rightSpeed = encoderError * PIDConstants.ENCODER_P - diffEncoderError
				* PIDConstants.ENCODER_D;
		leftSpeed = encoderError * PIDConstants.ENCODER_P - diffEncoderError
				* PIDConstants.ENCODER_D;

		// Calculate the gyro error.
		gyroError = tAngle - driveTrain.getYaw();
		// Find the difference between the current error and the error from the
		// last loop.
		rightSpeed = Math.max(-cap, Math.min(cap, rightSpeed));
		leftSpeed = Math.max(-cap, Math.min(cap, leftSpeed));
		diffGyroError = gyroError - lastGyroError;
		// Add the gyro PID to the left and right speeds.
		leftSpeed -= gyroError * gyroP + diffGyroError * gyroD;
		rightSpeed += gyroError * gyroP + diffGyroError * gyroD;


		// Send the values to the drivetrain.
		driveTrain.setLeft(leftSpeed);
		driveTrain.setRight(rightSpeed);
		// Save the current errors for the next loop.
		lastGyroError = gyroError;
		lastEncoderError = encoderError;

		//Place Readings for the SmartDashboard.
		//Places the speed of the leftSide of the driveTrain.
		SmartDashboard.putNumber("leftSpeed", leftSpeed);
		//Places the speed of the rightSide of the driveTrain.
		SmartDashboard.putNumber("rightSpeed", rightSpeed);
		//Instantiate a PowerDistributionPanel. 
		PowerDistributionPanel pdp =  new PowerDistributionPanel();
		//Places the currentdraw of port 14 to the SmartDashBoard. 
		SmartDashboard.putNumber("current", pdp.getCurrent(14));


	}

	protected boolean isFinished() {
		//Send # of ticks to SmartDashboard.
		SmartDashboard.putNumber("FinishTicks", tick);
		/*
		 * If our targetInches is zero, A_PositionMove is used as a wait, so isFinished will return
		 * whatever state isTimedOut is currently in. If it is false, it will wait for it to finish,
		 * causing the command to end.   
		*/		
		if (tInches == 0) {
			return isTimedOut();
		} else {
			//If within our target area, increment tick. 
			if (Math.abs(driveTrain.getAvgDistance() - tInches) < 5) {
				//Increment tick. 
				tick++;
				//If we are outside of our target zone, reset the tick counter. 
			} else {	
				tick = 0;
			}

			//If tick has reached our desired amount, stop the drivetrain, and end the command. 
			if (tick > 10) {
				driveTrain.setLeft(0);
				driveTrain.setRight(0);
				SmartDashboard.putBoolean("PositionMoveFinished", true);
				System.out.println("A_Position Finished");
				return true;

			} else {
				// System.out.println("A_Position not finished");
				return false;

			}
		}

	}

	protected void end() {
	}

	protected void interrupted() {
	}
}
