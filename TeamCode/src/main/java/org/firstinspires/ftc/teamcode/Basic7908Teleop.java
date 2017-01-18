package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//@Disabled
@TeleOp(name="Basic7908Teleop", group="7098")
public class Basic7908Teleop extends OpMode
{
    private Hardware7908Robot robot;

    @Override
    public void init()
    {
        robot = new Hardware7908Robot(hardwareMap);
    }

    @Override
    public void init_loop()
    {
    }

    @Override
    public void start()
    {
    }

    double driveSpeed = 1;
    @Override
    public void loop()
    {
        robot.drive(-driveSpeed * gamepad1.left_stick_y, driveSpeed * gamepad1.right_stick_y);

        if(gamepad1.a)
        {
            driveSpeed = 1;
        }
        else if (gamepad1.b)
        {
            driveSpeed = .7;
        }
        else if(gamepad1.x)
        {
            driveSpeed = .4;
        }

        if(gamepad1.left_bumper)
        {
            robot.moveMotor(4, 1);
        }
        else if(gamepad1.right_bumper)
        {
            robot.moveMotor(4, -1);
        }
        else
        {
            robot.moveMotor(4, 0);
        }

        if(gamepad1.right_trigger > .7 || !robot.shooterSwitch())
        {
            robot.moveMotor(5, 1);
        }
        else
        {
            robot.moveMotor(5, 0);
        }

        if(gamepad1.dpad_up)
        {
            robot.moveMotor(6, 1);
        }
        else if(gamepad1.dpad_down)
        {
            robot.moveMotor(6, -1);
        }
        else
        {
            robot.moveMotor(6, 0);
        }
    }

    @Override
    public void stop()
    {
        robot.stop();
    }
}
