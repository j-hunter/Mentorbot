package org.usfirst.frc.team342.mentorbot.commands;

import org.omg.PortableInterceptor.ObjectIdHelper;
import org.usfirst.frc.team342.mentorbot.OI;
import org.usfirst.frc.team342.mentorbot.subsystems.Gear;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class GearAlone extends Command {

	private Gear gear;
	private Joystick joystick;
	public GearAlone(Gear gearIn, Joystick stick){
		gear = gearIn;
		joystick = stick;
	}
	protected void execute(){
		double joystickinput = joystick.getRawAxis(OI.GearAxis);
		if (Math.abs(joystickinput) <= 0.1)
			joystickinput = 0.0;
		gear.move(joystickinput, joystick.getRawButton(OI.GearOveride));
	}
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
