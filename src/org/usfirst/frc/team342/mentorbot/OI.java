package org.usfirst.frc.team342.mentorbot;

import org.usfirst.frc.team342.mentorbot.commands.GearClose;
import org.usfirst.frc.team342.mentorbot.commands.GearOpen;
import org.usfirst.frc.team342.mentorbot.commands.RunPickup;
import org.usfirst.frc.team342.mentorbot.subsystems.Drive;
import org.usfirst.frc.team342.mentorbot.subsystems.Gear;
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
	
	//GearControl
	public static final int GearAxis = 1;
	public static final int GearOveride = 4;
	public static final int GearOpenBtn = 1;
	public static final int GearCloseBtn = 2;
	
	//Climb Controll
	public static final int ClimbAxis = 1;
	
	//Shooter
	public static final int ShooterAxis = 3;
	public static final int ShooterConvBtn = 6;
	
	public Joystick xbox;
	public Joystick logitech;
	private Pickup pickup;
	private Gear gear;
	
	private Button refStart;
	private Button revWheel;
	
	private Button openGear;
	private Button closeGear;
	
	
	private Button pickupBtn;
	/* Pickup - L Bumper 5
	 * Convey - R Bumper 6
	 * Shoot R trigger
	 * Slow - A 1
	 */
	private static int PICKUPBTN = 5;
	public 
	OI(Drive drive, Pickup pick, Gear gearIn, Joystick stick1, Joystick stick2){
		xbox = stick1;
		logitech = stick2;
		pickup = pick;
		gear = gearIn;
		//refStart = new JoystickButton(joystick,RobotMap.JREFSTART);
		//revWheel = new JoystickButton(joystick,RobotMap.JREVWHEEL);
		pickupBtn = new JoystickButton(logitech,PICKUPBTN);
		openGear = new JoystickButton(logitech, GearOpenBtn);
		closeGear = new JoystickButton(logitech, GearCloseBtn);
		
		//refStart.whenPressed(new RefDrive(drive));
		//revWheel.whenPressed(new RevWheel(drive, joystick));
		pickupBtn.whileHeld(new RunPickup(pickup));
		
		openGear.whenPressed(new GearOpen(gear, 1.2));
		closeGear.whenPressed(new GearClose(gear));
	}
}

