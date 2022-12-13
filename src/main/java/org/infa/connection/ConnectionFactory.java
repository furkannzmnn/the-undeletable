package org.infa.connection;

import org.infa.Connection;
import org.infa.FileInitializer;
import org.infa.RunnableScript;
import org.infa.exception.FileOpenException;
import org.infa.model.DebeziumStarter;
import org.infa.model.FileResponse;
import org.infa.util.LogUtil;

public final class ConnectionFactory {
    private final RunnableScript<String, String> script;

    private final FileInitializer<Connection> fileInitializer;
    private final Connector<DebeziumStarter, Connection> debeziumConnector;

    public ConnectionFactory(Builder builder) {
        this.fileInitializer = builder.fileInitializer;
        this.script = builder.script;
        this.debeziumConnector = builder.debeziumConnector;
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

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private RunnableScript<String, String> script;
        private FileInitializer<Connection> fileInitializer;
        private Connector<DebeziumStarter, Connection> debeziumConnector;

        private Builder() {
        }

        public Builder script(RunnableScript<String, String> script) {
            this.script = script;
            return this;
        }

        public Builder fileInitializer(FileInitializer<Connection> fileInitializer) {
            this.fileInitializer = fileInitializer;
            return this;
        }

        public Builder debeziumConnector(Connector<DebeziumStarter, Connection> debeziumConnector) {
            this.debeziumConnector = debeziumConnector;
            return this;
        }

        public ConnectionFactory build() {
           return new ConnectionFactory(this);
        }
    }
}