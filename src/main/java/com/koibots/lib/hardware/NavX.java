package com.koibots.lib.hardware;


import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.DriverStation.Alliance;


public class NavX extends AHRS{
  private static final NavX gyro = new NavX();


  NavX() {
    super(SPI.Port.kMXP);


    if (DriverStation.getAlliance() == Alliance.Red) {
      this.setAngleAdjustment(180);
    }
  }


  public static NavX get() {
    return gyro;
  }
}


