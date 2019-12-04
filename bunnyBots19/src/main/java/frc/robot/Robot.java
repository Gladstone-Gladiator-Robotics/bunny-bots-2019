/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private Joystick controller = new Joystick(0);
  private JoystickButton 
                        aButton = new JoystickButton(controller, 1),
                        bButton = new JoystickButton(controller, 2),
                        xButton = new JoystickButton(controller, 3),
                        yButton = new JoystickButton(controller, 4),
                        leftBumper = new JoystickButton(controller, 5),
                        rightBumper = new JoystickButton(controller, 6),
                        backButton = new JoystickButton(controller, 7), //left center button
                        startButton = new JoystickButton(controller, 8), //right center button
                        leftJoystickButton = new JoystickButton(controller, 9),
                        rightJoystickButton = new JoystickButton(controller, 10);
  private Talon leftDriveMotor = new Talon(0);
  private Talon rightDriveMotor = new Talon(1);
  private Talon spinManip = new Talon(2);
  private Talon upDownManip = new Talon(3);
  private DifferentialDrive driveTrain = new DifferentialDrive(leftDriveMotor, rightDriveMotor); 
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  }

 
  @Override
  public void robotPeriodic(){
  }

  public void teleopDrivePeriodic(){
    double speed = 0.7;
    if(leftJoystickButton.get()){
      speed = 1;
    }
    driveTrain.arcadeDrive(
      speed * -controller.getRawAxis(1),
      controller.getRawAxis(0));
  }

  public void teleopIntakePeriodic(){
    
    if(bButton.get() ){
      spinManip.set(-1);
    }
    else if (aButton.get() ){
      spinManip.set(1);
    }
    else{
      spinManip.set(0);
    }

  }
  
  public void teleopUpDownPeriodic(){
    if(leftBumper.get() ){
      upDownManip.set(-1);
    }
    else if (rightBumper.get() ){
      upDownManip.set(1);
    }
    else{
      upDownManip.set(0);
    }

  }
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // autoSelected = SmartDashboard.getString("Auto Selector",
    // defaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
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

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    teleopDrivePeriodic();
    teleopIntakePeriodic();
    teleopUpDownPeriodic();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
