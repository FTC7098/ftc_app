package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import sameer_s.processor.OpModeStage;

import static java.lang.Math.abs;

@Autonomous(name = "Basic7098Autonomous2", group = "7098")
public class Basic7098Autonomous2 extends LambdaStateMachineOp
{
	private Hardware7098Robot robot;
	private int stage = 0;

    OpModeStage stage__ = () -> true;

	private int initialEncoder = 0;
	private Runnable[] lambdas = new Runnable[] {
	() ->
	{
		initialEncoder = abs(robot.getEncoderValue(0));
		stage++;
	}, () ->
	{
		robot.drive(.3f, -.3f);
		if (abs(robot.getEncoderValue(0)) - initialEncoder > 1440)
		{
			robot.drive(0);
			stage++;
		}
	}, () ->
	{
		robot.moveMotor(5, 1);
		if (robot.shooterSwitch())
		{
			stage++;
		}
	}, () ->
	{
		if (!robot.shooterSwitch())
		{
			stage++;
		}
	}, () ->
	{
		if (robot.shooterSwitch())
		{
			stage++;
		}
	}, () ->
	{
		if (!robot.shooterSwitch())
		{
			stage++;
		}
	}, () -> robot.moveMotor(5, 0) };

	@Override
	public void init()
	{
		robot = new Hardware7098Robot(hardwareMap);
	}

	@Override
	public void start()
	{
		robot.init();
	}

	@Override
	public void loop()
	{
		runLambdas(stage, lambdas);
	}

	@Override
	public void stop()
	{
		robot.stop();
	}
}