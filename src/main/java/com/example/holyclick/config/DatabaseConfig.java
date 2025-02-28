package com.example.holyclick.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class DatabaseConfig {
    
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:test.db");
        
        // Configure SQLite connection properties
        Properties connectionProperties = new Properties();
        // Enable WAL mode for better concurrency
        connectionProperties.setProperty("journal_mode", "WAL");
        // Set busy timeout to 30 seconds
        connectionProperties.setProperty("busy_timeout", "30000");
        // Enable foreign keys
        connectionProperties.setProperty("foreign_keys", "true");
        
        dataSource.setConnectionProperties(connectionProperties);
        
        return dataSource;
    }
} 