package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.OI;
import org.usfirst.frc.team610.robot.constants.ElevatorConstants;
import org.usfirst.frc.team610.robot.constants.InputConstants;
import org.usfirst.frc.team610.robot.constants.PIDConstants;
import org.usfirst.frc.team610.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class A_Elevator extends Command {

	// Singleton Elevator
	private Elevator elevator;
	private double target;
	private double iCounter = 0;
	private int ticks = 0;

	public A_Elevator(double target) {

		elevator = Elevator.getInstance();
		this.target = target;
		requires(elevator);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		System.out.println("T_Elevator");

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// Error for P
		double error = 0;

		double curPot = Elevator.getInstance().getPot();
		// Calculate error
		error = curPot - target;
		if (error > 0 && iCounter < 1.0 / PIDConstants.ELEVATOR_I) {
			// Prevent windup
			if (error > 0.1) {
				iCounter = 0;
			} else {
				iCounter += error;
			}
		} else if (error < -0.003 && iCounter > -1.0 / PIDConstants.ELEVATOR_I) {
			// Prevent windup
			if (error < -0.1) {
				iCounter = 0;
			} else {
				iCounter += error;
			}
		}

		// Set the motor using PID
		double setMotorValue = PIDConstants.ELEVATOR_P * error + iCounter
				* PIDConstants.ELEVATOR_I;
		// Ian make it fancy
		if (setMotorValue > 1) {
			setMotorValue = 1;
		}
		// UNTESTED HARDSTOP LIMIT
		// Slow down the elevator when we're near the point where the stages
		// change.
		if (Math.abs(ElevatorConstants.ElEVATOR_MID - curPot) < 0.02) {
			if (setMotorValue > 0) {
				setMotorValue = Math.min(setMotorValue, 0.5);

			} else {
				setMotorValue = Math.max(setMotorValue, -0.5);

			}
		}
		
		elevator.setMotor(setMotorValue);

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (Math.abs(elevator.getPot() - target) < 0.05) {
			ticks++;
		} else {
			ticks = 0;
		}
		if (ticks > 10) {
			return true;
		} else {
			return false;

		}
	}

	// Called once after isFinished returns true
	protected void end() {
		elevator.setMotor(0);

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
