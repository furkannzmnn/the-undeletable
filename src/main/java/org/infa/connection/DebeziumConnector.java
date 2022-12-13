package org.infa.connection;

import io.debezium.config.Configuration;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import io.debezium.engine.format.CloudEvents;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.connect.source.SourceRecord;
import org.infa.Connection;
import org.infa.RunnableScript;
import org.infa.model.DebeziumStarter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Consumer;

public class DebeziumConnector implements Connector<DebeziumStarter, Connection> {
    private DebeziumEngine<ChangeEvent<String, String>> engine;

    @Override
    public DebeziumStarter connect(Connection data, RunnableScript<String, String> script) {
        final Configuration configuration = DebeziumConfigLoader.load(data);

        engine = DebeziumEngine.create(CloudEvents.class)
                .using(configuration.asProperties())
                .notifying(handleEvent(script))
                .build();

        return new DebeziumStarter(engine);
    }

    private Consumer<ChangeEvent<String, String>> handleEvent(RunnableScript<String, String> script) {
        return each -> {
            if (script == null) {
                sendToKafka(each);
            } else {
                executeCustomScript(each, script);
            }
        };
    }

    private void sendToKafka(ChangeEvent<String, String> event) {
        if (event.key() != null) {
            KafkaProducer<String, String> producer = new KafkaProducer<>(KafkaClient.defaultKafkaClient());
            producer.send(new ProducerRecord<>(event.destination(), event.key(), event.value()));
            producer.close();
        }

    }

    private void executeCustomScript(ChangeEvent<String, String> event, RunnableScript<String, String> runnableScript) {
        String key = event.key();
        String value = event.value();
        runnableScript.run(key, value);
    }


    private void handleEvent(RecordChangeEvent<SourceRecord> record) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(() -> {
            final Object value = record.record().value();
            final String topic = record.record().topic();
        }, 10, java.util.concurrent.TimeUnit.SECONDS);
    }
}
