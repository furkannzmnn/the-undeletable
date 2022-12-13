package org.infa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.infa.exception.FileOpenException;
import org.infa.model.FileResponse;
import org.infa.util.StringUtils;

import java.io.File;
import java.util.logging.Logger;

public final class YmlInitializer implements FileInitializer<Connection> {
    private static final Logger LOGGER = Logger.getLogger(YmlInitializer.class.getName());

    @Override
    public FileResponse<Connection> initialize(String path) {
        LOGGER.info("reading to yml file");

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        mapper.findAndRegisterModules();

        final Connection connection;
        try {
            connection = mapper.readValue(new File(path), Connection.class);
        } catch (Exception e) {
            return new FileResponse<>(new Connection(), true, new FileOpenException(e.getMessage()).getMessage());
        }

        LOGGER.info("connection: " + connection.getKafkaConnection().getTopic());

        return new FileResponse<>(connection, false, StringUtils.EMPTY);
    }
}
