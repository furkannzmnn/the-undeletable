package org.infa.connection;

import java.util.Optional;

public interface Connector<T, R> {
    T connect(R data, Optional<Runnable> script);

    default void connect(R data, Runnable script) {
        connect(data, script);
    }
}
