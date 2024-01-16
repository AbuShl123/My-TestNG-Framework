package com.abu.framework;

import org.testng.IExecutionListener;

public abstract class ExecutionListener implements IExecutionListener {
    @Override
    public void onExecutionStart() {
        IExecutionListener.super.onExecutionStart();
    }

    @Override
    public void onExecutionFinish() {
        IExecutionListener.super.onExecutionFinish();
    }
}
