package org.usfirst.frc.team610.robot.subsystems;

import org.usfirst.frc.team610.robot.constants.ElectricalConstants;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author Jamie
 */
public class Intake extends Subsystem {
    Talon leftRoller,rightRoller;
	static Intake instance;
	DoubleSolenoid intakeSol;
	
	//How does robot start
	boolean isOpen = true;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private Intake(){
		leftRoller = new Talon(ElectricalConstants.TALON_LEFT_ROLLER);
		rightRoller = new Talon(ElectricalConstants.TALON_RIGHT_ROLLER);
		intakeSol = new DoubleSolenoid(ElectricalConstants.INTAKE_SOL_F, ElectricalConstants.INTAKE_SOL_R);
		
	}
    
	//Returns the Intake	
	public static Intake getInstance(){
		if(instance == null){
			instance = new Intake();
		}
		return instance;
	}
	
	public void setIntakeSpeed(int v){
		//Can add battery management stuff later on.
		//One of these will eventually be negative, have to wait for finished robot.
		leftRoller.set(-v);
		rightRoller.set(v);
		
		
	}
	public void setIntakeOpen(boolean open){
		isOpen = open;
		if(isOpen){
		intakeSol.set(DoubleSolenoid.Value.kForward);
		}else{
			intakeSol.set(DoubleSolenoid.Value.kReverse);
		}
	}
	public boolean getIntakePos(){
		return isOpen;
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

