package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import sameer_s.processor.LogRobot;

@TeleOp(name = "Basic7098Teleop", group = "7098")
public class Basic7098Teleop extends OpMode
{
	@LogRobot // Part of our annotation processing library. Since it is disabled, it does nothing right now.
	private Hardware7098Robot robot;

	@Override
	public void init()
	{
		// Initializes the hardware class with the provided hardware map
		robot = new Hardware7098Robot(hardwareMap);
	}

	@Override
	public void start()
	{
        // Sets the servos to their default position
        robot.init();
	}

	// Controls the speed of the robot. There are three modes: (1=100% power, .7 = 70% power, and .4=40% power)
	private double driveSpeed = 1;
	// Allows us to enable and disable the touch sensor that automatically loads our spring mechanism
	private boolean usingTouch = false;
	// Allows us to use the switch as a toggle. Without this, the program would switch between using touch and not using touch many of times a second.
	private boolean back1Pressed = false;

	@Override
	public void loop()
	{
        //robot.csSide.enableLed(true);
        robot.csLeft.enableLed(true);
        robot.csRight.enableLed(true);

        telemetry.addData("SIDE_COLOR", robot.csSide.getLightDetected());
        telemetry.addData("LEFT_COLOR", robot.csLeft.getLightDetected());
        telemetry.addData("RIGHT_COLOR", robot.csRight.getLightDetected());
		telemetry.addData("ULTRA", robot.ultra.getUltrasonicLevel());

		// robot.drive(-driveSpeed * gamepad1.left_stick_y, -driveSpeed * gamepad1.right_stick_y);

		// Scales the movement of the robot proportional to the cube of the amount the joystick is moved.
		// This makes it so more precision can be gained when moving at slow speeds.
		double leftSpeed, rightSpeed;
		leftSpeed = -driveSpeed * Math.pow(gamepad1.left_stick_y, 3);
		rightSpeed = -driveSpeed * Math.pow(gamepad1.right_stick_y, 3);
		robot.drive(leftSpeed, rightSpeed);

		// Buttons to set the drive movement speed.
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

		// Left and right bumpers are used to move the collection in opposite directions.
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

		// Uses the back button as a toggle to disable and re-enable the touch sensor.
		if (gamepad1.back)
		{
			back1Pressed = true;
		}
		else if (!gamepad1.back)
		{
			if (back1Pressed)
			{
				back1Pressed = false;
				usingTouch = !usingTouch;
			}
		}

		// Right trigger loads/fires the spring-based launching mechanism.
		if (gamepad1.right_trigger > .7)
		{
			robot.moveMotor(5, 1);
		}
		// If the touch sensor is enabled, and it is not being pressed, that will cause it to load
		// the shooter, but not fire, since fully loading will press the sensor
		else if (usingTouch && !robot.shooterSwitch())
		{
			robot.moveMotor(5, 1);
		}
		else
		{
			robot.moveMotor(5, 0);
		}

		// A and B buttons on the second controller are used to move the servo that holds up
		// the forklift.
		if (gamepad2.a)
		{
			robot.setServo(0, robot.getServo(0) + .005);
		}
		else if (gamepad2.b)
		{
			robot.setServo(0, robot.getServo(0) - .005);
		}

		// D-PAD horizontal pressed control the button pusher we use to activate the beacons
		if (gamepad2.left_stick_x < .7)
		{
			robot.setServo(1, 1.0f);
		}
		else if (gamepad2.left_stick_x > .7)
		{
			robot.setServo(1, 0.0f);
		}
		else
		{
			robot.setServo(1, 0.5f);
		}

		if (gamepad2.x)
		{
			robot.setServo(2, robot.getServo(2) + .005);
		}
		else if (gamepad2.y)
		{
			robot.setServo(2, robot.getServo(2) - .005);
		}

		// Driver #2 can use their D-PAD to raise and lift our forklift
		// (Note that there are two motors, part of our speed-based design)
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
		// Stops all motors so that we're not running after time.
		robot.stop();
	}
}
