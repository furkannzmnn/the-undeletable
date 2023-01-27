package org.infa;

import org.infa.connection.ConnectionFactory;
import org.infa.connection.DebeziumConnector;
import org.infa.util.LogUtil;

public class Main {
    public static void main(String[] args) {
        new ConnectionFactory()
                .defaultConnect("/Users/furkanozmen/Desktop/the-undeletable/src/main/java/org/infa/unDeletable.yml");
    }
}


class ExampleClient implements RunnableScript<String, String> {

    public static ExampleClient of() {
        return new ExampleClient();
    }

    @Override
    public void run(String key, String value) {
        Runnable runnable = () -> {
            // send to kafka
            LogUtil.log(LogUtil.INFO, () -> "Sending to kafka: " + key + " " + value);
        };
        new Thread(runnable).start();
    }
}