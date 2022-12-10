package org.infa;

import org.infa.model.FileResponse;

public interface FileInitializer<T> {
    FileResponse<T> initialize(String path);
}
