package org.usfirst.frc.team610.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;


public class OI extends Subsystem {
	//Jamie's joystick.
	private Joystick driver;
	//Nicholas's joystick.
	private Joystick operator;
	//The instance of the subsystem that is returned in getInstance().
    private static OI instance;

	/**
	 * Creates a new OI.
	 */
	private OI(){
		//Set the driver to port 0 and the operator to port 1.
		driver = new Joystick(0);
		operator = new Joystick(1);
	}
	/**
	 * Get the current instance of OI.
	 * @return the singleton OI.
	 */
	public static OI getInstance(){
		//If an OI has not been created yet,
		if(instance == null){
			//Create a new OI and save it in OI.
			instance = new OI();
		}
		//Return the singleton OI.
		return instance;
	}
	/**
	 * 	Gets Jamie's joystick.
	 * @return Jamie's joystick.
	 */
	
	public Joystick getDriver(){
		return driver;
	}
	
	/**
	 * Get Nicholas's joystick.
	 * @return Nicholas's joystick.
	 */
	public Joystick getOperator(){
		return operator;
	}
	
    public void initDefaultCommand() {
        //There is no default command.
    }
}

