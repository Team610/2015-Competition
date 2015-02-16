package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.OI;
import org.usfirst.frc.team610.robot.constants.InputConstants;
import org.usfirst.frc.team610.robot.subsystems.DriveTrain;
import org.usfirst.frc.team610.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class T_Intake extends Command {

	// Singleton instance of the OI
	private OI oi;
	// Singleton instance of the Intake
	private Intake intake;
	// Singleton instance of the driver;
	private Joystick driver;
	boolean intaking = false;
	// Timer for the 0.5 second delay on the intake.
	Timer rollerTimer = new Timer();

	public T_Intake() {
		// Singleton Instance of
		oi = OI.getInstance();
		intake = Intake.getInstance();
		driver = oi.getDriver();
		requires(intake);
	}

	protected void initialize() {
		System.out.println("T_Intake");

	}

	protected void execute() {

		SmartDashboard.putBoolean("Optical Sensor", intake.getOptical());

		if (driver.getRawButton(InputConstants.BTN_L2)) {
			intake.setIntakeOpen(true);
			intake.setLeftRoller(DriveTrain.getInstance().getLeftVbus());
			intake.setRightRoller(DriveTrain.getInstance().getRightVbus());
			
		} else // Set intake pistons with buttons.
		if (driver.getRawButton(InputConstants.BTN_L1)) {
			intake.setIntakeOpen(true);

		} else if (driver.getRawButton(InputConstants.BTN_R1)) {

			intake.setIntakeSpeed(1);
		} else if (driver.getRawButton(InputConstants.BTN_R2) && !intaking) {
			// if the button is pressed and there is an object in the intake
			if (!intake.getOptical()) {
				// Start the intaking routine and reset the timer
				intaking = true;
				rollerTimer = new Timer();
				rollerTimer.reset();
				rollerTimer.start();
				// Close the intake on the object
				intake.setIntakeOpen(false);
			} else {
				// If no object is there yet, leave the intake open and intake.
				intake.setIntakeOpen(true);
				intake.setIntakeSpeed(1);
			}

		} else if(driver.getRawButton(InputConstants.BTN_Y)){
			intake.setIntakeSpeed(-0.75);

		}else {
		
			intake.setIntakeSpeed(0);
		}

		// If the button is released, cancel the intaking
		if (!driver.getRawButton(InputConstants.BTN_R2)) {
			intaking = false;
		}
		// If the robot should be intaking
		if (intaking) {
			intake.setIntakeOpen(false);

			// Run the intake for the first 0.5 second.
			if (rollerTimer.get() < 0.5) {
				intake.setIntakeSpeed(1);
			} else {
				// Stop the intake until the button is released.
				intake.setIntakeSpeed(0);
			}
		}

	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
	}
}
