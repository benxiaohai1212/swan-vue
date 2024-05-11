package com.swancloud.framework.config;


import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * @author 张立强
 * @version jdk1.8.0
 */
@Configuration
public class FlywayConfig {

    private static final Logger logger = LoggerFactory.getLogger(FlywayConfig.class);

    @Value("${spring.flyway.enabled}")
    private boolean enabled;

    @Value("${spring.flyway.baseline-on-migrate}")
    private boolean baselineOnMigrate;

    @Value("${spring.flyway.clean-on-validation-error}")
    private boolean cleanOnValidationError;

    @Value("${spring.flyway.out-of-order}")
    private boolean outOfOrder;

    @Value("${spring.flyway.clean-disabled}")
    private boolean cleanDisabled;

    @Value(("${spring.flyway.encoding}"))
    private String encoding;

    @Value("${spring.flyway.locations}")
    private String locations;

    @Value("${spring.flyway.ignore-ignored-migrations}")
    private boolean ignoreIgnoredMigrations;

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void flyway() {
        if (enabled) {
            Flyway flyway = Flyway.configure()
                    .baselineOnMigrate(baselineOnMigrate)
                    .locations(locations)
                    .cleanOnValidationError(cleanOnValidationError)
                    .encoding(encoding)
                    .outOfOrder(outOfOrder)
                    .cleanDisabled(cleanDisabled)
                    .dataSource(dataSource)
                    .ignoreIgnoredMigrations(ignoreIgnoredMigrations)
                    .load();
            try {
                flyway.migrate();
            } catch (FlywayException e) {
                logger.error("Flyway配置加载出错", e);
                e.printStackTrace();
//				try {
//					flyway.repair(); //生成版本记录表
//					logger.info("Flyway配置修复成功");
//					flyway.migrate();
//					logger.info("Flyway配置重新加载成功");
//				} catch (Exception e1) {
//					logger.error("Flyway配置第二次加载出错", e1);
//					throw e1;
//				}
            }
        }
    }
}
