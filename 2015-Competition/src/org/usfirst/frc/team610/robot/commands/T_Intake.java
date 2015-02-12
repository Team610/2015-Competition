package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.OI;
import org.usfirst.frc.team610.robot.constants.ElectricalConstants;
import org.usfirst.frc.team610.robot.constants.InputConstants;
import org.usfirst.frc.team610.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
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
	// Optical Sensor on intake, to detect totes.
	private DigitalInput optical;

	public T_Intake() {
		// Singleton Instance of 
		oi = OI.getInstance();
		intake = Intake.getInstance();
		driver = oi.getDriver();
		optical = new DigitalInput(ElectricalConstants.OPTICALSENSOR_PORT);

	}

	protected void initialize() {
	}

	protected void execute() {
		boolean isDetected = false;
		SmartDashboard.putBoolean("Optical Sensor", optical.get());
		// System.out.println(optical.get());

		if (driver.getRawButton(InputConstants.BTN_L2)) {
			// Negative
			intake.setIntakeSpeed(-1);

		} else if (driver.getRawButton(InputConstants.BTN_R2)) {
			// Positive Intaking
			intake.setIntakeSpeed(1);
			if (!optical.get() && !isDetected) {
				isDetected = true;
			}
		} else {
			intake.setIntakeSpeed(0);
		}

		//Close intake if the sensor detects an object.
		if (isDetected) {
			intake.setIntakeOpen(false);
			intake.setIntakeSpeed(0);
			isDetected = false;
		}
		
		//Set intake pistons with buttons.
		if (driver.getRawButton(InputConstants.BTN_L1)) {
			intake.setIntakeOpen(true);

		}
		if (driver.getRawButton(InputConstants.BTN_R1)) {

			intake.setIntakeOpen(false);
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
