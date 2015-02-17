package org.usfirst.frc.team610.robot;

import org.usfirst.frc.team610.robot.commands.G_NoAuto;
import org.usfirst.frc.team610.robot.commands.D_SensorReadings;
import org.usfirst.frc.team610.robot.commands.G_BinTwoLeft;
import org.usfirst.frc.team610.robot.commands.G_BinTwoRight;
import org.usfirst.frc.team610.robot.commands.G_Step;
import org.usfirst.frc.team610.robot.commands.G_StepBinCentre;
import org.usfirst.frc.team610.robot.commands.G_StepBinLeft;
import org.usfirst.frc.team610.robot.commands.G_TurnTest;
import org.usfirst.frc.team610.robot.commands.T_TeleopGroup;
import org.usfirst.frc.team610.robot.constants.InputConstants;
import org.usfirst.frc.team610.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	// Readings pushes values to the SD.
	private Command readings;
	// Commands to run at the beginning of teleop and auto.

	private CommandGroup auto,noAuto,twoBinStep,twoBinLeft,twoBinRight,twoStepBinCentre,twoStepBinLeft;
	private CommandGroup teleop;
	OI oi;
	Joystick driver,operator;

	// Command to run during auto.
	// 0: None
	// 1: Two Step
	// 2: Two Bins Right
	// 3: Two Bins Left
	// 4: Two Step Bin Centre
	// 5: Two Step Bin Left
	private int autoMode = 0;

	public void robotInit() {

		oi = OI.getInstance();
		
		// instantiate the command used for the autonomous and teleop period
		readings = new D_SensorReadings();
		// Start pushing values to the SD.
		readings.start();
		driver = oi.getDriver();
		operator = oi.getOperator();

		auto = new G_NoAuto();
		
		
		teleop = new T_TeleopGroup();

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
       
		if(driver.getRawButton(InputConstants.BTN_A)){
			autoMode = 1;
		}else if(driver.getRawButton(InputConstants.BTN_X)){
			autoMode = 2;
		} else if(driver.getRawButton(InputConstants.BTN_B)){
			autoMode = 3;
		}else if(driver.getRawButton(InputConstants.BTN_Y)){
			autoMode = 4;
		}else if(operator.getRawButton(InputConstants.BTN_A)){
			autoMode = 5;
		}else if (operator.getRawButton(InputConstants.BTN_B)){
			autoMode = 0;
		}
		
		
		switch (autoMode) {
        case 0:
            SmartDashboard.putString("Auto", "No Auto");
            break;
        case 1:
            SmartDashboard.putString("Auto", "Two Step");

            break;
        case 2:
            SmartDashboard.putString("Auto", "Two Right");

            break;
        case 3:
            SmartDashboard.putString("Auto", "Two Left");
            break;
        case 4:
            SmartDashboard.putString("Auto", "Two Step Bin Centre");
            break;
        case 5:
            SmartDashboard.putString("Auto", "Two Step Bin Left");
            break;
    
    }
		

	}

	public void autonomousInit() {
		// Start autonomous and cancel teleop.
		switch (autoMode) {
		case 0:
			auto = new G_NoAuto();
			break;
		case 1:
			auto = new G_Step();
			break;
		case 2:
			auto = new G_BinTwoRight();
			break;
		case 3:
			auto = new G_BinTwoLeft();
			break;
		case 4:
			auto = new G_StepBinCentre();
			break;
		case 5:
			auto = new G_StepBinLeft();
			break;
		}

		auto.start();
		teleop.cancel();
		// Start pushing sensor readings to the SD.
		readings.start();

	}

	public void autonomousPeriodic() {
		Scheduler.getInstance().run();

	}

	public void teleopInit() {
		// Cancel the autonomous in case it didn't end in time.
		auto.cancel();
		// Create the teleop command and start it.
		teleop.start();
		// Reset the drivetrain encoders
		DriveTrain.getInstance().resetEncoders();
		// Start pushing sensor readings to the SD.
		readings.start();
	}

	public void disabledInit() {
		// Start pushing sensor readings to the SD.
		readings.start();
	}

	public void teleopPeriodic() {
		Scheduler.getInstance().run();

	}

	public void testPeriodic() {
		LiveWindow.run();
	}
}
