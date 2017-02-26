package org.usfirst.frc.team342.mentorbot.commands;

import org.usfirst.frc.team342.mentorbot.OI;
import org.usfirst.frc.team342.mentorbot.subsystems.Climb;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class ClimbAlone extends Command{

	private Joystick joystick;
	private Climb climber;
	
	public ClimbAlone(Climb climb, Joystick stick){
		climber = climb;
		joystick = stick;
	}
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		climber.set(joystick.getRawAxis(OI.ClimbAxis));
		return false;
	}

}
