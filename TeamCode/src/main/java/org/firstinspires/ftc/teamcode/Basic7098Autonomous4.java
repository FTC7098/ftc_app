package org.firstinspires.ftc.teamcode;

import android.media.AudioManager;
import android.media.ToneGenerator;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import sameer_s.processor.OpModeStage;

import static java.lang.Math.abs;
import static sameer_s.processor.OpModeMethods.exec;

@Disabled
@Autonomous(name = "Basic7098Autonomous4", group = "7098")
public class Basic7098Autonomous4 extends HybridOpMode
{
    private Hardware7098Robot robot;

    private int initialEncoder = 0;
    private long startTime = 0;
    // @formatter:off
	OpModeStage[] program = new OpModeStage[] {
            exec(() -> initialEncoder = abs(robot.getEncoderValue(0))),
            exec(() -> robot.drive(.5f, 0)),
            () -> {
                telemetry.addData("01", robot.getEncoderValue(0));
                return abs(abs(robot.getEncoderValue(0)) - initialEncoder) >= 1425;
            },
            exec(() -> robot.drive(0)),
    };

	@Override
	public void init()
	{
		robot = new Hardware7098Robot(hardwareMap);
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
