package org.infa;

import java.util.List;

public class MysqlConnection extends BaseConnection {
    protected String driverClassName;
    protected String url;
    protected String dbName;
    private List<String> tables;

    public String getDriverClassName() {
        return driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public List<String> getTables() {
        return tables;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTables(List<String> tables) {
        this.tables = tables;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
}
