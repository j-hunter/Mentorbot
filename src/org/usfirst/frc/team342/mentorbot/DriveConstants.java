package org.usfirst.frc.team342.mentorbot;

public class DriveConstants {
	/*
	public static final boolean FREncReverse = false;
	public static final boolean FLEncReverse = false;
	public static final boolean RREncReverse = false;
	public static final boolean RLEncReverse = true; //false;
	*/
	public static final boolean FREncReverse = true;
	public static final boolean FLEncReverse = true;
	public static final boolean RREncReverse = true;
	public static final boolean RLEncReverse = true;
	
	
	public static final boolean FRMotReverse = true;
	public static final boolean FLMotReverse = true;
	public static final boolean RRMotReverse = true;
	public static final boolean RLMotReverse = true;
	
	public static final double refTollerance = 0.5;
	
	public static final double FRRefPos = 0.0;
	public static final double FLRefPos = 0.0;
	public static final double RRRefPos = 0.0;
	public static final double RLRefPos = 0.0;
	
	//Practice
	public static final int FRAngOffset = 355;
	public static final int FLAngOffset = 188;
	public static final int RRAngOffset = 31;
	public static final int RLAngOffset = 2000;
	
	//Competition
	//public static final int FRAngOffset = 2840;//1206;
	//public static final int FLAngOffset = 3980;
	//public static final int RRAngOffset = 3470;
	//public static final int RLAngOffset = 2230;
	
	
	public static final int ENCCOUNT = 4096;
	public static final int ENCPERWHEEL = 4096 * 1;
	public static final int ENCERROR = 20;

}
