package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import sameer_s.processor.LogRobot;
import sameer_s.processor.OpModeStage;
import sameer_s.processor.ProcessedOpMode;

import static java.lang.Math.abs;
import static sameer_s.processor.OpModeMethods.exec;
import static sameer_s.processor.OpModeType.AUTONOMOUS;

/**
 * Created by ssuri on 2/3/17.
 *
 */

@Autonomous(name = "Internal Sensor Turn Test", group = "7098")
public class InternalSensorTurnTestOpMode extends HybridOpMode
{
    @LogRobot
    private Hardware7098Robot robot;

    OpModeStage[] program = new OpModeStage[] {
            exec(() -> robot.setHeadingTarget(Math.PI / 2)),
            exec(() -> robot.drive(.5f, 0)),
            () -> {
                telemetry.addData("01", robot.getHeading());
                return robot.checkHeading(Math.PI / 16);
            },
            exec(() -> robot.drive(0))
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
}
