// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.constants;
import frc.robot.Subsystems.*;

public class XboxMove extends Command {
  //declare everything
  CommandXboxController xbox;
  Arm arm;
  Claw claw;
  Shoulder shoulder;
  Drive driveBase;
  Comp comp;
  double Throttle, Reverse, Rotation, Right, Left, moveArm, moveShoulder;
  boolean Pirouette, Toggle, Precision, toggleCompressor, highGear, lowGear, gear, toggleClaws;
  public XboxMove(CommandXboxController p_xbox, Drive p_driveBase, Claw p_claw, Arm p_arm, Shoulder p_shoulder, Comp p_comp) {
    xbox = p_xbox;
    driveBase = p_driveBase;
    claw = p_claw;
    arm = p_arm;
    shoulder = p_shoulder;
    comp = p_comp;
    addRequirements(driveBase);
  }
  
  @Override
  public void initialize() {
    //set everything to starting pos.
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
    moveArm = xbox.getRightY();
    moveShoulder = xbox.getLeftY();
    toggleClaws = xbox.a().getAsBoolean();

    //arm logic
    if(Math.abs(this.moveArm) > constants.DEADZONE){
      arm.setArmSpeed(this.moveArm);
    }
    else {
      arm.setArmSpeed(0);
    }

    //shoulder logic
    if(Math.abs(this.moveShoulder) > constants.DEADZONE){
      shoulder.setShoulderSpeed(this.moveShoulder);
    }
    else {
      shoulder.setShoulderSpeed(0);
    }
    //claw logic
    if(toggleClaws == true){
      claw.openClaw();
    }
    if(toggleClaws == false){
      claw.closeClaw();
    }

    //pirouette logic. if false - proceed to normal, true - pirouette
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
    //Logic of the Precision toggle.
    if(Toggle == true){
      if(Precision == false){
        Precision = true;
      }
      else if(Precision == true){
        Precision = false;
      }
    }
    //Logic of the compressor. Might be broken.
    if(toggleCompressor == true){
      toggleCompressor = false;
    }
    else if(toggleCompressor == false){
      toggleCompressor = true;
    }

    //Gear Shifting Logic.
    if(highGear == true){
      lowGear = false;
      driveBase.shiftGearHigh();
    }
    if(lowGear == true){
      highGear = false;
      driveBase.shiftGearLow();
    }
    //Precision Logic.
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
