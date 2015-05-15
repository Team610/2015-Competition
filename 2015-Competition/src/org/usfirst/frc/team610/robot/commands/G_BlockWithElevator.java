package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.constants.ElevatorConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class G_BlockWithElevator extends CommandGroup {

	public G_BlockWithElevator() {
		// Drive forward up to the step, set the wings open, bring the arm down.
		addParallel(new A_SetWingsOpen(true)); 
		addParallel(new A_Elevator(ElevatorConstants.ELEVATOR_THREETOTES));
		addSequential(new A_PositionMoveArm(-37, 1)); //35 centre block
		addSequential(new A_PositionMoveArm(0, 1)); // 56, 50


	}
}
