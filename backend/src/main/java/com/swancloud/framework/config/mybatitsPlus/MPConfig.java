/*
 * Copyright (c) 2022.  Asiacom Technology Inc. All rights reserved
 */

package com.swancloud.framework.config.mybatitsPlus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis plus 配置类
 */
@Configuration
public class MPConfig {

    /**
     * 自动填充字段bean
     *
     * @return
     */
    @Bean
    public MyMetaObjectHandler myMetaObjectHandler() {
        return new MyMetaObjectHandler();
    }
}
