package org.usfirst.frc.team342.mentorbot.commands;

import org.usfirst.frc.team342.mentorbot.subsystems.Gear;

import edu.wpi.first.wpilibj.command.Command;

public class GearClose extends Command {

	private Gear gear;
	
	public GearClose(Gear gearIn){
		gear = gearIn;
	}
	@Override
	protected void execute(){
		gear.move(0.5);
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
		return gear.isClosed();
	}

}
