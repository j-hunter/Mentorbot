package org.usfirst.frc.team342.mentorbot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;

	//Drive Addresses
	public static final int FRDriveAddr = 1;
	public static final int FLDriveAddr = 6;
	public static final int RRDriveAddr = 3;
	public static final int RLDriveAddr = 4;
	
	//Angle addresses
	public static final int FRAngleAddr = 5;
	public static final int FLAngleAddr = 2;
	public static final int RRAngleAddr = 5;
	public static final int RLAngleAddr = 5;
}
