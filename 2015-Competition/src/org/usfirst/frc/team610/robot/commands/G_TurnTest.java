package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.constants.ElevatorConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;


public class G_TurnTest extends CommandGroup {
    
    public  G_TurnTest() {
    	//Reset the encoders and gyro.
    	
    	//Drive forward up to the step, set the wings open, bring the arm down.
    	addSequential(new A_Turn(90));

    }
}
