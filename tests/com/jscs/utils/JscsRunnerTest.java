package com.jscs.utils;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.process.ProcessOutput;
import com.jscs.utils.data.JscsLint;
import com.jscs.utils.data.LintResult;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JscsRunnerTest {

    public static final String NODE_INTERPRETER = "/usr/local/bin/node";
    public static final String JSCS_BIN = "/usr/local/bin/jscs";
    public static final String JSCS_PLUGIN_ROOT = "/Users/idok/Projects/jscs-plugin";

    private static JscsSettings createSettings(String targetFile) {
        return JscsSettings.build(JSCS_PLUGIN_ROOT + "/testData", targetFile, NODE_INTERPRETER, JSCS_BIN, "", "");
    }

    private static JscsSettings createSettings() {
        return createSettings("");
    }

//    @Test
//    public void testSimpleLint() {
//        JscsSettings settings = createSettings(JSCS_PLUGIN_ROOT + "/testData/eq.js");
//        ProcessOutput out;
//        try {
//            out = JscsRunner.lint(settings);
////            System.out.println(settings);
//            System.out.println(out.getStdout());
//            assertEquals("Exit code should be 1", 1, out.getExitCode());
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void testSimpleLint2() {
        JscsSettings settings = createSettings(JSCS_PLUGIN_ROOT + "/testData/test.js");
        LintResult result = JscsRunner.runLint(settings);
        System.out.println(result.errorOutput);
        System.out.println(result.jscsLint.file.name);
        System.out.println("found " + result.jscsLint.file.errors.size() + " issues");

        for (JscsLint.Issue err : result.jscsLint.file.errors) {
            System.out.println(err.message);
        }
        assertEquals("Exit code should be 1", JSCS_PLUGIN_ROOT + "/testData/test.js", result.jscsLint.file.name);
    }

    @Test
    public void testVersion() {
        JscsSettings settings = createSettings();
        try {
            String version = JscsRunner.runVersion(settings);
            assertEquals("version should be", "1.6.1", version);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
