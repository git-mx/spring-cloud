package com.shyfay.order.utils;

import java.util.Random;
import java.util.UUID;

/**
 * Created by mx
 * 2019-03-23 16:57
 */
public class KeyUtil {

    /**
     * 生成唯一的主键
     * 格式: 时间+随机数
     */
    public static synchronized String genUniqueKey() {
//        Random random = new Random();
//        Integer number = random.nextInt(900000) + 100000;
//
//        return System.currentTimeMillis() + String.valueOf(number);
        String id = UUID.randomUUID().toString();
        return id;
    }
}
