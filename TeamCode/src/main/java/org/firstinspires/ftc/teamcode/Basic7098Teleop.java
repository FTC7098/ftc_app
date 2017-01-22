package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//@ProcessedOpMode(type= OpModeType.TELEOP, name="Basic7098Teleop", group="7098")
@TeleOp(name="Basic7098Teleop", group="7098")
public class Basic7098Teleop extends OpMode
{
//    @LogRobot
    private Hardware7098Robot robot;

    @Override
    public void init()
    {
        robot = new Hardware7098Robot(hardwareMap);
    }

    @Override
    public void init_loop()
    {
    }

    @Override
    public void start()
    {
    }

    private double driveSpeed = 1;
    @Override
    public void loop()
    {
        robot.drive(-driveSpeed * gamepad1.left_stick_y, driveSpeed * gamepad1.right_stick_y);

        if (gamepad1.a)
        {
            driveSpeed = 1;
        } else if (gamepad1.b)
        {
            driveSpeed = .7;
        } else if (gamepad1.x)
        {
            driveSpeed = .4;
        }

        if (gamepad1.left_bumper)
        {
            robot.moveMotor(4, 1);
        } else if (gamepad1.right_bumper)
        {
            robot.moveMotor(4, -1);
        } else
        {
            robot.moveMotor(4, 0);
        }

        if (gamepad1.right_trigger > .7 || !robot.shooterSwitch())
        {
            robot.moveMotor(5, 1);
        } else
        {
            robot.moveMotor(5, 0);
        }

        if (gamepad2.a)
        {
            robot.setServo(0, robot.getServo(0) + .005);
        } else if (gamepad2.b)
        {
            robot.setServo(0, robot.getServo(0) - .005);
        }

        if (gamepad2.dpad_up)
        {
            robot.moveMotor(6, 1);
            robot.moveMotor(7, 1);
        } else if (gamepad2.dpad_down)
        {
            robot.moveMotor(6, -1);
            robot.moveMotor(7, -1);
        } else
        {
            robot.moveMotor(6, 0);
            robot.moveMotor(7, 0);
        }
    }

    @Override
    public void stop()
    {
        while(robot.shooterSwitch())
        {
            robot.moveMotor(5, 1);
        }
        robot.stop();
    }
}