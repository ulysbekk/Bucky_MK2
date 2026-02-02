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
import frc.robot.Subsystems.Claw;
import frc.robot.Subsystems.Drive;

public class RobotContainer {
  CommandXboxController xbox1;
  Shoulder shoulder;
  Arm arm;
  CommandXboxController xbox2;
  Drive drive;
  Claw claws;
  XboxMove xboxMove;

  public RobotContainer() {
  xbox1 = new CommandXboxController(0);
  xbox2 = new CommandXboxController(1);
  shoulder = new Shoulder();
  arm = new Arm();
  drive = new Drive();
  claws = new Claw();
  xboxMove = new XboxMove(xbox1, drive, claws, arm, shoulder, null);
  
  drive.setDefaultCommand(xboxMove);
    configureBindings();
  }

  private void configureBindings() {
    shoulder.setDefaultCommand(shoulder.setShoulder(xbox2::getLeftY));
    arm.setDefaultCommand(arm.setArm(xbox2::getRightY));
    xbox2.a().onTrue(Commands.runOnce(() -> claws.openClaw(), claws));
    xbox2.x().onTrue(Commands.runOnce(() -> claws.closeClaw(), claws));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}