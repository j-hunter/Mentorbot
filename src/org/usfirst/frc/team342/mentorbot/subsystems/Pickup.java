package org.usfirst.frc.team342.mentorbot.subsystems;

import org.usfirst.frc.team342.mentorbot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Pickup extends Subsystem {

	//Talons
	private Talon pickup;
	
	
	
	public Pickup (){
		super();
		
		pickup = new Talon(RobotMap.Pickup);
		
	}
	
	
	public void set(double speed){
		pickup.set(speed);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
