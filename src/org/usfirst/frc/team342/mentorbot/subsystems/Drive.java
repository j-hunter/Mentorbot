package org.usfirst.frc.team342.mentorbot.subsystems;


import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drive extends Subsystem {

	public class SwerveDrive {
		
	}
	
	//Drive Talons
	private final CANTalon FRDrive;
	private final CANTalon FLDrive;
	private final CANTalon RRDrive;
	private final CANTalon RLDrive;
	
	//Angle Talons
	private final CANTalon FRAngle;
	private final CANTalon FLAngle;
	private final CANTalon RRAngle;
	private final CANTalon RLAngle;

	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
