package com.abu.framework;

import org.testng.IExecutionListener;

public class ExecutionListener implements IExecutionListener {
    @Override
    public void onExecutionStart() {
        // usually setting up proxy settings here
    }

    @Override
    public void onExecutionFinish() {
        // and doing something else...
    }
}
