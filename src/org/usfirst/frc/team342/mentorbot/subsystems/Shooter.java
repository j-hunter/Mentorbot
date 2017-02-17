package org.usfirst.frc.team342.mentorbot.subsystems;

import org.usfirst.frc.team342.mentorbot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem {

	//Talons
	private CANTalon frontShooter;
	private CANTalon backShooter;
	
	//Talon Constants
	private static final double ShootP = 1.0;
	private static final double ShootI = 0.0;
	private static final double ShootD = 0.0;
	private static final int ShootErr = 1;
	
	private double maxFrontSpeed = 2500.0;
	private double maxBackSpeed = 4500.0;
	
	
	public Shooter (){
		super();
		
		frontShooter = new CANTalon(RobotMap.FrontShooter);
		backShooter = new CANTalon(RobotMap.BackShooter);
		
		setupTalons();
	}
	
	public void setupTalons(){
		frontShooter.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		frontShooter.changeControlMode(TalonControlMode.Speed);
		frontShooter.setP(ShootP);
		frontShooter.setI(ShootI);
		frontShooter.setD(ShootD);
		frontShooter.setAllowableClosedLoopErr(ShootErr);
		
		backShooter.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		backShooter.changeControlMode(TalonControlMode.Speed);
		backShooter.setP(ShootP);
		backShooter.setI(ShootI);
		backShooter.setD(ShootD);
		backShooter.setAllowableClosedLoopErr(ShootErr);
	}
	
	public void setMaxSpeeds(double front, double back){
		maxFrontSpeed = front;
		maxBackSpeed = back;
	}
	
	public void set(double speed){
		frontShooter.set(speed * maxFrontSpeed);
		backShooter.set(speed * maxFrontSpeed);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
