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
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.*;
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
      speed * -controller.getX(Hand.kLeft),
      controller.getY(Hand.kLeft));
  }

  public void teleopIntakePeriodic(){
  /*  if(controller.getBButton() && intakeOn == false){
      spinManip.set(-0.4);
    }
    else{
      if (controller.{
        intakeOn = !intakeOn;
      }

      if (intakeOn){
        spinManip.set(0.4);
      }
      else{
        spinManip.set(0);
      } 
    }*/
    
    spinManip.set(-controller.getTriggerAxis(Hand.kLeft));
    spinManip.set(controller.getTriggerAxis(Hand.kRight));
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
    if (autonomousCycles * 20 <= 1 * 1000) {
      spinManip.set(1);
      driveTrain.arcadeDrive(0.7, 0);
    }
    else {
      driveTrain.arcadeDrive(0, 0);
      spinManip.set(0);
    }
    
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    teleopDrivePeriodic();
    teleopIntakePeriodic();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
