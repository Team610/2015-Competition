package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.constants.ElevatorConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class G_BinTwoLeft extends CommandGroup {

	/**
	 *  Autonomous CommandGroup to start in the staging zone, and bring two bins into the auto zone.  
	 */
	public G_BinTwoLeft() {
		//Close the Intake on the Bin Directly in front of us. 
		addParallel(new A_SetIntakeOpen(false, false,0));
		//Wait half a second to make sure we got the bin. 		
		addSequential(new A_Wait(0.5));
		//Set the elevator position to the BinCarrying height. 
		addSequential(new A_Elevator(ElevatorConstants.ELEVATOR_BINCARRYING));
		//Turn 90 degrees clockwise, to face the Step. 
		addSequential(new A_Turn(90));
		//Move forward 85 inches, to bring the Bin into the Auto Zone. 
		addSequential(new A_PositionMove(85, 0.6));
		//Open the intake
		addParallel(new A_SetIntakeOpen(true, false,0));
		addSequential(new A_Wait(0.5));

		//Move backwards 73 inches, while running the rollers in reverse to make sure we leave the first bin in the auto zone. 
		addSequential(new A_PositionMoveRollers(-73, 0.6));
		addSequential(new A_Wait(0.5));

		//Set the Elevator to the BinPickup Preset. 
		addParallel(new A_Elevator(ElevatorConstants.ELEVATOR_BINPICKUP));

		//Turn Clockwise 87 degrees. 
		addSequential(new A_Turn(87));
		//Move forward 50 inches, to start lining up with the second bin. 
		addParallel(new A_PositionMoveIndefinite(50, 0.6));
		//Set the intake open. 
		addSequential(new A_SetIntakeOpen(true, true,2));

		//Set the Elevator to the BinCarrying Preset.
		addSequential(new A_Elevator(ElevatorConstants.ELEVATOR_BINCARRYING));
		//Turn 90 degrees clockwise
		addSequential(new A_Turn(90));
		//Move 110 inches backwards, towards the Auto Zone
		addSequential(new A_PositionMove(-110, 0.8));
		//Turn 90 degrees clockwise. 
		addSequential(new A_Turn(90));
		//Open the Intake
		addParallel(new A_SetIntakeOpen(true, false,0));
		//Outtake the Intake, within the Auto Zone. 
		addSequential(new A_PositionMoveRollers(-10, 1));


	}
}
