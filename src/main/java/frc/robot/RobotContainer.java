// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Subsystems.Shoulder;
import frc.robot.Subsystems.Arm;
import frc.robot.Commands.XboxMove;
import frc.robot.Subsystems.Drive;


public class RobotContainer {
  CommandXboxController xbox1;
  Shoulder shoulder;
  Arm arm;
  CommandXboxController xbox2;
  Drive drive;
  XboxMove xboxMove;

  public RobotContainer() {
  xbox1 = new CommandXboxController(0);
  xbox2 = new CommandXboxController(1);
  shoulder = new Shoulder();
  arm = new Arm();
  drive = new Drive();
  xboxMove = new XboxMove(xbox1, drive);
  
  drive.setDefaultCommand(xboxMove);
    configureBindings();
  }

  private void configureBindings() {
    shoulder.setDefaultCommand(shoulder.setShoulder(xbox2::getLeftY));
    arm.setDefaultCommand(arm.setArm(xbox2::getRightY));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}