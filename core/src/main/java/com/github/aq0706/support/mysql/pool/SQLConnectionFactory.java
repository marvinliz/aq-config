package com.github.aq0706.support.mysql.pool;

import com.github.aq0706.support.mysql.SQLConfig;
import com.mysql.cj.conf.PropertyKey;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author lidq
 */
public class SQLConnectionFactory {

    private final SQLConfig config;
    private final Properties driverProperties;

    private Driver driver;

    public SQLConnectionFactory(final SQLConfig config) {
        this.config = config;
        this.driverProperties = new Properties();
        this.driverProperties.setProperty(PropertyKey.USER.getKeyName(), config.username);
        this.driverProperties.setProperty(PropertyKey.PASSWORD.getKeyName(), config.password);
        this.driverProperties.setProperty(PropertyKey.serverTimezone.getKeyName(), "Asia/Shanghai");
        try {
            initDriver();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to init driver instance for jdbcUrl=" + config.jdbcUrl, e);
        }
        if (DriverManager.getLoginTimeout() == 0) {
            DriverManager.setLoginTimeout(1);
        }
    }

    public Connection create() {
        try {
            return driver.connect(config.jdbcUrl, driverProperties);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void initDriver() throws SQLException {
        if (driver == null) {
            if (config.driverClassName != null) {
                Class driverClass = null;
                try {
                    driverClass = Class.forName(config.driverClassName);
                } catch (ClassNotFoundException ignored) {
                }
                if (driverClass != null) {
                    try {
                        driver = (Driver) driverClass.newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (!driver.acceptsURL(config.jdbcUrl)) {
                        throw new RuntimeException("Driver " + config.driverClassName + " do not accept jdbcUrl=" + config.jdbcUrl);
                    }
                }
            }
        }

        if (driver == null) {
            driver = DriverManager.getDriver(config.jdbcUrl);
        }
    }
}
