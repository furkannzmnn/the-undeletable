package org.infa;

import org.infa.connection.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // bu uygulamayı 9093 portunda çalıştır


        Executors.newCachedThreadPool()
                .submit(() ->
                        new ConnectionFactory()
                        .connect("/Users/furkanozmen/Desktop/the-undeletable/src/main/java/org/infa/unDeletable.yml"));


        Thread.sleep(3000);
    }
}
