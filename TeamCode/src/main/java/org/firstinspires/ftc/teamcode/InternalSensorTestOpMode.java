package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by ssuri on 2/3/17.
 *
 */
@Disabled
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
