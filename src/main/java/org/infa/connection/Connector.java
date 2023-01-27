package org.infa.connection;

import org.infa.RunnableScript;

public interface Connector<T, R> {
    T connect(R data);
    T connectWithScript(R data, RunnableScript<String, String> script );
}
