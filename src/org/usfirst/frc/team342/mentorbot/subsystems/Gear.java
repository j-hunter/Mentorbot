package org.usfirst.frc.team342.mentorbot.subsystems;

import org.usfirst.frc.team342.mentorbot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Gear extends Subsystem {

	private Spark gearMotor;
	private DigitalInput inLim;
	private boolean ForwardIsIn = false;
	
	public Gear(){
		gearMotor = new Spark(RobotMap.GearAddr);
		inLim = new DigitalInput(RobotMap.gearLim);
	}
	
	public void move(double speed, boolean overide){
		boolean xora, xorb;
		xora = ForwardIsIn;
		xorb = speed >= 0.0;
		
		if(!inLim.get() && !overide){
			SmartDashboard.putString("GearStat", "Limited - Speed = " + speed);
			if((xora || xorb) && ! (xora && xorb)){
				gearMotor.set(speed);
			
			}
			else{
				SmartDashboard.putString("GearStat", "Limited(running) - Speed = " + speed);
				gearMotor.set(0.0);
			}
		}
		else{
			SmartDashboard.putString("GearStat", "No Limit, Speed = " + speed);
			gearMotor.set(speed);
		}
	}
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

	public void move(double speed) {
		move(speed, false);
		
	}
	
	public boolean isClosed(){
		return ! inLim.get();
	}

}
