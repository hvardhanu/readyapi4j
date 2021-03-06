package com.smartbear.readyapi4j.support;

import com.smartbear.readyapi.client.model.HarResponse;
import com.smartbear.readyapi.client.model.ProjectResultReport;
import com.smartbear.readyapi4j.execution.Execution;
import com.smartbear.readyapi4j.result.TestStepResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Utility class for asserting an Execution
 */

public class AssertionUtils {

    private static final Logger LOG = LoggerFactory.getLogger(AssertionUtils.class);

    public static void assertExecution(Execution execution) {
        assertNotNull(execution);

        if (LOG.isDebugEnabled()) {
            for (TestStepResult result : execution.getExecutionResult().getTestStepResults()) {

                try {
                    HarResponse response = result.getHarResponse();
                    LOG.info("TestStep [" + result.getTestStepName() + "] response: " + response.getStatus() +
                            " - " + response.getContent().getText());
                } catch (RuntimeException e) {
                    LOG.debug("Missing  HAR response for TestStep [" + result.getTestStepName() + "]", e);
                }
            }
        }

        assertEquals(Arrays.toString(execution.getErrorMessages().toArray()),
                ProjectResultReport.StatusEnum.FINISHED, execution.getCurrentStatus());
    }
}
