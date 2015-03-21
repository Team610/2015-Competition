package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.OI;
import org.usfirst.frc.team610.robot.constants.InputConstants;
import org.usfirst.frc.team610.robot.constants.PIDConstants;
import org.usfirst.frc.team610.robot.subsystems.Bumper;
import org.usfirst.frc.team610.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class T_Bumper extends Command {
	private Bumper bumper;
	private DriveTrain driveTrain;
	private OI oi;
	private boolean wingsButtonPressed = false;
	private boolean wingsOpen = false;

	public T_Bumper() {
		// Get the singleton bumper and OI
		bumper = Bumper.getInstance();
		driveTrain = DriveTrain.getInstance();
		oi = OI.getInstance();
		requires(bumper);
	}

	protected void initialize() {
		wingsOpen = false;
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
		
		//winch should only run when the wings are open
		if(wingsOpen){
			//unwind winch to max unwind position
			if(oi.getDriver().getRawButton(InputConstants.BTN_L1)){
								
				double diff = bumper.getWinchEnc() - PIDConstants.winchMin;
				bumper.setWinch(diff * PIDConstants.winchD);
				
			}
			//wind up winch to max wind position
			else if(oi.getDriver().getRawButton(InputConstants.BTN_L2)){
				double diff = bumper.getWinchEnc() - PIDConstants.winchMax;
				bumper.setWinch(diff * PIDConstants.winchD);
			}
			else{
				bumper.setWinch(0);
			}
		}

		SmartDashboard.putNumber("Pitch", driveTrain.getPitch());

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
