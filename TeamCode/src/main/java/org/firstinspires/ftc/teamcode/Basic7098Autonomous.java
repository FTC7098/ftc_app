package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import sameer_s.processor.OpModeStage;

import static sameer_s.processor.OpModeMethods.*;

//@ProcessedOpMode(type = OpModeType.AUTONOMOUS, name = "Basic7098Autonomous", group = "7098")
public class Basic7098Autonomous extends OpMode
{
//	@LogRobot
	private Hardware7098Robot robot;

	// @formatter:off
	OpModeStage[] program = new OpModeStage[]
    {
        sleep(1000),
        linear(() ->
        {
            //DON'T KNOW HOW THIS WORKS
            //Drive forward some amount
            //Fire twice
            //Turn left
            //Drive until right sensor sees line
            //Turn right until left sensor sees line
            //Drive forward, test color
                //If correct, adjust and press
            //Otherwise test other color
                //If correct, adjust and press
            //Goto next line and repeat
            /*
		    for (int i = 0; i < 6; i++)
		    {
			    robot.moveMotor(i, .5);
			    Thread.sleep(1000);
			    robot.moveMotor(i, 0);
			    Thread.sleep(1000);
		    }
		    */
	    })
    };
	// @formatter:on

	@Override
	public void init()
	{
		robot = new Hardware7098Robot(hardwareMap);
	}

	@Override
	public void loop()
	{

	}
}
