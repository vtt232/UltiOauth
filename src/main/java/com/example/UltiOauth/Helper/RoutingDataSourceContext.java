package com.example.UltiOauth.Helper;

public class RoutingDataSourceContext implements AutoCloseable {

    // Holds data source key in thread local:
    static final ThreadLocal<String> threadLocalDataSourceKey = new ThreadLocal<>();

    // Static method to get the current data source routing key
    public static String getDataSourceRoutingKey() {
        String key = threadLocalDataSourceKey.get();
        return key == null ? "masterDataSource" : key;
    }

    // Constructor to set the data source routing key
    public RoutingDataSourceContext(String key) {
        threadLocalDataSourceKey.set(key);
    }

    // Close method to remove the data source key
    public void close() {
        threadLocalDataSourceKey.remove();
    }

}
