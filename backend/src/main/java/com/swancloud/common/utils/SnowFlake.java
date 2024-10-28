package com.swancloud.common.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

import java.util.Date;

/**
 * 雪花分布式id获取
 *
 * @author Hades
 */
public class SnowFlake {

    private static Snowflake snowflake;

    /**
     * 初始化配置
     *
     * @param workerId
     * @param datacenterId
     */
    public static void initialize(long workerId, long datacenterId) {
        snowflake = IdUtil.getSnowflake(workerId, datacenterId);
    }

    public static long getId() {
        return snowflake.nextId();
    }

    /**
     * 生成字符，带有前缀
     *
     * @param prefix
     * @return
     */
    public static String createStr(String prefix) {
        return prefix + DateUtils.dateTime(new Date()) + SnowFlake.getId();
    }

    public static String getIdStr() {
        return String.valueOf(snowflake.nextId());
    }
}
