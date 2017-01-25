package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import sameer_s.processor.OpModeMethods;
import sameer_s.processor.OpModeStage;

/**
 * Created by ssuri on 1/25/17.
 *
 */

public abstract class HybridOpMode extends OpMode
{
    private int counter = 0;

    protected void runProgram(OpModeStage[] program)
    {
        if (counter < program.length && OpModeMethods.execute(program[counter]))
        {
            counter++;
        }
    }
}
