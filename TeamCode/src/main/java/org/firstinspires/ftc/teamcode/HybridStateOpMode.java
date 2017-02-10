package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import sameer_s.processor.OpModeMethods;
import sameer_s.processor.OpModeStage;

/**
 * Created by ssuri on 1/25/17.
 *
 */

public abstract class HybridStateOpMode extends OpMode
{
    protected int counter = 0;
    protected int state = 0;

    protected void runProgram(OpModeStage[][] program)
    {
        if (state < program.length && counter < program[state].length && OpModeMethods.execute(program[state][counter]))
        {
            counter++;
        }
    }
}
