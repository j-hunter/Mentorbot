package org.usfirst.frc.team342.mentorbot.subsystems;

import org.usfirst.frc.team342.mentorbot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends Subsystem {

	//Talons
	private CANTalon frontShooter;
	private CANTalon backShooter;
	private Talon conveyor;
	
	//Talon Constants
	private static final double ShootP = 0.01;
	private static final double ShootI = 0.0;
	private static final double ShootD = 0.0;
	private static final double ShootFg = 0.037;
	private static final int ShootErr = 30;
	
	private double maxFrontSpeed = 1750.0; //2500.0;
	private double maxBackSpeed = 2950.0; //4500.0;
	private double conveyorSpeed = 0.60;
	
	
	public Shooter (){
		super();
		
		frontShooter = new CANTalon(RobotMap.FrontShooter);
		backShooter = new CANTalon(RobotMap.BackShooter);
		conveyor = new Talon(RobotMap.Conveyor);
		
		setupTalons();
	}
	
	public void setupTalons(){
		frontShooter.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		frontShooter.changeControlMode(TalonControlMode.Speed);
		frontShooter.set(0.0);
		frontShooter.setP(ShootP);
		frontShooter.setI(ShootI);
		frontShooter.setD(ShootD);
		frontShooter.setF(ShootFg);
		frontShooter.setAllowableClosedLoopErr(ShootErr);
		frontShooter.enableBrakeMode(false);
		
		backShooter.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		backShooter.changeControlMode(TalonControlMode.Speed);
		backShooter.set(0.0);
		backShooter.setP(ShootP);
		backShooter.setI(ShootI);
		backShooter.setD(ShootD);
		backShooter.setF(ShootFg);
		backShooter.setAllowableClosedLoopErr(ShootErr);
		backShooter.enableBrakeMode(false);
	}
	
	public void setMaxSpeeds(double front, double back){
		maxFrontSpeed = front;
		maxBackSpeed = back;
	}
	
	public void set(double speed){
		double front = speed * maxFrontSpeed * -1.0;
		double back = speed * maxBackSpeed * -1.0;
		SmartDashboard.putString("ShootSet", "Front: " + front + " Back: " + back + " MaxF: " + maxFrontSpeed + " Back: " + maxBackSpeed);
		frontShooter.set(front);
		backShooter.set(back);
	}
	
	public void convey(){
		convey(true);
	}
	public void convey(boolean go){
		if(go){
			conveyor.set(conveyorSpeed );
		} else {
			conveyor.set(0.0);
		}
	}
	public void constop(){
		convey(false);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
