package org.infa.connection;

import org.infa.ChanEventStringRunnable;
import org.infa.RunnableScript;

import java.util.Optional;

public interface Connector<T, R> {
    T connect(R data, RunnableScript<String, String> script);

    default void connect(R data, RunnableScript<String, String> script, RunnableScript<String, String> script2) {
        connect(data, new ChanEventStringRunnable());
    }
}
