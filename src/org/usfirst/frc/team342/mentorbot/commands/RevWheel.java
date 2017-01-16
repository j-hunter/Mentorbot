package org.usfirst.frc.team342.mentorbot.commands;

import org.usfirst.frc.team342.mentorbot.RobotMap;
import org.usfirst.frc.team342.mentorbot.subsystems.Drive;
import org.usfirst.frc.team342.mentorbot.subsystems.Drive.DriveSide;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class RevWheel extends Command {

	private Drive drive;
	private Joystick joystick;
	
	public RevWheel(Drive rDrive, Joystick rJoystick){
		drive = rDrive;
		joystick = rJoystick;
	}
	@Override
	protected boolean isFinished() {
		Drive.DriveSide side = DriveSide.None;
		// TODO Auto-generated method stub
		if (joystick.getRawButton(RobotMap.JSELFR))
			side = DriveSide.FR;
		if (joystick.getRawButton(RobotMap.JSELFL))
			side = DriveSide.FL;
		if (joystick.getRawButton(RobotMap.JSELRR))
			side = DriveSide.RR;
		if (joystick.getRawButton(RobotMap.JSELRL))
			side = DriveSide.RL;
		return side == DriveSide.None || drive.revWheel(side);
	}

}
