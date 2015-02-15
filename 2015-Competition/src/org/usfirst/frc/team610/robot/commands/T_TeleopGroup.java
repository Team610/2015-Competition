package org.usfirst.frc.team610.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class T_TeleopGroup extends CommandGroup {
    
    public  T_TeleopGroup() {
        addParallel(new T_Elevator());
        addParallel(new T_Intake());
        addParallel(new T_KajDrive());
        addSequential(new T_Bumper());
    }
}
