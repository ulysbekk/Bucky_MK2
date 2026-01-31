// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import frc.robot.constants;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drive extends SubsystemBase {
  SparkMax Right1;
  SparkMax Right2;
  SparkMax Right3;
  SparkMax Left1;
  SparkMax Left2;
  SparkMax Left3;

  SparkMaxConfig LeftConfig1;
  SparkMaxConfig LeftConfig2;
  SparkMaxConfig LeftConfig3;
  SparkMaxConfig RightConfig1;
  SparkMaxConfig RightConfig2;
  SparkMaxConfig RightConfig3;

  Solenoid gearShift;
  Comp comp;
  public Object toggleGear;

  public Drive() {
    Right1 = new SparkMax(constants.MOTOR_R1_ID, MotorType.kBrushless);
    Right2 = new SparkMax(constants.MOTOR_R2_ID, MotorType.kBrushless);
    Right3 = new SparkMax(constants.MOTOR_R3_ID, MotorType.kBrushless);
    Left1 = new SparkMax(constants.MOTOR_L1_ID, MotorType.kBrushless);
    Left2 = new SparkMax(constants.MOTOR_L2_ID, MotorType.kBrushless);
    Left3 = new SparkMax(constants.MOTOR_L3_ID, MotorType.kBrushless);

    RightConfig1 = new SparkMaxConfig();
    RightConfig2 = new SparkMaxConfig();
    RightConfig3 = new SparkMaxConfig();
    LeftConfig1 = new SparkMaxConfig();
    LeftConfig2 = new SparkMaxConfig();
    LeftConfig3 = new SparkMaxConfig();

    gearShift = new Solenoid(PneumaticsModuleType.CTREPCM, constants.GEAR_SHIFT);
    comp = new Comp();

    RightConfig1
      .smartCurrentLimit(50)
      .idleMode(IdleMode.kBrake);
    RightConfig2
      .smartCurrentLimit(50)
      .idleMode(IdleMode.kBrake)
      .follow(constants.MOTOR_R1_ID);
    RightConfig3
      .smartCurrentLimit(50)
      .idleMode(IdleMode.kBrake)
      .follow(constants.MOTOR_R1_ID);
    LeftConfig1
      .smartCurrentLimit(50)
      .idleMode(IdleMode.kBrake)
      .inverted(true);
    LeftConfig2
      .smartCurrentLimit(50)
      .idleMode(IdleMode.kBrake)
      .follow(constants.MOTOR_L1_ID);
    LeftConfig3
      .smartCurrentLimit(50)
      .idleMode(IdleMode.kBrake)
      .follow(constants.MOTOR_L1_ID);

    Right1.configure(RightConfig1,ResetMode.kResetSafeParameters,PersistMode.kPersistParameters);
    Right2.configure(RightConfig2,ResetMode.kResetSafeParameters,PersistMode.kPersistParameters);
    Right3.configure(RightConfig3,ResetMode.kResetSafeParameters,PersistMode.kPersistParameters);
    Left1.configure(LeftConfig1,ResetMode.kResetSafeParameters,PersistMode.kPersistParameters);
    Left2.configure(LeftConfig2,ResetMode.kResetSafeParameters,PersistMode.kPersistParameters);
    Left3.configure(LeftConfig3,ResetMode.kResetSafeParameters,PersistMode.kPersistParameters);

  }
    public void stopMotor(){
      Right1.set(0);
      Left1.set(0);
    }
    public void moveMotor(double rightSpeed, double leftSpeed){
      Right1.set(rightSpeed);
      Left1.set(leftSpeed);
      
    }

    public void shiftGearHigh(){
      gearShift.set(true);
    }
    public void shiftGearLow(){
      gearShift.set(false);
    }

  @Override
  public void periodic() {
  }
}
