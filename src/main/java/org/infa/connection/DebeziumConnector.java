package org.infa.connection;

import io.debezium.config.Configuration;
import io.debezium.connector.mysql.MySqlConnector;
import io.debezium.embedded.Connect;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import io.debezium.engine.format.ChangeEventFormat;
import io.debezium.engine.format.CloudEvents;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.connect.source.SourceRecord;
import org.infa.Connection;
import org.infa.model.DebeziumStarter;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Consumer;

public class DebeziumConnector implements Connector<DebeziumStarter, Connection> {
    private DebeziumEngine<ChangeEvent<String, String>> engine;

    @Override
    public DebeziumStarter connect(Connection data, Optional<Runnable> script) {
        final Configuration configuration = DebeziumConfigLoader.load(data);
        MySqlConnector connector = new MySqlConnector();
        connector.start(configuration.asMap());
        connector.validate(configuration.asMap());

        engine = DebeziumEngine.create(CloudEvents.class)
                .using(configuration.asProperties())
                .notifying(this::sendToKafka)
                .build();

        // poll


        return new DebeziumStarter(engine);
    }

    private void sendToKafka(ChangeEvent<String, String> event) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        if (event.key() != null) {
            KafkaProducer<String, String> producer = new KafkaProducer<>(props);
            producer.send(new ProducerRecord<>(event.destination(), event.key(), event.value()));
            producer.close();
        }

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
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(() -> {
            final Object value = record.record().value();
            final String topic = record.record().topic();
        }, 10, java.util.concurrent.TimeUnit.SECONDS);
    }
}
