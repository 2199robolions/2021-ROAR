// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;



/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private Joystick joystick;
  private Victor leftMotor;
  private Victor rightMotor;
  private DifferentialDrive drive;
  // right and left are from perspective of sitting in the chair
  private DigitalInput lowerLeftButton;
  private DigitalInput uperLeftButton;
  private DigitalInput lowerRightButton;
  private DigitalInput uperRightButton;

  public Robot() {
      joystick   = new Joystick(0); 
      leftMotor  = new Victor(0);
      rightMotor = new Victor(1); 
      drive      = new DifferentialDrive(leftMotor, rightMotor); 
      lowerRightButton     = new DigitalInput(0);
      lowerLeftButton      = new DigitalInput(1);
      uperRightButton      = new DigitalInput(2);
      uperLeftButton       = new DigitalInput(3);

  }

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    double x = 0;
    //double y = 0;
    double z = 0 ;
    boolean buttonStatusUpperLeft;
    boolean buttonStatusLowerRight;
    boolean buttonStatusUpperRight;
    boolean buttonStatusLowerLeft;


    //z = joystick.getZ();
    //y = joystick.getY();
    //x = joystick.getX();

   // System.out.println("x: " + x); 
   // System.out.println("y: " + y); 
   // System.out.println("z: " + z);

    //drive.arcadeDrive(y, z, true);

    buttonStatusLowerLeft  = lowerLeftButton.get();
    buttonStatusLowerRight = lowerRightButton.get();
    buttonStatusUpperLeft  = uperLeftButton.get();
    buttonStatusUpperRight = uperRightButton.get();
    //button pushed is flase button open is true
    //buttonStatus = lowerLeftButton.get(); 
    //System.out.println("Lower left button: " + buttonStatus);
    //buttonStatus = lowerRightButton.get(); 
    //System.out.println("lower right button: " + buttonStatus);
    //buttonStatus = uperLeftButton.get(); 
    //System.out.println("uper left button: " + buttonStatus);
    //buttonStatus = uperRightButton.get(); 
    //System.out.println("uper right button: " + buttonStatus + "\n");

    int count = 0;
    //upper right is back button
    if (buttonStatusUpperRight == false){
      count++;
      x = -.5;
      z = 0;
        
    }
    //lower right is right turn
    else if (buttonStatusLowerRight == false){
      count++;
      x = 0;
      z = .75;
    }
    //upper left is forward button
    else if (buttonStatusUpperLeft == false){
      count++;
      x = .5;
      z = 0;   
    }
    // lower left is left turn
    else if (buttonStatusLowerLeft == false){
      count++;
      x = 0;
      z = -.75;

    }
    // only drive if one singular button is pressed 
    if (count == 1){
      drive.arcadeDrive(x, z);
      System.out.println("count is:" + count);
      System.out.println("x is:" + x);
      System.out.println("z is:" + z);
    } 
    else{
      drive.arcadeDrive(0,0);
    }  
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {} 

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
    double x = 0;
    double z = .75;
    drive.arcadeDrive(x, z);
  }
}
