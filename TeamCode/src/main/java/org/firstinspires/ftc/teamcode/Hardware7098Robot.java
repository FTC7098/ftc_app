package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by ssuri on 10/19/16.
 *
 */
public class Hardware7098Robot
{
    // This class is fairly self-documenting and thus line-by-line comments are not provided.
    // Overall, this class is used to abstract the implementation of moving the robot. Instead of
    // moving individual motors, we have simplified it with methods like drive(power). Some of the
    // methods below are not completely finalized and/or implemented. If so, it has been
    // appropriately marked.


    public DcMotorController leftDrive, rightDrive, shooter, lift;
    public ServoController servo;
    public LightSensor csLeft, csRight, csSide;
    public UltrasonicSensor ultra;
    public TouchSensor shooterSwitch;
    public GyroSensor gyro;
    public Team7098RobotControllerActivity activity;

    public Hardware7098Robot(HardwareMap map)
    {
        leftDrive = map.dcMotorController.get("left_drive");
        rightDrive = map.dcMotorController.get("right_drive");
        servo = map.servoController.get("servo");
        shooter = map.dcMotorController.get("shooter");
        lift = map.dcMotorController.get("lift");
        csLeft = map.lightSensor.get("left");
        csRight = map.lightSensor.get("right");
        csSide = map.lightSensor.get("beacon");
        ultra = map.ultrasonicSensor.get("ultra");
        shooterSwitch = map.touchSensor.get("shooter_switch");
        activity = (Team7098RobotControllerActivity) map.appContext;
        //gyro = map.gyroSensor.get("gyro");
    }

    public void init()
    {
        setServo(0, 0.8);
        setServo(1, 0.5);
        setServo(2, 1.0);

//        gyro.calibrate();
    }

    public int getEncoderValue(int index)
    {
        DcMotorController controller = index < 2 ? leftDrive : (index < 4 ? rightDrive : (index < 6 ? shooter : lift));
        return controller.getMotorCurrentPosition(1 + (index % 2));
    }

    public boolean resetEncoder(int index)
    {
        DcMotorController controller = index < 2 ? leftDrive : (index < 4 ? rightDrive : (index < 6 ? shooter : lift));
        if(controller.getMotorMode(1 + (index % 2)) == DcMotor.RunMode.STOP_AND_RESET_ENCODER)
        {
            controller.setMotorMode(1 + (index % 2), DcMotor.RunMode.RUN_USING_ENCODER);
            return true;
        }
        else
        {
            controller.setMotorMode(1 + (index % 2), DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            return false;
        }
    }
    public void logRobot(Telemetry telemetry)
    {
        // not implemented
        telemetry.addData("Test Log", "Test Log");
    }

    public boolean shooterSwitch()
    {
        return shooterSwitch.isPressed();
    }

    public void stop()
    {
        for(int i = 0; i < 8; i++)
        {
            moveMotor(i, 0);
        }
    }


    public void moveMotor(int index, double pow)
    {
        DcMotorController controller = index < 2 ? leftDrive : (index < 4 ? rightDrive : (index < 6 ? shooter : lift));
        controller.setMotorPower(1 + (index % 2), pow);
    }

    public void setServo(int index, double pos)
    {
        servo.setServoPosition(index + 1, Math.max(0, Math.min(1, pos)));
    }

    public double getServo(int index)
    {
        return servo.getServoPosition(index + 1);
    }

    public void drive(double pow)
    {
        drive(pow, pow);
    }

    public void drive(double left, double right)
    {
        leftDrive.setMotorPower(1, left);
        leftDrive.setMotorPower(2, left);
        rightDrive.setMotorPower(1, -right);
        rightDrive.setMotorPower(2, -right);
    }

    public void swingTurn(double pow, boolean right)
    {
        leftDrive.setMotorPower(1, pow * (right ? 1 : -1));
        leftDrive.setMotorPower(2, pow * (right ? 1 : -1));
        rightDrive.setMotorPower(1, pow * (right ? -1 : 1));
        rightDrive.setMotorPower(2, pow * (right ? -1 : 1));
    }

    public void pointTurn(double pow, boolean right)
    {
        leftDrive.setMotorPower(1, pow * (right ? 1 : 0));
        leftDrive.setMotorPower(2, pow * (right ? 1 : 0));
        rightDrive.setMotorPower(1, pow * (right ? 0 : 1));
        rightDrive.setMotorPower(2, pow * (right ? 0 : 1));
    }

    public void gyroTurn() {
       gyro.getHeading();
    }

    public void lineFollow(LineFollowData data)
    {
        // not implemented
    }

    public static class LineFollowData
    {
        public int colorSensorNum;
        private int integral, differential;
        private float kP, kI, kD;

        public LineFollowData(int colorSensorNum, float kP, float kI, float kD)
        {
            this.colorSensorNum = colorSensorNum;
        }
    }
    private double targetHeading = -1;

    public void setHeadingTarget(double relativeTarget)
    {
        targetHeading = simplifyAngle(getHeading() + relativeTarget);
    }

    public boolean checkHeading(double threshold)
    {
        return Math.abs(simplifyAngle(getHeading() - targetHeading)) < threshold;
    }

    public double getHeading()
    {
        return activity.getOrientation()[0];
    }

    private double simplifyAngle(double angle)
    {
        while(angle <= -Math.PI) angle += 2 * Math.PI;
        while(angle > Math.PI) angle -= 2 * Math.PI;

        return angle;
    }
}
