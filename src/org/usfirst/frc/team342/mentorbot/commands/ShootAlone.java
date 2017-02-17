package org.usfirst.frc.team342.mentorbot.commands;

import org.usfirst.frc.team342.mentorbot.subsystems.Shooter;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShootAlone extends Command {
	Shooter shooter;
	Joystick joystick;
	
	public ShootAlone(Shooter shot, Joystick stick){
		shooter = shot;
		joystick = stick;
	}
	
	public void initialize(){
		double front, back;
		front = SmartDashboard.getDouble("FrontMaxSpeed",2500.0);
		back = SmartDashboard.getDouble("BackMaxSpeed", 4500.0);
		
		shooter.setMaxSpeeds(front, back);
	}

	public void execute(){
		shooter.set(joystick.getRawAxis(2));
	}
	
	public void end(){
		shooter.set(0.0);
	}
	
	public void interupted(){
		end();
	}
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
