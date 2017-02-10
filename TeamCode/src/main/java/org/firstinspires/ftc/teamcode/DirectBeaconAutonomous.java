package org.firstinspires.ftc.teamcode;

import static java.lang.Math.abs;
import static sameer_s.processor.OpModeMethods.exec;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

import android.media.AudioManager;
import android.media.ToneGenerator;

import sameer_s.processor.OpModeStage;


@TeleOp(name = "Beacon Only (Blue)", group = "7098")
public class DirectBeaconAutonomous extends HybridStateOpMode
{
    private Hardware7098Robot robot;

    private int initialEncoder = 0;
    private long startTime = 0;

    private static final double COLOR_THRESHOLD = 0.4;

    // @formatter:off
	OpModeStage[][] program = new OpModeStage[][]
        {
            // UNDECIDED
            {
                /*
                 * Drive towards the beacon a fixed distance by encoders
                 */
                exec(() -> initialEncoder = abs(robot.getEncoderValue(0))),
                exec(() -> robot.drive(.1f, .1f)),
                () -> {
                    telemetry.addData("01", robot.getEncoderValue(0));
                    telemetry.addData("02", initialEncoder);
                    telemetry.addData("03", abs(abs(robot.getEncoderValue(0)) - initialEncoder));
                    return abs(abs(robot.getEncoderValue(0)) - initialEncoder) >= 2000;
                },
                exec(this::beep),

//                exec(() -> robot.drive(0)),
//                () -> gamepad1.a || gamepad2.a,

                /*
                 * Drive forward until right light sensor reaches the white line
                 */
                exec(() -> robot.csLeft.enableLed(true)),
                exec(() -> robot.csRight.enableLed(true)),
                exec(() -> initialEncoder = abs(robot.getEncoderValue(0))),
                exec(() -> robot.drive(.1f, .1f)),
                () -> {
                    telemetry.addData("03", robot.csRight.getLightDetected());
                    return robot.csRight.getLightDetected() > COLOR_THRESHOLD;
                },
                exec(() -> robot.drive(0)),

                exec(() -> startTime = System.currentTimeMillis()),
                () -> System.currentTimeMillis() - startTime >= 250,

                exec(() -> initialEncoder = abs(robot.getEncoderValue(0))),
                exec(() -> robot.drive(-.5f, .5f)),
                exec(() -> startTime = System.currentTimeMillis()),

                () -> {
                    telemetry.addData("01", robot.getEncoderValue(0));
                    return abs(abs(robot.getEncoderValue(0)) - initialEncoder) >= 300;
                },
                exec(() -> robot.drive(0)),
                exec(() -> startTime = System.currentTimeMillis()),
                () -> System.currentTimeMillis() - startTime >= 250,

                exec(() -> initialEncoder = abs(robot.getEncoderValue(0))),
                exec(() -> robot.drive(.3f, .3f)),
                () -> {
                    telemetry.addData("01", robot.getEncoderValue(0));
                    telemetry.addData("02", initialEncoder);
                    return abs(abs(robot.getEncoderValue(0)) - initialEncoder) >= 700;
                },
                exec(() -> robot.drive(0)),

                exec(() -> startTime = System.currentTimeMillis()),
                () -> System.currentTimeMillis() - startTime >= 250,

                exec(() -> initialEncoder = abs(robot.getEncoderValue(0))),
                exec(() -> robot.drive(-.5f, .5f)),
                exec(() -> startTime = System.currentTimeMillis()),
                () -> {
                    telemetry.addData("01", robot.getEncoderValue(0));
                    return abs(abs(robot.getEncoderValue(0)) - initialEncoder) >= 150;
                },
                exec(() -> robot.drive(0)),
                exec(() -> startTime = System.currentTimeMillis()),
                () -> System.currentTimeMillis() - startTime >= 250,

    //            exec(() -> robot.drive(-.15, -.15)),
    //            () -> robot.csLeft.getLightDetected() > COLOR_THRESHOLD || robot.csRight.getLightDetected() > COLOR_THRESHOLD,
    //            exec(() -> robot.drive(0)),

                () ->
                {
                    boolean left = robot.csLeft.getLightDetected() > COLOR_THRESHOLD;
                    boolean right = robot.csRight.getLightDetected() > COLOR_THRESHOLD;

                    if(left && right)
                    {
                        return true;
                    }
                    else if(left)
                    {
                        robot.drive(-.2, .2);
                    }
                    else if(right)
                    {
                        robot.drive(.2, -.2);
                    }
                    else
                    {
                        robot.drive(-.15, -.15);
                    }

                    return false;
                },
                exec(() -> robot.drive(0)),

//                exec(() -> initialEncoder = abs(robot.getEncoderValue(0))),
//                exec(() -> robot.drive(.3f, .3f)),
//                () ->
//                {
//                    telemetry.addData("01", robot.getEncoderValue(0));
//                    telemetry.addData("02", initialEncoder);
//                    return abs(abs(robot.getEncoderValue(0)) - initialEncoder) >= 50;
//                },
//                exec(() -> robot.drive(0)),

                exec(() ->
                {
                    state = robot.csSide.getLightDetected() > .25 ? 1 : 2;
                    counter = 0;
                })

            },
            // FAR
            {

                exec(() -> initialEncoder = abs(robot.getEncoderValue(0))),
                exec(() -> robot.drive(.3f, .3f)),
                () ->
                {
                    telemetry.addData("01", robot.getEncoderValue(0));
                    telemetry.addData("02", initialEncoder);
                    return abs(abs(robot.getEncoderValue(0)) - initialEncoder) >= 175;
                },
                exec(() -> robot.drive(0)),

                exec(() ->
                {
                    state = 3;
                    counter = 0;
                })
            },
            // CLOSE
            {
                exec(() -> initialEncoder = abs(robot.getEncoderValue(0))),
                exec(() -> robot.drive(.3f, .3f)),
                () ->
                {
                    telemetry.addData("01", robot.getEncoderValue(0));
                    telemetry.addData("02", initialEncoder);
                    return abs(abs(robot.getEncoderValue(0)) - initialEncoder) >= 50;
                },
                exec(() -> robot.drive(0)),

                exec(() ->
                {
                    state = 3;
                    counter = 0;
                })
            },
            // AFTER IN PLACE
            {
                exec(this::beep),
                exec(() -> robot.setServo(1, 0.0f)),
            }
    };



	@Override
	public void init()
	{
		robot = new Hardware7098Robot(hardwareMap);
        robot.init();
	}

	@Override
	public void loop()
	{
        telemetry.addData("COLOR", robot.csSide.getLightDetected());
        telemetry.addData("RIGHT", robot.csRight.getLightDetected());
        telemetry.addData("LEFT", robot.csLeft.getLightDetected());
        runProgram(program);
	}

    @Override
    public void stop()
    {
        robot.stop();
    }

    private void beep()
    {
        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
    }
}
