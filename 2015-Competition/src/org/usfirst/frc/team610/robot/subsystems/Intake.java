package org.usfirst.frc.team610.robot.subsystems;

import org.usfirst.frc.team610.robot.constants.ElectricalConstants;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author Jamie
 */
public class Intake extends Subsystem {
	Talon leftRoller, rightRoller;
	static Intake instance;
	DoubleSolenoid intakeSol;
	PowerDistributionPanel pdp;
	// Optical Sensor on intake, to detect totes.
	private DigitalInput optical;
	boolean isOpen = true;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	private Intake() {
		pdp = new PowerDistributionPanel();
		leftRoller = new Talon(ElectricalConstants.TALON_LEFT_ROLLER);
		rightRoller = new Talon(ElectricalConstants.TALON_RIGHT_ROLLER);
		intakeSol = new DoubleSolenoid(ElectricalConstants.INTAKE_SOL_F,
				ElectricalConstants.INTAKE_SOL_R);
		optical = new DigitalInput(ElectricalConstants.OPTICALSENSOR_PORT);

	}

	// Returns the Intake
	public static Intake getInstance() {
		if (instance == null) {
			instance = new Intake();
		}
		return instance;
	}

	public boolean getOptical() {
		return optical.get();
	}

	public void setIntakeSpeed(double v) {
		// Can add battery management stuff later on.
		// One of these will eventually be negative, have to wait for finished
		// robot.
		if (pdp.getCurrent(ElectricalConstants.PDP_LEFTROLLER_CHANNEL) > 50
				|| pdp.getCurrent(ElectricalConstants.PDP_RIGHTROLLER_CHANNEL) > 50) {
			leftRoller.set(0);
			rightRoller.set(0);
		} else {
			leftRoller.set(-v);
			rightRoller.set(v);
		}

	}

	public void setLeftRoller(double v) {
		if (pdp.getCurrent(ElectricalConstants.PDP_LEFTROLLER_CHANNEL) > 50) {
			leftRoller.set(0);
		} else {
			leftRoller.set(-v);
		}

	}

	public void setRightRoller(double v) {
		if (pdp.getCurrent(ElectricalConstants.PDP_RIGHTROLLER_CHANNEL) > 50) {
			rightRoller.set(0);
		} else {
			rightRoller.set(v);
		}
	}

	public void setIntakeSameSpeed(double v) {
		if (pdp.getCurrent(ElectricalConstants.PDP_LEFTROLLER_CHANNEL) > 50
				|| pdp.getCurrent(ElectricalConstants.PDP_RIGHTROLLER_CHANNEL) > 50) {

			leftRoller.set(0);
			rightRoller.set(0);
		} else {
			leftRoller.set(v);
			rightRoller.set(v);
		}
	}

	public void setIntakeOpen(boolean open) {
		isOpen = open;
		if (isOpen) {
			intakeSol.set(DoubleSolenoid.Value.kReverse);

		} else {
			intakeSol.set(DoubleSolenoid.Value.kForward);

		}
	}

	public boolean getIntakePos() {
		return isOpen;
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
