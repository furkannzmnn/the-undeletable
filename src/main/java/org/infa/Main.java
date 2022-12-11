package org.infa;

import org.infa.connection.ConnectionFactory;
import org.infa.connection.DebeziumConnector;

import java.io.IOException;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        new ConnectionFactory(new DebeziumConnector())
                .connect("/Users/furkanozmen/Desktop/the-undeletable/src/main/java/org/infa/unDeletable.yml");
    }
}
