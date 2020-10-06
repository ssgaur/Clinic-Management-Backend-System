package com.ectosense.nightowl.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Slf4j
@Configuration
/**
 * This contains configuration for the datasource. Under the local profile, we load a default config. Otherwise, we read
 * the config from a YAML file in an S3 bucket.
 */
public class DatasourceConfig
{
    private static final String CONNECTION = "jdbc:postgresql://%s:%s/%s";
    DriverManagerDataSource dataSource;

    /**
     * DataSource for sandbox deploy that uses an installed postgres.
     */
    @Bean
    @Profile("local")
    public DataSource dataSourceLocal()
    {
        dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");

        String connectionUrl = String.format(CONNECTION, "localhost", 5432, "nightowl");
        log.info("Setting connection to " + connectionUrl);
        dataSource.setUrl(connectionUrl);
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");

        return dataSource;
    }

    @Bean
    @Profile("!local")
    public DataSource dataSource()
    {
        String hostName = "ec2-54-155-22-153.eu-west-1.compute.amazonaws.com";
        String port = "5432";
        String dbName = "dvn8dgrni62lq";
        String userName = "qglaeelsahazbs";
        String password = "6ecc788dc5b6532807d6e7eb67a31a7df9db1e731b8dce347b446a21609b85cd";
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");

        String connectionUrl = String.format(CONNECTION, hostName, port, dbName);
        log.info("Setting connection to " + connectionUrl);
        dataSource.setUrl(connectionUrl);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);

        return dataSource;
    }
}
