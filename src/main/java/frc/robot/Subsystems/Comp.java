// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants;

public class Comp extends SubsystemBase {
  Compressor comp;
  public Comp() {
    comp = new Compressor(constants.COMPRESSOR_ID, PneumaticsModuleType.CTREPCM);
  }
  public Command startCompressor(){
    return Commands.run(()-> comp.enableDigital());
  }
  public Command stopCompressor(){
    return Commands.run(()-> comp.disable());
  }
  public Command toggleCompressor(){
    if(comp.isEnabled()){
      return Commands.run(()-> stopCompressor());
    }else{
      return Commands.run(()-> startCompressor());
    }
  }
  @Override
  public void periodic(){}
}
