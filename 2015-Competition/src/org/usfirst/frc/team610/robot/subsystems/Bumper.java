package org.usfirst.frc.team610.robot.subsystems;

import org.usfirst.frc.team610.robot.constants.ElectricalConstants;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author Jamie
 */
public class Bumper extends Subsystem {

	// DoubleSolenoid for the arm
	DoubleSolenoid arm;
	// DoubleSolenoid for the wings
	DoubleSolenoid wings;

	Talon winch;
	boolean wingsOpen = true;

	// PDP in order to check the current to the winch, to prevent the motor from
	// stalling
	PowerDistributionPanel pdp;
	
	public static boolean armsIsOut = true;

	// Singleton instance of the Bumper
	static Bumper instance;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private Bumper() {
		// Init of DoubleSolenoids,Encoder, and PowerDistributionPanel
		arm = new DoubleSolenoid(ElectricalConstants.BUMPER_SOLENOID_ARM1,
				ElectricalConstants.BUMPER_SOLENOID_ARM2);
		wings = new DoubleSolenoid(ElectricalConstants.BUMPER_SOLENOID_WING1,
				ElectricalConstants.BUMPER_SOLENOID_WING2);
		winch = new Talon(ElectricalConstants.TALON_WINCH);

		pdp = new PowerDistributionPanel();
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	// Returns the Singleton Instance of the Bumper
	public static Bumper getInstance() {
		if (instance == null) {
			instance = new Bumper();
		}
		return instance;
	}
	public void setWinch(double speed){
		winch.set(speed);
	}

	// Method to change position of the arms, if position is true, bring the
	// arms up. If false, set the arms down
	public void setArmsUp(boolean position) {
		if (!position) {
			arm.set(DoubleSolenoid.Value.kForward);
			armsIsOut = true;
		} else {
			arm.set(DoubleSolenoid.Value.kReverse);
			armsIsOut = false;
		}
	}

	// Method to change position of the wings, if position is true, bring the
	// wings up. If false, set the arms down

	public void setWingsOpen(boolean position) {
		
		
		if (position) {
			wings.set(DoubleSolenoid.Value.kForward);
		} else {
			wings.set(DoubleSolenoid.Value.kReverse);
		}
	}
	public boolean getWingsOpen(){
		if(wings.get()==DoubleSolenoid.Value.kForward){
			return true;
		} else {
			return false;
		}
	}

	
	// Gets the current from the Winch, Currently deprecated.
	public double getCurrent() {
		return pdp.getCurrent(ElectricalConstants.PDP_WINCH_CHANNEL);

	}
	
	//MAY NEED TO FLIP DIRECTION OF WINCH
//	public void setWinch(double speed){
//		winch.set(speed);
//	}
//	
//	public double getWinchEnc(){
//		return winchEnc.getDistance();
//	}
//	public void resetWinchEnc(){
//		winchEnc.reset();
//	}

}
