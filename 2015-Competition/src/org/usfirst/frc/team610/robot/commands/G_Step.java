package org.usfirst.frc.team610.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class G_Step extends CommandGroup {

	public G_Step() {
		// Drive forward up to the step, set the wings open, bring the arm down.
		addParallel(new A_SetWingsOpen(true));

		addSequential(new A_PositionMoveArm(-51, 1)); // 56, 50
		addSequential(new A_Wait(0.5));// 0.5

		// Drive backwards and cap the speed at 0.5.
		addSequential(new A_PositionMove(115, 1)); // 100
		// Bring the arm back up
		addSequential(new A_SetArmUp(true));

	}
}
