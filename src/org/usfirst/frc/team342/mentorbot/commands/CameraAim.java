package org.usfirst.frc.team342.mentorbot.commands;

import org.usfirst.frc.team342.mentorbot.subsystems.CameraPod;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class CameraAim extends Command {

	private CameraPod camera;
	private Joystick joystick;

	public CameraAim(CameraPod cam, Joystick joy) {
		camera = cam;
		joystick = joy;
	}

	public void execute() {
		camera.point(joystick.getRawAxis(0), joystick.getRawAxis(1));
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
