package org.firstinspires.ftc.teamcode;

import android.media.AudioManager;
import android.media.ToneGenerator;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import sameer_s.processor.OpModeStage;

import static java.lang.Math.abs;
import static sameer_s.processor.OpModeMethods.exec;


@TeleOp(name = "Beacon (Blue)", group = "7098")
public class ShallowBeaconBlue extends HybridStateOpMode
{
    private Hardware7098Robot robot;

    private int initialEncoder = 0;
    private long startTime = 0;

    private int moving = 0;

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
            () ->
            {
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

            /*
             * Turn left 150 ticks
            */
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

            exec(() -> initialEncoder = abs(robot.getEncoderValue(0))),
            exec(() -> robot.drive(.1f, .1f)),
            () -> {
                telemetry.addData("01", robot.getEncoderValue(0));
                telemetry.addData("02", initialEncoder);
                telemetry.addData("03", abs(abs(robot.getEncoderValue(0)) - initialEncoder));
                return abs(abs(robot.getEncoderValue(0)) - initialEncoder) >= 150;
            },
            exec(() -> robot.drive(0)),

            exec(() -> startTime = System.currentTimeMillis()),
            () -> System.currentTimeMillis() - startTime >= 250,

            /*
             * Turn left 150 ticks
            */
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

            exec(() -> initialEncoder = abs(robot.getEncoderValue(0))),
            exec(() -> robot.drive(-.1f, -.1f)),
            () -> {
                telemetry.addData("01", robot.getEncoderValue(0));
                telemetry.addData("02", initialEncoder);
                telemetry.addData("03", abs(abs(robot.getEncoderValue(0)) - initialEncoder));
                return abs(abs(robot.getEncoderValue(0)) - initialEncoder) >= 250;
            },
            exec(() -> robot.drive(0)),
            exec(() -> startTime = System.currentTimeMillis()),
            () -> System.currentTimeMillis() - startTime >= 250,

            exec(() -> startTime = System.currentTimeMillis()),
            () ->
            {
                if(System.currentTimeMillis() - startTime >= 4000)
                {
//                    state = 1;
//                    counter = -1;
                    return true;
                }

                boolean left = robot.csLeft.getLightDetected() > COLOR_THRESHOLD;
                boolean right = robot.csRight.getLightDetected() > COLOR_THRESHOLD;

                if(left && right)
                {
                    return true;
                }
                else if(left)
                {
                    robot.drive(-.25, .25);
                }
                else if(right)
                {
                    robot.drive(.25, -.25);
                }
                else
                {
//                    if(seenWhite)
//                    {
//                        return true;
//                    }

                    robot.drive(-.2, -.2);
                }

                return false;
            },
            exec(() -> robot.drive(0)),

            exec(() ->
            {
//                state = 2;
                state = 1;
                counter = -1;
            }),
        },
        {
            exec(() -> initialEncoder = abs(robot.getEncoderValue(0))),
            exec(() -> robot.drive(-.2f, -.2f)),
            () ->
            {
                telemetry.addData("01", robot.getEncoderValue(0));
                telemetry.addData("02", initialEncoder);
                return abs(abs(robot.getEncoderValue(0)) - initialEncoder) >= 150;
            },
            exec(() -> robot.drive(0)),

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
                    moving = 1;
                    robot.drive(.25, -.25);
                }
                else if(right)
                {
                    moving = -1;
                    robot.drive(-.25, .25);
                }
                else
                {
                    moving = 0;
                    robot.drive(.2, .2);
                }

                return false;
            },

            exec(() ->
            {
                state = 2;
                counter = -1;
            }),
        },
        {
            exec(() -> startTime = System.currentTimeMillis()),
            () -> System.currentTimeMillis() - startTime >= 250,

            exec(() ->
            {
                state = robot.csSide.getLightDetected() > .25 ? 3 : 4;
                telemetry.addData("Color:", state);
                counter = -1;
            })
        },
        // FAR
        {
            () ->
            {
                telemetry.addData("Current State", "Far");
                return gamepad1.b;
            },

            exec(() -> initialEncoder = abs(robot.getEncoderValue(0))),
            exec(() -> robot.drive(.2f, .2f)),
            () ->
            {
                telemetry.addData("01", robot.getEncoderValue(0));
                telemetry.addData("02", initialEncoder);
                return abs(abs(robot.getEncoderValue(0)) - initialEncoder) >= 350;
            },
            exec(() -> robot.drive(0)),

            exec(() ->
            {
                state = 5;
                counter = -1;
            })
        },
        // CLOSE
        {
            () ->
            {
                telemetry.addData("Current State", "Close");
                return gamepad1.b;
            },

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
                state = 5;
                counter = -1;
            })
        },
        // AFTER IN PLACE
        {
            exec(this::beep),
            exec(() -> robot.setServo(1, 0.0f)),

            exec(() -> startTime = System.currentTimeMillis()),
            () -> System.currentTimeMillis() - startTime >= 1000,

            exec(this::beep),
            exec(() -> robot.setServo(1, 0.5f)),
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
