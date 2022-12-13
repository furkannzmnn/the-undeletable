package org.infa;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.infa.util.LogUtil;

public final class ChanEventStringRunnable implements RunnableScript<String, String> {
    @Override
    public void run(String key, String value) {
        Runnable runnable = () -> {
            // send to kafka
            LogUtil.log(LogUtil.INFO, () -> "Sending to kafka: " + key + " " + value);
        };
        new Thread(runnable).start();
    }
}
