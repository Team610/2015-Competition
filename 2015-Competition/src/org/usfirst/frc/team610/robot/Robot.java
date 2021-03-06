package org.usfirst.frc.team610.robot;

import org.usfirst.frc.team610.robot.commands.D_SensorReadings;
import org.usfirst.frc.team610.robot.commands.G_BinTwoLeft;
import org.usfirst.frc.team610.robot.commands.G_BinTwoRight;
import org.usfirst.frc.team610.robot.commands.G_Block;
import org.usfirst.frc.team610.robot.commands.G_BlockThenFour;
import org.usfirst.frc.team610.robot.commands.G_NoAuto;
import org.usfirst.frc.team610.robot.commands.G_Step;
import org.usfirst.frc.team610.robot.commands.G_StepBinCentre;
import org.usfirst.frc.team610.robot.commands.G_StepBinLeft;
import org.usfirst.frc.team610.robot.commands.T_TeleopGroup;
import org.usfirst.frc.team610.robot.constants.ElectricalConstants;
import org.usfirst.frc.team610.robot.constants.InputConstants;
import org.usfirst.frc.team610.robot.subsystems.Bumper;
import org.usfirst.frc.team610.robot.subsystems.DriveTrain;
import org.usfirst.frc.team610.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	// Readings pushes values to the SD.
	private Command readings;
	// Commands to run at the beginning of teleop and auto.

	//CommandGroup which will be instantiated to the desired Autonomous mode. 
	private CommandGroup auto;
	//CommandGroup which will be instantiated to our Tele-op commandGroup. 
	private CommandGroup teleop;
	//Get input from the driver/operator.
	OI oi;
	Joystick driver,operator;

	// Command to run during auto.
	// 0: None
	// 1: Two Step
	// 2: Block Then Four
	// 3: Two Bins Left
	// 4: Two Step Bin Centre
	// 5: Two Step Bin Left
	// 6: Step block
	private int autoMode = 0;

	public void robotInit() {

		//Get the Singleton instance of the OI.
		oi = OI.getInstance();
		
		// instantiate the command used for the autonomous and teleop period
		readings = new D_SensorReadings();
		// Start pushing values to the SD.
		readings.start();
		//Gets the single instances of driver and operator, from OI. 
		driver = oi.getDriver();
		operator = oi.getOperator();

		//Sets our default auto to NoAuto, if the remainder of our code doesn't seem to work. 
		auto = new G_NoAuto();
		
		//Sets our teleop commandGroup to T_TeleopGroup, which contains Kaj,ELevator, Intake, and Crossbow Commands. 
		teleop = new T_TeleopGroup();
		
	}
	public void disabledInit(){

	}
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		PowerDistributionPanel pdp = new PowerDistributionPanel();
		SmartDashboard.putNumber("Right Distance",
				DriveTrain.getInstance().getRightDistance());
		SmartDashboard.putNumber("Left Distance", DriveTrain.getInstance().getLeftDistance());
		SmartDashboard.putNumber("Gyro", DriveTrain.getInstance().getYaw());
		SmartDashboard.putNumber("Current Draw of Elevator", pdp.getCurrent(2));
		SmartDashboard.putNumber("Current Draw of Winch", pdp.getCurrent(ElectricalConstants.PDP_WINCH_CHANNEL));
		SmartDashboard.putNumber("Elevator Pot", Elevator.getInstance().getPot());
		SmartDashboard.putBoolean("Arm is out", Bumper.armsIsOut);
		SmartDashboard.putBoolean("Wings Up", Bumper.getInstance().getWingsOpen());
       
		//Get the desired Autonomous mode from the drive team, and set the autoMode's value accordingly. 
		if(driver.getRawButton(InputConstants.BTN_A)){
			autoMode = 1;
		}else if(driver.getRawButton(InputConstants.BTN_X)){
			autoMode = 2;
		} else if(driver.getRawButton(InputConstants.BTN_B)){
//			autoMode = 3;
		}else if(driver.getRawButton(InputConstants.BTN_Y)){
//			autoMode = 4;
		}else if(operator.getRawButton(InputConstants.BTN_A)){
//			autoMode = 5;
		} else if(operator.getRawButton(InputConstants.BTN_X)){
			autoMode = 6;
		}
			else if (operator.getRawButton(InputConstants.BTN_B)){
		
			autoMode = 0;
		}
		
		//Switch to push the current value of autoMode to the SmartDashboad, so the Drive Team can be sure as to what Auto will run.
		switch (autoMode) {
        case 0:
            SmartDashboard.putString("Auto", "No Auto");
            break;
        case 1:
            SmartDashboard.putString("Auto", "Four Step");

            break;
        case 2:
            SmartDashboard.putString("Auto", "Block Then Four");

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
        case 6:
            SmartDashboard.putString("Auto", "Step Block");
            break;
    
    }
		

	}

	public void autonomousInit() {
		// Start autonomous and cancel teleop.
		//Instantiate auto as whatever auto CommandGroup we decided to run. 
		switch (autoMode) {
		case 0:
			auto = new G_NoAuto();
			break;
		case 1:
			auto = new G_Step();
			break;
		case 2:
			auto = new G_BlockThenFour();
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
		case 6:
			auto = new G_Block();
			break;
		}

		//Start the chosen Autonomous command. 
		auto.start();
		//Cancel the tele-op command, in case it was running before autonomous began. 
		teleop.cancel();
		// Start pushing sensor readings to the SD.
		readings.start();

	}

	public void autonomousPeriodic() {
		Scheduler.getInstance().run();

	}

	public void teleopInit() {
		// Cancel the autonomous in case it didn't end after Auto.
		auto.cancel();
		// Create the teleop command and start it.
		teleop.start();
		// Reset the drivetrain encoders
		DriveTrain.getInstance().resetEncoders();
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
