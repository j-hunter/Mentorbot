package org.usfirst.frc.team342.mentorbot.subsystems;


import org.usfirst.frc.team342.mentorbot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drive extends Subsystem {
	public enum DriveSide {
		FR(1), FL(2), RR(3), RL(4);
		
		public int value;
		
		private DriveSide(int side){
			value = side;
		}
		
	}
	
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

		//Alignment inputs
		private final DigitalInput FRRef;
		private final DigitalInput FLRef;
		private final DigitalInput RRRef;
		private final DigitalInput RLRef;
		
		//Is facing forward?
		private boolean FRRotated;
		private boolean FLRotated;
		private boolean RRRotated;
		private boolean RLRotated;
		
		//Target Angles
		private double FRTarg;
		private double FLTarg;
		private double RRTarg;
		private double RLTarg;
		
		//Direction for angles
		private double FRDir;
		private double FLDir;
		private double RRDir;
		private double RLDir;
		
		//Actual Angles
		private double FRActual;
		private double FLActual;
		private double RRActual;
		private double RLActual;

		public SwerveDrive(CANTalon FRD, CANTalon FLD,
				CANTalon RRD, CANTalon RLD, CANTalon FRA,
				CANTalon FLA, CANTalon RRA, CANTalon RLA,
				int FRR, int FLR, int RRR, int RLR){
			
			this.SwerveDrive(FRD, FLD, RRD, RLD
					FRA, FLA, RRA, RLA,
					new DigitalInput(FRR),
					new DigitalInput(FLR),
					new DigitalInput(RRR),
					new DigitalInput(RLR), 4096);
		}
		
		public SwerveDrive(CANTalon FRD, CANTalon FLD,
				CANTalon RRD, CANTalon RLD, CANTalon FRA,
				CANTalon FLA, CANTalon RRA, CANTalon RLA,
				int FRR, int FLR, int RRR, int RLR,
				int encCountSize ){
			this.SwerveDrive(FRD, FLD, RRD, RLD
					FRA, FLA, RRA, RLA,
					new DigitalInput(FRR),
					new DigitalInput(FLR),
					new DigitalInput(RRR),
					new DigitalInput(RLR), encCountSize);
		}

		public SwerveDrive(CANTalon FRD, CANTalon FLD,
				CANTalon RRD, CANTalon RLD, CANTalon FRA,
				CANTalon FLA, CANTalon RRA, CANTalon RLA,
				DigitalInput FRR, DigitalInput FLR,
				DigitalInput RRR, DigitalInput RLR){
			this.SwerveDrive(FRD, FLD, RRD, RLD
					FRA, FLA, RRA, RLA,
					FRR, FLR, RRR, RLR, 4096);

		}

		public SwerveDrive(CANTalon FRD, CANTalon FLD,
				CANTalon RRD, CANTalon RLD, CANTalon FRA,
				CANTalon FLA, CANTalon RRA, CANTalon RLA,
				DigitalInput FRR, DigitalInput FLR,
				DigitalInput RRR, DigitalInput RLR,
				int encCountSize ){

			FRDrive = FRD;
			FLDrive = FLD;
			RRDrive = RRD;
			RLDrive = RLD;
			
			FRAngle = FRA;
			FLAngle = FLA;
			RRAngle = RRA;
			RLAngle = RLA;

			FRRef = FRR;
			FLRef = FLR;
			RRRef = RRR;
			RLRef = RLR;	
		}
		
		public void update(){
			
			
		}

		public void set(double angle, double power) {
			// TODO Auto-generated method stub
			FRAngle.getEncPosition();
		}
		
		public int getEnc(DriveSide side){
			int value = 0;
			
			switch(side){
			case FR:
				value = FRAngle.getEncPosition();
			case FL:
				value = FLAngle.getEncPosition();
			case RR:
				value = RRAngle.getEncPosition();
			case RL:
				value = RLAngle.getEncPosition();
			}

			return value;
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
	
	//The swerve drive
	private SwerveDrive sDrive;
	
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
		
		sDrive = new SwerveDrive(FRDrive, FLDrive, RRDrive,
				RLDrive, FRAngle, FLAngle, RRAngle, RLAngle);
		
		driveInit();
	}

	private void driveInit() {
		//Set encoders
		FRAngle.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		FLAngle.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		RRAngle.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		RLAngle.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		
	};
	
	public void cartDrive(double xIn, double yIn){
		double angle = Math.atan2(yIn, yIn);
		double power = Math.sqrt((yIn * yIn) + (xIn * xIn));
		
		sDrive.set(angle, power);
	}
	
	public void updateDriveData()
	{
		sDrive.update();
	}
	
	public int getEnc(DriveSide side){
		return sDrive.getEnc(side);
	}

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
