package org.usfirst.frc.team342.mentorbot.subsystems;

import org.usfirst.frc.team342.mentorbot.RobotMap;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;


public class Pickup extends Subsystem {

	//Talons
	private Talon pickup;
	
	//constants
	public static final double DEFAULTSPEED = -0.5;
	
	
	
	public Pickup (){
		super();
		
		pickup = new Talon(RobotMap.PickupMotor);
		
	}
	
	
	public void set(double speed){
		pickup.set(speed);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
