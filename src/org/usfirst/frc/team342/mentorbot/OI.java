package org.usfirst.frc.team342.mentorbot;

import org.usfirst.frc.team342.mentorbot.commands.RunPickup;
import org.usfirst.frc.team342.mentorbot.subsystems.Drive;
import org.usfirst.frc.team342.mentorbot.subsystems.Pickup;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
	
	private Joystick joystick;
	private Pickup pickup;
	private Button refStart;
	private Button revWheel;
	
	private Button pickupBtn;
	/* Pickup - L Bumper 5
	 * Convey - R Bumper 6
	 * Shoot R trigger
	 * Slow - A 1
	 */
	private static int PICKUPBTN = 5;
	public 
	OI(Drive drive, Pickup pick, Joystick stick){
		joystick = stick;
		pickup = pick;
		
		//refStart = new JoystickButton(joystick,RobotMap.JREFSTART);
		//revWheel = new JoystickButton(joystick,RobotMap.JREVWHEEL);
		pickupBtn = new JoystickButton(joystick,PICKUPBTN);
		
		
		//refStart.whenPressed(new RefDrive(drive));
		//revWheel.whenPressed(new RevWheel(drive, joystick));
		pickupBtn.whileHeld(new RunPickup(pickup));
	}
}

