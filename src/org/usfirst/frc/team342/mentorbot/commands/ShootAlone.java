package org.usfirst.frc.team342.mentorbot.commands;

import org.usfirst.frc.team342.mentorbot.subsystems.Shooter;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShootAlone extends Command {
	private static final String FRONT_MAX_SPEED = "FrontMaxSpeed";
	private static final String BACK_MAX_SPEED = "BackMaxSpeed";
	Shooter shooter;
	Joystick joystick;
	
	public ShootAlone(Shooter shot, Joystick stick){
		shooter = shot;
		joystick = stick;
	}
	
	public void initialize(){
		double front, back;
		SmartDashboard.putString(FRONT_MAX_SPEED, "" + 2500.0);
		SmartDashboard.putString(BACK_MAX_SPEED, "" + 4500.0);

	}

	public void execute(){

	}
	
	public void end(){
		shooter.set(0.0);
	}
	
	public void interupted(){
		end();
	}
	@Override
	protected boolean isFinished() {
		double front, back;
		front = Double.parseDouble(SmartDashboard.getString(FRONT_MAX_SPEED));
		back = Double.parseDouble(SmartDashboard.getString(BACK_MAX_SPEED));
		
		shooter.setMaxSpeeds(front, back);
		shooter.set(joystick.getRawAxis(3));
		shooter.convey(joystick.getRawButton(6));
		SmartDashboard.putString("Trigger Input", "" + joystick.getRawAxis(2));
		// TODO Auto-generated method stub
		return false;
	}

}
