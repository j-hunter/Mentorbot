package org.usfirst.frc.team342.mentorbot.commands;

import org.usfirst.frc.team342.mentorbot.subsystems.Gear;

import edu.wpi.first.wpilibj.command.Command;

public class GearOpen extends Command {

	private double timeEnd;
	private double OpenSeconds;
	private Gear gear;
	
	public GearOpen(Gear gearIn, double seconds){
		OpenSeconds = seconds;
		gear = gearIn;
	}
	@Override
	protected void initialize(){
		timeEnd = System.currentTimeMillis() + (OpenSeconds * 1000);
	}
	
	@Override
	protected void execute(){
		gear.move(-0.5);
	}
	
	@Override
	protected void end(){
		gear.move(0.0);
	}
	
	@Override
	protected void interrupted(){
		end();
	}
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return System.currentTimeMillis() > timeEnd;
	}

}
