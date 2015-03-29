package org.usfirst.frc.team610.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class G_Block extends CommandGroup {

	public G_Block() {
		// Drive forward up to the step, set the wings open, bring the arm down.
		addParallel(new A_SetWingsOpen(true)); 

		addSequential(new A_PositionMoveArm(-40, 1));//35 block
		addSequential(new A_PositionMoveArm(0, 1)); // 56, 50


	}
}
