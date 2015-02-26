package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.constants.ElevatorConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class G_BinOne extends CommandGroup {
	/**
	 * CommandGroup for oneBin Auton, which brings a single recycling bin from the staging zone to the auto zone. 
	 */
	public G_BinOne() {
		//Closes the intake on the bin, to intake it.  
		addParallel(new A_SetIntakeOpen(false, false,0));
		//Waits, to ensure Intake has correctly obtained the bin. 
		addSequential(new A_Wait(0.5));
		//Sets the Elevator Height to the BinCarrying Preset. 
		addSequential(new A_Elevator(ElevatorConstants.ELEVATOR_BINCARRYING));
		//Turns 90 degrees clockwise, to start the move towards the Auto Zone
		addSequential(new A_Turn(90));
		// 33
		
		//Move forward 75 inches, into the auto zone. 
		addSequential(new A_PositionMove(75, 0.6));
		//Open the intake, to drop the bin. 
		addParallel(new A_SetIntakeOpen(true, false,0));
		//Give the intake a second to open. 
		addSequential(new A_Wait(0.5));

		//Outtake the bin, and bring the elevator down to the binPickup height, in order to prepare for tele-op. 
		addSequential(new A_PositionMoveRollers(-75, 0.6));
		addParallel(new A_Elevator(ElevatorConstants.ELEVATOR_BINPICKUP));

		//Turn anti-clockwise 90 degrees, to face the step. 
		addSequential(new A_Turn(-90));

	}
}
