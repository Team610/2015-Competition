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

	Joystick driver;
	Joystick operator;
	
	DriveTrain driveTrain;
	
    public T_KajDrive() {
    	driver = OI.getInstance().getDriver();
    	operator = OI.getInstance().getOperator();
    	driveTrain = DriveTrain.getInstance();
    	
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    
    
    // Called just before this Command runs the first time
    double error;
    double tAngle;
    protected void initialize() {
    	driveTrain.zeroYaw();
    	error = driveTrain.getYaw();
    	driveTrain.resetEncoders();
    	tAngle = driveTrain.getYaw();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    
    double lastError;
    double intergral = 0;
    protected void execute() {
    	double leftSpeed, rightSpeed, x, y;
 
		
    	x = driver.getRawAxis(InputConstants.AXIS_RIGHT_X);
    	y = -driver.getRawAxis(InputConstants.AXIS_LEFT_Y);
    	x = x * x * x;  	
    	
    	leftSpeed = y + x;
    	rightSpeed = y - x;

    	
    	
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
