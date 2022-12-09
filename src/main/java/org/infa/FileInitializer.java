package org.infa;

import org.infa.model.FileResponse;

import java.io.IOException;

public interface FileInitializer<T> {
    FileResponse<T> initialize(String path) throws IOException;
}
