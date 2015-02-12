package org.usfirst.frc.team610.robot.subsystems;

import org.usfirst.frc.team610.robot.constants.ElectricalConstants;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

/**
 * @author Jamie
 */
public class Elevator extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    Talon motorTalon;
   static Elevator instance;
    AnalogPotentiometer pot;
    //
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    
    }
    public static Elevator getInstance(){
    	if(instance == null){
    		instance =  new Elevator();
    	}
    	return instance;
    }
   
    
    public Elevator(){
    	motorTalon = new Talon(ElectricalConstants.TALON_ELEVATOR);
    	pot = new AnalogPotentiometer(ElectricalConstants.POT_ANALOGPORT);
		}   
    /*
     * 2-3 possible positions?
     */
  
  
    public void setMotor(double upSpeed){
    	//Send positive goes up
    	motorTalon.set(-upSpeed);
    }
    public double getPot(){
    	return pot.get();
    }
    public void resetPot(){
    }
    public void setUpPos(){
    	
    }
    public void setMidPos(){
    	
    }
    public void setLowPos(){
    	
    }

}
