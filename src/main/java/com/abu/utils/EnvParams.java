package com.abu.utils;

import com.abu.framework.FrameworkConstants;

import java.util.Properties;

public class EnvParams {

    private EnvParams() {
    }

    public final static String BROWSER;

    static {
        Properties props = FileReader.readProperties(FrameworkConstants.PROPERTIES_PATH);

        BROWSER = props.getProperty("browser");
    }
}
