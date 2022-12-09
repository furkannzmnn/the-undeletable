package org.infa.connection;

import org.infa.Connection;
import org.infa.FileInitializer;
import org.infa.model.FileResponse;

import java.io.IOException;

public final class ConnectionFactory {

    private final FileInitializer<Connection> fileInitializer = new YmlInitializer();

    public void connect(String pathYml) throws IOException {
        final FileResponse<Connection> connectionFileResponse = fileInitializer.initialize(pathYml);

    }
}