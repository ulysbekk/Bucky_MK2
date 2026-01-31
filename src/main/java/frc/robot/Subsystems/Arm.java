// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants;

public class Arm extends SubsystemBase {
  SparkMax armMotor;
  SparkMaxConfig armMotorConfig;
  public Arm() {
    armMotor = new SparkMax(constants.MOTOR_ARM_ID, MotorType.kBrushless);

    armMotorConfig = new SparkMaxConfig();

    armMotorConfig
      .smartCurrentLimit(50)
      .idleMode(IdleMode.kBrake);

    armMotor.configure(armMotorConfig,ResetMode.kResetSafeParameters,PersistMode.kPersistParameters);
  }
  public void setArmSpeed(double speed){
    armMotor.set(speed);
  }
  public Command setArm(DoubleSupplier speed){
    if(Math.abs(speed.getAsDouble()) < constants.DEADZONE){
      return run(()-> setArmSpeed(speed.getAsDouble()));
    }
    return run(()-> setArmSpeed(0));
  }

  @Override
  public void periodic() {
  }
}
