package org.infa.connection;

import io.debezium.config.Configuration;
import io.debezium.embedded.Connect;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import io.debezium.engine.format.ChangeEventFormat;
import org.apache.kafka.connect.source.SourceRecord;
import org.infa.Connection;
import org.infa.model.DebeziumSignal;

public class DebeziumConnector implements Connector<DebeziumSignal, Connection> {
    private DebeziumEngine<RecordChangeEvent<SourceRecord>> engine;

    @Override
    public DebeziumSignal connect(Connection data) {
        final Configuration configuration = DebeziumConfigLoader.load(data);

        engine = DebeziumEngine.create(ChangeEventFormat.of(Connect.class))
                .using(configuration.asProperties())
                .notifying(this::handleEvent)
                .build();

        return new DebeziumSignal(engine);
    }

    private void handleEvent(RecordChangeEvent<SourceRecord> sourceRecordRecordChangeEvent) {
        System.out.println("Received event: " + sourceRecordRecordChangeEvent);
    }
}
