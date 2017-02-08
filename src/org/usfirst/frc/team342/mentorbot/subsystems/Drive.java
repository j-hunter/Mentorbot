package org.usfirst.frc.team342.mentorbot.subsystems;

import org.usfirst.frc.team342.mentorbot.DriveConstants;
import org.usfirst.frc.team342.mentorbot.RobotMap;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.kauailabs.navx.frc.AHRS;

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
			
			FRAngle.setD(0);

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
				FRAngle.disableControl();
				FLAngle.disableControl();
				RRAngle.disableControl();
				RLAngle.disableControl();
				
				commonInit();

				/*
				FRAngle.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
				FLAngle.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
				RRAngle.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
				RLAngle.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
				*/
				initLevel = 2;
				break;

			case 2:
				//
				/*
				absFR = FRAngle.getPulseWidthPosition() % DriveConstants.ENCCOUNT;
				absFL = FLAngle.getPulseWidthPosition();
				absRR = RRAngle.getPulseWidthPosition();
				absRL = RLAngle.getPulseWidthPosition();
				*/
				FRAngle.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
				FLAngle.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
				RRAngle.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
				RLAngle.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
				/*
				FRAngle.setEncPosition(absFR + DriveConstants.FRAngOffset);
				FLAngle.setEncPosition(absFL + DriveConstants.FLAngOffset);
				RRAngle.setEncPosition(absRR + DriveConstants.RRAngOffset);
				RLAngle.setEncPosition(absRL + DriveConstants.RLAngOffset);
				*/
				resetEnc(DriveSide.FR);
				//resetEnc(DriveSide.FL);
				resetEnc(DriveSide.RR);
				resetEnc(DriveSide.RL);
				resetEnc(DriveSide.FL);
				initLevel = 3;
				break;

			case 3:// drive.setzero?
				updateData();

				FRAngle.enableControl();
				FLAngle.enableControl();
				RRAngle.enableControl();
				RLAngle.enableControl();
				
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
			SmartDashboard.putString("HasReset", "NotYet");

			SmartDashboard.putDouble("angle", angle);
			updateData();
			setAngle(DriveSide.FR, angle);
			setAngle(DriveSide.FL, angle);
			setAngle(DriveSide.RR, angle);
			setAngle(DriveSide.RL, angle);

			setDriveVolt(power );//* 0.5);
		}

		private void resetEnc(DriveSide side) {
			CANTalon talon = decSideAng(side);
			int newEnc = 0;
			int offset = 0;
			int abs = 0;
			switch (side) {
			case FR:
				offset =  DriveConstants.FRAngOffset;
				break;
			case FL:
				offset = DriveConstants.FLAngOffset;
				break;
			case RR:
				offset = DriveConstants.RRAngOffset;
				break;
			case RL:
				offset = DriveConstants.RLAngOffset;
				break;
			}
			abs = talon.getPulseWidthPosition();
			if (abs >= 0){
				newEnc = (abs % 4096) + offset;
			}else{
				newEnc = (4096 + (-1 * (Math.abs(abs) % 4096))) + offset;
			}
				
			
			talon.setEncPosition(newEnc);
			SmartDashboard.putString("HasReset", "Yes");
			SmartDashboard.putInt("abs", abs);
			SmartDashboard.putInt("newEnc", newEnc);
		}

		public void setAngle(DriveSide side, double angle) {
			CANTalon talon = decSideAng(side);
			double actual = 0.0;

			updateData();
			SmartDashboard.putString("rotReady", "FR Not Ready, is" + getRelPos(DriveSide.FR));
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
			/*
			if (actual < 0.0 || actual > 1.0) {
				resetEnc(side);
				updateData();
			}
			*/
		
			if (actual > 0.0){
				angle = angle + Math.floor(actual);
			
			
				if (angle < actual) {
					if (Math.abs(actual - angle) < 0.5) {
						angle = angle;
					} else {
						angle = (angle + 1.0);
					}
				} else {
					if (Math.abs(angle - actual) < 0.5) {
						angle = angle;
					} else {
						angle = (angle - 1.0);
					}
				
				}
			}
			else
			{
				angle = 1.0 - angle;
				angle = angle * -1.0;
				angle = angle - Math.floor(Math.abs(actual));
				
				if (angle < actual) {
					if (Math.abs(actual - angle) < 0.5) {
						angle = angle;
					} else {
						angle = (angle + 1.0);
					}
				} else {
					if (Math.abs(angle - actual) < 0.5) {
						angle = angle;
					} else {
						angle = (angle - 1.0);
					}
				
				}
				
			}
			
			

			
			SmartDashboard.putDouble("Target", angle);
			
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
		
		public double getRelPos(DriveSide side){
			double value = 0.0;
			updateData();
			switch (side) {
			case FR:
				value = FRActual;
				break;
			case FL:
				value = FLActual;
				break;
			case RR:				
				value = RRActual;
				break;
			case RL:
				value = RLActual;
				break;
			}
			
			if (value >= 0.0){
				value = value - Math.floor(value);
			}
			else
			{
				value = value * -1.0;
				value = value - Math.floor(value);
				value = 1.0 - value;
			
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

		public void spin(double rot) {
			// TODO Auto-generated method stub
			boolean ready = true;
			setAngle(DriveSide.FR, 0.875);
			setAngle(DriveSide.FL, 0.625);
			setAngle(DriveSide.RR, 0.125);
			setAngle(DriveSide.RL, 0.325);
			//updateData();
			
			SmartDashboard.putString("rotReady", "Ready");
			
			if (Math.abs(getRelPos(DriveSide.FR) - 0.875) > 0.1){
				ready = false;
				SmartDashboard.putString("rotReady", "FR Not Ready, is" + getRelPos(DriveSide.FR));
			}
			
			if (Math.abs(getRelPos(DriveSide.FL) - 0.625) > 0.1){
				ready = false;
				SmartDashboard.putString("rotReady", "FL Not Ready");
			}
			
			if (Math.abs(getRelPos(DriveSide.RR) - 0.125) > 0.1){
				ready = false;
				SmartDashboard.putString("rotReady", "RR Not Ready");
			}
			
			if (Math.abs(getRelPos(DriveSide.RL) - 0.325) > 0.1){
				ready = false;
				SmartDashboard.putString("rotReady", "RL Not Ready, is" + getRelPos(DriveSide.RL));
			}
			if(ready){
				setDriveVolt(rot);
			}
			
			
		}
		
		public void swerve(double angle, double power, double rot){
			//swerve const?
			double rotMag = 0.3;
			
			double frAngOff = 0.125;
			double flAngOff = 0.875;
			double rrAngOff = 0.375;
			double rlAngOff = 0.625;
			
			double frAng, flAng, rrAng, rlAng;
			double frMag, flMag, rrMag, rlMag;
			double tempx, tempy;
			double frx, fry, flx, fly, rrx, rry, rlx, rly;
			//rad = turns *2 * Math.PI;
			
			/*// cosine is wrong!
			setAngle(DriveSide.FR, angle + (Math.cos((angle - frAngOff) * 2.0 * Math.PI) * (rot * rotMag)));
			FRDrive.set(power + (Math.cos((angle - frAngOff) * 2.0 * Math.PI) * powDiff));
			
			setAngle(DriveSide.FL, angle + (Math.cos((angle - flAngOff) * 2.0 * Math.PI) * (rot * rotMag)));
			FLDrive.set(power + (Math.cos((angle - flAngOff) * 2.0 * Math.PI) * powDiff));
			
			setAngle(DriveSide.RR, angle + (Math.cos((angle - rrAngOff) * 2.0 * Math.PI) * (rot * rotMag)));
			RRDrive.set(power + (Math.cos((angle - rrAngOff) * 2.0 * Math.PI) * powDiff));
			
			setAngle(DriveSide.RL, angle + (Math.cos((angle - rlAngOff) * 2.0 * Math.PI) * (rot * rotMag)));
			RLDrive.set(power + (Math.cos((angle - rlAngOff) * 2.0 * Math.PI) * powDiff));
			*/
			///*
			frAngOff = frAngOff * 2.0 * Math.PI;
			flAngOff = flAngOff * 2.0 * Math.PI;
			rrAngOff = rrAngOff * 2.0 * Math.PI;
			rlAngOff = rlAngOff * 2.0 * Math.PI;
			
			tempx = Math.sin(angle * 2.0 * Math.PI) * power;
			tempy = Math.cos(angle * 2.0 * Math.PI) * power;
			
			SmartDashboard.putString("inputVector", "Input vector X: " + tempx + ", Y: " + tempy);
			SmartDashboard.putString("InputAngle", "Input angle = " + calcAngle( 1.0 * tempx,-1.0 * tempy) / 360.0);
			
			frx = Math.sin(frAngOff + (Math.PI / 2.0) ) * (rot * rotMag);
			fry = Math.cos(frAngOff + (Math.PI / 2.0) ) * (rot * rotMag);
			 
			flx = Math.sin(flAngOff + (Math.PI / 2.0) ) * (rot * rotMag);
			fly = Math.cos(flAngOff + (Math.PI / 2.0) ) * (rot * rotMag);
			 
			rrx = Math.sin(rrAngOff + (Math.PI / 2.0) ) * (rot * rotMag);
			rry = Math.cos(rrAngOff + (Math.PI / 2.0) ) * (rot * rotMag);
			 
			rlx = Math.sin(rlAngOff + (Math.PI / 2.0) ) * (rot * rotMag);
			rly = Math.cos(rlAngOff + (Math.PI / 2.0) ) * (rot * rotMag);
			
			frAng = calcAngle(frx + tempx, -1.0 * (fry + tempy)) / 360.0;
			flAng = calcAngle(flx + tempx, -1.0 * (fly + tempy)) / 360.0;
			rrAng = calcAngle(rrx + tempx, -1.0 * (rry + tempy)) / 360.0;
			rlAng = calcAngle(rlx + tempx, -1.0 * (rly + tempy)) / 360.0;
			 
			frMag = Math.sqrt(Math.pow(frx + tempx, 2) + Math.pow(fry + tempy, 2));
			flMag = Math.sqrt(Math.pow(flx + tempx, 2) + Math.pow(fly + tempy, 2));
			rrMag = Math.sqrt(Math.pow(rrx + tempx, 2) + Math.pow(rry + tempy, 2));
			rlMag = Math.sqrt(Math.pow(rlx + tempx, 2) + Math.pow(rly + tempy, 2));
			
			setAngle(DriveSide.FR, frAng);
			setAngle(DriveSide.FL, flAng);
			setAngle(DriveSide.RR, rrAng);
			setAngle(DriveSide.RL, rlAng);
			
			FRDrive.set(frMag);
			FLDrive.set(flMag);
			RRDrive.set(rrMag);
			RLDrive.set(rlMag);
		//*/
		}

	}

	private final AHRS navx;
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
		
		navx = new AHRS(SPI.Port.kMXP);
		driveInit();
	}

	private void driveInit() {
		// Set encoders
		//Init gyro
		navx.setAngleAdjustment(0.0);

	};
	public void resetGyro(){
		//navx.setAngleAdjustment(0.0);
		navx.reset();
	}

	public void cartDrive(double xIn, double yIn, double rotIn, boolean slow, boolean fieldO) {
		//double angle = Math.atan(yIn / xIn);
		sDrive.updateData();
		rotIn = rotIn * -1.0;
		double power = Math.sqrt((yIn * yIn) + (xIn * xIn));
		double powCurve;
		double rotCurve;
		double gyroAngle = navx.getAngle();
		double steerAngle = calcAngle(xIn,yIn) / 360.0;
		
		if(!fieldO)
			resetGyro();
		
		SmartDashboard.putString("gyro1", "" + gyroAngle);
		gyroAngle = (gyroAngle % 360) / 360.0;
		SmartDashboard.putString("gyro2", "" + gyroAngle);
		if(gyroAngle < 0.0){
			gyroAngle = 1.0 + gyroAngle;
		}
		SmartDashboard.putString("gyro3", "" + gyroAngle);
		if(fieldO){
			steerAngle = steerAngle - gyroAngle;
			steerAngle = steerAngle % 1.0;
		}
		SmartDashboard.putString("gyro4", "" + steerAngle);
		if (power > 1.0)
			power = 1.0;
		if(slow)
			powCurve = (Math.pow(power,3) / 2) + 0.2;
		else
			powCurve = Math.pow(power, 3);
		
		
		if(slow)
			rotCurve = Math.pow(rotIn,3) / 2;
		else
			rotCurve = Math.pow(rotIn,3);
		
		if (power > 0.1){
			if(power > 0.3){
				if(Math.abs(rotIn) > 0.2){
					sDrive.swerve(steerAngle, powCurve, rotCurve * -1.0);
				}
				else
				{
					sDrive.set(steerAngle, powCurve );//- 0.25);
				}
			}
			else
			{
				if(Math.abs(rotIn) > 0.4)
					sDrive.spin(rotCurve);
				else
					sDrive.set(steerAngle, 0);
			}
		}
		else{
			if (Math.abs(rotIn) < 0.2){
				sDrive.setDriveVolt(0.0);
			}
			else
			{
				sDrive.spin(rotCurve);
			}
		}
		SmartDashboard.putString("driveVolts", "Drive Voltage: " +FRDrive.getOutputVoltage());
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
