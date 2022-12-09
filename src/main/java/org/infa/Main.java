package org.infa;

import org.infa.connection.ConnectionFactory;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        new ConnectionFactory().connect("/Users/furkanozmen/Desktop/the-undeletable/src/main/java/org/infa/unDeletable.yml");
    }
}
