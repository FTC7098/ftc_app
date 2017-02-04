package org.firstinspires.ftc.teamcode;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import sameer_s.processor.OpModeStage;
import sameer_s.processor.ProcessedOpMode;

import static sameer_s.processor.OpModeType.AUTONOMOUS;

/**
 * Created by ssuri on 2/3/17.
 *
 */

@Autonomous(name = "Internal Sensor Test", group = "7098")
public class InternalSensorTestOpMode extends OpMode
{
    private Team7098RobotControllerActivity activity;

    @Override
    public void init()
    {
        activity = (Team7098RobotControllerActivity) hardwareMap.appContext;
    }

    @Override
    public void loop()
    {
        float[] orientation = activity.getOrientation();
        telemetry.addData("Orientation", String.format("%f, %f, %f", orientation[0], orientation[1], orientation[2]));
    }
}
