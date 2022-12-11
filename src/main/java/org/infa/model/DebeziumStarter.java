package org.infa.model;

import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import org.apache.kafka.connect.source.SourceRecord;
import org.infa.util.LogUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class DebeziumStarter {
    private boolean isRunning;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();


    public DebeziumStarter(DebeziumEngine<RecordChangeEvent<SourceRecord>> engine) {
        LogUtil.log(LogUtil.INFO, () -> "Triggered debezium engine");
        executorService.submit(engine);
        isRunning = true;
    }

    public void stop() {
        executorService.shutdown();
        isRunning = false;
    }

    public void start() {
        if (!isRunning) {
            executorService = Executors.newSingleThreadExecutor();
            isRunning = true;
        }
    }

    public boolean isRunning() {
        return !executorService.isShutdown();
    }

}
