package org.usfirst.frc.team610.robot.constants;

public class ElectricalConstants {
	
	public static final int TALON_LEFT_FRONT = 1;
	public static final int TALON_LEFT_BACK = 2;
	public static final int TALON_RIGHT_FRONT = 3;
	public static final int TALON_RIGHT_BACK = 4;
	//public static final int TALON_MID = 5;
	public static final int TALON_ELEVATOR = 0;
	public static final int TALON_LEFT_ROLLER = 6;
	public static final int TALON_RIGHT_ROLLER = 8;
	public static final int TALON_WINCH = 5;
	public static final int PDP_WINCH_CHANNEL = 13;
	
	public static final int INTAKE_SOL_F = 3;
	public static final int INTAKE_SOL_R = 2;	
	
	public static final boolean CROSSBOW_DOWN = false;
	public static final boolean CROSSBOW_UP = true;
	public static final double ENCODER_INCHES = 0.0772635814889336 ;
	public static final double GYRO_P = -0.035; //-0.035
	public static final double GYRO_D = -0.225; //0.012
	public static final double ENCODER_P = 0.1; //0.1
	public static final double ENCODER_D = 5; //3
	public static final int POT_ANALOGPORT = 0;
	public static final int OPTICALSENSOR_PORT = 4;
	
	public static final double ELEVATOR_BOTTOM = 0.87; // real bottom 0.89 //Middle .60
	public static final double ELEVATOR_TOTEPICKUP = 0.80;
	public static final double ElEVATOR_MID = 0.45;
	public static final double ELEVATOR_TOP = 0.125;   //0.121 top
	public static final double ELEVATOR_P  = 16; //12
	public static final double ELEVATOR_I  = 0.005;//0.0025
	public static final double ELEVATOR_ONETOTES = 0.668;
	public static final double ELEVATOR_TWOTOTES = 0.53;
	public static final double ELEVATOR_THREETOTES = 0.38;
	public static final double ELEVATOR_FOURTOTES = 0.24;
	public static final double ELEVATOR_FIVETOTES = 0.15;
	
	public static final int BUMPER_SOLENOID_ARM1 = 0;
	public static final int BUMPER_SOLENOID_ARM2 = 1;
	public static final int BUMPER_SOLENOID_WING1 = 6;
	public static final int BUMPER_SOLENOID_WING2 = 7;
	public static final int BUMPER_ENCODER_WINCH1 = 5;
	public static final int BUMPER_ENCODER_WINCH2 = 6;
	
}
