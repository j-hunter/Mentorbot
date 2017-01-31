package org.usfirst.frc.team342.mentorbot.commands;

import org.usfirst.frc.team342.mentorbot.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

public class RefDrive extends Command {
	private Drive drive;
	public RefDrive(Drive rDrive)
	{
		drive = rDrive;
	}

	protected void initialize(){
		
	}
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return drive.refSwerve();
	}

}
