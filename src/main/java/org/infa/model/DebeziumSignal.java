package org.infa.model;

import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import org.apache.kafka.connect.source.SourceRecord;
import org.infa.util.LogUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DebeziumSignal {

    private ExecutorService executorService = Executors.newSingleThreadExecutor();


    public DebeziumSignal(DebeziumEngine<RecordChangeEvent<SourceRecord>> engine) {
        LogUtil.log(LogUtil.INFO, () -> "Triggered debezium engine");
        executorService.submit(engine);
    }
}
