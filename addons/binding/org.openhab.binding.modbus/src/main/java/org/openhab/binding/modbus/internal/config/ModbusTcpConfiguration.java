package org.openhab.binding.modbus.internal.config;

/**
 *
 * @author Sami Salonen
 *
 */
public class ModbusTcpConfiguration {
    private String host;
    private int port;
    private int id;
    private int timeBetweenTransactionsMillis;
    private int timeBetweenReconnectMillis;
    private int connectMaxTries;
    private int reconnectAfterMillis;
    private int connectTimeoutMillis;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimeBetweenTransactionsMillis() {
        return timeBetweenTransactionsMillis;
    }

    public void setTimeBetweenTransactionsMillis(int timeBetweenTransactionsMillis) {
        this.timeBetweenTransactionsMillis = timeBetweenTransactionsMillis;
    }

    public int getTimeBetweenReconnectMillis() {
        return timeBetweenReconnectMillis;
    }

    public void setTimeBetweenReconnectMillis(int timeBetweenReconnectMillis) {
        this.timeBetweenReconnectMillis = timeBetweenReconnectMillis;
    }

    public int getConnectMaxTries() {
        return connectMaxTries;
    }

    public void setConnectMaxTries(int connectMaxTries) {
        this.connectMaxTries = connectMaxTries;
    }

    public int getReconnectAfterMillis() {
        return reconnectAfterMillis;
    }

    public void setReconnectAfterMillis(int reconnectAfterMillis) {
        this.reconnectAfterMillis = reconnectAfterMillis;
    }

    public int getConnectTimeoutMillis() {
        return connectTimeoutMillis;
    }

    public void setConnectTimeoutMillis(int connectTimeoutMillis) {
        this.connectTimeoutMillis = connectTimeoutMillis;
    }

}
