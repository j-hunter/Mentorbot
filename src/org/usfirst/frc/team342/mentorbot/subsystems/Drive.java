package org.usfirst.frc.team342.mentorbot.subsystems;

import org.usfirst.frc.team342.mentorbot.DriveConstants;
import org.usfirst.frc.team342.mentorbot.RobotMap;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive extends Subsystem {
	public enum DriveSide {
		FR(1), FL(2), RR(3), RL(4), None(0);

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

		// Encoder data
		private int encPerWheelRot;
		private int encPerEncRot;

		private int initLevel;

		private boolean[] angSwitching = new boolean[4];

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

			for (boolean angSw : angSwitching) {
				angSw = false;
			}

			encPerWheelRot = DriveConstants.ENCPERWHEEL;
			encPerEncRot = DriveConstants.ENCCOUNT;
		}

		public boolean startInit() {
			// If we haven't started the reference process, start now; do init
			// does nothing at initLevel 0
			if (initLevel == 0 || initLevel == 10)
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
			
			FRAngle.reverseOutput(DriveConstants.FRMotReverse);
			FLAngle.reverseOutput(DriveConstants.FLMotReverse);
			RRAngle.reverseOutput(DriveConstants.RRMotReverse);
			RLAngle.reverseOutput(DriveConstants.RLMotReverse);
			
			/*
			 * FRAngle.setPulseWidthPosition(Math.floorMod(FRAngle.
			 * getPulseWidthPosition() , encPerWheelRot));
			 * FLAngle.setPulseWidthPosition(Math.floorMod(FLAngle.
			 * getPulseWidthPosition() , encPerWheelRot));
			 * RRAngle.setPulseWidthPosition(Math.floorMod(RRAngle.
			 * getPulseWidthPosition() , encPerWheelRot));
			 * RLAngle.setPulseWidthPosition(Math.floorMod(RLAngle.
			 * getPulseWidthPosition() , encPerWheelRot));
			 * 
			 * 
			 */

			FRAngle.setP(1);
			FLAngle.setP(1);
			RRAngle.setP(1);
			RLAngle.setP(1);

			FRAngle.enable();
			FLAngle.enable();
			RRAngle.enable();
			RLAngle.enable();

		}

		public void doInit() {

			int absFR, absFL, absRR, absRL;

			SmartDashboard.putInt("InitLevel", initLevel);
			switch (initLevel) {
			case 0:
				// do nothing, explicitly
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
				//
				absFR = FRAngle.getPulseWidthPosition() % DriveConstants.ENCCOUNT;
				absFL = FLAngle.getPulseWidthPosition();
				absRR = RRAngle.getPulseWidthPosition();
				absRL = RLAngle.getPulseWidthPosition();

				FRAngle.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
				FLAngle.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
				RRAngle.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
				RLAngle.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);

				FRAngle.setEncPosition(absFR + DriveConstants.FRAngOffset);
				FLAngle.setEncPosition(absFL + DriveConstants.FLAngOffset);
				RRAngle.setEncPosition(absRR + DriveConstants.RRAngOffset);
				RLAngle.setEncPosition(absRL + DriveConstants.RLAngOffset);

				initLevel = 3;
				break;

			case 3:// drive.setzero?
				updateData();

				initLevel = 4;
				break;

			case 4:

				initLevel = 10;

				break;
			}
		}

		public void updateData() {
			
			FRActual = FRAngle.getPosition();
			FLActual = FLAngle.getPosition();
			RRActual = RRAngle.getPosition();
			RLActual = RLAngle.getPosition();
			SmartDashboard.putDouble("FRAngle", FRActual);
		}

		public void set(double angle, double power) {
			// TODO Auto-generated method stub

			SmartDashboard.putDouble("angle", angle);
			updateData();
			setAngle(DriveSide.FR, angle);
			setAngle(DriveSide.FL, angle);
			setAngle(DriveSide.RR, angle);
			setAngle(DriveSide.RL, angle);

			setDriveVolt(power * 0.5);
		}

		private void resetEnc(DriveSide side) {
			CANTalon talon = decSideAng(side);
			int newEnc = 0;
			switch (side) {
			case FR:
				newEnc = talon.getPulseWidthPosition() + DriveConstants.FRAngOffset;
				break;
			case FL:
				newEnc = talon.getPulseWidthPosition() + DriveConstants.FLAngOffset;
				break;
			case RR:
				newEnc = talon.getPulseWidthPosition() + DriveConstants.RRAngOffset;
				break;
			case RL:
				newEnc = talon.getPulseWidthPosition() + DriveConstants.RLAngOffset;
				break;
			}
			talon.setEncPosition(newEnc);

		}

		public void setAngle(DriveSide side, double angle) {
			CANTalon talon = decSideAng(side);
			double actual = 0.0;

			switch (side) {
			case FR:
				actual = FRActual;
				break;
			case FL:
				actual = FLActual;
				break;
			case RR:
				actual = RRActual;
				break;
			case RL:
				actual = RLActual;
				break;
			}
			if (actual < 0.0 || actual > 1.0) {
				resetEnc(side);
			}
			if (Math.abs(angle - actual) > 0.5) {
				if (angle > actual) {

					angle = angle + 1.0;
				} else {
					angle = angle - 1.0;
				}

			}
			talon.set(angle);
		}

		public void setDriveVolt(double speed) {
			FRDrive.set(speed);
			FLDrive.set(speed);
			RRDrive.set(speed);
			RLDrive.set(speed);
		}

		public boolean flipWheel(DriveSide side) {
			// TODO : Find best way to simply flip a wheel
			CANTalon talon = decSideAng(side);
			double tempPos;
			boolean answer = false;
			SmartDashboard.putString("TalonAddr", Integer.toString(talon.getDeviceID()));
			if (angSwitching[side.value - 1]) {
				SmartDashboard.putDouble("RevWheelMovingPos", talon.getPosition());
				answer = (Math.abs(talon.getClosedLoopError()) < DriveConstants.ENCERROR);
				if (answer) {
					talon.disable();
					angSwitching[side.value - 1] = false;
				}
			} else {
				SmartDashboard.putString("RevStart", "InFalse");
				talon.enable();
				angSwitching[side.value - 1] = true;
				tempPos = talon.getPosition();
				talon.setPulseWidthPosition(talon.getPulseWidthPosition() + (encPerWheelRot / 2));
				SmartDashboard.putDouble("RevWheelInitPos", tempPos);
				talon.set(tempPos);
			}
			return answer;
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

		public CANTalon decSideAng(DriveSide side) {
			CANTalon answer = null;
			switch (side) {
			case FR:
				answer = FRAngle;
				break;
			case FL:
				answer = FLAngle;
				break;
			case RR:
				answer = RRAngle;
				break;
			case RL:
				answer = RLAngle;
				break;
			}
			return answer;
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
		//double angle = Math.atan(yIn / xIn);
		double power = Math.sqrt((yIn * yIn) + (xIn * xIn));
		if (power > 0.1)
			sDrive.set(calcAngle(xIn, yIn) / 360.0, power);
		else
			sDrive.setDriveVolt(0.0);
	}

	public double calcAngle(double x, double y) {

		double angle = 0.0;

		angle = Math.atan(y / x);
		angle = Math.toDegrees(angle);
		if (Math.abs(x) > 0.001) {
			if (x > 0.0) {

				angle = angle + 90.0;

			} else {

				angle = angle + 270;
			}
		} else {
			if (y < 0.0)
				angle = 0;
			else
				angle = 180;
		}
		return angle;

	}

	public void singleDriveControlMode(DriveSide side, int mode) {
		CANTalon talon = sDrive.decSideAng(side);

	}

	public void singleDrive(DriveSide side, double speed) {
		CANTalon talon = sDrive.decSideAng(side);

		talon.setControlMode(CANTalon.TalonControlMode.Voltage.value);
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

	public boolean refSwerve() {
		return sDrive.startInit();
	}

	public boolean revWheel(DriveSide side) {
		return sDrive.flipWheel(side);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
