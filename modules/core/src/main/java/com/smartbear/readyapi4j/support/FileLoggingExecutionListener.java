package com.smartbear.readyapi4j.support;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.smartbear.readyapi4j.ExecutionListenerAdapter;
import com.smartbear.readyapi4j.execution.Execution;
import com.smartbear.readyapi4j.result.TestStepResult;
import io.swagger.util.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

/**
 * ExecutionListener that writes response HAR entries to a single log file after execution
 */
public class FileLoggingExecutionListener extends ExecutionListenerAdapter {

    private final static Logger LOG = LoggerFactory.getLogger(FileLoggingExecutionListener.class);

    public static final String DEFAULT_EXTENSION = "log";

    private final String targetFolder;
    private final String extension;

    public FileLoggingExecutionListener(String targetFolder, String extension) {
        this.targetFolder = targetFolder;
        this.extension = extension;
    }

    public FileLoggingExecutionListener(String targetFolder) {
        this(targetFolder, DEFAULT_EXTENSION);
    }

    @Override
    public void executionFinished(Execution execution) {
        try {
            File directory = new File(targetFolder);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            List<Map<Object,Object>> entries = Lists.newArrayList();
            for(TestStepResult result : execution.getExecutionResult().getTestStepResults()){
                entries.add( getLogDataForResult(result));
            }

            File file = File.createTempFile( "execution-" + execution.getId(), "." + extension, directory);
            try ( FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                fileOutputStream.write( Json.pretty(entries).getBytes());
            }
        } catch (Exception e) {
            LOG.error("Failed to write response logs to file", e);
        }
    }

    private Map<Object,Object> getLogDataForResult(TestStepResult testStepResult) {
        Map<Object,Object> result = Maps.newConcurrentMap();

        result.put( "testStep", testStepResult.getTestStepName());
        result.put( "timeTaken", testStepResult.getTimeTaken() );
        result.put( "status", testStepResult.getAssertionStatus());

        if( testStepResult.getMessages() != null && !testStepResult.getMessages().isEmpty()){
            result.put( "messsages", testStepResult.getMessages());
        }
        if( testStepResult.getHarEntry() != null ) {
            result.put("harEntry", testStepResult.getHarEntry());
        }

        return result;
    }
}