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

	//Singleton instance of the OI
	OI oi;
	//Singleton instance of the Intake
	Intake intake;
	//Singleton instance of the driver;
	Joystick driver;
	//Optical Sensor on intake, to detect totes. 
	DigitalInput optical;
    public T_Intake() {
        //Gets the singleton in
    	oi = OI.getInstance();
    	intake = Intake.getInstance();
    	driver = oi.getDriver();
    	optical = new DigitalInput(ElectricalConstants.OPTICALSENSOR_PORT);
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	boolean isDetected = false;
    	SmartDashboard.putBoolean("Optical Sensor", optical.get());
    	//System.out.println(optical.get());
    	if(driver.getRawButton(InputConstants.BTN_L2)){
    		//Negative
    		intake.setIntakeSpeed(-1);
    		
    		
    	}else if(driver.getRawButton(InputConstants.BTN_R2)){
    		//Positive Intaking
    		intake.setIntakeSpeed(1);
    		if(!optical.get() && !isDetected){
    			isDetected = true;
    			
    		}
    	}else{
    		intake.setIntakeSpeed(0);
    	}
     
    	
    	if(isDetected){
    		intake.setIntakeOpen(false);
    		intake.setIntakeSpeed(0);
    		isDetected = false;
    	}
    	if(driver.getRawButton(InputConstants.BTN_L1)){
    		intake.setIntakeOpen(true);
    		
    	}
    	if(driver.getRawButton(InputConstants.BTN_R1)){
    		
    		intake.setIntakeOpen(false);
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
