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
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
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
  private XboxController controller = new XboxController(0);
  private Talon leftDriveMotor = new Talon(0);
  private Talon rightDriveMotor = new Talon(1);
  private Talon spinManip = new Talon(2);
  private Talon upDownManip = new Talon(3);
  private DigitalInput topLimitSwitch = new DigitalInput(0);
  private DigitalInput bottomLimitSwitch = new DigitalInput(1);
  private DifferentialDrive driveTrain = new DifferentialDrive(leftDriveMotor, rightDriveMotor);
  private Boolean intakeOn = false; 
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
    if(controller.getStickButton(Hand.kLeft)){
      speed = 1;
    }
    driveTrain.arcadeDrive(
      speed * -controller.getRawAxis(1),
      controller.getRawAxis(0));
  }

  public void teleopIntakePeriodic(){
    // TODO: convert this to a Command - https://github.com/FRCTeam1719/BunnyBots2019/blob/master/frc/robot/commands/UseDrive.java
    if(controller.getBButton() && intakeOn == false){
      spinManip.set(-1);
    }
    else{
      if (controller.getAButtonPressed()){
        intakeOn = !intakeOn;
      }
  
      if (intakeOn){
        spinManip.set(1);
      }
      else{
        spinManip.set(0);
      } 
    }
  }
  
  public void teleopUpDownPeriodic(){
    if(controller.getBumper(Hand.kLeft) && !bottomLimitSwitch.get()){
      upDownManip.set(-1);
    }
    else if (controller.getBumper(Hand.kRight) && !topLimitSwitch.get() ){
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
    autonomousCycles = 0;
  }

  private int autonomousCycles = 0;

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    autonomousCycles = autonomousCycles + 1;
   /* switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }*/
    if (autonomousCycles * 20 <= 5 * 1000) {
      driveTrain.arcadeDrive(0.7, 0);
    }
    else {
      driveTrain.arcadeDrive(0, 0);
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
