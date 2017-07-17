package com.possoajudar.app.JUnit;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.assertEquals;

/**
 * Created by renato on 17/07/2017.
 */

public class TestRunner {
    @Test
    public void junitAnnotation() throws Exception {
        Result result = JUnitCore.runClasses(JunitAnnotation.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }

}
