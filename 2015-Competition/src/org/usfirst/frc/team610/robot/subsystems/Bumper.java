package org.usfirst.frc.team610.robot.subsystems;

import org.usfirst.frc.team610.robot.constants.ElectricalConstants;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Bumper extends Subsystem {
	
	DoubleSolenoid arm;
	DoubleSolenoid wings;
	Talon winch;
	Encoder winchEncoder;
	PowerDistributionPanel pdp;
	
	static Bumper instance;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private Bumper(){
    	arm = new DoubleSolenoid(ElectricalConstants.BUMPER_SOLENOID_ARM1, ElectricalConstants.BUMPER_SOLENOID_ARM2);
    	wings = new DoubleSolenoid(ElectricalConstants.BUMPER_SOLENOID_WING1, ElectricalConstants.BUMPER_SOLENOID_WING2);
    	winch = new Talon(ElectricalConstants.TALON_WINCH);
    	winchEncoder = new Encoder(ElectricalConstants.BUMPER_ENCODER_WINCH1, ElectricalConstants.BUMPER_ENCODER_WINCH2);
    	pdp = new PowerDistributionPanel();
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public static Bumper getInstance(){
    	if(instance == null){
    		instance = new Bumper();
    	}
    	return instance;
    }
    
    public void setArm(boolean position){
    	if(position){
    		arm.set(DoubleSolenoid.Value.kForward);
    	} else {
    		arm.set(DoubleSolenoid.Value.kReverse);
    	}
    }
    
    public void setWings(boolean position){
    	if(position){
    		wings.set(DoubleSolenoid.Value.kForward);
    	} else {
    		wings.set(DoubleSolenoid.Value.kReverse);
    	}
    }
    
    public void setWinch(double distance){
    	double error = distance - winchEncoder.getDistance();
    	if(Math.abs(error) > 150){
    		winch.set(error);
    	} else {
    		winch.set(0);
    	}
    	
    }
    
    public double getWinchEncoder(){
    	return winchEncoder.getDistance();
    }
    
    public void resetWinchEncoder(){
    	winchEncoder.reset();
    }
    
    public double getCurrent(){
    	return pdp.getCurrent(ElectricalConstants.PDP_WINCH_CHANNEL);
    	
    }
    
    
    
}

