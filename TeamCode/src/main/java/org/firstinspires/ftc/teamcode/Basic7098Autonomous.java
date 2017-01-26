package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import sameer_s.processor.LogRobot;
import sameer_s.processor.OpModeStage;
import sameer_s.processor.OpModeType;
import sameer_s.processor.ProcessedOpMode;

import static sameer_s.processor.OpModeMethods.*;
import static java.lang.Math.abs;

@Disabled
@ProcessedOpMode(type = OpModeType.AUTONOMOUS, name = "Basic7098Autonomous", group = "7098")
public class Basic7098Autonomous extends OpMode
{
//	@LogRobot
	private Hardware7098Robot robot;

	// @formatter:off
	OpModeStage[] program = new OpModeStage[]
    {
		() -> robot.resetEncoder(0),
		() -> robot.resetEncoder(2),
        linear(() ->
        {
			while(abs(robot.getEncoderValue(0)) < 1440)
			{
				robot.drive(.3f, .3f);
			}
			robot.drive(0, 0);
	    }),
		sleep(100),
		() ->
		{
			robot.moveMotor(5, 1);
			return robot.shooterSwitch();
		},
		() -> !robot.shooterSwitch(),
		() -> robot.shooterSwitch(),
		() -> !robot.shooterSwitch(),
		linear(() -> robot.moveMotor(5, 0))
    };
	// @formatter:on

	//DON'T KNOW HOW THIS WORKS
	//Drive forward some amount
	//Fire twice
	//Turn left
	//Drive until right sensor sees line
	//Turn right until left sensor sees line
	//Drive forward, test color
	//If correct, adjust and press
	//Otherwise test other color
	//If correct, adjust and press
	//Goto next line and repeat
            /*
		    for (int i = 0; i < 6; i++)
		    {
			    robot.moveMotor(i, .5);
			    Thread.sleep(1000);
			    robot.moveMotor(i, 0);
			    Thread.sleep(1000);
		    }
		    */

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
