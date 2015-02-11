package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.constants.ElectricalConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class A_ForwardBack extends CommandGroup {
    
    public  A_ForwardBack() {
    	
    	addSequential(new T_ResetSensors());
    	addParallel(new A_PositionMove(12, 1));
    	addParallel(new A_SetWings(ElectricalConstants.CROSSBOW_UP));
    	addSequential(new A_SetArm(ElectricalConstants.CROSSBOW_DOWN));
    	addSequential(new A_PositionMove(-12,0.5));
    	addParallel(new A_Winch(2200));
    	addSequential(new A_SetArm(ElectricalConstants.CROSSBOW_UP));
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
