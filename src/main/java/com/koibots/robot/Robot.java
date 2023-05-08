// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.koibots.robot;

import edu.wpi.first.hal.DriverStationJNI;
import edu.wpi.first.util.WPIUtilJNI;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.internal.DriverStationModeThread;



/**
 * The VM is configured to automatically run this class. If you change the name of this class or the
 * package after creating this project, you must also update the build.gradle file in the project.
 */
public class Robot extends RobotBase
{
    public void robotInit() {}
    
    
    public void disabled() {}
    
    
    public void autonomous() {}
    
    
    public void teleop() {}
    
    
    public void test() {}
    
    
    private volatile boolean exit;
    
    
    @Override
    public void startCompetition()
    {
        robotInit();
        
        DriverStationModeThread modeThread = new DriverStationModeThread();
        
        int event = WPIUtilJNI.createEvent(false, false);
        
        DriverStation.provideRefreshedDataEventHandle(event);
        
        // Tell the DS that the robot is ready to be enabled
        DriverStationJNI.observeUserProgramStarting();
        
        while (!Thread.currentThread().isInterrupted() && !exit)
        {
            if (isDisabled())
            {
                modeThread.inDisabled(true);
                disabled();
                modeThread.inDisabled(false);
                while (isDisabled())
                {
                    try
                    {
                        WPIUtilJNI.waitForObject(event);
                    }
                    catch (InterruptedException e)
                    {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            else if (isAutonomous())
            {
                modeThread.inAutonomous(true);
                autonomous();
                modeThread.inAutonomous(false);
                while (isAutonomousEnabled())
                {
                    try
                    {
                        WPIUtilJNI.waitForObject(event);
                    }
                    catch (InterruptedException e)
                    {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            else if (isTest())
            {
                modeThread.inTest(true);
                test();
                modeThread.inTest(false);
                while (isTest() && isEnabled())
                {
                    try
                    {
                        WPIUtilJNI.waitForObject(event);
                    }
                    catch (InterruptedException e)
                    {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            else
            {
                modeThread.inTeleop(true);
                teleop();
                modeThread.inTeleop(false);
                while (isTeleopEnabled())
                {
                    try
                    {
                        WPIUtilJNI.waitForObject(event);
                    }
                    catch (InterruptedException e)
                    {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
        
        DriverStation.removeRefreshedDataEventHandle(event);
        modeThread.close();
    }
    
    
    @Override
    public void endCompetition()
    {
        exit = true;
    }
}
