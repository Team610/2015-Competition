package org.usfirst.frc.team610.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;


public class A_ForwardBack extends CommandGroup {
    
    public  A_ForwardBack() {
    	//Reset the encoders and gyro.
    	addSequential(new T_ResetSensors());
    	//Drive forward up to the step, set the wings open, bring the arm down.
    	addParallel(new A_PositionMove(-54, 1));
    	addParallel(new A_SetWingsOpen(true));
    	addSequential(new A_SetArmUp(false));
    	//Drive backwards and cap the speed at 0.5.
    	addSequential(new A_PositionMove(54,0.5));
    	//Bring the arm back up.
    	addSequential(new A_SetArmUp(true));
        
    }
}
