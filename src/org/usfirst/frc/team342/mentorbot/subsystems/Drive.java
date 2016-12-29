package org.usfirst.frc.team342.mentorbot.subsystems;


import org.usfirst.frc.team342.mentorbot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drive extends Subsystem {

	public class SwerveDrive {
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
		
		
		public SwerveDrive(CANTalon FRD, CANTalon FLD,
				CANTalon RRD, CANTalon RLD, CANTalon FRA,
				CANTalon FLA, CANTalon RRA, CANTalon RLA){
			FRDrive = FRD;
			FLDrive = FLD;
			RRDrive = RRD;
			RLDrive = RLD;
			
			FRAngle = FRA;
			FLAngle = FLA;
			RRAngle = RRA;
			RLAngle = RLA;
			
		}
		
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
	
	public Drive(){
		
		//Initialize talons
		FRDrive = new CANTalon(RobotMap.FRDriveAddr);
		FLDrive = new CANTalon(RobotMap.FLDriveAddr);
		RRDrive = new CANTalon(RobotMap.RRDriveAddr);
		RLDrive = new CANTalon(RobotMap.RLDriveAddr);
		
		FRAngle = new CANTalon(RobotMap.FRAngleAddr);
		FLAngle = new CANTalon(RobotMap.FLAngleAddr);
		RRAngle = new CANTalon(RobotMap.RRAngleAddr);
		RLAngle = new CANTalon(RobotMap.RLAngleAddr);
		
	};

	public boolean checkReference(){
		return checkReference(1) && checkReference(2) &&
				checkReference(3) && checkReference(4);
	}
	
	public boolean checkReference(int ID){
		return true;
	}
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
