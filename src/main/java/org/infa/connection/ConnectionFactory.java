package org.infa.connection;

import org.infa.Connection;
import org.infa.FileInitializer;
import org.infa.exception.FileOpenException;
import org.infa.model.DebeziumStarter;
import org.infa.model.FileResponse;
import org.infa.util.LogUtil;

import java.util.Optional;

public final class ConnectionFactory {

    private final FileInitializer<Connection> fileInitializer = new YmlInitializer();
    private final Connector<DebeziumStarter, Connection> debeziumConnector;

    public ConnectionFactory(Connector<DebeziumStarter, Connection> debeziumConnector) {
        this.debeziumConnector = debeziumConnector;
    }

    public void connect(String pathYml) {
        final Connection connection = getConnection(pathYml);
        final DebeziumStarter connected = debeziumConnector.connect(connection, Optional.empty());
        LogUtil.log(LogUtil.INFO, () -> "Connected to debezium engine" + connected);
    }

    public void connect(String pathYml, Runnable script) {
        final Connection connection = getConnection(pathYml);
        debeziumConnector.connect(connection, script);
    }

    private Connection getConnection(String pathYml) {
        final FileResponse<Connection> connectionFileResponse = fileInitializer.initialize(pathYml);
        if (connectionFileResponse.hasError()) {
            throw new FileOpenException(connectionFileResponse.message());
        }

        final Connection connection = connectionFileResponse.data();
        return connection;
    }
}