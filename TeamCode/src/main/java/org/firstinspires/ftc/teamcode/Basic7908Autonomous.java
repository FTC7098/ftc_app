package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import sameer_s.processor.ProcessedOpMode;

@ProcessedOpMode
@Autonomous(name="Basic7908Autonomous", group="7098")
public class Basic7908Autonomous extends LinearOpMode
{
    private Hardware7908Robot robot;

    public void runOpMode() throws InterruptedException
    {
        robot = new Hardware7908Robot(hardwareMap);

        for(int i = 2; i < 3; i++)
        {
            robot.moveMotor(i, .5);
            Thread.sleep(1000);
            robot.moveMotor(i, 0);
            Thread.sleep(1000);
        }
    }

    @Override
    public void init()
    {
    }

    @Override
    public void loop()
    {

    }
}
