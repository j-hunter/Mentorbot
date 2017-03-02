package org.usfirst.frc.team342.mentorbot.commands;

import org.usfirst.frc.team342.mentorbot.subsystems.Drive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class DriveWithJoystick extends Command {

	Drive drive;
	Joystick joystick;
	public DriveWithJoystick(Drive driveIn, Joystick joystickIn){
		drive = driveIn;
		joystick = joystickIn;
	}
	
	protected void execute(){
		double x = joystick.getRawAxis(0);
		double y = joystick.getRawAxis(1);
		double rot = joystick.getRawAxis(4);
		drive.cartDrive(y, -x, -rot, joystick.getRawButton(5), ! joystick.getRawButton(1));
		//drive.updateDriveData();
	}
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
