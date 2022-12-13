package org.infa;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.infa.exception.FileOpenException;
import org.infa.model.FileResponse;
import org.infa.util.StringUtils;

import java.io.File;

public class JsonInitializer implements FileInitializer<Connection> {
    @Override
    public FileResponse<Connection> initialize(String path) {
        ObjectMapper objectMapper = new ObjectMapper(new JsonFactory());

        Connection connection;
        try {
            connection = objectMapper.readValue(new File(path), Connection.class);
        } catch (Exception e) {
            return new FileResponse<>(new Connection(), true, new FileOpenException(e.getMessage()).getMessage());
        }

        return new FileResponse<>(connection, false, StringUtils.EMPTY);
    }
}
