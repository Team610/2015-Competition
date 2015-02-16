package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.constants.ElevatorConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;


public class G_StepBinCentre extends CommandGroup {
    
    public  G_StepBinCentre() {
    	//Reset the encoders and gyro.
    	
    	//Drive forward up to the step, set the wings open, bring the arm down.
    	//addParallel(new A_SetWingsOpen(true));
    	//addParallel(new A_SetWingsOpen(true));
    	addParallel(new A_SetArmUp(false));
    	addSequential(new A_PositionMove(-51, 0.6)); // 49

		addSequential(new A_PositionMove(150,0.6));
		addSequential(new A_SetWingsOpen(false));
    	
		addSequential(new A_SetArmUp(true));
    	addSequential(new A_Turn(45));
    	addSequential(new A_PositionMove(57,0.6));
    	addSequential(new A_Turn(-74));
    	addParallel(new A_PositionMoveIndefinite(70, 0.4));
		addSequential(new A_SetIntakeOpen(true, true,4));
		addSequential(new A_Wait(0.5));
		addSequential(new A_Elevator(ElevatorConstants.ELEVATOR_BINCARRYING));
    	addSequential(new A_PositionMove(-130, 0.6));
    	addParallel(new A_SetIntakeOpen(true, false,0));
		addSequential(new A_Wait(0.5));
        
    }
}
