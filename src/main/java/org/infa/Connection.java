package org.infa;

public class Connection {
    private final MysqlConnection mysqlConnection;
    private final KafkaConnection kafkaConnection;

    public Connection() {
        this.mysqlConnection = null;
        this.kafkaConnection = null;
    }

    public Connection(MysqlConnection mysqlConnection, KafkaConnection kafkaConnection) {
        this.mysqlConnection = mysqlConnection;
        this.kafkaConnection = kafkaConnection;
    }

    public MysqlConnection getMysqlConnection() {
        return mysqlConnection;
    }

    public KafkaConnection getKafkaConnection() {
        return kafkaConnection;
    }
}
