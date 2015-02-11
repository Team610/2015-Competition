
package org.usfirst.frc.team610.robot;

import org.usfirst.frc.team610.robot.commands.A_ForwardBack;
import org.usfirst.frc.team610.robot.commands.A_PositionMove;
import org.usfirst.frc.team610.robot.commands.D_SensorReadings;
import org.usfirst.frc.team610.robot.commands.T_Elevator;
import org.usfirst.frc.team610.robot.commands.T_Intake;
import org.usfirst.frc.team610.robot.commands.T_KajDrive;
import org.usfirst.frc.team610.robot.commands.T_ResetSensors;
import org.usfirst.frc.team610.robot.subsystems.DriveTrain;
import org.usfirst.frc.team610.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static OI oi;
	//test
    Command kajDrive;
    Command readings;
    Command reset;
    Command elevator;
    Command intake;
    Command auto;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // instantiate the command used for the autonomous period
        kajDrive = new T_KajDrive();
        readings = new D_SensorReadings();
        reset = new T_ResetSensors();
        auto = new A_PositionMove(-51,0.5);
        elevator = new T_Elevator();
        intake = new T_Intake();
        readings.start();
    }
	
	public void disabledPeriodic() {
    	Scheduler.getInstance().run();
    	System.out.println(DriveTrain.getInstance().getAvgDistance());
//    	System.out.println(Elevator.getInstance().getPot());
		
	}

    public void autonomousInit() {
        // schedule the autonomous command (example)
        readings.start();
    	kajDrive.cancel();
    	auto.start();
    	elevator.cancel();
    	intake.cancel();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        auto.cancel();
        
    	readings.start();
        kajDrive.start();
        elevator.start();
        intake.start();
        DriveTrain.getInstance().resetEncoders();
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){
    	readings.start();
    	// random comment
    }

    /**
     * This function is called periodically during operator control
     */
    
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
       
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
