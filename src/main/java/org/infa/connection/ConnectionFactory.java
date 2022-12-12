package org.infa.connection;

import org.infa.Connection;
import org.infa.FileInitializer;
import org.infa.exception.FileOpenException;
import org.infa.model.DebeziumStarter;
import org.infa.model.FileResponse;
import org.infa.util.LogUtil;

import java.util.Optional;

public final class ConnectionFactory {

    private final FileInitializer<Connection> fileInitializer;
    private final Connector<DebeziumStarter, Connection> debeziumConnector;

    public ConnectionFactory(FileInitializer<Connection> fileInitializer, Connector<DebeziumStarter, Connection> debeziumConnector) {
        this.fileInitializer = fileInitializer;
        this.debeziumConnector = debeziumConnector;
    }

    public void connect(String pathYml) {
        final Connection connection = getConnectionConfig(pathYml);
        final DebeziumStarter connected = debeziumConnector.connect(connection, Optional.empty());
        LogUtil.log(LogUtil.INFO, () -> "Connected to debezium engine" + connected);
    }

    public void connect(String pathYml, Runnable script) {
        final Connection connection = getConnectionConfig(pathYml);
        debeziumConnector.connect(connection, script);
        LogUtil.log(LogUtil.INFO, () -> "Connected to debezium engine" + connection.toString());
    }

    private Connection getConnectionConfig(String pathYml) {
        final FileResponse<Connection> connectionFileResponse = fileInitializer.initialize(pathYml);
        if (connectionFileResponse.hasError()) {
            throw new FileOpenException(connectionFileResponse.message());
        }

        return connectionFileResponse.data();
    }
}