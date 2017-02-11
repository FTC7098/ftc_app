package org.firstinspires.ftc.teamcode;

import android.media.AudioManager;
import android.media.ToneGenerator;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import sameer_s.processor.OpModeMethods;
import sameer_s.processor.OpModeStage;

import static java.lang.Math.abs;
import static sameer_s.processor.OpModeMethods.exec;

@Autonomous(name = "CenterExercisePark", group = "7098")
public class CenterExercisePark extends HybridOpMode
{
	private Hardware7098Robot robot;

	// Stores the encoder value before we do an encoder-based operation so that
	// we can get the
	// total number of encoder ticks travelled
	private int initialEncoder = 0;
	// Stores the time before we do a time-based operation so that we can get
	// the total time elapsed
	private long startTime = 0;
    // Records the number of times the touch sensor has been toggled
	private int touchNum = 0;
    //@formatter:off
	private boolean pushed;

    // Our autonomous program. Read the "Autonomous Programming" Section of our notebook
    // to see how this was implemented

    // @formatter:off
    private OpModeStage[] program = new OpModeStage[]
    {
        // Drive forward to get within range of the center vortex
        // Drives forward at 30% power for 3600 encoder ticks
        exec(() -> initialEncoder = abs(robot.getEncoderValue(0))),
        exec(() -> robot.drive(0.3f, 0.3f)),
        () ->
        {
            telemetry.addData("Initial Encoder", initialEncoder);
            telemetry.addData("Current Encoder", robot.getEncoderValue(0));
            telemetry.addData("Diff", abs(abs(robot.getEncoderValue(0)) - initialEncoder));

            return abs(abs(robot.getEncoderValue(0)) - initialEncoder) >= 5200;
        },
        exec(() -> robot.drive(0)),

        exec(() -> initialEncoder = abs(robot.getEncoderValue(0))),
        exec(() -> robot.drive(-0.15f, -0.15f)),
        () -> abs(abs(robot.getEncoderValue(0)) - initialEncoder) < 5200,
        exec(() -> robot.drive(0)),

        // Runs the robot shooter until either: 1.) the touch sensor has been toggled twice
        // (and therefore the shooter has fired twice) or 2.) seven seconds have elapsed.`
        exec(() -> robot.moveMotor(5, 1.0f)),
        exec(() -> startTime = System.currentTimeMillis()),
        // This code runs in a loop until it returns true
        () ->
        {
            // Sets the value of pushed based on whether or not the sensor is pressed
            if (robot.shooterSwitch.isPressed())
            {
                pushed = true;
            }
            else
            {
                // If the sensor was pushed, and now it's not (it was toggled), we increment
                // touchNum
                if (pushed)
                {
                    pushed = false;
                    touchNum++;
                }
            }
            // Debug log data
            telemetry.addData("Time: ",
                    System.currentTimeMillis() - startTime);
            telemetry.addData("TouchNum: ", touchNum);

            // If either 7000 milliseconds (7 seconds) have elapsed or the touch sensor has toggled
            // twice, we move on. Until then, this stage will run in a loop.
            return System.currentTimeMillis() - startTime >= 7000 || touchNum == 2;
        },
        exec(() -> robot.moveMotor(5, 0.0f)),

        // Drives the robot forward for 3500 encoder ticks, knocking off the cap ball and parking
        // the robot on the center vortex
//        exec(() -> initialEncoder = abs(robot.getEncoderValue(0))),
//        exec(() -> robot.drive(1.0f, 1.0f)),
//        () -> abs(abs(robot.getEncoderValue(0)) - initialEncoder) >= 3500,
//        exec(() -> robot.drive(0))
    };
    // @formatter:on

	@Override
	public void init()
	{
        // Basic initialization code sets servo location
		robot = new Hardware7098Robot(hardwareMap);
        robot.init();
	}

	@Override
	public void loop()
	{
        // Interpreter runs the array defined above. Normally, this would be handled by our
        // annotation processor, but we have disabled that for this tournament (see "Autonomous
        // Programming" section of the Engineering Notebook)
        runProgram(program);
	}

    @Override
    public void stop()
    {
        // Stops all motor movement after autonomous period has ended
        robot.stop();
    }

    // Test code for debugging; not used in final version
    private void beep()
    {
        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
    }
}
