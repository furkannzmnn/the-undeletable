package org.infa.connection;

public interface Connector<T, R> {
    T connect(R data);
}
