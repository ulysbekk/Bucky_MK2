// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.constants;
import frc.robot.Subsystems.*;

public class XboxMove extends Command {
  CommandXboxController xbox;
  Drive driveBase;
  double Throttle, Reverse, Rotation, Right, Left;
  boolean Pirouette, Toggle, Precision, toggleCompressor, highGear, lowGear, gear;
  public XboxMove(CommandXboxController p_xbox, Drive p_driveBase){
    xbox = p_xbox;
    driveBase = p_driveBase;
    addRequirements(driveBase);
  }
  

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Throttle = 0;
    Rotation = 0;
    Reverse = 0;
    Right = 0;
    Left = 0;
    Pirouette = true;
    Precision = false;
    Toggle = true;
    toggleCompressor = false;
    
    
  }

  @Override
  public void execute() {
    Throttle = xbox.getRightTriggerAxis();
    Reverse = xbox.getLeftTriggerAxis();
    Rotation = xbox.getLeftX();
    Pirouette = xbox.leftStick().getAsBoolean();
    Toggle = xbox.x().getAsBoolean();
    highGear = xbox.rightBumper().getAsBoolean();
    lowGear = xbox.leftBumper().getAsBoolean();
    toggleCompressor = xbox.start().getAsBoolean();
    
    

    if(Pirouette == true){
      if(Math.abs(Rotation) > constants.DEADZONE){
        Right = -1 * Rotation;
        Left = Rotation;
      }
    }
    else if(Pirouette == false){
      if(Throttle > constants.DEADZONE && Math.abs(Rotation) < constants.DEADZONE){
        Right = Throttle;
        Left = Throttle;
      }
      else if(Reverse > constants.DEADZONE && Math.abs(Rotation) < constants.DEADZONE){
        Right = -1 * Reverse;
        Left = -1 * Reverse;
      }
      else if(Rotation > constants.DEADZONE && Throttle > constants.DEADZONE){
        Right = Throttle * (Rotation - 1);
        Left = Throttle;
      }
      else if(Rotation < -1 * constants.DEADZONE && Throttle > constants.DEADZONE){
        Right = Throttle;
        Left = Throttle * (Rotation + 1);
      }
      else if(Reverse > constants.DEADZONE && Rotation < -1 * constants.DEADZONE){
        Right = -1 * Reverse;
        Left = (-1 * Reverse) * (Rotation + 1);
      }
      else if(Reverse > constants.DEADZONE && Rotation > constants.DEADZONE){
        Right = (-1 * Reverse) * (Rotation - 1);
        Left = -1 * Reverse;
      }
      else {
        Right = 0;
        Left = 0;
      }
    }
    else {
      Right = 0;
      Left = 0;
    }
    if(Toggle == true){
      if(Precision == false){
        Precision = true;
      }
      else if(Precision == true){
        Precision = false;
      }
    }
    if(toggleCompressor == true){
      toggleCompressor = false;
    }
    else if(toggleCompressor == false){
      toggleCompressor = true;
    }

    if(highGear == true){
      lowGear = false;
      driveBase.shiftGearHigh();
    }
    if(lowGear == true){
      highGear = false;
      driveBase.shiftGearLow();
    }

    

    if(Precision == true){
      Right = Right * 0.5;
      Left = Left * 0.5;
    }
    driveBase.moveMotor(Right, Left);
    
  }

  @Override
  public void end(boolean interrupted) {
    driveBase.stopMotor();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
