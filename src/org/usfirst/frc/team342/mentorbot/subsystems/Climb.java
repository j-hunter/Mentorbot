package org.usfirst.frc.team342.mentorbot.subsystems;

import org.usfirst.frc.team342.mentorbot.RobotMap;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Climb extends Subsystem {

	private Talon climber;
	
	public Climb(){
		climber = new Talon(RobotMap.ClimbMotor);
		
	}
	
	public void set(double speed){
		climber.set(speed);
	}
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
