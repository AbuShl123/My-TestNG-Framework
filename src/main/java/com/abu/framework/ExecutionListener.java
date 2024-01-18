package com.abu.framework;

import org.testng.IExecutionListener;

public class ExecutionListener implements IExecutionListener {
    @Override
    public void onExecutionStart() {
        System.setProperty("logfile.name", "tc001");
    }

    @Override
    public void onExecutionFinish() {

    }
}
