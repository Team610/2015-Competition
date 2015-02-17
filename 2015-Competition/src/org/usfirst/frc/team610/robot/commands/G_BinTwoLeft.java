package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.constants.ElevatorConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class G_BinTwoLeft extends CommandGroup {

	public G_BinTwoLeft() {
		addParallel(new A_SetIntakeOpen(false, false,0));
		addSequential(new A_Wait(0.5));
		addSequential(new A_Elevator(ElevatorConstants.ELEVATOR_BINCARRYING));
		addSequential(new A_Turn(90));
		addSequential(new A_PositionMove(85, 0.6));
		addParallel(new A_SetIntakeOpen(true, false,0));
		addSequential(new A_Wait(0.5));

		addSequential(new A_PositionMoveRollers(-73, 0.6));
		addSequential(new A_Wait(0.5));

		addParallel(new A_Elevator(ElevatorConstants.ELEVATOR_BINPICKUP));

		addSequential(new A_Turn(87));
		addParallel(new A_PositionMoveIndefinite(50, 0.6));
		addSequential(new A_SetIntakeOpen(true, true,2));

		addSequential(new A_Elevator(ElevatorConstants.ELEVATOR_BINCARRYING));
		addSequential(new A_Turn(90));
		addSequential(new A_PositionMove(-110, 0.8));
		addSequential(new A_Turn(90));

		addParallel(new A_SetIntakeOpen(true, false,0));
		addSequential(new A_PositionMoveRollers(-10, 1));


	}
}
