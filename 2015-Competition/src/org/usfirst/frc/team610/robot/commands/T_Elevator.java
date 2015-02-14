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
public class T_Elevator extends Command {

	// Singleton Elevator
	private Elevator elevator;
	// Singleton driver and operator joysticks.
	private Joystick driver, operator;
	// Singleton subsystem for joysticks.
	private OI oi;
	// Predetermined height position of elevator.
	private int elevatorPosition = 0;
	// For I correction.
	private double iCounter;
	// Positions for tote and bin stacking.
	private double totePickup, toteCarrying, oneTote, twoTotes, threeTotes,
			fourTotes, fiveTotes;
	private double binPickup, binCarrying, oneBin, twoBins, threeBins,
			fourBins, fiveBins;
	// The target height.
	private double oneBinDown, twoBinsDown, threeBinsDown, fourBinsDown,
			fiveBinsDown;
	private double targetSetpoint = 0.7;
	// Input from dpad on operator controller.
	private int getPov = 0;
	// Boolean for whether elevator is going to bin or tote set positions.
	// private boolean isStackingBin = true;
	// private boolean isStackingBinDown = false;
	// 0 is bin standing up, 1 is bins lying down, 2 is totes
	int stackingType = 0;
	// Booleans for whether Dpad buttons are pressed
	private boolean upDIsPressed = false;
	private boolean downDIsPressed = false;

	public T_Elevator() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		// Set oi to singleton instance of the subsystem of joysticks.
		oi = OI.getInstance();
		// Set to singleton instance of the driver and operator joysticks.
		driver = oi.getDriver();
		operator = oi.getOperator();
		// Set to singleton instance of the elevator.
		elevator = Elevator.getInstance();
		// Set values to predetermined heights.

		totePickup = ElevatorConstants.ELEVATOR_TOTEPICKUP;
		toteCarrying = ElevatorConstants.ELEVATOR_TOTECARRYING;
		oneTote = ElevatorConstants.ELEVATOR_ONETOTES;
		twoTotes = ElevatorConstants.ELEVATOR_TWOTOTES;
		threeTotes = ElevatorConstants.ELEVATOR_THREETOTES;
		fourTotes = ElevatorConstants.ELEVATOR_FOURTOTES;
		fiveTotes = ElevatorConstants.ELEVATOR_FIVETOTES;

		binPickup = ElevatorConstants.ELEVATOR_BINPICKUP;
		binCarrying = ElevatorConstants.ELEVATOR_BINCARRYING;
		oneBin = ElevatorConstants.ELEVATOR_ONEBINS;
		twoBins = ElevatorConstants.ELEVATOR_TWOBINS;
		threeBins = ElevatorConstants.ELEVATOR_THREEBINS;
		fourBins = ElevatorConstants.ELEVATOR_FOURBINS;
		fiveBins = ElevatorConstants.ELEVATOR_FIVEBINS;

		oneBinDown = ElevatorConstants.ELEVATOR_ONEBINS_DOWN;
		twoBinsDown = ElevatorConstants.ELEVATOR_TWOBINS_DOWN;
		threeBinsDown = ElevatorConstants.ELEVATOR_THREEBINS_DOWN;
		fourBinsDown = ElevatorConstants.ELEVATOR_FOURBINS_DOWN;
		fiveBinsDown = ElevatorConstants.ELEVATOR_FIVEBINS_DOWN;

	}

	// Called just before this Command runs the first time
	protected void initialize() {
		System.out.println("T_Elevator");

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		// b for bins
		// When b pressed, set heights to container heights.
		if (operator.getRawButton(InputConstants.BTN_B)) {
			stackingType = ElevatorConstants.carryingBinUp;
		}
		if (operator.getRawButton(InputConstants.BTN_A)) {
			stackingType = ElevatorConstants.carryingBinDown;
		}
		// x for totes
		// When x pressed, set heights to tote heights.
		if (operator.getRawButton(InputConstants.BTN_X)) {
			stackingType = ElevatorConstants.carryingTote;
		}

		// D Pad input from driver
		getPov = operator.getPOV();
		// Gets current pot value from the Elevator
		double curPot = elevator.getPot();

		// If Dpad is in any of the upwards 3 position, increment the Elevator
		// Position.
		if ((getPov == 0 || getPov == 45 || getPov == 315) && !upDIsPressed) {
			if (elevatorPosition < 6) {
				elevatorPosition++;
				iCounter = 0;
			}
			upDIsPressed = true;

		}
		// Same as before, but decrements the Elevator Position when dpad is
		// one of the downwards 3 positions
		else if ((getPov == 180 || getPov == 215 || getPov == 135)
				&& !downDIsPressed) {
			if (elevatorPosition > 0) {
				elevatorPosition--;
				iCounter = 0;
			}
			downDIsPressed = true;

		}

		// When the Dpad is not pressed, set the booleans for isPressed to both
		// false.
		if (getPov == -1) {
			upDIsPressed = false;
			downDIsPressed = false;
		}

		// TOTE POSITION START \\
		// SetElevator Position
		// if (operator.getRawButton(InputConstants.BTN_A)) {
		// elevatorPosition = 0;
		// iCounter = 0;
		// }
		// if (operator.getRawButton(InputConstants.BTN_B)) {
		// elevatorPosition = 1;
		// iCounter = 0;
		//
		// }
		// if (operator.getRawButton(InputConstants.BTN_Y)) {
		// elevatorPosition = 2;
		// iCounter = 0;
		// }
		// if (operator.getRawButton(InputConstants.BTN_X)) {
		// elevatorPosition = 3;
		// iCounter = 0;
		// }

		// Trimming
		double trim = operator.getRawAxis(InputConstants.AXIS_RIGHT_Y);
		trim *=  0.005;
		if (Math.abs(trim) > 0.05 * 0.005) {
			switch (stackingType) {
			case ElevatorConstants.carryingBinUp:
				switch (elevatorPosition) {
				case 0:
					if ((binPickup + trim > ElevatorConstants.ELEVATOR_TOP && binPickup
							+ trim < ElevatorConstants.ELEVATOR_BOTTOM)) {
						binPickup += trim;
					}
					break;
				case 1:
					if ((binCarrying + trim > ElevatorConstants.ELEVATOR_TOP && binCarrying
							+ trim < ElevatorConstants.ELEVATOR_BOTTOM)) {
						binCarrying += trim;
					}
					break;
				case 2:
					if ((oneBin + trim > ElevatorConstants.ELEVATOR_TOP && oneBin
							+ trim < ElevatorConstants.ELEVATOR_BOTTOM)) {
						oneBin += trim;
					}
					break;
				case 3:
					if ((twoBins + trim > ElevatorConstants.ELEVATOR_TOP && twoBins
							+ trim < ElevatorConstants.ELEVATOR_BOTTOM)) {
						twoBins += trim;
					}
					break;
				case 4:
					if ((threeBins + trim > ElevatorConstants.ELEVATOR_TOP && threeBins
							+ trim < ElevatorConstants.ELEVATOR_BOTTOM)) {
						threeBins += trim;
					}
					break;
				case 5:
					if ((fourBins + trim > ElevatorConstants.ELEVATOR_TOP && fourBins
							+ trim < ElevatorConstants.ELEVATOR_BOTTOM)) {
						fourBins += trim;
					}
					break;
				case 6:

					if ((fiveBins + trim > ElevatorConstants.ELEVATOR_TOP && fiveBins
							+ trim < ElevatorConstants.ELEVATOR_BOTTOM)) {
						fiveBins += trim;
					}
					break;
				}
				break;
			case ElevatorConstants.carryingTote:

				switch (elevatorPosition) {

				case 0:
					if ((totePickup + trim > ElevatorConstants.ELEVATOR_TOP && totePickup
							+ trim < ElevatorConstants.ELEVATOR_BOTTOM)) {
						totePickup += trim;
					}
					break;
				case 1:
					if ((toteCarrying + trim > ElevatorConstants.ELEVATOR_TOP && toteCarrying
							+ trim < ElevatorConstants.ELEVATOR_BOTTOM)) {
						toteCarrying += trim;
					}
					break;
				case 2:
					if ((oneTote + trim > ElevatorConstants.ELEVATOR_TOP && oneTote
							+ trim < ElevatorConstants.ELEVATOR_BOTTOM)) {
						oneTote += trim;
					}
					break;
				case 3:
					if ((twoTotes + trim > ElevatorConstants.ELEVATOR_TOP && twoTotes
							+ trim < ElevatorConstants.ELEVATOR_BOTTOM)) {
						twoTotes += trim;
					}
					break;
				case 4:
					if ((threeTotes + trim > ElevatorConstants.ELEVATOR_TOP && threeTotes
							+ trim < ElevatorConstants.ELEVATOR_BOTTOM)) {
						threeTotes += trim;
					}
					break;
				case 5:
					if ((fourTotes + trim > ElevatorConstants.ELEVATOR_TOP && fourTotes
							+ trim < ElevatorConstants.ELEVATOR_BOTTOM)) {
						fourTotes += trim;
					}
					break;
				case 6:

					if ((fiveTotes + trim > ElevatorConstants.ELEVATOR_TOP && fiveTotes
							+ trim < ElevatorConstants.ELEVATOR_BOTTOM)) {
						fiveTotes += trim;
					}
					break;
				}
				break;
			case ElevatorConstants.carryingBinDown:
				switch (elevatorPosition) {
				case 0:
					if ((binPickup + trim > ElevatorConstants.ELEVATOR_TOP && binPickup
							+ trim < ElevatorConstants.ELEVATOR_BOTTOM)) {
						binPickup += trim;
					}
					break;
				case 1:
					if ((binCarrying + trim > ElevatorConstants.ELEVATOR_TOP && binCarrying
							+ trim < ElevatorConstants.ELEVATOR_BOTTOM)) {
						binCarrying += trim;
					}
					break;
				case 2:
					if ((oneBinDown + trim > ElevatorConstants.ELEVATOR_TOP && oneBinDown
							+ trim < ElevatorConstants.ELEVATOR_BOTTOM)) {
						oneBinDown += trim;
					}
					break;
				case 3:
					if ((twoBinsDown + trim > ElevatorConstants.ELEVATOR_TOP && twoBinsDown
							+ trim < ElevatorConstants.ELEVATOR_BOTTOM)) {
						twoBinsDown += trim;
					}
					break;
				case 4:
					if ((threeBinsDown + trim > ElevatorConstants.ELEVATOR_TOP && threeBinsDown
							+ trim < ElevatorConstants.ELEVATOR_BOTTOM)) {
						threeBinsDown += trim;
					}
					break;
				case 5:
					if ((fourBinsDown + trim > ElevatorConstants.ELEVATOR_TOP && fourBinsDown
							+ trim < ElevatorConstants.ELEVATOR_BOTTOM)) {
						fourBinsDown += trim;
					}
					break;
				case 6:

					if ((fiveBinsDown + trim > ElevatorConstants.ELEVATOR_TOP && fiveBinsDown
							+ trim < ElevatorConstants.ELEVATOR_BOTTOM)) {
						fiveBinsDown += trim;
					}
					break;
				}
				break;

			}
		}
		
		if(operator.getRawButton(InputConstants.BTN_L1)){
			elevatorPosition = 1;
		}
		if(operator.getRawButton(InputConstants.BTN_L2)){
			elevatorPosition = 0;
		}
		if(operator.getRawButton(InputConstants.BTN_START)){
			// Set values to predetermined heights.

			totePickup = ElevatorConstants.ELEVATOR_TOTEPICKUP;
			toteCarrying = ElevatorConstants.ELEVATOR_TOTECARRYING;
			oneTote = ElevatorConstants.ELEVATOR_ONETOTES;
			twoTotes = ElevatorConstants.ELEVATOR_TWOTOTES;
			threeTotes = ElevatorConstants.ELEVATOR_THREETOTES;
			fourTotes = ElevatorConstants.ELEVATOR_FOURTOTES;
			fiveTotes = ElevatorConstants.ELEVATOR_FIVETOTES;

			binPickup = ElevatorConstants.ELEVATOR_BINPICKUP;
			binCarrying = ElevatorConstants.ELEVATOR_BINCARRYING;
			oneBin = ElevatorConstants.ELEVATOR_ONEBINS;
			twoBins = ElevatorConstants.ELEVATOR_TWOBINS;
			threeBins = ElevatorConstants.ELEVATOR_THREEBINS;
			fourBins = ElevatorConstants.ELEVATOR_FOURBINS;
			fiveBins = ElevatorConstants.ELEVATOR_FIVEBINS;

			oneBinDown = ElevatorConstants.ELEVATOR_ONEBINS_DOWN;
			twoBinsDown = ElevatorConstants.ELEVATOR_TWOBINS_DOWN;
			threeBinsDown = ElevatorConstants.ELEVATOR_THREEBINS_DOWN;
			fourBinsDown = ElevatorConstants.ELEVATOR_FOURBINS_DOWN;
			fiveBinsDown = ElevatorConstants.ELEVATOR_FIVEBINS_DOWN;
		}
		
		// If we are stacking containers
		switch (stackingType) {
		case ElevatorConstants.carryingBinUp:
			switch (elevatorPosition) {
			case 0:
				targetSetpoint = binPickup;

				break;
			case 1:
				targetSetpoint = binCarrying;

				break;
			case 2:
				targetSetpoint = oneBin;

				break;
			case 3:
				targetSetpoint = twoBins;
				break;
			case 4:
				targetSetpoint = threeBins;
				break;
			case 5:
				targetSetpoint = fourBins;
				break;
			case 6:
				targetSetpoint = fiveBins;
				break;
			}
			break;

		// If isStacking is false, then we are stacking totes.
		case ElevatorConstants.carryingTote:
			switch (elevatorPosition) {

			case 0:
				targetSetpoint = totePickup;

				break;
			case 1:
				targetSetpoint = toteCarrying;

				break;
			case 2:
				targetSetpoint = oneTote;

				break;
			case 3:
				targetSetpoint = twoTotes;
				break;
			case 4:
				targetSetpoint = threeTotes;
				break;
			case 5:
				targetSetpoint = fourTotes;
				break;
			case 6:
				targetSetpoint = fiveTotes;
				break;
			}
			break;
		// bin down
		case ElevatorConstants.carryingBinDown:
			switch (elevatorPosition) {

			case 0:
				targetSetpoint = binPickup;

				break;
			case 1:
				targetSetpoint = binCarrying;

				break;
			case 2:
				targetSetpoint = oneBinDown;

				break;
			case 3:
				targetSetpoint = twoBinsDown;
				break;
			case 4:
				targetSetpoint = threeBinsDown;
				break;
			case 5:
				targetSetpoint = fourBinsDown;
				break;
			case 6:
				targetSetpoint = fiveBinsDown;
				break;
			}
			break;
		}
		
		// To display to Dashboard what is being carried 
		String stackingMode = "";
		switch(stackingType){
		case ElevatorConstants.carryingBinUp:
			stackingMode = "Bin Standing Up";
			break;
		case ElevatorConstants.carryingBinDown:
			stackingMode = "Bin Lying Down";
			break;
		case ElevatorConstants.carryingTote:
			stackingMode = "Totes";
		}
		
		// TOTE POSITION STOP \\
		SmartDashboard.putString("Bin Mode:", stackingMode);
		SmartDashboard.putNumber("Position", elevatorPosition);

		SmartDashboard.putNumber("binPickup:", binPickup);
		SmartDashboard.putNumber("binCarrying:", binCarrying);
		SmartDashboard.putNumber("oneBin:", oneBin);
		SmartDashboard.putNumber("twoBins:", twoBins);
		SmartDashboard.putNumber("threeBins:", threeBins);
		SmartDashboard.putNumber("fourBins:", fourBins);
		SmartDashboard.putNumber("fiveBins:", fiveBins);

		SmartDashboard.putNumber("totePickup:", totePickup);
		SmartDashboard.putNumber("toteCarrying:", toteCarrying);
		SmartDashboard.putNumber("oneTote:", oneTote);
		SmartDashboard.putNumber("twoTotes:", twoTotes);
		SmartDashboard.putNumber("threeTotes:", threeTotes);
		SmartDashboard.putNumber("fourTotes:", fourTotes);
		SmartDashboard.putNumber("fiveTotes:", fiveTotes);
		
		SmartDashboard.putNumber("oneBinDown:", oneBinDown);
		SmartDashboard.putNumber("twoBinsDown:", twoBinsDown);
		SmartDashboard.putNumber("threeBinsDown:", threeBinsDown);
		SmartDashboard.putNumber("fourBinsDown:", fourBinsDown);
		SmartDashboard.putNumber("fiveBinsDown:", fiveBinsDown);

		// Error for P
		double error = 0;

		// Pot Cap

		// Calculate error
		error = curPot - targetSetpoint;
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
//		elevator.setMotor(0);

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
