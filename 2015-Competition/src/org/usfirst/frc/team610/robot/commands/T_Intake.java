package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.OI;
import org.usfirst.frc.team610.robot.constants.ElectricalConstants;
import org.usfirst.frc.team610.robot.constants.InputConstants;
import org.usfirst.frc.team610.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;

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

	Timer timer = new Timer();

	public T_Intake() {
		// Singleton Instance of
		oi = OI.getInstance();
		intake = Intake.getInstance();
		driver = oi.getDriver();

	}

	protected void initialize() {
		System.out.println("T_Intake");

	}

	protected void execute() {

		boolean isDetected = false;
		SmartDashboard.putBoolean("Optical Sensor", intake.getOptical());
		// System.out.println(optical.get());

		if (driver.getRawButton(InputConstants.BTN_L2)) {
			// Negative
			intake.setIntakeSpeed(-1);

		} else if (driver.getRawButton(InputConstants.BTN_R1)) {

			intake.setIntakeSpeed(1);
		} else if (driver.getRawButton(InputConstants.BTN_R2)&&!intaking) {
			//if the button is pressed and there is an object in the intake
			if(!intake.getOptical()){
				//Start the intaking routine and reset the timer
				intaking = true;
				timer = new Timer();
				timer.reset();
				timer.start();
				//Close the intake on the object
				intake.setIntakeOpen(false);
			} else {
				//If no object is there yet, leave the intake open and intake.
				intake.setIntakeOpen(true);
				intake.setIntakeSpeed(1);
			}
			
			// Close intake if the sensor detects an object.

//			if (!intake.getOptical()) {
//				intake.setIntakeOpen(false);
//				intake.setIntakeSpeed(0);
//
//			} else {
//				// Positive Intaking
//				intake.setIntakeOpen(true);
//				intake.setIntakeSpeed(1);
//			}
		} else {
			intake.setIntakeSpeed(0);
		}
		//If the button is released, cancel the intaking
		if(!driver.getRawButton(InputConstants.BTN_R2)){
			intaking = false;
		}
		//If the robot should be intaking
		if(intaking){
			intake.setIntakeOpen(false);

			//Run the intake for the first 0.5 second.
			if(timer.get()<0.5){
				intake.setIntakeSpeed(1);
			} else {
				//Stop the intake until the button is released.
				intake.setIntakeSpeed(0);
			}
		}
		
		// Set intake pistons with buttons.
		if (driver.getRawButton(InputConstants.BTN_L1)) {
			intake.setIntakeOpen(true);

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
