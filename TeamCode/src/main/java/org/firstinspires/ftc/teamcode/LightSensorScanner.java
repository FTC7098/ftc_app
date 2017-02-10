package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import sameer_s.processor.LogRobot;

//@ProcessedOpMode(type= OpModeType.TELEOP, name="Basic7098Teleop", group="7098")
@Disabled
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
		//robot.csSide.enableLed(true);
    }

	private double driveSpeed = 1;
	private boolean rt = true;
	@Override
	public void loop()
	{
		telemetry.addData("01", "Left light: " + robot.csLeft.getLightDetected());
		telemetry.addData("02", "Right light: " + robot.csRight.getLightDetected());
		telemetry.addData("03", "Beacon light: " + robot.csSide.getLightDetected());


		robot.drive(-driveSpeed * gamepad1.left_stick_y, -driveSpeed * gamepad1.right_stick_y);

		if (gamepad1.a)
		{
			driveSpeed = 1;
		}
		else if (gamepad1.b)
		{
			driveSpeed = .7;
		}
		else if (gamepad1.x)
		{
			driveSpeed = .4;
		}

		if (gamepad1.dpad_left) {
			robot.gyro.calibrate();
		}
		// telemetry.addData("01", robot.gyro.status());
		//telemetry.addData("02", robot.gyro.getRotationFraction());
		//telemetry.addData("03", robot.gyro.rawZ());

		if (gamepad1.left_bumper)
		{
			robot.moveMotor(4, 1);
		}
		else if (gamepad1.right_bumper)
		{
			robot.moveMotor(4, -1);
		}
		else
		{
			robot.moveMotor(4, 0);
		}

		if (gamepad1.right_trigger > .7)
		{
			rt = true;
			robot.moveMotor(5, 1);
		}
		else if(gamepad1.left_trigger > .7)
		{
			rt = false;
			robot.moveMotor(5, -1);
		}
		else if(!robot.shooterSwitch())
		{
			//robot.moveMotor(5, rt ? 1 : -1);
		}
		else
		{
			robot.moveMotor(5, 0);
		}

		if (gamepad2.a)
		{
			robot.setServo(0, robot.getServo(0) + .005);
		}
		else if (gamepad2.b)
		{
			if (Double.isNaN(robot.getServo(0)))
			{
				robot.setServo(0, 0.5);
			}
			else
			{
				robot.setServo(0, robot.getServo(0) - .005);
			}
		}

		if (gamepad1.a)
		{
			robot.setServo(1, 1.0f);
		}
		else if (gamepad1.b)
		{
			robot.setServo(1, 0.0f);
		}
		else
		{
			robot.setServo(1, 0.5f);
		}

		if (gamepad2.dpad_up)
		{
			robot.moveMotor(6, 1);
			robot.moveMotor(7, 1);
		}
		else if (gamepad2.dpad_down)
		{
			robot.moveMotor(6, -1);
			robot.moveMotor(7, -1);
		}
		else
		{
			robot.moveMotor(6, 0);
			robot.moveMotor(7, 0);
		}

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
