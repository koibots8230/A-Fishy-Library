package com.koibots.lib.hardware;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

/** Singleton wrapper for AHRS class.
 * @author Caleb O'Neal
 */
public class NavX extends AHRS{
  private static final NavX gyro = new NavX();

  NavX() {
    super(SPI.Port.kMXP);
  }

  public static NavX get() {
    return gyro;
  }
}


