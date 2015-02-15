package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.OI;
import org.usfirst.frc.team610.robot.constants.InputConstants;
import org.usfirst.frc.team610.robot.subsystems.Bumper;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class T_Bumper extends Command {
	Bumper bumper;
	OI oi;
	boolean wingsButtonPressed = false;
	boolean wingsOpen = false;

	public T_Bumper() {
		// Get the singleton bumper and OI
		bumper = Bumper.getInstance();
		oi = OI.getInstance();
		requires(bumper);
	}

	protected void initialize() {
		System.out.println("T_Bumper");
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		if (oi.getDriver().getRawButton(InputConstants.BTN_A)) {
			bumper.setArmsUp(true);
		} else if (oi.getDriver().getRawButton(InputConstants.BTN_B)) {
			bumper.setArmsUp(false);

		}
		if (oi.getDriver().getRawButton(InputConstants.BTN_X)
				&& !wingsButtonPressed) {
			wingsOpen = !wingsOpen;
			wingsButtonPressed = true;
		} else if (!oi.getDriver().getRawButton(InputConstants.BTN_X)) {
			wingsButtonPressed = false;
		}

		bumper.setWingsOpen(wingsOpen);
		if(oi.getDriver().getRawButton(InputConstants.BTN_Y)){
			bumper.setWinchVbus(0.7);
		} else {
			bumper.setWinchVbus(0);
		}

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
