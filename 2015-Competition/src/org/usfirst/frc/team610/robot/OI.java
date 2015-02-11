package org.usfirst.frc.team610.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class OI extends Subsystem {
	
	Joystick driver;
	Joystick operator;
    static OI instance;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private OI(){
		driver = new Joystick(0);
		operator = new Joystick(1);
		
		
	}
	
	public static OI getInstance(){
		if(instance == null){
			instance = new OI();
			
		}
		return instance;
	}
	public Joystick getDriver(){
		return driver;
	}
	public Joystick getOperator(){
		return operator;
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

