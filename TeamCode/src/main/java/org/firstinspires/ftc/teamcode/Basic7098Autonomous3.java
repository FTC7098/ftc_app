package org.firstinspires.ftc.teamcode;

import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.ToneGenerator;
import android.net.Uri;

import static java.lang.Math.abs;
import static sameer_s.processor.OpModeMethods.exec;
import static sameer_s.processor.OpModeMethods.linear;
import static sameer_s.processor.OpModeMethods.sleep;

import com.qualcomm.robotcore.eventloop.SyncdDevice;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import sameer_s.processor.OpModeStage;

@Disabled
@Autonomous(name = "Basic7098Autonomous3", group = "7098")
public class Basic7098Autonomous3 extends HybridOpMode
{
    private Hardware7098Robot robot;

    private int initialEncoder = 0;
    private long startTime = 0;
    // @formatter:off
	OpModeStage[] program = new OpModeStage[] {

            /*
             * Drive forward to get within range of center vortex
             */
            exec(() -> initialEncoder = abs(robot.getEncoderValue(0))),
            exec(() -> robot.drive(.3f, .3f)),
            () -> {
                telemetry.addData("01", robot.getEncoderValue(0));
                telemetry.addData("02", initialEncoder);
                return abs(abs(robot.getEncoderValue(0)) - initialEncoder) >= 1440;
            },
            exec(() -> robot.drive(0)),

            exec(() -> startTime = System.currentTimeMillis()),
            () -> System.currentTimeMillis() - startTime >= 250,

            exec(this::beep),

            /*
             * Fire both balls into the vortex
             */

//            exec(() -> robot.moveMotor(5, 1)),
//            exec(() -> startTime = System.currentTimeMillis()),
//            () -> System.currentTimeMillis() - startTime >= 5000,
//            exec(() -> robot.moveMotor(5, 0)),


            /*
             * Back away from the vortex
             */
            exec(() -> initialEncoder = abs(robot.getEncoderValue(0))),
            exec(() -> robot.drive(-.3f, -.3f)),
            () -> {
                telemetry.addData("01", robot.getEncoderValue(0));
                telemetry.addData("02", initialEncoder);
                return abs(abs(robot.getEncoderValue(0)) - initialEncoder) >= 540;
            },
            exec(() -> robot.drive(0)),

            exec(() -> startTime = System.currentTimeMillis()),
            () -> System.currentTimeMillis() - startTime >= 250,

            /*
             * Turn towards the near beacon
             */
            exec(() -> initialEncoder = abs(robot.getEncoderValue(0))),
            exec(() -> robot.drive(.5f, -.5f)),
            exec(() -> startTime = System.currentTimeMillis()),

            () -> {
                telemetry.addData("01", robot.getEncoderValue(0));
                return abs(abs(robot.getEncoderValue(0)) - initialEncoder) >= 1100;
            },
            exec(() -> robot.drive(0)),

            exec(() -> startTime = System.currentTimeMillis()),
            () -> System.currentTimeMillis() - startTime >= 250,

            exec(this::beep),

            /*
             * Drive towards the beacon a fixed distance by encoders
             */
            exec(() -> initialEncoder = abs(robot.getEncoderValue(0))),
            exec(() -> robot.drive(.3f, .3f)),
            () -> {
                telemetry.addData("01", robot.getEncoderValue(0));
                telemetry.addData("02", initialEncoder);
                return abs(abs(robot.getEncoderValue(0)) - initialEncoder) >= 2000;
            },
            // exec(() -> robot.drive(0)),


            /*
             * Drive forward until left light sensor reaches the white line
             */
            exec(() -> robot.csLeft.enableLed(true)),
            exec(() -> robot.csRight.enableLed(true)),
            exec(() -> initialEncoder = abs(robot.getEncoderValue(0))),
            exec(() -> robot.drive(.1f, .1f)),
            () -> {
                telemetry.addData("03", robot.csLeft.getLightDetected());
                return robot.csLeft.getLightDetected() > 0.3;
            },
            exec(() -> robot.drive(0)),

            /*
             * Drive forward past the line
             */
            /*
            exec(() -> initialEncoder = abs(robot.getEncoderValue(0))),
            exec(() -> robot.drive(.1f, .1f)),
            () -> {
                telemetry.addData("01", robot.getEncoderValue(0));
                telemetry.addData("02", initialEncoder);
                return abs(abs(robot.getEncoderValue(0)) - initialEncoder) >= 200;
            },
            // exec(() -> robot.drive(0)),
            */

            exec(() -> startTime = System.currentTimeMillis()),
            () -> System.currentTimeMillis() - startTime >= 250,

            /*
             * Turn left the same amount as was turned right
             */
            exec(() -> initialEncoder = abs(robot.getEncoderValue(2))),
            exec(() -> robot.drive(-.5, .5)),
            () -> {
                telemetry.addData("01", robot.getEncoderValue(2));
                return abs(abs(robot.getEncoderValue(2)) - initialEncoder) >= 1100;
            },
            exec(() -> robot.drive(0)),

            exec(() -> startTime = System.currentTimeMillis()),
            () -> System.currentTimeMillis() - startTime >= 250,

            /*
             * Drive forward past the line
             */
            exec(() -> initialEncoder = abs(robot.getEncoderValue(0))),
            exec(() -> robot.drive(.3f, .3f)),
            () -> {
                telemetry.addData("01", robot.getEncoderValue(0));
                telemetry.addData("02", initialEncoder);
                return abs(abs(robot.getEncoderValue(0)) - initialEncoder) >= 400;
            },
            exec(() -> robot.drive(0)),


            /*
             * Back up and align both light sensors on the line.
             */
            exec(() -> startTime = System.currentTimeMillis()),
            () -> System.currentTimeMillis() - startTime >= 250,

            () ->
            {
                robot.drive(robot.csLeft.getLightDetected() > 0.3 ? 0 : -.1, robot.csRight.getLightDetected() > 0.3 ? 0 : -.1);
                return robot.csLeft.getLightDetected() > 0.3 && robot.csRight.getLightDetected() > 0.3;
            },


//            exec(() -> robot.drive(0, .5)),
//            () -> robot.csRight.getLightDetected() > 0.3,
//            exec(() -> robot.drive(0)),
//
//            exec(() -> startTime = System.currentTimeMillis()),
//            () -> System.currentTimeMillis() - startTime >= 250,
//
//            exec(() -> initialEncoder = abs(robot.getEncoderValue(0))),
//            exec(() -> robot.drive(-.3f, -.3f)),
//            () -> {
//                telemetry.addData("01", robot.getEncoderValue(0));
//                telemetry.addData("02", initialEncoder);
//                return abs(abs(robot.getEncoderValue(0)) - initialEncoder) >= 460;
//            },
//            exec(() -> robot.drive(0)),
//
//            exec(() -> startTime = System.currentTimeMillis()),
//            () -> System.currentTimeMillis() - startTime >= 250,
//
//            () ->
//            {
//                robot.drive(robot.csLeft.getLightDetected() > 0.3 ? 0 : .1, robot.csRight.getLightDetected() > 0.3 ? 0 : .1);
//                return robot.csLeft.getLightDetected() > 0.3 && robot.csRight.getLightDetected() > 0.3;
//            },
//            exec(() -> robot.drive(0))
    };

    // HOW TO USE
    // DO NOT USE LOOPS
    /* exec(() -> {
            // actions to run.
            // these only run once
       });

      () -> {
            // actions to run
            // these run in a loop until it returns true

            return value;
      };

      () -> <boolean expression>
      // This waits until the boolean expression becomes true
   */

	// @formatter:on

    //            () ->
//			{
//                robot.moveMotor(5, 1);
//			    return robot.shooterSwitch();
//            },
//			() -> !robot.shooterSwitch(),
//			() -> robot.shooterSwitch(),
//			() -> !robot.shooterSwitch(),
//			exec(() -> robot.moveMotor(5, 0))

	@Override
	public void init()
	{
		robot = new Hardware7098Robot(hardwareMap);
        robot.init();
	}

	@Override
	public void loop()
	{
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
