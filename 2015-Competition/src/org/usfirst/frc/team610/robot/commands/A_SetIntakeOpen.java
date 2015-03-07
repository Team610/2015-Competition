package org.usfirst.frc.team610.robot.commands;


import org.usfirst.frc.team610.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class A_SetIntakeOpen extends Command {
	// The singleton bumper.
	private Intake intake;
	// The value to return in isFinished.
	private boolean finished;
	// The position that we want to set the intake to.
	private boolean position;
	// Whether or not we should use the sensor
	private boolean sensor;
	private boolean sensed = false;
	double timeout = 0;
	Timer timer;

	public A_SetIntakeOpen(boolean position, boolean sensor, double timeout) {
		// Get the singleton bumper.
		intake = Intake.getInstance();
		// Save the position to the local variable.
		this.sensor = sensor;
		this.position = position;
		this.timeout = timeout;
		if (timeout != 0) {
			setTimeout(timeout);
		}
		timer = new Timer();
		timer.reset();
		
		requires(intake);
	}

	// Unused
	protected void initialize() {
		System.out.println("A_SetIntakeOpen");
		timer.start();

	}

	protected void execute() {
		// Set the position of the arms.
		if (sensor) {
			if (intake.getOptical()) {
				intake.setIntakeSpeed(1);
				intake.setIntakeOpen(true);
			} else if(!intake.getOptical()&&timer.get()>3){
				intake.setIntakeOpen(false);
				intake.setIntakeSpeed(0);
				sensed = true;
			}
		} else {

			intake.setIntakeOpen(position);
		}

		// Finish the command.
		finished = true;

	}

	// returns true if arms are moved to correct position
	protected boolean isFinished() {
		System.out.println("A_SetIntakeOpen is finished");
		if (sensor) {
			return sensed||isTimedOut();
		} else if (timeout == 0) {

			return finished;

		} else {

			return isTimedOut();
		}

	}

	// Unused
	protected void end() {
		intake.setIntakeSpeed(0);

	}

	// Unused
	protected void interrupted() {
	}
}
