package org.firstinspires.ftc.teamcode;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;

/**
 * Created by siddharth on 2/1/17.
 *
 */

public class Team7098RobotControllerActivity extends FtcRobotControllerActivity implements SensorEventListener
{
	private SensorManager sensorManager;
	private Sensor accelerometer;
	private Sensor magnetometer;

	private float[] lastAccelerometer = new float[3];
	private float[] lastMagnetometer = new float[3];
	private boolean lastAccelerometerSet = false;
	private boolean lastMagnetometerSet = false;

	private float[] rotationMat = new float[9];

	private float[] orientation = new float[3];

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		lastAccelerometerSet = false;
		lastMagnetometerSet = false;
		sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public void onPause()
	{
		super.onPause();
		sensorManager.unregisterListener(this);
	}

	@Override
	public void onSensorChanged(SensorEvent event)
	{
		if (event.sensor == accelerometer)
		{
			System.arraycopy(event.values, 0, lastAccelerometer, 0, event.values.length);
			lastAccelerometerSet = true;
		}
		else if (event.sensor == magnetometer)
		{
			System.arraycopy(event.values, 0, lastMagnetometer, 0, event.values.length);
			lastMagnetometerSet = true;
		}
		if (lastAccelerometerSet && lastMagnetometerSet)
		{
			SensorManager.getRotationMatrix(rotationMat, null, lastAccelerometer, lastMagnetometer);
			SensorManager.getOrientation(rotationMat, orientation);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{
	}

	public float[] getOrientation()
	{
		return orientation;
	}
}
