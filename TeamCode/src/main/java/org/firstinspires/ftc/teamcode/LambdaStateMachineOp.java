package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.List;

/**
 * Created by Sameer on 8/21/2015.
 *
 */
public abstract class LambdaStateMachineOp extends OpMode
{
    private int[] indices(int... indices)
    {
        return indices;
    }

    private void runLambdas(int[] indices, Runnable[]... lambdas)
    {
        for (int i = 0; i < lambdas.length; i++)
        {
            lambdas[i][indices[i]].run();
        }
    }

    protected void runLambdas(List<Integer> indices, List<Runnable[]> lambdas)
    {
        int[] indicesArr = new int[indices.size()];
        for(int i = 0; i < indices.size(); i++)
        {
            indicesArr[i] = indices.get(i);
        }
        runLambdas(indicesArr, lambdas.toArray(new Runnable[0][0]));
    }

    protected void runLambdas(int[] indices, List<Runnable[]> lambdas)
    {
        runLambdas(indices, lambdas.toArray(new Runnable[0][0]));
    }

    protected void runLambdas(List<Integer> indices, Runnable[]... lambdas)
    {
        int[] indicesArr = new int[indices.size()];
        for(int i = 0; i < indices.size(); i++)
        {
            indicesArr[i] = indices.get(i);
        }
        runLambdas(indicesArr, lambdas);
    }

    protected void runLambdas(int index, List<Runnable[]> lambdas)
    {
        runLambdas(indices(index), lambdas.toArray(new Runnable[0][0]));
    }

    void runLambdas(int index, Runnable[]... lambdas)
    {
        runLambdas(indices(index), lambdas);
    }
}