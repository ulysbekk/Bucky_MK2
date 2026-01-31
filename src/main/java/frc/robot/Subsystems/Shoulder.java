// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants;

public class Shoulder extends SubsystemBase {
 
  SparkMax shoulderMotor1;
  SparkMax shoulderMotor2;
  SparkMaxConfig shoulderMotorConfig1;
  SparkMaxConfig shoulderMotorConfig2;
  public Shoulder() {
    shoulderMotor1 = new SparkMax(constants.MOTOR_SHOULDER1_ID, MotorType.kBrushless);
    shoulderMotor2 = new SparkMax(constants.MOTOR_SHOULDER2_ID, MotorType.kBrushless);

    


    shoulderMotorConfig1 = new SparkMaxConfig();
    shoulderMotorConfig2 = new SparkMaxConfig();

    shoulderMotorConfig1
      .smartCurrentLimit(50)
      .idleMode(IdleMode.kBrake);
    shoulderMotorConfig2
      .smartCurrentLimit(50)
      .idleMode(IdleMode.kBrake);
    shoulderMotor1.configure(shoulderMotorConfig1,ResetMode.kResetSafeParameters,PersistMode.kPersistParameters);
    shoulderMotor2.configure(shoulderMotorConfig2,ResetMode.kResetSafeParameters,PersistMode.kPersistParameters);
    }
  public void setShoulderSpeed(double speed){
    shoulderMotor1.set(speed);
    shoulderMotor2.set(speed);
  }
  public Command setShoulder(DoubleSupplier speed){
    if(Math.abs(speed.getAsDouble()) < constants.DEADZONE){
      return run(()-> setShoulderSpeed(speed.getAsDouble()));
    }
    return run(()-> setShoulderSpeed(0));
  }

  @Override
  public void periodic() {
    
  }
}
