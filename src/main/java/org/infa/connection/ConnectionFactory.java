package org.infa.connection;

import org.infa.Connection;
import org.infa.FileInitializer;
import org.infa.exception.FileOpenException;
import org.infa.model.DebeziumSignal;
import org.infa.model.FileResponse;

public final class ConnectionFactory {

    private final FileInitializer<Connection> fileInitializer = new YmlInitializer();
    private final Connector<DebeziumSignal, Connection> debeziumConnector = new DebeziumConnector();

    public void connect(String pathYml) {
        final FileResponse<Connection> connectionFileResponse = fileInitializer.initialize(pathYml);
        if (connectionFileResponse.hasError()) {
            throw new FileOpenException(connectionFileResponse.message());
        }

        final Connection connection = connectionFileResponse.data();
        debeziumConnector.connect(connection);

    }
}