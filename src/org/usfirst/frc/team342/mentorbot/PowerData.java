package org.usfirst.frc.team342.mentorbot;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class PowerData {

	private PowerDistributionPanel pdp;
	
	public PowerData(){
		pdp = new PowerDistributionPanel(RobotMap.PDPADDR);
	}
	
	public double getAmps(int addr){
		return pdp.getCurrent(addr);
	}
	
	public double getClimbAmps(){
		return pdp.getCurrent(RobotMap.PowClimb);
	}
}
