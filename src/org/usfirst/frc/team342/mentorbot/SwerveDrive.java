package org.usfirst.frc.team342.mentorbot;

import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.MotorSafetyHelper;

public class SwerveDrive implements MotorSafety {

	protected MotorSafetyHelper m_safetyHelper;
	
	
	@Override
	public void setExpiration(double timeout) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getExpiration() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isAlive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void stopMotor() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSafetyEnabled(boolean enabled) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isSafetyEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDescription() {
		// IS a swerve drive
		return "SwerveDrive";
	}

}
