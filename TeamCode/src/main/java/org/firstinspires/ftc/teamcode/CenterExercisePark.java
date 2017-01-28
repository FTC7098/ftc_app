package org.firstinspires.ftc.teamcode;

import android.media.AudioManager;
import android.media.ToneGenerator;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import sameer_s.processor.OpModeStage;

import static java.lang.Math.abs;
import static sameer_s.processor.OpModeMethods.exec;

@Autonomous(name = "CenterExercisePark", group = "7098")
public class CenterExercisePark extends HybridOpMode
{
    private Hardware7098Robot robot;

    private int initialEncoder = 0;
    private long startTime = 0;
    private int touchNum = 0;
    private boolean pushed;
    // @formatter:off
	OpModeStage[] program = new OpModeStage[] {
        /*
         * Drive forward to get within range of the center vortex
         */
            exec(() -> initialEncoder = abs(robot.getEncoderValue(0))),
            exec(() -> robot.drive(0.3f, 0.3f)),
            () -> abs(abs(robot.getEncoderValue(0)) - initialEncoder) >= 4100,
            exec(() -> robot.drive(0)),

            exec(() -> robot.moveMotor(5, 1.0f)),
            exec(() -> startTime = System.currentTimeMillis()),
            () -> {
                if (robot.shooterSwitch.isPressed()) {
                    pushed = true;
                } else
                {
                    if (pushed) {
                        pushed = false;
                        touchNum++;
                    }
                }
                telemetry.addData("Time: ", System.currentTimeMillis() - startTime);
                telemetry.addData("TouchNum: ", touchNum);
                return System.currentTimeMillis() - startTime >= 7000 || touchNum == 2;
            },
            exec(() -> robot.moveMotor(5, 0.0f)),


            exec(() -> initialEncoder = abs(robot.getEncoderValue(0))),
            exec(() -> robot.drive(1.0f, 1.0f)),
            () -> abs(abs(robot.getEncoderValue(0)) - initialEncoder) >= 3500,
            exec(() -> robot.drive(0)),
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
