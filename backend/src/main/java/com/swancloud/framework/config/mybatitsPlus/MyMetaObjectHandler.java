/*
 * Copyright (c) 2022.  Asiacom Technology Inc. All rights reserved
 */

package com.swancloud.framework.config.mybatitsPlus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.swancloud.common.utils.SecurityUtils;

import com.swancloud.common.utils.SnowFlake;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * 自动填充的字段处理
 * 字段上方需要添加@TableField注解
 */
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "createBy", Long.class, SecurityUtils.getUserId());
        //有值，则写入
        if (metaObject.hasGetter("delFlag")) {
            this.setFieldValByName("delFlag", "0", metaObject);
        }
        if (metaObject.hasGetter("id")) {
            //如果已经配置id，则不再写入
            if (metaObject.getValue("id") == null) {
                this.setFieldValByName("id", String.valueOf(SnowFlake.getId()), metaObject);
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date()); // 起始版本 3.3.0(推荐)
        this.strictUpdateFill(metaObject, "updateBy", Long.class, SecurityUtils.getUserId());
    }
}
