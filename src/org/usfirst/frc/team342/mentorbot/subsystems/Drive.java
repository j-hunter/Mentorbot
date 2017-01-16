package org.usfirst.frc.team342.mentorbot.subsystems;

import org.usfirst.frc.team342.mentorbot.DriveConstants;
import org.usfirst.frc.team342.mentorbot.RobotMap;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drive extends Subsystem {
	public enum DriveSide {
		FR(1), FL(2), RR(3), RL(4);

		public int value;

		private DriveSide(int side) {
			value = side;
		}

	}

	public class SwerveDrive {

		// Drive Talons
		private final CANTalon FRDrive;
		private final CANTalon FLDrive;
		private final CANTalon RRDrive;
		private final CANTalon RLDrive;

		// Angle Talons
		private final CANTalon FRAngle;
		private final CANTalon FLAngle;
		private final CANTalon RRAngle;
		private final CANTalon RLAngle;

		// Alignment inputs
		private final DigitalInput FRRef;
		private final DigitalInput FLRef;
		private final DigitalInput RRRef;
		private final DigitalInput RLRef;

		// Is facing forward?
		private boolean FRRotated;
		private boolean FLRotated;
		private boolean RRRotated;
		private boolean RLRotated;

		// Target Angles
		private double FRTarg;
		private double FLTarg;
		private double RRTarg;
		private double RLTarg;

		// Direction for angles
		private double FRDir;
		private double FLDir;
		private double RRDir;
		private double RLDir;

		// Actual Angles
		private double FRActual;
		private double FLActual;
		private double RRActual;
		private double RLActual;

		private int initLevel;

		public SwerveDrive(CANTalon FRD, CANTalon FLD, CANTalon RRD, CANTalon RLD, CANTalon FRA, CANTalon FLA,
				CANTalon RRA, CANTalon RLA, int FRR, int FLR, int RRR, int RLR) {

			this(FRD, FLD, RRD, RLD, FRA, FLA, RRA, RLA, new DigitalInput(FRR), new DigitalInput(FLR),
					new DigitalInput(RRR), new DigitalInput(RLR), 4096);
		}

		public SwerveDrive(CANTalon FRD, CANTalon FLD, CANTalon RRD, CANTalon RLD, CANTalon FRA, CANTalon FLA,
				CANTalon RRA, CANTalon RLA, int FRR, int FLR, int RRR, int RLR, int encCountSize) {
			this(FRD, FLD, RRD, RLD, FRA, FLA, RRA, RLA, new DigitalInput(FRR), new DigitalInput(FLR),
					new DigitalInput(RRR), new DigitalInput(RLR), encCountSize);
		}

		public SwerveDrive(CANTalon FRD, CANTalon FLD, CANTalon RRD, CANTalon RLD, CANTalon FRA, CANTalon FLA,
				CANTalon RRA, CANTalon RLA, DigitalInput FRR, DigitalInput FLR, DigitalInput RRR, DigitalInput RLR) {

			this(FRD, FLD, RRD, RLD, FRA, FLA, RRA, RLA, FRR, FLR, RRR, RLR, 4096);

		}

		public SwerveDrive(CANTalon FRD, CANTalon FLD, CANTalon RRD, CANTalon RLD, CANTalon FRA, CANTalon FLA,
				CANTalon RRA, CANTalon RLA, DigitalInput FRR, DigitalInput FLR, DigitalInput RRR, DigitalInput RLR,
				int encCountSize) {

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

			initLevel = 0;
		}

		public boolean startInit() {
			// If we haven't started the reference process, start now; do init
			// does nothing at initLevel 0
			if (initLevel == 0)
				initLevel = 1;
			doInit();
			return initLevel >= 10;
		}

		private void commonInit() {
			FRAngle.changeControlMode(CANTalon.TalonControlMode.Position);
			FLAngle.changeControlMode(CANTalon.TalonControlMode.Position);
			RRAngle.changeControlMode(CANTalon.TalonControlMode.Position);
			RLAngle.changeControlMode(CANTalon.TalonControlMode.Position);

			FRAngle.reverseSensor(DriveConstants.FREncReverse);
			FLAngle.reverseSensor(DriveConstants.FLEncReverse);
			RRAngle.reverseSensor(DriveConstants.RREncReverse);
			RLAngle.reverseSensor(DriveConstants.RLEncReverse);
		}

		private void doInit(){
			
			double frrefpos = DriveConstants.FRRefPos;
			double flrefpos = DriveConstants.FLRefPos;
			double rrrefpos = DriveConstants.RRRefPos;
			double rlrefpos = DriveConstants.RLRefPos;
			double reftollerance = DriveConstants.refTollerance;

			
			switch (initLevel){
			case 0:
				//do nothing, explicitly
				break;
			case 1:
				commonInit();
				
				FRAngle.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
				FLAngle.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
				RRAngle.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
				RLAngle.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
				
				initLevel = 2;
				break;
				
			case 2:
				

				FRAngle.set(frrefpos);
				FLAngle.set(flrefpos);
				RRAngle.set(rrrefpos);
				RLAngle.set(rlrefpos);
				
				initLevel = 3;
				break;
				
			case 3:
				updateData();

				if(Math.abs(FRActual - frrefpos) < reftollerance &&
						Math.abs(FLActual - flrefpos) < reftollerance &&
						Math.abs(RRActual - rrrefpos) < reftollerance &&
						Math.abs(RLActual - rlrefpos) < reftollerance)
					initLevel = 4;
				break;
				
			case 4:
				initLevel = 10;
				break;
			}
		}

		public void updateData() {

		}

		public void set(double angle, double power) {
			// TODO Auto-generated method stub

		}

		public void flipWheel(DriveSide side) {
			// TODO : Find best way to simply flip a wheel
		}

		public int getEnc(DriveSide side) {
			int value = 0;

			switch (side) {
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

	// Drive Talons
	private final CANTalon FRDrive;
	private final CANTalon FLDrive;
	private final CANTalon RRDrive;
	private final CANTalon RLDrive;

	// Angle Talons
	private final CANTalon FRAngle;
	private final CANTalon FLAngle;
	private final CANTalon RRAngle;
	private final CANTalon RLAngle;

	// The swerve drive
	private SwerveDrive sDrive;

	public Drive() {

		// Initialize talons
		FRDrive = new CANTalon(RobotMap.FRDriveAddr);
		FLDrive = new CANTalon(RobotMap.FLDriveAddr);
		RRDrive = new CANTalon(RobotMap.RRDriveAddr);
		RLDrive = new CANTalon(RobotMap.RLDriveAddr);

		FRAngle = new CANTalon(RobotMap.FRAngleAddr);
		FLAngle = new CANTalon(RobotMap.FLAngleAddr);
		RRAngle = new CANTalon(RobotMap.RRAngleAddr);
		RLAngle = new CANTalon(RobotMap.RLAngleAddr);

		sDrive = new SwerveDrive(FRDrive, FLDrive, RRDrive, RLDrive, FRAngle, FLAngle, RRAngle, RLAngle, 1, 2, 3, 4);

		driveInit();
	}

	private void driveInit() {
		// Set encoders

	};

	public void cartDrive(double xIn, double yIn) {
		double angle = Math.atan2(yIn, yIn);
		double power = Math.sqrt((yIn * yIn) + (xIn * xIn));

		sDrive.set(angle, power);
	}

	public void updateDriveData() {
		sDrive.updateData();
	}

	public int getEnc(DriveSide side) {
		return sDrive.getEnc(side);
	}

	public boolean checkReference() {
		return checkReference(1) && checkReference(2) && checkReference(3) && checkReference(4);
	}

	public boolean checkReference(int ID) {
		return true;
	}
	
	public boolean refSwerve(){
		return sDrive.startInit();
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
