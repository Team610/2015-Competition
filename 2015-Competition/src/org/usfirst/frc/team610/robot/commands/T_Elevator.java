package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.OI;
import org.usfirst.frc.team610.robot.constants.ElectricalConstants;
import org.usfirst.frc.team610.robot.constants.InputConstants;
import org.usfirst.frc.team610.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class T_Elevator extends Command {

	Elevator elevator;
	Joystick driver, operator;
	OI oi;
	double bottomPoint, midPoint;
	int elevatorPosition = 0;
	double iCounter;
	double oneTote, twoTotes, threeTotes, fourTotes;
	double currentPosition;
	double targetSetpoint = 0.7;
	int lastElevatorPosition;

	public T_Elevator() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		oi = OI.getInstance();
		driver = oi.getDriver();
		operator = oi.getOperator();
		elevator = Elevator.getInstance();
		twoTotes = ElectricalConstants.ELEVATOR_TWOTOTES;
		threeTotes = ElectricalConstants.ELEVATOR_THREETOTES;
		oneTote = ElectricalConstants.ELEVATOR_BOTTOM;
		fourTotes = ElectricalConstants.ELEVATOR_FOURTOTES;
		currentPosition = 0;
		lastElevatorPosition = 0;

	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double curPot = elevator.getPot();

		// TOTE POSITION START \\
		// SetElevator Position
		if (operator.getRawButton(InputConstants.BTN_A)) {
			elevatorPosition = 0;
			iCounter = 0;
		}
		if (operator.getRawButton(InputConstants.BTN_B)) {
			elevatorPosition = 1;
			iCounter = 0;

		}
		if (operator.getRawButton(InputConstants.BTN_Y)) {
			elevatorPosition = 2;
			iCounter = 0;
		}
		if (operator.getRawButton(InputConstants.BTN_X)) {
			elevatorPosition = 3;
			iCounter = 0;
		}

		// Trimming
		double trim = operator.getRawAxis(InputConstants.AXIS_RIGHT_Y) * 0.005;
		if (Math.abs(trim) > 0.05*0.005) {

			switch (elevatorPosition) {

			case 0:
				if ((oneTote + trim > ElectricalConstants.ELEVATOR_TOP && oneTote
						+ trim < ElectricalConstants.ELEVATOR_BOTTOM)) {
					oneTote += trim;
				}
				break;
			case 1:
				if ((twoTotes + trim > ElectricalConstants.ELEVATOR_TOP && twoTotes
						+ trim < ElectricalConstants.ELEVATOR_BOTTOM)) {
					twoTotes += trim;
				}
				break;
			case 2:
				if ((threeTotes + trim > ElectricalConstants.ELEVATOR_TOP && threeTotes
						+ trim < ElectricalConstants.ELEVATOR_BOTTOM)) {
					threeTotes += trim;
				}
				break;
			case 3:
				if ((fourTotes + trim > ElectricalConstants.ELEVATOR_TOP && fourTotes
						+ trim < ElectricalConstants.ELEVATOR_BOTTOM)) {
					fourTotes += trim;
				}
				break;
			}
		}

		switch (elevatorPosition) {

		case 0:
			targetSetpoint = oneTote;

			break;
		case 1:
			targetSetpoint = twoTotes;
			break;
		case 2:
			targetSetpoint = threeTotes;
			break;
		case 3:
			targetSetpoint = fourTotes;
			break;

		}
		// TOTE POSITION STOP \\
		SmartDashboard.putNumber("TotePos:", oneTote);
		SmartDashboard.putNumber("LowPos:", twoTotes);
		SmartDashboard.putNumber("MidPos:", threeTotes);
		SmartDashboard.putNumber("TopPos:", fourTotes);

		double error = 0;

		// Pot Cap

		// Calculate error
		error = curPot - targetSetpoint;
		if (error > 0 && iCounter < 1.0 / ElectricalConstants.ELEVATOR_I) {
			// Prevent windup
			if (error > 0.1) {
				iCounter = 0;
			} else {
				iCounter += error;
			}
		} else if (error < -0.003
				&& iCounter > -1.0 / ElectricalConstants.ELEVATOR_I) {
			// Prevent windup
			if (error < -0.1) {
				iCounter = 0;
			} else {
				iCounter += error;
			}
		}

		// PID motor

		//elevator.setMotor(ElectricalConstants.ELEVATOR_P * error + iCounter
		//		* ElectricalConstants.ELEVATOR_I);
		SmartDashboard.putNumber("Elevator Position", elevatorPosition);
		SmartDashboard
				.putNumber("Last Elevator Position", lastElevatorPosition);
		lastElevatorPosition = elevatorPosition;

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
