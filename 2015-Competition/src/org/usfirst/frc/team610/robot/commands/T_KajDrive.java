package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.OI;
import org.usfirst.frc.team610.robot.constants.ElectricalConstants;
import org.usfirst.frc.team610.robot.constants.InputConstants;
import org.usfirst.frc.team610.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class T_KajDrive extends Command {

	
	// Singleton instance of the driver
	private Joystick driver;
	// Singleton instance of the operator
	private Joystick operator;
	// Singleton instance of the drive train
	private DriveTrain driveTrain;

	
    public T_KajDrive() {
    	driver = OI.getInstance().getDriver();
    	operator = OI.getInstance().getOperator();
    	driveTrain = DriveTrain.getInstance();
    	requires(driveTrain);
    }

    
    
    // Called just before this Command runs the first time

    protected void initialize() {
		System.out.println("T_KajDrive");

    	driveTrain.zeroYaw();
    	driveTrain.resetEncoders();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    
    double lastError;
    double intergral = 0;
    protected void execute() {
    	
    	//Left and right speed that will be set to the motors
    	double leftSpeed, rightSpeed;
    	//The right x and left y values on the joystick
    	double x, y;
 
		//Set the x and y variables to the joystick values
    	x = driver.getRawAxis(InputConstants.AXIS_RIGHT_X);
    	y = -driver.getRawAxis(InputConstants.AXIS_LEFT_Y);
    	
    	
    	//Set right and left speed
    	leftSpeed = y + x;
    	rightSpeed = y - x;

    	
    	//Set left and right speed to the motors
    	driveTrain.setLeft(leftSpeed);
    	driveTrain.setRight(rightSpeed);
    	
    	
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
