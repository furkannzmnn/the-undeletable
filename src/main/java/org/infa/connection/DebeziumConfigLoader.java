package org.infa.connection;

import io.debezium.config.Configuration;
import org.infa.Connection;
import org.infa.MysqlConnection;

public final class DebeziumConfigLoader {

    private DebeziumConfigLoader() {
        throw new IllegalStateException("Config class");
    }

    public static Configuration load(Connection connection) {
        final MysqlConnection mysql = connection.getMysqlConnection();
        return Configuration.create()
                .with("name", "customer-mysql-connector")
                .with("connector.class", "io.debezium.connector.mysql.MySqlConnector")
                .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
                .with("offset.flush.interval.ms", "60000")
                .with("database.hostname", mysql.getHost())
                .with("database.port", mysql.getPort())
                .with("database.user", mysql.getUsername())
                .with("database.password", mysql.getPassword())
                .with("database.dbname", mysql.getDbName())
                .with("database.include.list", String.join(",", mysql.getTables()))
                .with("include.schema.changes", "true")
                .with("database.server.id", "10181")
                .with("database.server.name", "customer-mysql-db-server")
                .with("database.history",
                        "io.debezium.relational.history.FileDatabaseHistory")
                .with("database.history.file.filename",
                        "/tmp/dbhistory.dat")
                .with("database.whitelist", "mysql.*")
                .with("offset.storage.file.filename", "/tmp/offsets.dat")
                .with("table.whitelist", "mysql.*")
                .build();
    }
}
