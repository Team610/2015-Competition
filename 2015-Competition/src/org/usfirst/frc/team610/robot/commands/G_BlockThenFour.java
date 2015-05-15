package org.usfirst.frc.team610.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class G_BlockThenFour extends CommandGroup {

	public G_BlockThenFour() {
		// Drive forward up to the step, set the wings open, bring the arm down.
		addParallel(new A_SetWingsOpen(true));

		addSequential(new A_PositionMoveArm(-35, 1)); // 35 centre block
		addSequential(new A_PositionMoveArm(0, 1)); // 56, 50
		addSequential(new A_Wait(2));
		addSequential(new A_PositionMoveArm(-7, 1)); // 35 centre block
		addSequential(new A_Wait(1));
		// Drive backwards and cap the speed at 0.5.
		addSequential(new A_PositionMove(115, 1)); // 100
		// Bring the arm back up
		addSequential(new A_SetArmUp(true));
		addSequential(new A_Wait(1));// 0.5
		addSequential(new A_PositionMove(-55, 1)); // 100

	}
}
