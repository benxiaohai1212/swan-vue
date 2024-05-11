/*
 * Copyright (c) 2022.  Asiacom Technology Inc. All rights reserved
 */

package com.swancloud.framework.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.swancloud.common.utils.DateUtils;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
public class DataSourceHelper {

    private Logger logger = LoggerFactory.getLogger(DataSourceHelper.class);

    @Value("${spring.datasource.driverClassName}")
    private String driver; // com.mysql.cj.jdbc.Driver
    @Value("${spring.datasource.druid.master.url}")
    private String url; // jdbc:mysql://localhost:3306/pybbs?useSSL=false&characterEncoding=utf8
    @Value("${spring.datasource.druid.master.username}")
    private String username; // root
    @Value("${spring.datasource.druid.master.password}")
    private String password; // password

    @Value("${spring.profiles.active}")
    private String env;


    @PostConstruct
    public void init() {

        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName(driver);
            URI uri = new URI(url.replace("jdbc:", ""));
            String host = uri.getHost();
            int port = uri.getPort();
            String path = uri.getPath();
            String database = path.replace("/", "");
            String fileName = database + "_" + DateUtils.dateTimeNow() + ".sql";
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "?useSSL=false&useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai", username, password);
            statement = connection.createStatement();
            int i = statement.executeUpdate("CREATE DATABASE IF NOT EXISTS `" + database + "` DEFAULT CHARACTER SET = `utf8mb4` COLLATE `utf8mb4_general_ci`;");
            // 设置时区+8
            statement.executeUpdate("set global time_zone = '+8:00';");
            // 设置sql_mode，解决sql_mode=only_full_group_by异常
            statement.executeUpdate("set @@global.sql_mode ='STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';");
        } catch (URISyntaxException | ClassNotFoundException | SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
    }

}