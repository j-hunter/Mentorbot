package org.usfirst.frc.team342.mentorbot.commands;

import org.usfirst.frc.team342.mentorbot.subsystems.Pickup;

import edu.wpi.first.wpilibj.command.Command;

public class RunPickup extends Command {
	
	private Pickup pickup;
	
	public RunPickup(Pickup pick){
		pickup = pick;
	}

    protected void interrupted() {
    	end();
    }
    
    protected void end() {
    	pickup.set(0.0);
    }
    
	@Override
	protected boolean isFinished() {
		pickup.set(pickup.DEFAULTSPEED);
		// TODO Auto-generated method stub
		return false;
	}

}
