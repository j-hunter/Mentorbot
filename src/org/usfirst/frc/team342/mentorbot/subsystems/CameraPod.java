package org.usfirst.frc.team342.mentorbot.subsystems;

import org.usfirst.frc.team342.mentorbot.RobotMap;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CameraPod extends Subsystem {

	private Servo camAzi;
	private Servo camEle;
	
	public CameraPod(){
		camAzi = new Servo(RobotMap.CAM_AzPin);
		camEle = new Servo(RobotMap.CAM_ElePin);
		
	}
	
	public void point(double azi, double ele){
		double calcAzi;
		double calcEle;
		
		calcAzi = ((azi + 1.0) * 0.5);
		calcEle = ((ele + 1.0) * .5);
		SmartDashboard.putDouble("CamPodAzi", calcAzi);
		SmartDashboard.putDouble("CamPodEle", calcEle);
		camAzi.set(calcAzi);
		camEle.set(calcEle);
	}
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
