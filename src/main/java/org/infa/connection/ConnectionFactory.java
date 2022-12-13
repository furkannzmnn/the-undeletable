package org.infa.connection;

import org.infa.ChanEventStringRunnable;
import org.infa.Connection;
import org.infa.FileInitializer;
import org.infa.RunnableScript;
import org.infa.exception.FileOpenException;
import org.infa.model.DebeziumStarter;
import org.infa.model.FileResponse;
import org.infa.util.LogUtil;

import java.util.Optional;

public final class ConnectionFactory {
    private RunnableScript<String, String> script;

    private final FileInitializer<Connection> fileInitializer;
    private final Connector<DebeziumStarter, Connection> debeziumConnector;

    public ConnectionFactory(FileInitializer<Connection> fileInitializer, Connector<DebeziumStarter, Connection> debeziumConnector) {
        this.fileInitializer = fileInitializer;
        this.debeziumConnector = debeziumConnector;
    }

    public ConnectionFactory withCustomScript(RunnableScript<String, String> script) {
        this.script = script;
        return this;
    }

    public void connect(String pathYml) {
        final Connection connection = getConnectionConfig(pathYml);
        final DebeziumStarter connected = debeziumConnector.connect(connection, script);
        LogUtil.log(LogUtil.INFO, () -> "Connected to debezium engine" + connected);
    }


    private Connection getConnectionConfig(String pathYml) {
        final FileResponse<Connection> connectionFileResponse = fileInitializer.initialize(pathYml);
        if (connectionFileResponse.hasError()) {
            throw new FileOpenException(connectionFileResponse.message());
        }

        return connectionFileResponse.data();
    }
}