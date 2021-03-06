package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.OI;
import org.usfirst.frc.team610.robot.constants.ElectricalConstants;
import org.usfirst.frc.team610.robot.subsystems.Bumper;
import org.usfirst.frc.team610.robot.subsystems.DriveTrain;
import org.usfirst.frc.team610.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class D_SensorReadings extends Command {

	// The singleton  DriveTrain
	DriveTrain driveTrain;
	// Power Distribution Panel, to get Current from the motors
	PowerDistributionPanel pdp;
	// The singleton  Elevator
	Elevator elevator;
	
	Bumper bumper;

	public D_SensorReadings() {
		// Gets the singleton instance of the drivetrain
		driveTrain = DriveTrain.getInstance();
		// Gets the singleton instance of the elevator
		elevator = Elevator.getInstance();
		bumper = Bumper.getInstance();
		pdp = new PowerDistributionPanel();
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		System.out.println("D_SensorReadings");

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// System.out.println("Gyro: " + driveTrain.getYaw());
		// System.out.println("Right Distance: " +
		// driveTrain.getRightDistance());
		// System.out.println("Left Distance: " + driveTrain.getLeftDistance());
		//
		// System.out.println("Pot" + elevator.getPot());

		//Place numbers to the SmartDashboard
		SmartDashboard.putNumber("Right Distance",
				driveTrain.getRightDistance());
		SmartDashboard.putNumber("Left Distance", driveTrain.getLeftDistance());
		SmartDashboard.putNumber("Gyro", driveTrain.getYaw());
		SmartDashboard.putNumber("Current Draw of Elevator", pdp.getCurrent(2));
		SmartDashboard.putNumber("Current Draw of Winch", pdp.getCurrent(ElectricalConstants.PDP_WINCH_CHANNEL));
		SmartDashboard.putNumber("Elevator Pot", elevator.getPot());
		SmartDashboard.putBoolean("Arm is out", Bumper.armsIsOut);
		SmartDashboard.putBoolean("Wings Up", bumper.getWingsOpen());

	}

	// Make this return true when this Command no longer needs to run execute()
	//D_SensorReadings never finishes
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
