package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import sameer_s.processor.LogRobot;
import sameer_s.processor.OpModeType;
import sameer_s.processor.OpModeStage;
import sameer_s.processor.ProcessedOpMode;

import static sameer_s.processor.OpModeMethods.*;

@ProcessedOpMode(type = OpModeType.AUTONOMOUS, name = "Basic7098Autonomous", group = "7098")
public class Basic7098Autonomous extends OpMode
{
	@LogRobot
	private Hardware7098Robot robot;

	// @formatter:off
	OpModeStage[] program = new OpModeStage[]
    {
        sleep(1000),
        linear(() ->
        {
		    for (int i = 0; i < 6; i++)
		    {
			    robot.moveMotor(i, .5);
			    Thread.sleep(1000);
			    robot.moveMotor(i, 0);
			    Thread.sleep(1000);
		    }
	    })
    };
	// @formatter:on

	@Override
	public void init()
	{
		robot = new Hardware7098Robot(hardwareMap);
	}

	@Override
	public void loop()
	{

	}
}
