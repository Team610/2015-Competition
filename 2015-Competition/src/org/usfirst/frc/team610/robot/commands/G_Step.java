package org.usfirst.frc.team610.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;


public class G_Step extends CommandGroup {
    
    public  G_Step() {
    	//Reset the encoders and gyro.
//    	addSequential(new T_ResetSensors());
    	
    	//Drive forward up to the step, set the wings open, bring the arm down.
    	addParallel(new A_SetWingsOpen(true));

    	addSequential(new A_PositionMoveArm(-51, 1)); // 49
    	

    	//Drive backwards and cap the speed at 0.5.
    	addSequential(new A_PositionMove(150,1));
    	addSequential(new A_SetArmUp(true));
//    	  addSequential(new A_Turn(3));
//          addParallel(new A_SetIntaking(1,5));
//          addParallel(new A_SetIntakeOpen(true,true,0));
//          addSequential(new A_PositionMove(120,1));


    	//Bring the arm back up.
        
    }
}
