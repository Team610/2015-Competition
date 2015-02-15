package org.usfirst.frc.team610.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;


public class A_ForwardBack extends CommandGroup {
    
    public  A_ForwardBack() {
    	//Reset the encoders and gyro.
//    	addSequential(new T_ResetSensors());
    	
    	//Drive forward up to the step, set the wings open, bring the arm down.
    	addParallel(new A_SetWingsOpen(true));

    	addSequential(new A_PositionMoveArm(-49, 1));
    	

    	//Drive backwards and cap the speed at 0.5.
    	addSequential(new A_PositionMove(140,1));
    	addSequential(new A_SetArmUp(true));



    	//Bring the arm back up.
        
    }
}
