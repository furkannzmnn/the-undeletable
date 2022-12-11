package org.infa.connection;

import io.debezium.config.Configuration;
import io.debezium.embedded.Connect;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import io.debezium.engine.format.ChangeEventFormat;
import org.apache.kafka.connect.source.SourceRecord;
import org.infa.Connection;
import org.infa.model.DebeziumStarter;

import java.util.Optional;
import java.util.function.Consumer;

public class DebeziumConnector implements Connector<DebeziumStarter, Connection> {
    private DebeziumEngine<RecordChangeEvent<SourceRecord>> engine;

    @Override
    public DebeziumStarter connect(Connection data, Optional<Runnable> script) {
        final Configuration configuration = DebeziumConfigLoader.load(data);

        engine = DebeziumEngine.create(ChangeEventFormat.of(Connect.class))
                .using(configuration.asProperties())
                .notifying(getEventConsumer(script))
                .build();

        return new DebeziumStarter(engine);
    }

    private Consumer<RecordChangeEvent<SourceRecord>> getEventConsumer(Optional<Runnable> script) {
        return record -> {
            final boolean present = script.isPresent();
            if (present) {
                script.get().run();
            } else {
                handleEvent(record);
            }
        };
    }

    private void handleEvent(RecordChangeEvent<SourceRecord> record) {
        System.out.println("Received event: " + record);
    }
}
