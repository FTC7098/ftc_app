package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import sameer_s.processor.LogRobot;

//@ProcessedOpMode(type= OpModeType.TELEOP, name="Basic7098Teleop", group="7098")
@TeleOp(name="Light", group="7098")
public class LightSensorScanner extends OpMode
{
    @LogRobot
	private Hardware7098Robot robot;

	@Override
	public void init()
	{
		robot = new Hardware7098Robot(hardwareMap);
	}

	@Override
	public void init_loop()
	{}

	@Override
	public void start()
	{
        robot.init();
		robot.csLeft.enableLed(true);
		robot.csRight.enableLed(true);
		robot.csSide.enableLed(true);
    }

	@Override
	public void loop()
	{
		telemetry.addData("01", "Left light: " + robot.csLeft.getLightDetected());
		telemetry.addData("02", "Right light: " + robot.csRight.getLightDetected());
		telemetry.addData("03", "Beacon light: " + robot.csSide.getLightDetected());
	}

	@Override
	public void stop()
	{
//		while (robot.shooterSwitch())
//		{
//			robot.moveMotor(5, 1);
//		}
		robot.stop();
	}
}
